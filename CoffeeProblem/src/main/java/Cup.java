import org.apache.commons.math3.fraction.Fraction;

/**
 * Created by faiter on 11/2/17.
 */
public class Cup {

    private final String name;
    private Fraction part1;
    private Fraction part2;

    public Cup(String name, Fraction part1, Fraction part2) {

        this.name = name;
        this.part1 = part1;
        this.part2 = part2;
    }

    public String getName() {return name;}
    public Fraction getPart1() {return part1;}
    public void setPart1(Fraction part1) {this.part1 = part1;}
    public Fraction getPart2() {return part2;}
    public void setPart2(Fraction part2) {this.part2 = part2;}
    public double getPercentFull(){return part1.add(part2).doubleValue();}
    public double getPercentPart1(){return part1.divide(getFullness()).doubleValue();}
    public double getPercentPart2(){return part2.divide(getFullness()).doubleValue();}
    public Fraction getFullness(){return part1.add(part2);}

    public boolean isFull(){

        return part1.add(part2).equals(Fraction.ONE);

    }
    // pour into other cup
    public void pour(Cup other, Fraction toPour){

        if (other.isFull()) throw new IllegalArgumentException("Other cup is full");

        System.out.println("\nPouring "+getName()+" into "+other.getName());

        pourPart1(other, toPour);
        pourPart2(other, toPour);

    }
    private void pourPart1(Cup other, Fraction toPour){

        Fraction amountToPour = Fraction.ONE;
        try {
            amountToPour = part1.multiply(toPour);
        }
        catch (Exception e){
            System.out.println("ERROR");
            System.out.println(this);
            System.out.println(other);
        }

        other.setPart1(
                other.getPart1().add(amountToPour)
        );

        setPart1(
                part1.subtract(amountToPour)
        );
    }
    private void pourPart2(Cup other, Fraction toPour){

        Fraction amountToPour = part2.multiply(toPour);

        other.setPart2(
                other.getPart2().add(amountToPour)
        );
        setPart2(
                part2.subtract(amountToPour)
        );
    }

    @Override
    public String toString() {
        return "Cup{" + "name='" + name + '\'' + ", part1= [" + part1 + "], part2=[" + part2 + "], full="+isFull()+", %= "+getPercentFull()+", %part1="+getPercentPart1()+", %part2="+getPercentPart2()+"}";
    }
}
