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

        int stolpeAmount = 2 * ((stolpeLængde - 130) / 310);
        int stolpePris = stolpeAmount * (int) stolpePrisPrMeter * stolpeLængde;

        OrderLine stolpe = new OrderLine(STOLPE, stolpeLængde, stolpePris, stolpeAmount, "Stolper");
        orderLines.add(stolpe);
    }

    private void calcRemmer() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(REM, connectionPool);
        Variant bestFit = null;

        for (Variant variant : variants) {
            if (variant.getLength() <= width && (bestFit == null || variant.getLength() > bestFit.getLength())) {
                bestFit = variant;
            }
        }
        int remmeAmount = 2;
        int remmeLængde = bestFit.getLength();
        int remmePrisPrMeter = bestFit.getMateriale().getPrice();
        int remmePris = (remmePrisPrMeter * remmeLængde) * remmeAmount;

        OrderLine remme = new OrderLine(SPÆR, remmeLængde, remmePris, remmeAmount, "Remmer");
        orderLines.add(remme);
    }

    private void calcSpær() {
        List<Variant> variants = VariantMapper.getAllVariantsByMaterialId(SPÆR, connectionPool);



     /*if (spærVariant.getLength() > 300){
         spærVariant=
     }*/

        int spærLængde = 300;
        int spærPris = 100;
        //OrderLine spær1 = new OrderLine(SPÆR, spærLængde, spærPris, spærAmount, "Spær");
        //orderLines.add(spær1);

    }

    public List<OrderLine> getOrderLines() {
        System.out.println(orderLines);
        return orderLines;

    }


}
