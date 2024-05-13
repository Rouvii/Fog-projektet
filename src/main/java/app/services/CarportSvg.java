package app.services;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class CarportSvg {



    private int width;
    private int height;

    private Svg carportSvg;

    public CarportSvg(int width, int height) {
        this.width = width;
        this.height = height;
        carportSvg= new Svg(0, 0, "0 0 855 690", "75%", "auto");
        carportSvg.addRectangle(0,0,600,780,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        carportSvg.addArrow(0,height/2,0,height/2,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        carportSvg.addArrow(width/2,0,width/2,height,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        addBeams();
        addRafters();
    }

    private void addBeams(){

        carportSvg.addRectangle(0,35,4.5,780,"stroke-width:1px;stroke:#000000;fill:#ffffff");
        carportSvg.addRectangle(0,565,4.5,780,"stroke-width:1px;stroke:#000000;fill:#ffffff");

    }

    public void addRafters(){
        for (double i = 0; i < 780; i+=55.714) {
            carportSvg.addRectangle(i,0.0 , 600, 4.5, "stroke-width:1px;stroke:#000000;fill:#ffffff");
        }
    }


    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
