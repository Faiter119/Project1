package stuff.REST;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stuff.backend.data.option.Choice;
import stuff.backend.data.vote.RankedVote;
import stuff.backend.data.poll.PollImpl;
import stuff.backend.data.stats.Statistic;
import stuff.backend.data.vote.VoteImpl;
import stuff.backend.enums.PollType;
import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Poll;
import stuff.backend.interfaces.Vote;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by faiter on 6/28/17.
 */
@RestController
@RequestMapping("/polls")
public class PollController {

    private Map<Integer, Poll> pollMap = new HashMap<>();

    {
        Poll poll = new PollImpl(PollType.FIRST_PAST_THE_POST);

        poll.addOption(new Choice("Food"));
        poll.addOption(new Choice("Cheese"));
        poll.addOption(new Choice("Money"));

        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(0),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(0),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(1),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(1),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(2),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(2),1).build());
        poll.vote(new VoteImpl.Builder().vote(poll.getOptions().get(2),1).build());

        pollMap.put(0, poll);
    }

    private int pollCounter = 1;

    @GetMapping(value = "", produces = "Application/JSON")
    public Collection<Poll> getPolls(){

        System.out.println("Get polls: "+pollMap.values());

        return pollMap.values();
    }
    @PostMapping(value = "", consumes = "Application/JSON")
    @ResponseStatus(HttpStatus.OK)
    public int makePoll(@RequestBody Poll poll){

        System.out.println(poll);

        pollMap.put(pollCounter++, poll);

        return pollCounter - 1;
    }

    @GetMapping("/{id}")
    public Poll getPollOfID(@PathVariable int id){

        return pollMap.get(id);

    }

    @GetMapping("/{pollId}/votes")
    public List<Vote> getVotes(@PathVariable int pollId){

        System.out.println("Get votes");
        System.out.println(pollMap.get(pollId).getVotes());

        return pollMap.get(pollId).getVotes();


    }

    @PostMapping(value = "/{pollID}/votes", consumes = "Application/JSON")
    @ResponseStatus(HttpStatus.OK)
    public void vote(@PathVariable int pollID, @RequestBody List<RankedVote> votes){

        System.out.println("Vote "+pollID+" on "+votes);

        Poll pollOfID = getPollOfID(pollID);

        VoteImpl vote = new VoteImpl();

        votes.forEach(voteData -> {


            vote.vote(voteData);//optionFromTitle, voteData.getRank());

        });

        pollOfID.vote(vote);

        System.out.println("Votes so far: "+pollOfID.getVotes());
    }

    @GetMapping("/{pollId}/result")
    public Option getResult(@PathVariable int pollId){

        Poll pollOfID = getPollOfID(pollId);

        Option calculate = pollOfID.getWinner();

        System.out.println("Result");
        System.out.println("Poll: "+pollOfID);
        System.out.println("Winner: "+calculate);

        return calculate;

    }
    @GetMapping("/{pollId}/stats")
    public Statistic getStats(@PathVariable int pollId){

        Poll pollOfID = getPollOfID(pollId);
        Statistic statistic = Statistic.of(pollOfID);

        System.out.println(statistic.optionVoteDistribution);

        return statistic;

    }
}
