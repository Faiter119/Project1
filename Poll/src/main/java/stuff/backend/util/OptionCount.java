package stuff.backend.util;

import stuff.backend.interfaces.Option;

/**
 * Created by faiter on 6/18/17.
 */
public class OptionCount {

    private Option option;
    private int count;

    public OptionCount(Option option, int count) {

        this.option = option;
        this.count = count;
    }

    public Option getOption() {

        return option;
    }

    public int getCount() {

        return count;
    }
}
