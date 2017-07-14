package stuff.backend.data.stats;

import collections.Shelf;
import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Vote;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by faiter on 7/14/17.
 */
public class RankVote {

    private int rank;
    private int votes;

    public RankVote() {
    }

    public RankVote(int rank, int votes) {

        this.rank = rank;
        this.votes = votes;
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getVotes() {
        return votes;
    }
    public void setVotes(int votes) {
        this.votes = votes;
    }

    public static List<RankVote> of(List<Vote> votes, Option option){


        Shelf<Integer> shelf = new Shelf<>();


        List<Vote> collect = votes.stream().filter(vote -> vote.containsOption(option)).collect(Collectors.toList());

        collect.stream().forEach(vote -> {

            shelf.add(vote.getRankOf(option));

        });

        List<RankVote> collect1 = shelf.getItemCountMap()
                .entrySet()
                .stream()
                .map(integerIntegerEntry -> new RankVote(integerIntegerEntry.getKey(), integerIntegerEntry.getValue()))
                .collect(Collectors.toList());

        return collect1;
    }
}
