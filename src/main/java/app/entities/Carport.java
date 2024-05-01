package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Carport {

    private int carportId;
    private double højde;

    private double bredde;

    private double længde;

    private double pris;


    public Carport(int carportId, double højde, double bredde, double længde, double pris) {
        this.carportId = carportId;
        this.højde = højde;
        this.bredde = bredde;
        this.længde = længde;
        this.pris = pris;
    }

    public int getCarportId() {
        return carportId;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    public double getHøjde() {
        return højde;
    }

    public void setHøjde(double højde) {
        this.højde = højde;
    }

    public double getBredde() {
        return bredde;
    }

    public void setBredde(double bredde) {
        this.bredde = bredde;
    }

    public double getLængde() {
        return længde;
    }

    public void setLængde(double længde) {
        this.længde = længde;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }
}
