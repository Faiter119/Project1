package stuff.backend.data.option;

import stuff.backend.data.vote.VoteImpl;
import stuff.backend.enums.PollType;
import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Vote;

import java.util.Arrays;
import java.util.List;

/**
 * Created by faiter on 6/7/17.
 */
public class Choice implements Option {

    private String title;

    public Choice() {
    }

    public Choice(String title) {

        this.title = title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    @Override
    public String getTitle() {

        return title;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Choice choice = (Choice) o;

        return title != null ? title.equals(choice.title) : choice.title == null;
    }

    @Override
    public int hashCode() {

        return title != null ? title.hashCode() : 0;
    }

    @Override
    public String toString() {

        return title;
    }

    public static void main(String[] args) {

        Option option1 = new Choice("Food");
        Option option2 = new Choice("Money");


        List<Vote> votes = Arrays.asList(
                new VoteImpl.Builder().vote(option1, 1).vote(option2, 2).build(),
                new VoteImpl.Builder().vote(option1, 1).vote(option2, 2).build(),
                new VoteImpl.Builder().vote(option2, 1).vote(option1, 2).build(),
                new VoteImpl.Builder().vote(option2, 1).vote(option1, 2).build()
                );


        System.out.println(PollType.FIRST_PAST_THE_POST.calculate(votes));

        System.out.println(PollType.RANKED_CHOICE.calculate(votes));

    }
}
