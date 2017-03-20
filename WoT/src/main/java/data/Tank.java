package data;

import enums.Country;
import enums.TankType;
import enums.Tier;

/**
 * Created by OlavH on 27-Feb-17.
 */
public class Tank {

    private TankType tankType;
    private String tankName;
    private Tier tier;
    private Country country;

    private long creditCost = 0;
    private long experienceCost = 0;

    public Tank() {
    }

    public Tank(String tankName, Country country, Tier tier, TankType tankType) {
        this.tankType = tankType;
        this.tankName = tankName;
        this.tier = tier;
        this.country = country;
    }

    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public long getCreditCost() {
        return creditCost;
    }

    public void setCreditCost(long creditCost) {
        this.creditCost = creditCost;
    }

    public long getExperienceCost() {
        return experienceCost;
    }

    public void setExperienceCost(long experienceCost) {
        this.experienceCost = experienceCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tank tank = (Tank) o;

        if (creditCost != tank.creditCost) return false;
        if (experienceCost != tank.experienceCost) return false;
        if (tankType != tank.tankType) return false;
        if (tankName != null ? !tankName.equals(tank.tankName) : tank.tankName != null) return false;
        if (tier != tank.tier) return false;
        return country == tank.country;

    }

    @Override
    public int hashCode() {
        int result = tankType != null ? tankType.hashCode() : 0;
        result = 31 * result + (tankName != null ? tankName.hashCode() : 0);
        result = 31 * result + (tier != null ? tier.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (int) (creditCost ^ (creditCost >>> 32));
        result = 31 * result + (int) (experienceCost ^ (experienceCost >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Tank{" + "tankName=" + '\'' + tankName + '\'' +", country="+ '\'' +country+ '\'' +", tier="+ '\'' +tier+ '\'' +", tankType="+ '\'' +tankType
                + '\'' +", creditCost="+ '\'' +creditCost+ '\'' +", experienceCost="+ '\'' +experienceCost+ '\'' +'}';
    }



    public static void main(String[] args) {

        Tank m4Sherman = new Tank("M4 Sherman", Country.USA, Tier.V, TankType.MEDIUM);
        m4Sherman.setCreditCost(350_500);
        m4Sherman.setExperienceCost(14_055);

        System.out.println(m4Sherman);

    }
}
