package stuff.backend.data.vote;

import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Vote;

import java.util.*;

/**
 * Created by faiter on 6/7/17.
 */
public class VoteImpl implements Vote {

    private List<RankedVote> rankedVoteList = new ArrayList<>();

    public VoteImpl() {}

    public void setRankedVoteList(List<RankedVote> rankedVoteList) {this.rankedVoteList = rankedVoteList;}

    public List<RankedVote> getRankedVoteList() {
        return rankedVoteList;
    }
    @Override
    public void vote(Option option){

        rankedVoteList.add(new RankedVote(option, 1)); // FPTP

    }
    @Override
    public void vote(Option option, int rank){

        rankedVoteList.add(new RankedVote(option, rank));

    }

    @Override
    public boolean containsOption(Option option) {

        return rankedVoteList.stream().anyMatch(rankedVote -> rankedVote.getOption().equals(option));
    }

    public void vote(RankedVote option){

        rankedVoteList.add(option); // FPTP

    }

    @Override
    public void changeRank(int oldRank, int newRank) {

        Option ofRank = getOfRank(oldRank);

        rankedVoteList.stream().filter(rankedVote -> rankedVote == ofRank).findAny().orElseThrow(IllegalArgumentException::new)
                .setRank(newRank);

    }

    @Override
    public void eliminate(Option option) {

        RankedVote rankedVote1 = rankedVoteList.stream().filter(rankedVote -> rankedVote.getOption() == option).findAny().orElseThrow(IllegalArgumentException::new);

        rankedVoteList.forEach(rankedVote -> {

            if (rankedVote.getRank() == rankedVote1.getRank()){
                rankedVote.setRank(-1); // eliminated
            }

            if (rankedVote.getRank() > rankedVote1.getRank()){

                rankedVote.setRank(rankedVote.getRank()-1); // move up one

            }

        });
    }

    @Override
    public Option getOfRank(int rank) {

        return rankedVoteList
                .stream()
                .filter(rankedVote -> rankedVote.getRank() == rank)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getOption();

    }

    public int getRankOf(Option option) { // could just do contains and get... but that is not F U N C T I O N A L

        return rankedVoteList
                .stream()
                .filter(rankedVote -> rankedVote.getOption() == option)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getRank();


    }

    @Override
    public String toString() {
        return "Vote{"+ rankedVoteList + '}';
    }


    public static class Builder{ // convenience
        private List<RankedVote> rankedVoteList = new ArrayList<>();
        public Builder(){}
        public Builder vote(Option option, int rank){
            rankedVoteList.add(new RankedVote(option, rank));
            return this;
        }
        public Vote build(){
            VoteImpl vote = new VoteImpl();
            vote.setRankedVoteList(rankedVoteList);
            return vote;
        }
    }
}
