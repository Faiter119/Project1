package interview;

import java.util.Comparator;

/**
 * Created by faiter on 10/2/17.
 */
public class Employee implements Comparable<Employee>{

    private String name;
    private int age;

    public Employee(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        return name != null ? name.equals(employee.name) : employee.name == null;
    }

    @Override
    public int hashCode() {

        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    @Override
    public int compareTo(Employee employee) {

        return name.compareTo(employee.getName());
    }

    public static void main(String[] args) {

        Employee olav = new Employee("Olav", 21);
        Employee magnus = new Employee("Magnus", 22);

        Comparator<Employee> comparing = Comparator.<Employee, Boolean>comparing(employee -> employee.getAge() == 20);

        int compare = comparing.compare(olav, magnus);

        System.out.println(compare);
    }
}
