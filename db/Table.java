package db;

import java.util.*;

/**
 * Created by tetsuji07 on 2/25/17.
 */
public class Table extends HashMap<String, Row> {
    private String tableName;
    private String[] colums;

    public Table () {
        this.tableName = null;
    }

    public Table(String tableName, String[] colums) {
        this.tableName = tableName;
        this.colums = colums;
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
        if(size() == 0){
            TypeChecker check = new TypeChecker();
            for (int i = 0; i < colums.length; i++) {
                String type = check.typeCheck(newRow[i]);
                if (type.equals("Integer")){
                    put(colums[i], new Row<Integer>(colums[i],"integer"));
                    get(colums[i]).add(newRow[i]);
                } else if (type.equals("Float")){
                    put(colums[i], new Row<Float>(colums[i], "float"));
                    get(colums[i]).add(newRow[i]);
                } else {
                    put(colums[i], new Row<String>(colums[i], "string"));
                    get(colums[i]).add(newRow[i]);
                }
            }
            return;
        }

        //Otherwise
        for (int i = 0; i < colums.length; i++) {
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

    public String[] getSharedRow(int rowNum, String[] colNames) {
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
        return colums;
    }

    public String toString() {
        if (size() == 0){
            return" ";
        }
        StringJoiner sj = new StringJoiner(",");
        for (String clm : colums) {
            sj.add(clm+" "+get(clm).getDataType());
        }
        String result = sj.toString();
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

    public void join(Table t1, Table t2){
        ArrayList<String> shared = new ArrayList<String>();
        for (String str : t1.getColums()) {
            if (t2.containsKey(str)){
                shared.add(str);
            }
        }
        //Case 1 : the two tables do not have shared colum
        if (shared.isEmpty()){
            System.out.println("Let's not worry for now, also you're in wrong field");
        } else {
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
            //Converting shared to String[]
            String[] sh = new String[shared.size()];
            sh = shared.toArray(sh);
            //Check which row has same values
            for(int i = 1; i <=t1.getRowLength(); i++){
                for (int j = 1; j<=t2.getRowLength(); j++){
                    if(Arrays.equals(t1.getSharedRow(i, sh),(t2.getSharedRow(j,sh)))){
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
    public static void main(String[] args){
        Table t1 = new Table("t1",new String[]{"X", "Y","Z","W"});
        t1.addRow(new String[]{"1","7","2","10"});
        t1.addRow(new String[]{"1","7","4","1"});
        t1.addRow(new String[]{"1","9","9","1"});
        t1.get("X").printRow();
        t1.get("Y").printRow();
        t1.get("Z").printRow();
        t1.printTable();
        System.out.println("");
        System.out.println(t1.toString());
        Table t2 = new Table("t1",new String[]{"W", "B", "Z"});
        t2.addRow(new String[]{"1","7","4"});
        t2.addRow(new String[]{"7","7","3"});
        t2.addRow(new String[]{"1","9","6"});
        t2.addRow(new String[]{"1","11","9"});
        Table t3 = new Table(t1, t2);
        System.out.print(t3.toString());

    }
}
