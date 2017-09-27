package stuff.backend.enums;

import stuff.backend.interfaces.Option;
import stuff.backend.interfaces.Vote;
import stuff.backend.util.Calculator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by faiter on 6/7/17.
 */
public enum PollType {

    FIRST_PAST_THE_POST {
        @Override
        public Option calculate(List<Vote> votes) {

            Map<Option, List<Option>> collect = votes.stream()
                    .map(vote -> vote.getOfRank(1))
                    .collect(Collectors.groupingBy(Function.identity()));

            return collect.entrySet().stream()
                    .max(Comparator.comparingInt(optionListEntry -> optionListEntry.getValue().size()))
                    .map(Map.Entry::getKey)
                    .orElseThrow(IllegalArgumentException::new);
        }
    },
    RANKED_CHOICE {
        @Override
        public Option calculate(List<Vote> votes) {

            return Calculator.instantRunoff(votes);
        }
    };

    public abstract Option calculate(List<Vote> options);
}
