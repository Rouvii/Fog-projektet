package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Orderline {


   private int orderlineId;
   private int orderId;
    private Variant variant;
    private int quantity;

    private String decription;

public Orderline(int orderlineId, int orderId, Variant variant, int quantity, String decription) {
        this.orderlineId = orderlineId;
        this.orderId = orderId;
        this.variant = variant;
        this.quantity = quantity;
        this.decription = decription;
    }

    public int getOrderlineId() {
        return orderlineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Variant getVariant() {
        return variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDecription() {
        return decription;
    }
}
