package backend.data;

import backend.util.validation.Checker;

import java.time.LocalDateTime;

/**
 * Created by OlavH on 27-Feb-17.
 */
public class Player {

    private String email;
    private String userName;
    private long credits = 0;
    private long gold = 0;
    private String clan;

    private LocalDateTime createdDateTime;

    public Player(String email) {
        this.email = email;
        createdDateTime = LocalDateTime.now();

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public void addCredits(int c) {
        Checker.requireNonNegative(c);

        credits += c;
    }

    public void addGold(int g) {
        gold += g;
    }

    public void removeCredits(int c) {
        Checker.requireNonNegative(c);

        if (c >= 0 && c <= credits) {

            credits -= c;
        }

    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public long getGold() {
        return gold;
    }

    public String getClan() {
        return clan;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (credits != player.credits) return false;
        if (gold != player.gold) return false;
        if (email != null ? !email.equals(player.email) : player.email != null) return false;
        if (userName != null ? !userName.equals(player.userName) : player.userName != null) return false;
        if (clan != null ? !clan.equals(player.clan) : player.clan != null) return false;
        return createdDateTime != null ? createdDateTime.equals(player.createdDateTime) : player.createdDateTime == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (int) (credits ^ (credits >>> 32));
        result = 31 * result + (int) (gold ^ (gold >>> 32));
        result = 31 * result + (clan != null ? clan.hashCode() : 0);
        result = 31 * result + (createdDateTime != null ? createdDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (clan != null) {
            return userName + "[" + clan + "]";
        }
        return userName;
    }
}
