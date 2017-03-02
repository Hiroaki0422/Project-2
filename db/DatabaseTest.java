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

    public static void selectJoinTest(){
        Database db = new Database();
        db.loadTable("t1");
        db.loadTable("t2");
        db.loadTable("t4");
        db.getTable("t4").toString();
        //System.out.println(db.join("t2,t1,t4").toString());
    }

    public static void selectTest() throws Exception {
        Database db = new Database();
        db.loadTable("t1");
        db.loadTable("t2");
        db.loadTable("t4");
        System.out.println(db.getTable("t4").toString());
        //db.select("*","t2,t1,t4",null);
        //db.select("x,z", "t2,t1,t4",null);
        //db.select("x int+z int as T,x int-z int as Y", "t2,t1,t4",null);
        db.select("x+z as T,x-z as Y", "t2,t1,t4","T>9,Y<0");
    }

    public static void main(String[] args) throws Exception {
        //loadTest();
        //printTest();
        selectTest();
    }

}
