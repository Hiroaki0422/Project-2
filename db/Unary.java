package db;

/**
 * Created by tetsuji07 on 2/28/17.
 */
public class Unary {
    private static final String[] OPERATIONS = {"==", "!=", "<", ">", "<=", ">="};
    private String columName;
    private String operation;
    private String literal;
    private String type;

    public Unary(String expression){
        checkOperation(expression);
    }

    private void checkOperation(String inputStr)
    {
        TypeChecker check = new TypeChecker();
        for(int i =0; i < OPERATIONS.length; i++)
        {
            if(inputStr.contains(OPERATIONS[i]))
            {
                this.operation = OPERATIONS[i];
                String[] pair = inputStr.split(operation);
                this.columName = pair[0];
                this.literal = pair[1];
                this.type = check.typeCheck(this.literal);
            }
        }
    }

    public String getColumName(){
        return columName;
    }

    public boolean evaluate(String subject){
        if (operation.equals("==")){
            return equalOperation(subject);
        } else if (operation.equals("!=")){
            return unequalOperation(subject);
        } else if (operation.equals("<")){
            return smallerOperation(subject);
        } else if (operation.equals(">")) {
            return greaterOperation(subject);
        } else if (operation.equals("<=")){
            return smallerEqual(subject);
        } else {
            return greaterEqual(subject);
        }
    }

    private  boolean equalOperation(String subject){
        return (subject.compareTo(literal)==0);
    }

    private  boolean unequalOperation(String subject){
        return (subject.compareTo(literal)!=0);
    }

    private boolean smallerOperation(String subject){
        if (this.type.equals("String")) {
            return (subject.compareTo(literal) < 0);
        } else if (this.type.equals("Float")) {
            Float subj = Float.parseFloat(subject);
            Float lit = Float.parseFloat(this.literal);
            return subj < lit;
        } else {
            Integer subj = Integer.parseInt(subject);
            Integer lit = Integer.parseInt(this.literal);
            return subj < lit;
        }
    }

    private boolean greaterOperation(String subject){
        if (this.type.equals("String")) {
            return (subject.compareTo(literal) > 0);
        } else if (this.type.equals("Float")) {
            Float subj = Float.parseFloat(subject);
            Float lit = Float.parseFloat(this.literal);
            return subj > lit;
        } else {
            Integer subj = Integer.parseInt(subject);
            Integer lit = Integer.parseInt(this.literal);
            return subj > lit;
        }
    }

    private boolean smallerEqual(String subject){
        if (this.type.equals("String")) {
            return (subject.compareTo(literal) <= 0);
        } else if (this.type.equals("Float")) {
            Float subj = Float.parseFloat(subject);
            Float lit = Float.parseFloat(this.literal);
            return subj <= lit;
        } else {
            Integer subj = Integer.parseInt(subject);
            Integer lit = Integer.parseInt(this.literal);
            return subj <= lit;
        }
    }

    private boolean greaterEqual(String subject){
        if (this.type.equals("String")) {
            return (subject.compareTo(literal) >= 0);
        } else if (this.type.equals("Float")) {
            Float subj = Float.parseFloat(subject);
            Float lit = Float.parseFloat(this.literal);
            return subj >= lit;
        } else {
            Integer subj = Integer.parseInt(subject);
            Integer lit = Integer.parseInt(this.literal);
            return subj >= lit;
        }
    }

}
