package db;

import java.util.*;

/**
 * Created by tetsuji07 on 2/25/17.
 */
public class Table extends HashMap<String, Row> {
    private String tableName;
    private String[] colums;
    private String[] dataTypes;

    public Table () {
        this.tableName = null;
    }

    public Table(String[] colums){
        this.colums = colums;
        this.dataTypes = new String[colums.length];
    }

    public Table(String[] colums, String[] dataTypes){
        this.colums = colums;
        this.dataTypes = dataTypes;
    }

    public Table(Table tb){
        this.tableName = tb.tableName;
        this.colums = tb.colums;
        this.entrySet().addAll(tb.entrySet());
    }

    public Table(String tableName, String[] colums) {
        this.tableName = tableName;
        this.colums = colums;
        this.dataTypes = new String[colums.length];
    }

    public Table(Table tb1, Table tb2){
        join(tb1,tb2);
    }

    public int getColLength() {
        return  colums.length;
    }

    public int getRowLength() {
        return getColum(colums[0]).size();
    }

    public void addRow(String[] newRow){
        // You already have colums : String[]
        if (newRow == null){return;}

        if (newRow.length != colums.length){
            System.out.println("Error, row size does not match");
            return;
        }

        //Inserting First Row while checking the variable type
        TypeChecker check = new TypeChecker();
        if(size() == 0){
            for (int i = 0; i < colums.length; i++) {
                String type = check.typeCheck(newRow[i]);
                if (type.equals("Integer")){
                    put(colums[i], new Row(colums[i],"integer"));
                    this.dataTypes[i] = "integer";
                    get(colums[i]).add(newRow[i]);
                } else if (type.equals("Float")){
                    put(colums[i], new Row(colums[i], "float"));
                    this.dataTypes[i] = "float";
                    get(colums[i]).add(newRow[i]);
                } else {
                    put(colums[i], new Row(colums[i], "string"));
                    this.dataTypes[i] = "string";
                    get(colums[i]).add(newRow[i]);
                }
            }
            return;
        }

        //Otherwise
        for (int i = 0; i < colums.length; i++) {
            if(this.dataTypes[i] != check.typeCheck(newRow[i])){
                System.out.println("the types do not match");
            }
            get(colums[i]).add(newRow[i]);
        }

        return;
    }

    public Row getColum(String rowName){
        return get(rowName);
    }

    public String[] getRow(int rowNum){
        if (size() == 0 || rowNum > getColum(colums[0]).size()){
            System.out.println("Error, such row does not exist");
            return null;
        }
        String[] row = new String[colums.length];
        for (int i = 0; i < colums.length; i++){
            row[i] = getColum(colums[i]).get(rowNum).toString();
        }
        return row;
    }

    public String[] getRowWithColum(int rowNum, String[] colNames) {
        if (size() == 0 || rowNum > getColum(colums[0]).size()){
            System.out.println("Error, such row does not exist");
            return null;
        }
        String[] row = new String[colNames.length];
        for (int i = 0; i < colNames.length; i++){
            row[i] = getColum(colNames[i]).get(rowNum).toString();
        }
        return row;
    }

    public String[] getColums() {
        return this.colums;
    }

    public String[] getDataTypes() {return this.dataTypes; }

    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        for (int i = 0; i < getColLength(); i++) {
            sj.add(this.colums[i]+" "+this.dataTypes[i]);
        }
        String result = sj.toString();
        if (size() == 0){
            return result;
        }
        result = result + "\n";
        for (int i = 1; i <= get(colums[0]).size(); i++ ){
            sj = new StringJoiner(",");
            for(String str: colums) {
                if (get(str).getDataType() == "string"){
                    String bracketed = "'" + get(str).get(i)+ "'";
                    sj.add(bracketed);
                } else {
                    sj.add(get(str).get(i).toString());
                }
            }
            result = result + sj.toString() + "\n";
        }
        return result;
    }

    private void join(Table t1, Table t2){
        ArrayList<String> shared = new ArrayList<String>();
        for (String str : t1.getColums()) {
            if (t2.containsKey(str)){
                shared.add(str);
            }
        }
        ArrayList<String> newcols = new ArrayList<String>(shared);
        for (String str : t1.colums){
            if (!shared.contains(str)){
                newcols.add(str);
            }
        }
        for (String str : t2.colums){
            if (!shared.contains(str)){
                newcols.add(str);
            }
        }
        //Assign New Colums Name
        String newColums[] = new String[newcols.size()];
        newColums = newcols.toArray(newColums);
        this.colums = newColums;
        this.dataTypes = new String[this.colums.length];

        //Case 1 : the two tables do not have shared colum
        if (shared.isEmpty()){
            for (int i = 1; i <= t1.getRowLength(); i++){
                for (int j = 1; j<= t2.getRowLength(); j++){
                    String[] newRow = new String[this.getColLength()];
                    for (int k = 0; k < colums.length; k++){
                        if (t1.keySet().contains(colums[k])){
                            newRow[k] = t1.getColum(colums[k]).get(i).toString();
                        } else {
                            newRow[k] = t2.getColum(colums[k]).get(j).toString();
                        }
                    }
                    addRow(newRow);
                }
            }
        } else {
            //Converting shared to String[]
            String[] sh = new String[shared.size()];
            sh = shared.toArray(sh);
            //Check which row has same values
            for(int i = 1; i <=t1.getRowLength(); i++){
                for (int j = 1; j<=t2.getRowLength(); j++){
                    if(Arrays.equals(t1.getRowWithColum(i, sh),(t2.getRowWithColum(j,sh)))){
                        String[] newRow = new String[this.getColLength()];
                        //Inserting new Row with corresponding values from each tables
                        for (int k = 0; k < colums.length; k++){
                            if (t1.keySet().contains(colums[k])){
                                newRow[k] = t1.getColum(colums[k]).get(i).toString();
                            } else {
                                newRow[k] = t2.getColum(colums[k]).get(j).toString();
                            }
                        }
                        this.addRow(newRow);
                    }
                }
            }
        }
    }

    public void printTable() {
        if (size() == 0){
            System.out.println("The table is empty");
            return;
        }
        for (String str : colums) {
            System.out.print(str+" ");
        }
        System.out.println("");
        for (int i = 1; i <= get(colums[0]).size(); i++ ) {
            for(String str: colums) {
                System.out.print(get(str).get(i) + " ");
            }
            System.out.println(" ");
        }
    }

}
