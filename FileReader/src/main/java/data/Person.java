package data;

import java.io.Serializable;

/**
 * Created by OlavH on 20-Mar-17.
 */
public class Person implements Serializable {

    static final long serialVersionUID = 42L;

    private String name;
    private int tlf;

    public Person(String name, int tlf) {

        this.name = name;
        this.tlf = tlf;
    }

    public String getName() {

        return name;
    }

    public int getTlf() {

        return tlf;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (tlf != person.tlf) return false;
        return name != null ? name.equals(person.name) : person.name == null;

    }

    @Override
    public int hashCode() {

        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + tlf;
        return result;
    }

    @Override
    public String toString() {

        return "Person{" + "name='" + name + '\'' + ", tlf=" + tlf + '}';
    }
}
