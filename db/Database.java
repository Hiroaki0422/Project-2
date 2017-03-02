package db;

import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.introcs.In;

public class Database {
    private HashMap<String, Table> tables;

    public Database() {
        this.tables = new HashMap<String, Table>();
    }

    public String transact(String query) {
        Parse parse = new Parse(query);
        String[] transacted = parse.getTransacted();
        return "YOUR CODE HERE";
    }

    public void addTable(String name, Table newtable){
        this.tables.put(name, newtable);
    }

    public Table getTable(String tableName){
        try {
            Table tb = this.tables.get(tableName);
            return tb;
        } catch (Exception e) {
            System.out.println("Error, that table does not exit");
            return null;
        }
    }

    public String printTable(String tableName){
        try{
            Table tb = getTable(tableName);
            return tb.toString();
        } catch (Exception e) {
            return "Error, such table does not exist";
        }
    }


    public String loadTable(String name){
        try {
            In in = new In(name + ".tbl");
            String line = in.readLine();
            String[] newLine = line.split(",");
            String[] newCols = new String[newLine.length];
            String[] dataTypes = new String[newLine.length];
            for (int i = 0; i < newLine.length; i++){
                String[] pair = newLine[i].split("\\s+");
                newCols[i] = pair[0];
                dataTypes[i] = pair[1];
            }
            Table newtb = new Table(newCols,dataTypes);
            while (in.hasNextLine()) {
                line = in.readLine();
                newLine = line.split(",");
                newtb.addRow(newLine);
            }
            addTable(name, newtb);
            return "";
        } catch (Exception e){
            return "Error, such table does not exist";
        }
    }

    public String select (String expr, String tbs, String filters) throws Exception {
        Select select = new Select(expr, tbs, filters);
        return select.toString();
    }

    public class Select{
        //private final String[] unOperations = new String[]{">",">=","=","<=","<"};
        private final String ALL = "*";
        private Table tb;
        private String[] selectedColums;
        private String[] selectedDataTypes;
        private ArrayList<Unary> unconds;
        private ArrayList<Binary> biconds;

        public Select(String expr, String tbs, String filters) throws Exception {
            this.biconds =  new ArrayList<Binary>();
            this.unconds = new ArrayList<Unary>();
            from(tbs);
            select(expr);
            where(filters);
        }

        private void from(String tbs){
            String selectedTables[] = tbs.split(",");
            Table holdTable = getTable(selectedTables[0]);
            for (int i = 1; i < selectedTables.length; i++) {
                Table jointed = new Table (holdTable, getTable(selectedTables[i]));
                holdTable = jointed;
            }
            this.tb = holdTable;
        }

        private void select(String expr) throws Exception {
            // if "*" don't modify the table at all
            if (expr == ALL){
                return;
            }
            //Split the expression by the number of arguments
            String[] exprs = expr.split(",");
            String[] selectedCols = new String[exprs.length];
            this.selectedDataTypes = new String[exprs.length];

            for (int i = 0; i < exprs.length; i++){
                if (exprs[i].contains("as")){ //Check if they have mathematical operations
                    String[] pair = exprs[i].split(" as ");
                    Binary bi = new Binary(pair[0]);
                    this.biconds.add(bi);
                    selectedCols[i] = pair[1];
                    String left = this.tb.getColum(bi.getLeft()).getDataType();
                    String right = this.tb.getColum(bi.getRight()).getDataType();
                    this.selectedDataTypes[i] = bi.evaluateReturnType(left, right);
                } else {
                    this.biconds.add(new Binary());
                    selectedCols[i] = exprs[i];
                    this.selectedDataTypes[i] = this.tb.getColum(selectedCols[i]).getDataType();
                }
            }
            this.selectedColums = selectedCols;
            applyBinary();
        }

        private void applyBinary() throws Exception {
            Table nTb = new Table(this.selectedColums,this.selectedDataTypes);
            for (int i = 1; i <= this.tb.getRowLength(); i++){
                String[] newRow = new String[this.selectedColums.length];
                for (int j = 0; j < this.selectedColums.length; j++){
                    Binary bi = this.biconds.get(j);
                    if (bi.needOperation()){
                        newRow[j] = bi.evaluate(this.tb.getColum(bi.getLeft()).get(i), tb.getColum(bi.getRight()).get(i));
                    } else {
                        newRow[j] = (this.tb.getColum(this.selectedColums[j]).get(i));
                    }
                }
                nTb.addRow(newRow);
            }
            this.tb = nTb;
            this.selectedDataTypes = this.tb.getDataTypes();
        }

        private void where(String expr) {
            if (expr == null){
                return;
            }
            String[] exprs = expr.split(",");
            for (String exp : exprs){
                this.unconds.add(new Unary(exp));
            }
            applyUnary();
        }

        private void applyUnary() {
            for (int k = 0; k < this.unconds.size(); k++ ) {
                Table nTb = new Table(this.selectedColums, this.selectedDataTypes);
                Unary cond = this.unconds.get(k);
                for (int i = 1; i <= this.tb.getRowLength(); i++){
                    String subject = this.tb.getColum(cond.getColumName()).get(i);
                    if(cond.evaluate(subject)){
                        nTb.addRow(this.tb.getRow(i));
                    }
                }

                this.tb = nTb;
            }
        }

        public void storeTable(String tableName) {
            addTable(tableName, this.tb);
        }

        public String toString(){
            return this.tb.toString();
        }

    }


}
