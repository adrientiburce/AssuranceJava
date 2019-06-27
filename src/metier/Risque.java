package metier;

public class Risque {

    private int nRisque;

    private int niveau;

    public Risque(int nRisque, int niveau) {
        this.nRisque = nRisque;
        this.niveau = niveau;
    }

    public int getNiveau() {
        return niveau;
    }

    @Override
    public String toString() {
        return "niveau : " + niveau;
    }
}
