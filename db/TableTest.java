package db;

/**
 * Created by tetsuji07 on 2/27/17.
 */
public class TableTest {
    public static void joinTest(){
        Table t1 = new Table("t1",new String[]{"X", "Y","Z","W"});
        t1.addRow(new String[]{"1","7","2","10"});
        t1.addRow(new String[]{"1","7","4","1"});
        t1.addRow(new String[]{"1","9","9","1"});
        System.out.println("");
        System.out.println(t1.toString());
        Table t2 = new Table("t1",new String[]{"W", "B", "Z"});
        t2.addRow(new String[]{"1","7","4"});
        t2.addRow(new String[]{"7","7","3"});
        t2.addRow(new String[]{"1","9","6"});
        t2.addRow(new String[]{"1","11","9"});
        Table t3 = new Table(t1, t2);
        System.out.print(t3.toString());
        Table t4 = new Table("t4",new String[]{"X", "Y","Z"});
        t4.addRow(new String[]{"2","5","4"});
        t4.addRow(new String[]{"8","3","9"});
        Table t5 = new Table("t5",new String[]{"A", "B"});
        t5.addRow(new String[]{"7","0"});
        t5.addRow(new String[]{"2","8"});
        Table t6 = new Table(t4,t5);
        System.out.println(t6.toString());
    }
    public static void main(String[] args){
        joinTest();
    }
}
