package enums;

/**
 * Created by OlavH on 27-Feb-17.
 */
public enum Tier {

    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10);

    private int tier;

    Tier(int tier){

        this.tier = tier;

    }

    public int getTier() {
        return tier;
    }
}
