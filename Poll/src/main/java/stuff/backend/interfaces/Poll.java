package stuff.backend.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import stuff.backend.data.poll.PollImpl;
import stuff.backend.enums.PollType;

import java.util.List;

/**
 * Created by faiter on 6/7/17.
 */
@JsonDeserialize(as = PollImpl.class)
public interface Poll {

    String getTitle();
    List<Option> getOptions();
    void addOption(Option option);
    void vote(Vote vote);
    Option getWinner();
    List<Vote> getVotes();
    PollType getPollType();
}
