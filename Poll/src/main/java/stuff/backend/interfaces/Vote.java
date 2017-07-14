package stuff.backend.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import stuff.backend.data.vote.VoteImpl;

/**
 * Created by faiter on 6/7/17.
 */
@JsonDeserialize(as = VoteImpl.class)
public interface Vote {

    Option getOfRank(int rank);
    int getRankOf(Option option);
    void changeRank(int oldRank, int newRank);
    void eliminate(Option option);

    void vote(Option option);
    void vote(Option option, int rank);

    boolean containsOption(Option option);

}
