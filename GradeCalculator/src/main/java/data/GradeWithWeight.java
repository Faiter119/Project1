package data;

import java.util.Arrays;

/**
 * Created by faiter on 5/24/17.
 */
public class GradeWithWeight {

    private Grade grade;
    private int weight;

    public GradeWithWeight(Grade grade, int weight) {

        this.grade = grade;
        this.weight = weight;
    }

    public Grade getGrade() {

        return grade;
    }

    public int getWeight() {

        return weight;
    }

    public void setGrade(Grade grade) {

        this.grade = grade;
    }
    public void setToNextGrade(){

        int index = Arrays.binarySearch(Grade.values(), grade);

        this.grade = Grade.values()[ (index == Grade.values().length -1) ? 0 : index+1];

    }

    @Override
    public String toString() {

        return grade.toString()+"["+weight+"]";
    }
}
