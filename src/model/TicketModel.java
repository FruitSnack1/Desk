package model;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TicketModel extends AbstractTableModel {
    Helpdesk helpdesk;
    String[] names = {"Čas vzniku", "Priorita", "Popis", "Uživatel", "Vyřízeno"};

    public TicketModel(Helpdesk helpdesk) {
        this.helpdesk = helpdesk;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 2:
            case 3:
                return String.class;
            case 1:
                return Integer.class;
            case 4:
                return Boolean.class;
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return helpdesk.velikost();
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = helpdesk.vratTicket(rowIndex);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        switch (columnIndex) {
            case 0 :
                return df.format(ticket.getVznik());
            case 1 :
                return ticket.getPrio();
            case 2 :
                return ticket.getPopis();
            case 3 :
                return ticket.getUzivatel();
            case 4 :
                return ticket.isVyrizeno();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Ticket ticket = helpdesk.vratTicket(rowIndex);

        if (columnIndex == 4) {
            ticket.setVyrizeno((Boolean) aValue);
        }
        this.fireTableDataChanged();
    }



    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }

    public void refresh() {
        fireTableDataChanged();
    }
}
