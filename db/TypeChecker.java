package db;

/**
 * Created by tetsuji07 on 2/25/17.
 */
//Citation for those boolean codes : http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
public class TypeChecker {

    public TypeChecker(){}
    public String typeCheck(String input){
        if(input == null ){
            System.out.println("Error null Argument Class: TypeChecker -> typeCheck");
            return "";
        } else if (isInteger(input)){
            return "Integer";
        } else if (isFloat(input)) {
            return "Float";
        } else {
            return "String";
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    private static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
    public static void main(String[] args){
        TypeChecker check = new TypeChecker();
        System.out.println(check.typeCheck("hfwoafu"));
    }*/

}
