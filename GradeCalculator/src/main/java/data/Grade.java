package data;

import java.util.stream.Stream;

/**
 * Created by faiter on 5/24/17.
 */
public enum Grade {

    A(89, 100, 6),
    B(77, 88, 5),
    C(65, 76, 4),
    D(53, 64, 3),
    E(41, 52, 2),
    F(0, 40, 1);

    private int fromInc;
    private int toInc;
    private int value;

    Grade(int fromInc, int toInc, int value) {

        this.fromInc = fromInc;
        this.toInc = toInc;
        this.value = value;
    }

    public int getFrom() {

        return fromInc;
    }

    public int getTo() {

        return toInc;
    }

    public int getValue() {

        return value;
    }

    public static Grade of(int nr){

        return Stream.of(values())
                .filter(grade -> grade.inRange(nr))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        // Love the smell of a fresh ()-> in the morning
    }
    public static Grade ofPoint(int point){

        return Stream.of(values())
                .filter(grade -> grade.getValue() == point)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

    }

    private boolean inRange(int nr){

        return nr >= fromInc && nr <= toInc;

    }

    public static void main(String[] args) {


        System.out.println("test");

        main(args);

    }

}
