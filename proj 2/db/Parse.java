package db;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringJoiner;

public class Parse {

    private String rawCode;
    private String[] transacted;

    public Parse(String query){
        this.rawCode = query;
        this.transacted = null;
        eval();
    }


    // Various common constructs, simplifies parsing.
    private static final String REST  = "\\s*(.*)\\s*",
                                COMMA = "\\s*,\\s*",
                                AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
                                 LOAD_CMD   = Pattern.compile("load " + REST),
                                 STORE_CMD  = Pattern.compile("store " + REST),
                                 DROP_CMD   = Pattern.compile("drop table " + REST),
                                 INSERT_CMD = Pattern.compile("insert into " + REST),
                                 PRINT_CMD  = Pattern.compile("print " + REST),
                                 SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\((\\S+\\s+\\S+\\s*" +
                                               "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
                                 SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                                               "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                                               "([\\w\\s+\\-*/'<>=!]+?(?:\\s+and\\s+" +
                                               "[\\w\\s+\\-*/'<>=!]+?)*))?"),
                                 CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                                                   SELECT_CLS.pattern()),
                                 INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                                               "\\s*(?:,\\s*.+?\\s*)*)");

/**
    public static void main(String[] args) {

        for (String st : args) {
            System.out.print("1 :");
            System.out.println(st);
        }

        if (args.length != 1) {
            System.err.println("Expected a single query argument");
            return;
        }

        eval(args[0]);
    }*/

    private void eval() {
        Matcher m;
        if ((m = CREATE_CMD.matcher(rawCode)).matches()) {
             createTable(m.group(1));
        } else if ((m = LOAD_CMD.matcher(rawCode)).matches()) {
             loadTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(rawCode)).matches()) {
             storeTable(m.group(1));
        } else if ((m = DROP_CMD.matcher(rawCode)).matches()) {
             dropTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(rawCode)).matches()) {
             insertRow(m.group(1));
        } else if ((m = PRINT_CMD.matcher(rawCode)).matches()) {
             printTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(rawCode)).matches()) {
             select(m.group(1));
        } else {
            System.err.printf("Malformed query: %s\n", rawCode);
        }
    }

    private void createTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            createNewTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {
            System.err.printf("Malformed create: %s\n", expr);
        }
    }

    private void createNewTable(String name, String[] cols) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < cols.length-1; i++) {
            joiner.add(cols[i]);
        }

        String colSentence = joiner.toString() + " and " + cols[cols.length-1];
        System.out.printf("You are trying to create a table named %s with the columns %s\n", name, colSentence);
        this.transacted = new String[]{"createNewTable", name, colSentence};
    }

    private void createSelectedTable(String name, String exprs, String tables, String conds) {
        System.out.printf("You are trying to create a table named %s by selecting these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", name, exprs, tables, conds);
        this.transacted = new String[]{"createSelectedTable", name, exprs, tables, conds};
    }

    private void loadTable(String name) {
        System.out.printf("You are trying to load the table named %s\n", name);
       this.transacted = new String[]{"loadTable", name};
    }

    private void storeTable(String name) {
        System.out.printf("You are trying to store the table named %s\n", name);
        this.transacted = new String[] {"storeTable", name};

    }

    private void dropTable(String name) {
        System.out.printf("You are trying to drop the table named %s\n", name);
        this.transacted = new String[] {"dropTable", name};
    }

    private static void insertRow(String expr) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return;
        }

        System.out.printf("You are trying to insert the row \"%s\" into the table %s\n", m.group(2), m.group(1));
    }

    private void printTable(String name) {
        System.out.printf("You are trying to print the table named %s\n", name);
        this.transacted = new String[]{"printTable",name};
    }

    private void select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return;
        }

        select(m.group(1), m.group(2), m.group(3));
    }

    private void select(String exprs, String tables, String conds) {
        System.out.printf("You are trying to select these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", exprs, tables, conds);
        this.transacted = new String[]{"select",exprs,tables,conds};
    }

    public String[] getTransacted(){
        return this.transacted;
    }
}
