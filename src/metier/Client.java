package metier;

public class Client {

    private int nClient;
    private String nom;
    private String prenom;
    private String telephone;
    private int revenu;
    private NumSecu nNumSecu;
    private int risque;

    public Client(int nClient, String nom, String prenom, String telephone, int revenu, NumSecu nNumSecu, int risque) {
        this.nClient = nClient;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.revenu = revenu;
        this.nNumSecu = nNumSecu;
        this.risque = risque;
    }

    public Client( String nom, String prenom, String telephone, int revenu, NumSecu nNumSecu, int risque) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.revenu = revenu;
        this.nNumSecu = nNumSecu;
        this.risque = risque;
    }

    public int getnClient() {
        return nClient;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getRevenu() {
        return revenu;
    }

    public NumSecu getnNumSecu() {
        return nNumSecu;
    }

    public int getRisque() {
        return risque;
    }
}
