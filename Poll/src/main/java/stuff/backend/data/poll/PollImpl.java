package stuff.backend.data.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import stuff.backend.enums.PollType;
import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Poll;
import stuff.backend.interfaces.Vote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiter on 6/28/17.
 */
public class PollImpl implements Poll{

    private String title;

    private PollType pollType;
    private List<Option> options = new ArrayList<>();
    private List<Vote> votes = new ArrayList<>();

    public PollImpl() {

    }

    public PollImpl(PollType pollType) {

        this.pollType = pollType;
    }


    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
    public void setOptions(List<Option> options) {this.options = options;}
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    public void setTitle(String title) {this.title = title;}

    @Override
    public String getTitle() {

        return title;
    }

    @Override
    public List<Option> getOptions() {

        return options;
    }

    @JsonIgnore
    @Override
    public List<Vote> getVotes() {

        return votes;
    }
    @Override
    public PollType getPollType() {

        return pollType;
    }

    @Override
    public void addOption(Option option) {

        options.add(option);
    }

    @Override
    public void vote(Vote vote) {

        votes.add(vote);
    }

    @JsonIgnore
    @Override
    public Option getWinner() {

        return pollType.calculate(votes);
    }

    @Override
    public String toString() {

        return "PollImpl{title="+title+ ", pollType=" + pollType + ", options=" + options+'}';
    }
}
