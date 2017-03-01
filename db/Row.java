package db;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
/**
 * Created by tetsuji07 on 2/25/17.
 */
public class Row extends HashMap<Integer, String> {
    private String rawName;
    private String dataType;

    public Row(){
        this.rawName = null;
        this.dataType = null;
    }

    public Row(String rawName) {
        this.rawName = rawName;
        this.dataType = null;
    }

    public Row(String rawName, String dataType) {
        this.rawName = rawName;
        this.dataType = dataType;
    }

    public void setRawName(String rawName){
        this.rawName = rawName;
    }

    public void setDataType(String dataType){
        this.dataType = dataType;
    }

    public String getRawName(){
        return this.rawName;
    }

    public String getDataType(){
        return this.dataType;
    }

    public void add(String value){
        put(size()+1, value);
    }

    public void removeLast() {
        remove(size());
    }

    public void printRow(){
        System.out.println(this.rawName);
        System.out.println(this.dataType);
        /* Display content using Iterator*/
        Set set = entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        }
    }

/**
    public static void main(String[] args){
        Row<String> ro = new Row<String>();
        ro.setRawName("Row1");
        ro.setDataType("String");
        ro.add("b");
        ro.add("a");
        ro.add("c");
        ro.printRow();

        Row<Integer> ro2 = new Row<Integer>("Row2","Integer");
        ro2.add(1);
        ro2.add(2);
        ro2.add(3);
        ro2.printRow();

        //Test for table
        HashMap<String, Row> hss = new HashMap<String, Row>();
        hss.put(ro.getRawName(), ro);
        hss.put(ro2.getRawName(), ro2);
        hss.get("Row1").printRow();
        hss.get("Row2").printRow();

    }*/
}
