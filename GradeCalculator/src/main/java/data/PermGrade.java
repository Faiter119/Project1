package data;

import java.util.List;

/**
 * Created by faiter on 5/28/17.
 */
public class PermGrade {

    private List<GradeWithWeight> grades;

    public PermGrade(List<GradeWithWeight> grades) {

        this.grades = grades;
    }

    public Grade getGrade(){

        return GradeCalculator.getGrade(grades);
    }
}
