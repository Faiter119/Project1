package stuff.backend.util;

import stuff.backend.data.option.Choice;
import stuff.backend.data.vote.VoteImpl;
import stuff.backend.enums.PollType;
import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Vote;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by faiter on 6/13/17.
 */
public class Calculator {

    public static boolean has50PercentMajority(List<Vote> votes){

        int size = votes.size();

        Map<Option, List<Option>> collect = votes.stream().map(vote -> vote.getOfRank(1)) // only first choices
                .collect(Collectors.groupingBy(o -> o));

        Map<Option, Integer> amountMap = new HashMap<>();

        collect.forEach((option, options) -> amountMap.put(option, options.size())); // how crude

        return amountMap.entrySet().stream().anyMatch(optionIntegerEntry -> optionIntegerEntry.getValue() > size/2);

    }

    private static Option leastVotesOfRank(List<Vote> votes, int rank){

        Map<Option, List<Option>> collect = votes.stream().map(vote -> vote.getOfRank(1)) // only first choices
                .collect(Collectors.groupingBy(o -> o));


        Map<Option, Integer> amountMap = new HashMap<>();

        collect.forEach((option, options) -> amountMap.put(option, options.size())); // how crude


        return amountMap.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow(IllegalArgumentException::new).getKey();

    }

    public static Option instantRunoff(List<Vote> votes){

        if (has50PercentMajority(votes)) return PollType.FIRST_PAST_THE_POST.calculate(votes); // base case

        Option optionToEliminate = leastVotesOfRank(votes, 1);
        System.out.println(optionToEliminate+" is eliminated");

        votes.stream()
                .filter(vote -> vote.getOfRank(1) == optionToEliminate)
                .forEach(vote -> vote.eliminate(optionToEliminate));


        return instantRunoff(votes);
    }

    public static void main(String[] args) {

        Option option1 = new Choice("Food");
        Option option2 = new Choice("Money");
        Option option3 = new Choice("Bitches");


        List<Vote> votes = Arrays.asList(
                new VoteImpl.Builder().vote(option1, 1).vote(option3, 2).vote(option2,3).build(),
                new VoteImpl.Builder().vote(option2, 1).vote(option1, 2).vote(option3,3).build(),
                new VoteImpl.Builder().vote(option2, 1).vote(option1, 2).vote(option3,3).build(),
                new VoteImpl.Builder().vote(option2, 1).vote(option1, 2).vote(option3,3).build(),
                new VoteImpl.Builder().vote(option3, 1).vote(option1, 2).vote(option2,3).build(),
                new VoteImpl.Builder().vote(option3, 1).vote(option1, 2).vote(option2,3).build(),
                new VoteImpl.Builder().vote(option1, 1).vote(option2, 2).vote(option3,3).build()


        );

        System.out.println(PollType.RANKED_CHOICE.calculate(votes)+" is the winner!");

    }

}
