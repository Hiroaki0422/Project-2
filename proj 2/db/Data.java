package db;

/**
 * Created by tetsuji07 on 2/25/17.
 */
public class Data<T> {
    private T value;

    public Data(T value){
        this.value = value;
    }

    public T getValue(){
        return this.value;
    }

}
