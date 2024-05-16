package app.services;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Variant;
import app.mappers.VariantMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {


    private final static int STOLPE = 2;
    private final static int SPÆR = 1;

    private final static int REM = 1;

    private List<OrderItem> orderItems = new ArrayList<>();
    private int width;
    private int length;
    private ConnectionPool connectionPool = null;

    public Calculator(int width, int length, ConnectionPool connectionPool) {
        this.width = width;
        this.length = length;
        this.connectionPool = connectionPool;
    }

    public void calcCarport() {
        calcStolper();
        calcRemmer();
        calcSpær();

    }

    private void calcStolper() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(STOLPE, connectionPool);
        Variant stolpeVariant = variants.get(0);

        int stolpeLængde = stolpeVariant.getLength();
        double stolpePrisPrMeter = stolpeVariant.getMateriale().getPrice();

        int stolpeAmount = 2 * ((stolpeLængde - 130) / 310);
        int stolpePris = stolpeAmount * (int) stolpePrisPrMeter * stolpeLængde;

        OrderItem stolpe = new OrderItem(stolpeAmount, stolpeLængde, stolpePris, stolpeAmount, "Stolper");
        orderItems.add(stolpe);
    }

    private void calcRemmer() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(REM, connectionPool);
        Variant bestFit = null;

        for (Variant variant : variants) {
            if (variant.getLength() <= width && (bestFit == null || variant.getLength() > bestFit.getLength())) {
                bestFit = variant;
            }
        }

        int remmeLængde = bestFit.getLength();
        double remmePrisPrMeter = bestFit.getMateriale().getPrice();
        int remmePris = (int) (remmePrisPrMeter * remmeLængde);

        //  OrderItem remme = new OrderItem(remmeAmount , remmeLængde, remmePris, "Remmer");
        // orderItems.add(remme);
    }

    private void calcSpær() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(SPÆR, connectionPool);


    /*
     if (spærVariant.getLength() > 300){
         spærVariant=
     }

            int spærLængde = 300;
            int spærPris = 100;
            OrderItem spær1 = new OrderItem(spær, spærLængde, spærPris, "Spær");
            orderItems.add(spær1);

    }

    public List<OrderItem> getOrderItems(){
        System.out.println(orderItems);
        return orderItems;

    }

     */
    }
}
