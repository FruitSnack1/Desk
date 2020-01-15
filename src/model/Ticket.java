package model;

import java.util.Date;

public class Ticket {
    private Date vznik;
    private int prio;
    private String popis;
    private String uzivatel;
    private boolean vyrizeno;

    public Ticket(int prio, String popis, String uzivatel) {
        vznik = new Date();
        this.prio = prio;
        this.popis = popis;
        this.uzivatel = uzivatel;
        vyrizeno = false;
    }

    public Ticket(Date vznik, int prio, String popis, String uzivatel, boolean vyrizeno) {
        this.vznik = vznik;
        this.prio = prio;
        this.popis = popis;
        this.uzivatel = uzivatel;
        this.vyrizeno = vyrizeno;
    }

    public Date getVznik() {
        return vznik;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getUzivatel() {
        return uzivatel;
    }

    public void setUzivatel(String uzivatel) {
        this.uzivatel = uzivatel;
    }

    public boolean isVyrizeno() {
        return vyrizeno;
    }

    public void setVyrizeno(boolean vyrizeno) {
        this.vyrizeno = vyrizeno;
    }
}
