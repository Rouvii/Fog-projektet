package app.services;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class CarportSvg {



    private int width;
    private int length;


    private Svg carportSvg;

    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg= new Svg(0, 0, "0 0 600 780", "50%", "auto");
        carportSvg.addRectangle(0,0,length,width,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        addBeams(width,length);
        addRafters(width, length);
        addPoles();
        addDiaganol();
    }


    private void addBeams(int width, int length){
       // Tilføj remmen langs længden af carporten
        carportSvg.addRectangle(0,35,4.5,width,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        carportSvg.addRectangle(0,length-35,4.5,width,"stroke-width:1px;stroke:#000000;fill:#ffffff");

    }

    public void addRafters(int width, int length){
        // Tilføj spær langs bredden af carporten
        for (double i = 0; i < width; i+=55.714) {
            carportSvg.addRectangle(i,0.0 , length, 4.5, "stroke-width:1px;stroke:#000000;fill:#ffffff");
        }
    }

    public void addPoles() {
        double beamY1 = 35.0;
        double beamY2 = length - 35.0;

        String poleStyle = "stroke-width:1px;stroke:#000000;fill:#ffffff";
        double poleWidth = 10.0;
        double poleLength = 10.0;

        // Udregn mængden af spær i tegningen
        int numberOfRafters = (int) (width / 55.714);

        // For hver 3 spær, tilføjer vi 1 pæl
        int numberOfPolesWidth = numberOfRafters / 3;

        // Gør så der som minimum altid er 4 pæle
        numberOfPolesWidth = Math.max(numberOfPolesWidth, 2);

        // Beregn afstanden mellem pælene langs bredden
        double poleDistanceWidth = (width - poleWidth) / (numberOfPolesWidth + 1);

        // Tilføj pæle langs den første rem
        for (int i = 1; i <= numberOfPolesWidth; i++) {
            double x = i * poleDistanceWidth;
            carportSvg.addRectangle(x, beamY1, poleLength, poleWidth, poleStyle);
        }

        // Tilføj pæle langs den anden rem, hvis der er mere end en rem
        if (numberOfPolesWidth > 1) {
            for (int i = 1; i <= numberOfPolesWidth; i++) {
                double x = i * poleDistanceWidth;
                carportSvg.addRectangle(x, beamY2, poleLength, poleWidth, poleStyle);
            }
        }

    }

    public void addDiaganol(){

        String diagonalStyle = "stroke-width:1px;stroke:#000000;stroke-dasharray:5,5"; // Gør linjen stiplede


        carportSvg.addLineWithoutArrow(0, 0, width, length, diagonalStyle);


        carportSvg.addLineWithoutArrow(width, 0, 0, length, diagonalStyle);

    }


    public Svg getCarportSvg() {
        return carportSvg;
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
