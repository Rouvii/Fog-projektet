package app.entities;

import java.sql.Date;
import java.util.Objects;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou,Daniel Rouvillain
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

    public Order(int ordreId,Date dato,int userId,int længde,int bredde,int statusId,int totalPris){
        this.ordreId = ordreId;
        this.userId = userId;
        this.dato = dato;
        this.længde = længde;
        this.bredde = bredde;
        this.totalPris = totalPris;
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


    public int getLængde() {
        return længde;
    }

    public int getBredde() {
        return bredde;
    }


    public int getTotalPris() {
        return totalPris;
    }

    public void setLængde(int længde) {
        this.længde = længde;
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

    public int getUserId() {
        return userId;
    }

    public Date getDato() {
        return dato;
    }

    public int getStatusId() {
        return statusId;
    }

    //Brugt til OrdreMapperTest, det så JUNIT kan sammenligne objekterne ud fra indhold og ikke fra hukommelse
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return ordreId == order.ordreId &&
                betalt == order.betalt &&
                afsendt == order.afsendt &&
                afvist == order.afvist &&
                modtaget == order.modtaget &&
                længde == order.længde &&
                bredde == order.bredde &&
                userId == order.userId &&
                totalPris == order.totalPris &&
                Objects.equals(dato, order.dato);
    }


    @Override
    public int hashCode() {
        return Objects.hash(ordreId, betalt, afsendt, afvist, modtaget, længde, bredde, userId, totalPris, dato);
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
