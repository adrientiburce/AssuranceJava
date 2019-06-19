package metier;

public class NumSecu {

    private int nNumSecu;
    private int sexe;
    private int anneNaissance;
    private int moisNaissance;
    private int departement;
    private int commune;
    private int ordre;
    private int cle;

    public NumSecu(int nNumSecu, int sexe, int anneNaissance, int moisNaissance, int departement, int commune, int ordre, int cle) {
        this.nNumSecu = nNumSecu;
        this.sexe = sexe;
        this.anneNaissance = anneNaissance;
        this.moisNaissance = moisNaissance;
        this.departement = departement;
        this.commune = commune;
        this.ordre = ordre;
        this.cle = cle;
    }
    public NumSecu(int sexe, int anneNaissance, int moisNaissance, int departement, int commune, int ordre, int cle) {
        this.sexe = sexe;
        this.anneNaissance = anneNaissance;
        this.moisNaissance = moisNaissance;
        this.departement = departement;
        this.commune = commune;
        this.ordre = ordre;
        this.cle = cle;
    }

    public int getnNumSecu() {
        return nNumSecu;
    }

    public int getSexe() {
        return sexe;
    }

    public int getAnneNaissance() {
        return anneNaissance;
    }

    public int getMoisNaissance() {
        return moisNaissance;
    }

    public int getDepartement() {
        return departement;
    }

    public int getCommune() {
        return commune;
    }

    public int getOrdre() {
        return ordre;
    }

    public int getCle() {
        return cle;
    }
}
