package db;

/**
 * Created by tetsuji07 on 2/25/17.
 */
public class DatabaseTest {

    public static void loadTest(){
        Database db = new Database();
    }

    public static void printTest(){
        Database db = new Database();
        db.loadTable("t1");
        db.loadTable(("t2"));
        System.out.println(db.printTable("t1"));
        System.out.println(db.printTable("t2"));
    }

    public static void main(String[] args){
        loadTest();
        printTest();
    }
}
