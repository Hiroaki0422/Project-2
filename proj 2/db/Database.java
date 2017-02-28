package db;

import java.util.HashMap;
import edu.princeton.cs.introcs.In;

public class Database {
    HashMap<String, Table> tables;

    public Database() {
        this.tables = null;
    }

    public String transact(String query) {
        Parse parse = new Parse(query);
        String[] transacted = parse.getTransacted();
        /**
        for (String str : transacted){
            System.out.print(str+" ");
        }*/
        return "YOUR CODE HERE";
    }

    public String loadTable(String name){
        In in = new In(name+".tbl");
        String line = in.readLine();
        String[] newLine = line.split(",");
        Table newtb = new Table(name, newLine);

        while(in.hasNextLine()){
            line = in.readLine();
            newLine = line.split(",");
            newtb.addRow(newLine);
        }
        return newtb.toString();
    }


}
