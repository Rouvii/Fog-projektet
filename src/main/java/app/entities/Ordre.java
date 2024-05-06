package app.entities;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Ordre {

    private int ordreId;
    private boolean betalt;
    private boolean afsendt;
    private boolean afvist;
    private boolean modtaget;
    private double længde;
    private double bredde;
    private int userId;
    private double slutPris;
    private Date dato;


    public Ordre(int ordreId, Date dato) {
        this.ordreId = ordreId;
        this.dato = dato;
    }

    public Ordre(int orderId, int userId, java.sql.Date dato, double længde, double bredde, boolean betalt, boolean afsendt, boolean afvist, boolean modtaget, double slutPris) {
        this.ordreId = orderId;
        this.userId = userId;
        this.dato = dato;
        this.længde = længde;
        this.bredde = bredde;
        this.betalt = betalt;
        this.afsendt = afsendt;
        this.afvist = afvist;
        this.modtaget = modtaget;
        this.slutPris = slutPris;
    }


    public int getOrdreId() {
        return ordreId;
    }

    public void setOrdreId(int ordreId) {
        this.ordreId = ordreId;
    }


    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public boolean isBetalt() {
        return betalt;
    }

    public boolean isAfsendt() {
        return afsendt;
    }

    public boolean isAfvist() {
        return afvist;
    }

    public boolean isModtaget() {
        return modtaget;
    }

    public double getLængde() {
        return længde;
    }

    public double getBredde() {
        return bredde;
    }

    public int getUserId() {
        return userId;
    }

    public double getSlutPris() {
        return slutPris;
    }
}
