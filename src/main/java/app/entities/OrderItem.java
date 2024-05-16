package app.entities;

public class OrderItem {

    private int id;
    private int længde;
    private int pris;
    private String description;

    private Order order;

    private Variant variant;

    private int quantity;

    public OrderItem(int id, int længde, int pris,int quantity, String description) {
        this.id = id;
        this.længde = længde;
        this.pris = pris;
        this.description = description;
        this.quantity=quantity;
    }
}
