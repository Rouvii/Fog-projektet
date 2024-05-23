package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain
 */
public class User {

    private int userId;
    private String fornavn;

    private String efternavn;

    private String email;

    private String password;

    private String telefonNr;

    private String adresse;


    private boolean isAdmin;

    public User(int userId, String fornavn, String efternavn, String email, String password, String telefonNr, String adresse, boolean isAdmin) {
        this.userId = userId;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.password = password;
        this.telefonNr = telefonNr;
        this.adresse = adresse;
        this.isAdmin = isAdmin;
    }


    public User(int userId, String email, String password, boolean isAdmin) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(int userId, String fornavn, String efternavn, String adresse, String telefon) {
        this.userId = userId;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.adresse = adresse;
        this.telefonNr =telefon;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefonNr() {
        return telefonNr;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getrole() {
        return isAdmin ? "admin" : "user";
    }
}
