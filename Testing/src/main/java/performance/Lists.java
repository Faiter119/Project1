package performance;

import java.util.Vector;
import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

/**
 * Created by faiter on 8/30/17.
 */
public class Lists {

    public static void main(String[] args) {

        Vector<Double> doubles = new Vector<>();


        DoubleSupplier supplier = () -> Math.random()*100;

        DoubleStream.generate(supplier)
                .limit(100)
                .forEach(v -> {
                    System.out.println(v);
                });



    }
}
