package metier;

import exceptions.NumSecuException;

import java.math.BigInteger;

public class NumSecu {
    private int nNumSecu;
    private int sexe;  // length 1
    private int anneNaissance; // length 2
    private int moisNaissance; // length 2
    private int departement; // length 2, < 95
    private int codeCommune; // length 3
    private int ordre; // length 3 : ordre de Naissance
    private int cle; // length 2 : cle=97 − (n%97)

    public NumSecu(String numSecuAsString) throws NumSecuException {
        setSexe(Integer.valueOf(numSecuAsString.substring(0, 1))); // 1 exclusive
        setAnneNaissance(Integer.valueOf(numSecuAsString.substring(1, 3)));
        setMoisNaissance(Integer.valueOf(numSecuAsString.substring(3, 5)));
        setDepartement(Integer.valueOf(numSecuAsString.substring(5, 7)));
        setCodeCommune(Integer.valueOf(numSecuAsString.substring(7, 10)));
        setOrdre(Integer.valueOf(numSecuAsString.substring(10, 13)));
        setCle(numSecuAsString, Integer.valueOf(numSecuAsString.substring(13, 15)));
    }

    public NumSecu(int nNumSecu, int sexe, int anneNaissance, int moisNaissance, int departement, int codeCommune, int ordre, int cle) {
        this.nNumSecu = nNumSecu;
        this.sexe = sexe;
        this.anneNaissance = anneNaissance;
        this.moisNaissance = moisNaissance;
        this.departement = departement;
        this.codeCommune = codeCommune;
        this.ordre = ordre;
        this.cle = cle;
    }

    public NumSecu(int sexe, int anneNaissance, int moisNaissance, int departement, int codeCommune, int ordre, int cle) throws NumSecuException {
        this.setSexe(sexe);
        this.anneNaissance = anneNaissance;
        this.moisNaissance = moisNaissance;
        this.departement = departement;
        this.codeCommune = codeCommune;
        this.ordre = ordre;
        this.cle = cle;
    }

    public int getnNumSecu() {
        return nNumSecu;
    }

    private void setSexe(int sexe) throws NumSecuException {
        if (sexe == 1 || sexe == 2) {
            this.sexe = sexe;
        } else {
            throw new NumSecuException("Premier chiffre, valeur : 1 ou 2");
        }
    }

    private void setAnneNaissance(int anneNaissance) {
        this.anneNaissance = anneNaissance;
    }

    private void setMoisNaissance(int moisNaissance) throws NumSecuException {
        if (moisNaissance > 0 && moisNaissance < 12) {
            this.moisNaissance = moisNaissance;
        } else {
            throw new NumSecuException("Chiffre 3 et 4 : mois de naissance (entre 0 et 12)");
        }
    }

    private void setDepartement(int departement) throws NumSecuException {
        if (this.departement <= 95) {
            this.departement = departement;
        } else {
            throw new NumSecuException("Departement inférieur à 95");
        }
    }

    private void setCodeCommune(int codeCommune) {
        this.codeCommune = codeCommune;
    }

    private void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    private void setCle(String numSecu, int cle) throws NumSecuException {
        // verification de la valeur
        long n = Long.parseLong((numSecu.substring(0, 13)));
        if (97 - (n % 97) == cle) {
            this.cle = cle;
        } else {
            throw new NumSecuException("Clé Invalide");
        }
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

    public int getCodeCommune() {
        return codeCommune;
    }

    public int getOrdre() {
        return ordre;
    }

    public int getCle() {
        return cle;
    }

    public static void main(String[] args) throws NumSecuException {
        NumSecu s = new NumSecu(1, 98, 10, 24, 24, 22, 168);
        System.out.println(s);
        NumSecu numClaire = new NumSecu("267045918300767");
    }
}
