package stuff.backend.data.stats;

import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Poll;
import stuff.backend.interfaces.Vote;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by faiter on 7/8/17.
 */
public class Statistic {

    public Map<Option, Integer> optionVoteDistribution;
    public Map<Option, List<RankVote>> optionRankVoteMap;

    private Statistic() {

    }

    public static Statistic of(Poll poll){

        List<Vote> votes = poll.getVotes();

        Statistic statistic = new Statistic();


        // Group by option
        Map<Option, List<Vote>> collect = votes.stream().collect(Collectors.groupingBy(vote -> vote.getOfRank(1)));

        // map to size of list
        statistic.optionVoteDistribution = collect
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                optionListEntry -> optionListEntry.getValue().size()
                        )
                );

        /**/

        poll.getOptions()
                .stream()
                .forEach(option -> {

                    statistic.optionRankVoteMap.put(option, RankVote.of(votes, option));

                });




        return statistic;
    }

}
