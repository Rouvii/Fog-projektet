package app.entities;

import java.sql.Date;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Order {

    private int ordreId;
    private boolean betalt;
    private boolean afsendt;
    private boolean afvist;
    private boolean modtaget;
    private int længde;
    private int bredde;
    private int userId;
    private int totalPris;
    private Date dato;

    private int statusId;


    public Order(int ordreId, Date dato) {
        this.ordreId = ordreId;
        this.dato = dato;
    }

    public Order(int orderId, int userId, java.sql.Date dato, int længde, int bredde, boolean betalt, boolean afsendt, boolean afvist, boolean modtaget, int totalPris) {
        this.ordreId = orderId;
        this.userId = userId;
        this.dato = dato;
        this.længde = længde;
        this.bredde = bredde;
        this.betalt = betalt;
        this.afsendt = afsendt;
        this.afvist = afvist;
        this.modtaget = modtaget;
        this.totalPris = totalPris;
    }

    public Order(int orderId, int userId, java.sql.Date dato, int længde, int bredde, int statusId, boolean betalt, boolean afsendt, boolean afvist, boolean modtaget, int totalPris) {
        this.ordreId = orderId;
        this.userId = userId;
        this.dato = dato;
        this.længde = længde;
        this.bredde = bredde;
        this.statusId = statusId;
        this.betalt = betalt;
        this.afsendt = afsendt;
        this.afvist = afvist;
        this.modtaget = modtaget;
        this.totalPris = totalPris;
    }

    public Order(int længde, int bredde) {
        this.længde = længde;
        this.bredde = bredde;
    }

    public Order(int orderId, int userId, Date dato, int længde, int bredde, int statusId, int totalPris)
    {
        this.ordreId = orderId;
        this.userId = userId;
        this.dato = dato;
        this.længde = længde;
        this.bredde = bredde;
        this.statusId = statusId;
        this.totalPris = totalPris;
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

    public int getLængde() {
        return længde;
    }

    public int getBredde() {
        return bredde;
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalPris() {
        return totalPris;
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

    public void setLængde(int længde) {
        this.længde = længde;
    }

    public void setBredde(int bredde) {
        this.bredde = bredde;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalPris(int totalPris) {
        this.totalPris = totalPris;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
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
                ", slutPris=" + totalPris +
                ", dato=" + dato +
                '}';
    }


}
