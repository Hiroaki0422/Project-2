package db;

/**
 * Created by tetsuji07 on 3/1/17.
 */
public class UnaryTest {
    public static void unaryTest(){
        Unary u = new Unary("u>9");
        System.out.println(u.evaluate("17"));
        Unary uv = new Unary("l<=3.7");
        System.out.println(uv.evaluate("2.5"));
        System.out.println(uv.evaluate("9"));
    }

    public static void main(String[] args){
        unaryTest();
    }
}
