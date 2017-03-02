package db;

/**
 * Created by tetsuji07 on 2/28/17.
 */
public class Binary {
    //private static final String[] OPERATIONS = new String[]{"+","-","*","/"};
    private String left;
    private String right;
    private String operation;
    private boolean doOperation = false;
    private static final String NaN = "NaN";
    private String type;

    Binary(){
        this.doOperation = false;
    }

    Binary(String expr){
        this.doOperation = true;
        String[] pair = new String[2];
        if (expr.contains("+")){
            this.operation = "+";
            pair = expr.split("\\+");
        } else if (expr.contains("-")){
            this.operation = "-";
            pair = expr.split("-");
        } else if (expr.contains("*")){
            this.operation = "*";
            pair = expr.split("\\*");
        } else if (expr.contains("/")){
            this.operation = "/";
            pair = expr.split("/");
        }

        this.left = pair[0];
        this.right = pair[1];
    }

    public String getOperation() {
        return this.operation;
    }

     public boolean needOperation(){
        return doOperation;
     }

     public String getLeft(){
         return this.left;
     }

     public String getRight(){
         return this.right;
     }

    public String evaluate(String left, String right) throws Exception {
         TypeChecker check = new TypeChecker();
         this.type = evaluateReturnType(check.typeCheck(left), check.typeCheck(right));

         if (this.operation.equals("+")){
             return addOperation(left, right);
         } else if (this.operation.equals("-")){
             return minusOperation(left, right);
         } else if (this.operation.equals("*")) {
             return multipleOperation(left, right);
         } else {
             return divideOperation(left, right);
         }

    }

    public String evaluateReturnType(String a, String b) throws Exception {
        if (a.equals("String") && b.equals("String")) {
            return "string";

        } else if (a.equals("String") || b.equals("String")) {
            System.out.println("Error, You can add String to Integer or Float");
            throw new Exception("Error adding string with numeric");

        } else if (a.equals("Float") || b.equals("Float")) {
            return "float";

        } else {
            return "int";

        }
    }

    private String addOperation(String left, String right){
         if (this.type.equals("string")) {
             return left + right;
         } else if (this.type.equals("float")){
             Float lFloat = Float.parseFloat(left);
             Float rFloat = Float.parseFloat(right);
             Float sum = lFloat + rFloat;
             return sum.toString();
         } else {
             Integer lInt = Integer.parseInt(left);
             Integer rInt = Integer.parseInt(right);
             Integer sum = rInt + lInt;
             return sum.toString();
         }
    }

    private String minusOperation(String left, String right){
        if (this.type.equals("float")){
            Float lFloat = Float.parseFloat(left);
            Float rFloat = Float.parseFloat(right);
            Float minus = lFloat - rFloat;
            return minus.toString();
        } else {
            Integer lInt = Integer.parseInt(left);
            Integer rInt = Integer.parseInt(right);
            Integer minus = lInt - rInt;
            return minus.toString();
        }
    }

    private String multipleOperation(String left, String right){
        if (this.type.equals("float")){
            Float lFloat = Float.parseFloat(left);
            Float rFloat = Float.parseFloat(right);
            Float mult = lFloat * rFloat;
            return mult.toString();
        } else {
            Integer lInt = Integer.parseInt(left);
            Integer rInt = Integer.parseInt(right);
            Integer mult = rInt * lInt;
            return mult.toString();
        }
    }

    private String divideOperation(String left, String right){
        Float isZero = Float.parseFloat(right);
        if(isZero == 0.0){
            return NaN;
        } else if (this.type.equals("float")){
            Float lFloat = Float.parseFloat(left);
            Float rFloat = Float.parseFloat(right);
            Float div = lFloat / rFloat;
            return div.toString();
        } else {
            Integer lInt = Integer.parseInt(left);
            Integer rInt = Integer.parseInt(right);
            Integer div = lInt / rInt;
            return div.toString();
        }
    }

}
