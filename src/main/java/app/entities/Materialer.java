package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Materialer {

    private int materialeId;

    private int skruer;

    public Materialer(int materialeId,int skruer) {
        this.materialeId = materialeId;
        this.skruer=skruer;
    }


    public int getMaterialeId() {
        return materialeId;
    }

    public void setMaterialeId(int materialeId) {
        this.materialeId = materialeId;
    }

    public int getSkruer() {
        return skruer;
    }

    public void setSkruer(int skruer) {
        this.skruer = skruer;
    }
}
