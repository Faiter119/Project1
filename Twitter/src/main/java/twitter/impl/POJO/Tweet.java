package twitter.impl.POJO;

import java.util.HashMap;
import java.util.List;

/**
 * Created by faiter on 8/30/17.
 */
public class Tweet {

    String in_reply_to_status_id_str;
    String in_reply_to_status_id;
    String created_at;
    String in_reply_to_user_id_str;
    String source;
    int retweet_count;
    boolean retweeted;
    String geo;
    String filter_level;
    String in_reply_to_screen_name;
    boolean is_quote_status;
    String id_str;
    String in_reply_to_user_id;
    String favorite_count;
    long id;
    String text;
    String place;
    String lang;
    int quote_count;
    boolean favorited;
    String possibly_sensitive;
    String coordinates;
    String truncated;
    String timestamp_ms;
    int reply_count;
    List<String> entities;
    String contributors;
    HashMap user;

    public Tweet() {}

    public String getIn_reply_to_status_id_str() {
        return in_reply_to_status_id_str;
    }
    public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
        this.in_reply_to_status_id_str = in_reply_to_status_id_str;
    }
    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }
    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getIn_reply_to_user_id_str() {
        return in_reply_to_user_id_str;
    }
    public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
        this.in_reply_to_user_id_str = in_reply_to_user_id_str;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public int getRetweet_count() {
        return retweet_count;
    }
    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }
    public boolean isRetweeted() {
        return retweeted;
    }
    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
    public String getGeo() {
        return geo;
    }
    public void setGeo(String geo) {
        this.geo = geo;
    }
    public String getFilter_level() {
        return filter_level;
    }
    public void setFilter_level(String filter_level) {
        this.filter_level = filter_level;
    }
    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }
    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }
    public boolean isIs_quote_status() {
        return is_quote_status;
    }
    public void setIs_quote_status(boolean is_quote_status) {
        this.is_quote_status = is_quote_status;
    }
    public String getId_str() {
        return id_str;
    }
    public void setId_str(String id_str) {
        this.id_str = id_str;
    }
    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }
    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }
    public String getFavorite_count() {
        return favorite_count;
    }
    public void setFavorite_count(String favorite_count) {
        this.favorite_count = favorite_count;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public int getQuote_count() {
        return quote_count;
    }
    public void setQuote_count(int quote_count) {
        this.quote_count = quote_count;
    }
    public boolean isFavorited() {
        return favorited;
    }
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
    public String getPossibly_sensitive() {
        return possibly_sensitive;
    }
    public void setPossibly_sensitive(String possibly_sensitive) {
        this.possibly_sensitive = possibly_sensitive;
    }
    public String getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    public String getTruncated() {
        return truncated;
    }
    public void setTruncated(String truncated) {
        this.truncated = truncated;
    }
    public String getTimestamp_ms() {
        return timestamp_ms;
    }
    public void setTimestamp_ms(String timestamp_ms) {
        this.timestamp_ms = timestamp_ms;
    }
    public int getReply_count() {
        return reply_count;
    }
    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }
    public List<String> getEntities() {
        return entities;
    }
    public void setEntities(List<String> entities) {
        this.entities = entities;
    }
    public String getContributors() {
        return contributors;
    }
    public void setContributors(String contributors) {
        this.contributors = contributors;
    }
    public HashMap getUser() {
        return user;
    }
    public void setUser(HashMap user) {
        this.user = user;
    }

    @Override
    public String toString() {

        return "TWEET";

    }
}
