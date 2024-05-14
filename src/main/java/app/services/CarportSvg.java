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
    }

    private void addBeams(int width, int length){

        carportSvg.addRectangle(0,35,4.5,width,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        carportSvg.addRectangle(0,length-35,4.5,width,"stroke-width:1px;stroke:#000000;fill:#ffffff");

    }

    public void addRafters(int width, int length){
        for (double i = 0; i < width; i+=55.714) {
            carportSvg.addRectangle(i,0.0 , length, 4.5, "stroke-width:1px;stroke:#000000;fill:#ffffff");
        }
    }


    public Svg getCarportSvg() {
        return carportSvg;
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
