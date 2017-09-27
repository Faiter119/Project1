package bedrift;

/**
 * Created by faiter on 8/11/17.
 */
public class Employee implements Comparable<Employee>{

    private String navn;
    private double lønn;

    public Employee(String navn, double lønn) {

        this.navn = navn;
        this.lønn = lønn;
    }

    public String getNavn() {

        return navn;
    }

    public double getLønn() {

        return lønn;
    }

    @Override
    public String toString() {

        return "Employee{" + "navn='" + navn + '\'' + ", lønn=" + lønn + '}';
    }

    public static void main(String[] args) {

        Employee employee = new Employee("Stian", 190);

        System.out.println(employee);



    }

    @Override
    public int compareTo(Employee employee) {

        return Double.compare(lønn, employee.getLønn());
    }
}
