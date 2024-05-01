package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Stykliste {

    private int styklisteId;

    public Stykliste(int styklisteId) {
        this.styklisteId = styklisteId;
    }


    public int getStyklisteId() {
        return styklisteId;
    }

    public void setStyklisteId(int styklisteId) {
        this.styklisteId = styklisteId;
    }
}
