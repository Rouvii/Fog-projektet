package app.services;

import app.entities.ConnectionPool;
import app.entities.Order;
import app.entities.OrderLine;
import app.entities.Variant;
import app.mappers.VariantMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {


    private final static int STOLPE = 2;
    private final static int SPÆR = 1;

    private final static int REM = 1;

    private List<OrderLine> orderLines = new ArrayList<>();
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
        int stolpePrisPrMeter = stolpeVariant.getMateriale().getPrice();

        int stolpeAmount = (int) 2 * ((stolpeLængde - 130) / 310);
        if(stolpeAmount<1){
            stolpeAmount=2;
        }
        int stolpePris = stolpeAmount * (int) stolpePrisPrMeter * (stolpeLængde /100);

        OrderLine stolpe = new OrderLine(STOLPE, stolpeLængde, stolpePris, stolpeAmount, "Stolper");
        orderLines.add(stolpe);
    }

    private void calcRemmer() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(REM, connectionPool);
        Variant bestFit = null;

        for (Variant variant : variants) {
            if (variant.getLength() >= width && (bestFit == null || variant.getLength() < bestFit.getLength())) {
                bestFit = variant;
            }
        }
        int remmeAmount = 2;
        int remmeLængde = bestFit.getLength();
        int remmePrisPrMeter = bestFit.getMateriale().getPrice();
        int remmePris = (remmePrisPrMeter * (remmeLængde/100) * remmeAmount);

        OrderLine remme = new OrderLine(SPÆR, remmeLængde, remmePris, remmeAmount, "Remmer");
        orderLines.add(remme);
    }


        private void calcSpær() {
            List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(SPÆR, connectionPool);
            Variant bestFit=null;

            for (Variant variant : variants) {
                if (variant.getLength() <= length && (bestFit == null || variant.getLength() > bestFit.getLength())) {
                    bestFit = variant;
                }
            }

            int spærLængde = bestFit.getLength();
            double spærPrisPrMeter = bestFit.getMateriale().getPrice();


            int spærAmount = width / 55;
            int spærPris = spærAmount * (int) (spærPrisPrMeter * (spærLængde/100));

            OrderLine spær = new OrderLine(SPÆR, spærLængde, spærPris, spærAmount, "Spær");
            orderLines.add(spær);
        }



    public List<OrderLine> getOrderLines() {
        System.out.println(orderLines);
        return orderLines;

    }


    public int getTotalPrice() {
        int totalPrice = 0;
        int skruer = 100;
        for (OrderLine orderLine : orderLines) {
            totalPrice += orderLine.getPris();
        }
        double totalPriceWithIntrest = totalPrice * 1.30;
        return (int)totalPriceWithIntrest + skruer;
    }


}
