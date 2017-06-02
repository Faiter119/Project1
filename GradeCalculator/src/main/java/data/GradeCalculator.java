package data;

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

    @Deprecated
    public static void permutate(List<GradeWithWeight> grades) {

        maxIter = (int) Math.pow(Grade.values().length, grades.size());
        currentIndex = grades.size()-1;

        grades.forEach(gradeWithWeight -> gradeWithWeight.setGrade(Grade.A));

        permv2(grades, grades.size()-1);
    }

    static int iter = 0;
    static int maxIter;
    static int currentIndex;
    static int count = 0;
    @Deprecated
    static void permv2(List<GradeWithWeight> grades, int toSkip){

        System.out.println(grades+" = "+getGrade(grades) + " - "+(++iter)+"/"+maxIter);

        if (grades.stream().allMatch(gradeWithWeight -> gradeWithWeight.getGrade() == Grade.F)){

            return;
        }

        try {
            Thread.sleep(0);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        grades.stream()
                .skip(toSkip)
                //.filter(gradeWithWeight -> gradeWithWeight.getGrade() != Grade.F)
                .forEach(gradeWithWeight -> {

                    if (gradeWithWeight.getGrade() == Grade.F){

                        gradeWithWeight.setGrade(Grade.A);

                        int i = grades.indexOf(gradeWithWeight);

                        //permv2(grades, toSkip-1);

                        if (grades.get(i-1).getGrade() == Grade.F){

                            grades.stream().skip(currentIndex-1).forEach(gradeWithWeight1 -> gradeWithWeight1.setGrade(Grade.A));

                            grades.get(i-1).setGrade(Grade.A);


                            System.out.println("\nCurrent "+currentIndex+"\n");
                            grades.get(currentIndex-2).setToNextGrade();

                            if (++count >= Grade.values().length-1) {
                                count = 0;

                                currentIndex--;
                            }

                            permv2(grades, toSkip);
                        }
                        else {

                            //gradeWithWeight.setGrade(Grade.A);
                            grades.get(i-1).setToNextGrade();
                            permv2(grades, toSkip);
                        }


                    }
                    else {
                        gradeWithWeight.setToNextGrade();
                        permv2(grades, toSkip);

                    }

        });


    }

    @Deprecated
    public static List<PermGrade> getPermuatationRecursive(List<GradeWithWeight> grades){

        //grades.forEach(gradeWithWeight -> gradeWithWeight.setGrade(Grade.A));

        int i = grades.size()-1;
        while (!grades.stream().allMatch(gradeWithWeight -> gradeWithWeight.getGrade() == Grade.F)){

            for (int j = grades.size()-1; j >= i ; j--) {

                System.out.println(grades + " = " + getGrade(grades));

                if (grades.get(j).getGrade() == Grade.F){

                    grades.get(j).setGrade(Grade.A);
                    //i--;
                }
                grades.get(j).setToNextGrade();
                //getPermuatationRecursive(grades);

            }
            i--;
        }


        return Collections.emptyList();
    }

    public static void findPermutations(List<GradeWithWeight> grades){

        maxIter = (int) Math.pow(Grade.values().length, grades.size());
        grades.forEach(gradeWithWeight -> gradeWithWeight.setGrade(Grade.A));

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


                    System.out.println("\n");
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

    public static void main(String[] args) throws InterruptedException {

        List<GradeWithWeight> grades = new ArrayList<>();

        // Matte 2

        grades.add(new GradeWithWeight(Grade.C, 20));
        grades.add(new GradeWithWeight(Grade.D, 20));
        grades.add(new GradeWithWeight(Grade.D, 20));
        grades.add(new GradeWithWeight(Grade.D, 20));
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
