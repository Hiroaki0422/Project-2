package db;

/**
 * Created by tetsuji07 on 2/25/17.
 */
public class DatabaseTest {
    public static void loadTest(){
        Database db = new Database();
        System.out.println(db.loadTable("t1"));
    }

    public static void main(String[] args){
        loadTest();
    }
}
