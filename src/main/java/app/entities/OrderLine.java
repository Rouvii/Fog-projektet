package app.entities;

public class OrderLine {

    private int id;
    private int længde;
    private int pris;
    private String description;
    private int totalPris;
    private String type;


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

    public OrderLine(String type,int længde,int quantity, String description, int totalPris) {
        this.længde = længde;
        this.description = description;
        this.totalPris = totalPris;
        this.type = type;
        this.quantity = quantity;
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

    public int getTotalPris() {
        return totalPris;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", længde=" + længde +
                ", pris=" + pris +
                ", description='" + description + '\'' +
                ", totalPris=" + totalPris +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
