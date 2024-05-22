package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou, Daniel Rouvillain
 */
public class Materialer {

    private int materialeId;

    private String type;

    private int price;


    public Materialer(int materialeId) {
        this.materialeId = materialeId;

    }

    public Materialer(int materialeId, String type, int price) {
        this.materialeId = materialeId;
        this.type = type;
        this.price = price;
    }


    public int getMaterialeId() {
        return materialeId;
    }

    public void setMaterialeId(int materialeId) {
        this.materialeId = materialeId;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Materialer{" +
                "materialeId=" + materialeId +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
