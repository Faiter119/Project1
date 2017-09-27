package bedrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by faiter on 8/11/17.
 */
public class Bedrift {

    private List<Employee> employeeList = new ArrayList<>();

    private double omseting;
    private double breakEven;
    private double targetProfit;

    private List<Utgift> utgifts = new ArrayList<>();

    public List<Employee> getEmployeeList() {

        return employeeList;
    }
    public double getOmseting() {
        return omseting;
    }
    public void setOmseting(double omseting) {
        this.omseting = omseting;
    }
    public double getBreakEven() {
        return breakEven;
    }
    public void setBreakEven(double breakEven) {
        this.breakEven = breakEven;
    }
    public double getTargetProfit() {
        return targetProfit;
    }
    public void setTargetProfit(double targetProfit) {
        this.targetProfit = targetProfit;
    }

    public List<Utgift> getUtgifts() {

        return utgifts;
    }

    public double getNetProfit(){

        return omseting - utgifts.stream().mapToDouble(Utgift::getKostnad).sum();

    }

    @Override
    public String toString() {
        return "Bedrift{" + "employeeList=" + employeeList + ", omseting=" + omseting + ", breakEven=" + breakEven + ", targetProfit=" + targetProfit + '}';
    }

    public static void main(String[] args) {

        Bedrift bedrift = new Bedrift();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Skriv inn dagens omsetning: ");
        bedrift.setOmseting(Double.parseDouble(scanner.nextLine()));

        bedrift.setBreakEven(2000);

        System.out.println("Skriv inn target profit: ");
        bedrift.setTargetProfit(Double.parseDouble(scanner.nextLine()));

        bedrift.getEmployeeList().add(new Employee("Stian", 190));
        bedrift.getEmployeeList().add(new Employee("Olav", 177));
        bedrift.getEmployeeList().add(new Employee("Stine", 165));
        bedrift.getEmployeeList().add(new Employee("Birk", 150));


        bedrift.getUtgifts().add(new Utgift("Kjøpte ingredienser", 1000));
        bedrift.getUtgifts().add(new Utgift("Kurs", 500));

        System.out.println("***LØNNING***");
        bedrift.getEmployeeList().forEach(employee -> {

            double enDag = employee.getLønn() * 7.5;

            System.out.println(employee.getNavn()+": "+enDag+" for en dag");

        });
        System.out.println("***LØNNING***");

        double sum = bedrift.getEmployeeList()
                .stream()
                .mapToDouble(employee -> employee.getLønn()*7.5)
                .sum();

        System.out.println("\nSum alle en dag: "+sum);
        System.out.println("Omseting: "+bedrift.getOmseting());
        System.out.println("Target profit: "+bedrift.getTargetProfit());
        System.out.println("Break even: "+bedrift.getBreakEven());
        System.out.println("Utgifter: "+bedrift.getUtgifts()+"\n");

        bedrift.getEmployeeList().forEach(employee -> {

            System.out.println("***"+employee.getNavn()+"***");

            double enDag = employee.getLønn() * 7.5;

            double netProfit = bedrift.getNetProfit() - enDag;

            System.out.println("Net profit: "+netProfit);

            System.out.println("% av target: "+netProfit/ bedrift.getTargetProfit()*100+"%");
            System.out.println();
        });

        System.out.println("***MAXIMUM PROFIT***");

        List<List<Employee>> permutate = permutate(bedrift.getEmployeeList());

        permutate.sort((employees, t1) -> Double.compare(employees.stream().mapToDouble(employee -> employee.getLønn()*7.5).sum(), t1.stream().mapToDouble(employee -> employee.getLønn()*7.5).sum()));

        permutate.forEach(employees -> {

            double lønn = employees.stream().mapToDouble(employee -> employee.getLønn() * 7.5).sum();

            System.out.println(employees);
            double netProfit = bedrift.getNetProfit() - lønn;
            System.out.println("% av target: "+netProfit/ bedrift.getTargetProfit()*100+"%");

        });
    }

    static List<List<Employee>> permutate(List<Employee> employees){

        List<List<Employee>> out = new ArrayList<>();

        employees.stream().forEach(employee -> {

            employees.forEach(employee1 -> {

                List<Employee> pair = Arrays.asList(employee, employee1);

                if (employee != employee1 && !contains(out, pair)) out.add(Arrays.asList(employee, employee1));


            });

        });

        return out;
    }
    static boolean contains(List<List<Employee>> pairs, List<Employee> pair){

        return pairs.stream().anyMatch(employees -> employees.containsAll(pair));
    }
}
