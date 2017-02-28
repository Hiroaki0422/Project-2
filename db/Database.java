package db;

import java.util.HashMap;
import edu.princeton.cs.introcs.In;

public class Database {
    private HashMap<String, Table> tables;

    public Database() {
        tables = new HashMap<String, Table>();
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
        return tables.get(tableName);
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
            Table newtb = new Table(name, newLine);
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

    public class Select{
        private final String[] unOperations = new String[]{">",">=","=","<=","<"};
        private final String[] 
        private Table selectedTable;
        private String[] selectedColums;
        private String filter;
    }


}
