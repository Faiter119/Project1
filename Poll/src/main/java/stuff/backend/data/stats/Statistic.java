package stuff.backend.data.stats;

import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Poll;
import stuff.backend.interfaces.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by faiter on 7/8/17.
 */
public class Statistic {

    public Map<Option, List<RankVote>> optionRankVoteMap = new HashMap<>();

    private Statistic() {

    }

    public static Statistic of(Poll poll){

        List<Vote> votes = poll.getVotes();

        System.out.println("Making stats for votes: "+votes);

        Statistic statistic = new Statistic();

        poll.getOptions()
                .stream()
                .forEach(option -> {

                    System.out.println("OPTION: "+option+" - RANKVOTES: "+RankVote.of(votes, option));

                    statistic.optionRankVoteMap.put(option, RankVote.of(votes, option));

                });




        return statistic;
    }

}
