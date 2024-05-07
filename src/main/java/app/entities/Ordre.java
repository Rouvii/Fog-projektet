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

    private int statusId;


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

    public Ordre(double længde,double bredde){
        this.længde=længde;
        this.bredde=bredde;
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

    public void setBetalt(boolean betalt) {
        this.betalt = betalt;
    }

    public void setAfsendt(boolean afsendt) {
        this.afsendt = afsendt;
    }

    public void setAfvist(boolean afvist) {
        this.afvist = afvist;
    }

    public void setModtaget(boolean modtaget) {
        this.modtaget = modtaget;
    }

    public void setLængde(double længde) {
        this.længde = længde;
    }

    public void setBredde(double bredde) {
        this.bredde = bredde;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSlutPris(double slutPris) {
        this.slutPris = slutPris;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Ordre{" +
                "ordreId=" + ordreId +
                ", betalt=" + betalt +
                ", afsendt=" + afsendt +
                ", afvist=" + afvist +
                ", modtaget=" + modtaget +
                ", længde=" + længde +
                ", bredde=" + bredde +
                ", userId=" + userId +
                ", slutPris=" + slutPris +
                ", dato=" + dato +
                '}';
    }

    public int getStatusId() {
        return statusId;
    }
}
