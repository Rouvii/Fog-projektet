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
    private boolean status;
    private Date dato;


    public Ordre(int ordreId, boolean status, Date dato) {
        this.ordreId = ordreId;
        this.status = status;
        this.dato = dato;
    }


    public int getOrdreId() {
        return ordreId;
    }

    public void setOrdreId(int ordreId) {
        this.ordreId = ordreId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }
}
