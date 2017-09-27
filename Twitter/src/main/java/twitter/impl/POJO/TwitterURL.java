package twitter.impl.POJO;

import java.util.List;

/**
 * Created by faiter on 8/30/17.
 */
public class TwitterURL {
    String url;
    String expanded_url;
    String display_url;
    List<Double> indices;
    List<User> user_mentions;
    List<String> symbols;

    public TwitterURL() {}

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getExpanded_url() {

        return expanded_url;
    }

    public void setExpanded_url(String expanded_url) {

        this.expanded_url = expanded_url;
    }

    public String getDisplay_url() {

        return display_url;
    }

    public void setDisplay_url(String display_url) {

        this.display_url = display_url;
    }

    public List<Double> getIndices() {

        return indices;
    }

    public void setIndices(List<Double> indices) {

        this.indices = indices;
    }

    public List<User> getUser_mentions() {

        return user_mentions;
    }

    public void setUser_mentions(List<User> user_mentions) {

        this.user_mentions = user_mentions;
    }

    public List<String> getSymbols() {

        return symbols;
    }

    public void setSymbols(List<String> symbols) {

        this.symbols = symbols;
    }

    @Override
    public String toString() {

        return "URL";
    }
}

