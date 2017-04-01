package utils;

/**
 * Created by OlavH on 31-Mar-17.
 */
public class ArrayRange {

    public static int[] intRange(int startInc, int endInc){

        int[] out = new int[1+endInc-startInc];

        for (int i = startInc, index = 0; i <= endInc ; i++, index++) {

            out[index] = i;

        }
        return out;


    }

}
