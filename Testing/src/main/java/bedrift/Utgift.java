package bedrift;

/**
 * Created by faiter on 8/11/17.
 */
public class Utgift {

    private String beskrivelse;
    private double kostnad;

    public Utgift(String beskrivelse, double kostnad) {

        this.beskrivelse = beskrivelse;
        this.kostnad = kostnad;
    }

    public String getBeskrivelse() {

        return beskrivelse;
    }

    public double getKostnad() {

        return kostnad;
    }

    @Override
    public String toString() {

        return "Utgift{" + "beskrivelse='" + beskrivelse + '\'' + ", kostnad=" + kostnad + '}';
    }

    public static void main(String[] args) {

        Utgift utgift = new Utgift("Kjøpt ost", 100);

    }
}
