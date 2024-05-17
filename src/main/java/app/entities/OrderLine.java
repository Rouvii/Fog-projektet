package app.entities;

public class OrderLine {

    private int id;
    private int længde;
    private int pris;
    private String description;

    private Order order;

    private Variant variant;

    private int quantity;

    public OrderLine(int id, int længde, int pris,int quantity, String description) {
        this.id = id;
        this.længde = længde;
        this.pris = pris;
        this.description = description;
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public int getLængde() {
        return længde;
    }

    public int getPris() {
        return pris;
    }

    public String getDescription() {
        return description;
    }

    public Order getOrder() {
        return order;
    }

    public Variant getVariant() {
        return variant;
    }

    public int getQuantity() {
        return quantity;
    }
}
