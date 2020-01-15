package model;

import java.util.ArrayList;
import java.util.List;

public class Helpdesk {
    private List<Ticket> helpdesk = new ArrayList<>();

    public void pridej(Ticket ticket) {
        helpdesk.add(ticket);
    }

    public void odeber(int index) {
        helpdesk.remove(index);
    }

    public void vycisti() {
        helpdesk = new ArrayList<>();
    }

    public int velikost() {
        return helpdesk.size();
    }

    public Ticket vratTicket (int index) {
        return helpdesk.get(index);
    }
}
