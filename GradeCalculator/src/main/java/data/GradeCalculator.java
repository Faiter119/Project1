package data;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by faiter on 5/24/17.
 */
public class GradeCalculator {

    public static Grade getGrade(List<GradeWithWeight> grades) {

        int sum = grades.stream().mapToInt(GradeWithWeight::getWeight).sum();

        //System.out.println(sum);

        double percentage = 1d / (double) sum;

        Map<Grade, Double> gradePointMap = new HashMap<>();

        grades.forEach(grade -> {

            gradePointMap.putIfAbsent(grade.getGrade(), 0d);
            gradePointMap.put(grade.getGrade(), (gradePointMap.get(grade.getGrade()) + grade.getGrade().getValue() * (grade.getWeight() * percentage)));
        });

        //System.out.println(gradePointMap);


        double asDouble = gradePointMap.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();

        //System.out.println("Sum of weighted points: "+asDouble);

        return Grade.ofPoint((int) Math.round(asDouble));
    }

    /**
     * This was suprisingly hard, probably easier with recursion (first time those words have been said)
     *
     * @param grades
     * @return
     */
    public static String gradePermutations(List<GradeWithWeight> grades) {


        StringBuilder builder = new StringBuilder();

        grades.forEach(gradeWithWeight -> {
            gradeWithWeight.setGrade(Grade.A);

            builder.append("\t|\t");
            builder.append(gradeWithWeight.getWeight()).append("%");
        });
        builder.append("\t||\tResult");
        builder.append("\n\t");
        grades.forEach(gradeWithWeight -> builder.append("***********"));
        builder.append("\n");


        grades.forEach(gradeWithWeight -> {

            Grade startGrade = gradeWithWeight.getGrade();

            Stream.of(Grade.values()).forEach(grade -> {

                //gradeWithWeight.setGrade(grade);

                //builder.append(gradeWithWeight.getGrade());

                Grade grade1 = getGrade(grades);

                grades.forEach(gradeWithWeight1 -> {
                    builder.append("\t|\t");
                    builder.append(gradeWithWeight1.getGrade());
                });

                builder.append("\t||\t");
                builder.append(grade1);
                builder.append("\n");
            });
            gradeWithWeight.setGrade(startGrade);
        });


        return builder.toString();
    }

    /**
     * Now with 100% more recursion
     */
    public static void permutate(List<GradeWithWeight> grades) {

        if (grades.size() == 2) {

            perm(grades.get(0), grades.get(1));

        }
        else {

            permutate(grades.subList(0, grades.size() - 1));
            permutate(grades.subList(1, grades.size()));

        }
    }

    public static void perm(GradeWithWeight grade1, GradeWithWeight grade2) {


        Stream.of(Grade.values()).forEach(grade -> {

            grade2.setGrade(grade);

            System.out.println(grade1 + " + " + grade2 + " = " + getGrade(Arrays.asList(grade1, grade2)));

        });

        if (grade1.getGrade() == Grade.F) {
            return;
        }
        grade2.setGrade(Grade.A);
        grade1.setToNextGrade();
        perm(grade1, grade2);
    }


    public static void main(String[] args) throws InterruptedException {

        List<GradeWithWeight> grades = new ArrayList<>();

        // Matte 2

        /*grades.add(new GradeWithWeight(Grade.D, 45));
        grades.add(new GradeWithWeight(Grade.A, 20));
        grades.add(new GradeWithWeight(Grade.A, 35));
*/

        // Realfag

       /* grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.A, 50));
*/

        // Nettprogg

       /* grades.add(new GradeWithWeight(Grade.A, 50));
        grades.add(new GradeWithWeight(Grade.B, 50));
*/
        grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.A, 50));

        System.out.println(getGrade(grades));

        permutate(grades);

        //perm(grades.get(0), grades.get(1));
        //System.out.println(gradePermutations(grades));


    }
}
