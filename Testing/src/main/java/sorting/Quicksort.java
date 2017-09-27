package sorting;

import java.util.Arrays;

/**
 * Created by faiter on 8/16/17.
 */
public class Quicksort {


    private static void quicksort(int[] data, int left, int right){


        //System.out.println("Subsort: "+Arrays.toString(Arrays.copyOfRange(data, left, right)));

        int half = (right-left)/2;

        if (half > 2){


            int pos = left+half;
            System.out.println("left: "+left+" - right: "+right+" - pos: "+pos);

            //System.out.println("Copy: ");
            System.out.println("\t"+Arrays.toString(Arrays.copyOfRange(data, left, right)));

            quicksort(data, left, pos);
            quicksort(data, pos+1, right);
        }
        else {

            sort(data, left, right);


        }

    }

    private static void sort(int[] data, int left, int right){

        //if (right-left != 2) throw new IllegalArgumentException("Illegal length");

        System.out.println("Range: "+Arrays.toString(Arrays.copyOfRange(data, left, right)));

        int l = data[left];

        int r = data[right];

        System.out.println("Sorting: ["+l+", "+r+"]");

        if(l > r){
            data[left] = r;
            data[right] = l;
        }

        System.out.println("\tSorted: ["+data[left]+", "+data[right]+"]");

    }



    public static void main(String[] args) {

        int[] data = {7,6,8,5,3,3,6,8,9,6,8,4,3,5,6};
        System.out.println(Arrays.toString(data));
        quicksort(data, 0, data.length-1);

        System.out.println("Sorted: "+Arrays.toString(data));

    }
}
