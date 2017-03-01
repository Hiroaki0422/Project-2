package db;

/**
 * Created by tetsuji07 on 2/28/17.
 */
public class BinaryTest {
    public static void binaryTest() throws Exception {
        Binary b = new Binary("x+y");
        System.out.println(b.getOperation());
        System.out.println(b.evaluate("7","5"));
        Binary c = new Binary("x-y");
        System.out.println(c.getLeft());
        System.out.println(c.evaluate("9","2"));
        Binary d = new Binary("x*yfddfd");
        System.out.println(d.getRight());
        System.out.println(d.evaluate("9.23","2"));
        Binary e = new Binary("Ab/de");
        System.out.println(e.getOperation());
        System.out.println(e.evaluate("3.0","2"));
        System.out.println(e.evaluate("3.0","0"));
    }

    public static void main(String[] args) throws  Exception{
        binaryTest();
    }
}
