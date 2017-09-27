package backend.data;

import java.util.*;

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

    static int iter = 0;
    static int maxIter;

    public static void findPermutations(List<GradeWithWeight> grades){

        maxIter = (int) Math.pow(Grade.values().length, grades.size());
        grades.forEach(gradeWithWeight -> gradeWithWeight.setGrade(Grade.A));

        printHeader(grades);
        permutate(grades, false);
        System.out.println(grades + " = " + getGrade(grades) + " - " + (++iter) + "/" + maxIter); // Else the last iteration is skipped



    }

    private static void permutate(List<GradeWithWeight> grades, boolean skip){

        Final<Boolean> thisSkip = new Final<>(skip); // Because lambdas are dumb

        grades.stream().forEach(gradeWithWeight -> {

            if (grades.stream().allMatch(gradeWithWeight1 -> gradeWithWeight1.getGrade() == Grade.F)) {
                return;
            }

            if (gradeWithWeight.getGrade() == Grade.F) { // At the end of an iteration

                if (thisSkip.get()) {
                    //thisSkip.set(false);
                    gradeWithWeight.setToNextGrade();
    
                }
                else { // To avoid duplicates the first iteration
                    System.out.println(grades + " = " + getGrade(grades) + " - " + (++iter) + "/" + maxIter);


                    printHeader(grades);
                    gradeWithWeight.setToNextGrade();
                    thisSkip.set(true);
                }
            }

            else { // still in the main iteration loop


                if (thisSkip.get()) { // First time
                    thisSkip.set(false);
                }
                else {
                    System.out.println(grades + " = " + getGrade(grades) + " - " + (++iter) + "/" + maxIter);

                }

                gradeWithWeight.setToNextGrade();

                permutate(grades, thisSkip.get());
            }

        });

    }

    private static void printHeader(List<GradeWithWeight> grades){

        System.out.println();
        grades.forEach(gradeWithWeight -> {

            System.out.print(gradeWithWeight.getWeight()+"%\t\t");

        });
        System.out.println();

    }

    public static void main(String[] args) throws InterruptedException {

        List<GradeWithWeight> grades = new ArrayList<>();

        // Matte 2

        grades.add(new GradeWithWeight(Grade.C, 50));
        grades.add(new GradeWithWeight(Grade.D, 50));

        // Realfag

        /*grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.A, 50));
*/
        // Nettprogg

        /*grades.add(new GradeWithWeight(Grade.A, 50));
        grades.add(new GradeWithWeight(Grade.A, 50));
        *//*grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.B, 25));
        grades.add(new GradeWithWeight(Grade.A, 50));*/

        System.out.println(getGrade(grades));

        //getPermuatationRecursive(grades);

        findPermutations(grades);

        //perm(grades.get(0), grades.get(1));
        //System.out.println(gradePermutations(grades));


    }
}
