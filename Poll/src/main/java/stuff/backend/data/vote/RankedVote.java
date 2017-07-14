package stuff.backend.data.vote;

import stuff.backend.interfaces.Option;

/**
 * Created by faiter on 6/18/17.
 */
public class RankedVote {

    private Option option;
    private int rank;

    public RankedVote() {

    }

    public RankedVote(Option option, int rank) {

        this.option = option;
        this.rank = rank;
    }

    public Option getOption() {

        return option;
    }

    public int getRank() {

        return rank;
    }

    public void setRank(int rank) {

        this.rank = rank;
    }

    public void setOption(Option option) {

        this.option = option;
    }

    @Override
    public String toString() {

        return "{" + "option=" + option + ", rank=" + rank + '}';
    }

    public String getTitle() {

        return option.getTitle();
    }
}
