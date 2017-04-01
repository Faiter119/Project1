package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlavH on 31-Mar-17.
 */
public class ListUtil {

    public static <T> List<T> toList(T ... elements){

        List<T> list = new ArrayList<T>();

        for (T t : elements) {
            System.out.println(t);
            list.add(t);
        }

        return list;

    }
    public static List<Number> toList(int ... elements){

        List<Number> list = new ArrayList<>();

        for (Number t : elements) {
            System.out.println(t);
            list.add(t);
        }

        return list;

    }

}
