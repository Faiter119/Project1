package utils;

import java.util.stream.IntStream;

/**
 * Created by OlavH on 31-Mar-17.
 */
public class ArrayUtil {

    public static Integer[] toArray(int ... ints){


        return IntStream.of(ints).mapToObj(Integer::valueOf).toArray(Integer[]::new);

    }

}
