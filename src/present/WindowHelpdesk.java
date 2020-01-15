package present;

import model.Helpdesk;
import model.Ticket;
import model.TicketModel;
import utils.CSVFileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WindowHelpdesk extends JFrame {
    private Helpdesk helpdesk = new Helpdesk();
    private TicketModel model = new model.TicketModel(helpdesk);
    private JTable table = new JTable(model);
    private TableRowSorter<TicketModel> sorter;

    private Action actionNew, actionOpen, actionSave, actionAdd, actionClose;

    public WindowHelpdesk() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Helpdesk");

        initActions();

        JMenuBar menuBar = new JMenuBar();

        JMenu menuSoubor = new JMenu("Soubor");
        menuSoubor.add(actionNew);
        menuSoubor.addSeparator();
        menuSoubor.add(actionOpen);
        menuSoubor.addSeparator();
        menuSoubor.add(actionSave);
        menuSoubor.addSeparator();
        menuSoubor.add(actionClose);
        menuBar.add(menuSoubor);

        JMenu menuDokument = new JMenu("Incident");
        menuDokument.add(actionAdd);
        menuBar.add(menuDokument);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar("Nástroje",JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        toolBar.add(actionNew);
        toolBar.addSeparator();
        toolBar.add(actionOpen);
        toolBar.add(actionSave);
        toolBar.addSeparator();
        toolBar.add(actionAdd);
        this.add(toolBar, "North");

        table.setAutoCreateRowSorter(true);
        sorter = (TableRowSorter<TicketModel>) table.getRowSorter();

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        this.add(new JScrollPane(table), BorderLayout.CENTER);

        this.pack();
        this.setSize(600, 600);
    }

    private void initActions() {
        actionNew = new AbstractAction("Nový", new ImageIcon(getClass().getResource("/icons/new.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpdesk.vycisti();
                model.refresh();
            }
        };
        actionNew.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK ));
        actionNew.putValue(Action.SHORT_DESCRIPTION,"Vytvoř nový soubor");

        actionOpen = new AbstractAction("Načti", new ImageIcon(getClass().getResource("/icons/load.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                nactiSoubor();
            }
        };
        actionOpen.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK ));
        actionOpen.putValue(Action.SHORT_DESCRIPTION,"Otevreni souboru");

        actionSave = new AbstractAction("Ulož", new ImageIcon(getClass().getResource("/icons/save.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulozSoubor();
            }
        };
        actionSave.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ));
        actionSave.putValue(Action.SHORT_DESCRIPTION,"Uložení souboru");

        actionClose = new AbstractAction("Konec", new ImageIcon(getClass().getResource("/icons/exit.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        actionClose.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK ));
        actionClose.putValue(Action.SHORT_DESCRIPTION,"Ukončení aplikace");

        actionAdd = new AbstractAction("Vytvořit", new ImageIcon(getClass().getResource("/icons/add.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                vytvorTicket();
            }
        };
        actionAdd.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK ));
        actionAdd.putValue(Action.SHORT_DESCRIPTION,"Vytvoření nového dukumentu");
    }

    private void nactiSoubor(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        file.setFileFilter(filter);
        file.showOpenDialog(this);
        if (file.getSelectedFile() != null) {
            CSVFileHandler csv = new CSVFileHandler(file.getSelectedFile().toString());
            try {
                helpdesk.vycisti();
                csv.nacti(helpdesk);
            } catch (IOException | ParseException e) {
                JOptionPane.showMessageDialog(this, "Error File Not Found", "Varování", JOptionPane.WARNING_MESSAGE);
            }
            model.refresh();
        }
    }

    private void ulozSoubor(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        file.setFileFilter(filter);
        file.showSaveDialog(this);
        if (file.getSelectedFile() != null) {
            String fileName = file.getSelectedFile().toString();
            CSVFileHandler csv = new CSVFileHandler(fileName + ".csv");
            if (fileName.substring(fileName.length() - 3).contains("csv")) {
                csv = new CSVFileHandler(fileName);
            }
            try {
                csv.uloz(helpdesk);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error File Not Found", "Varování", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void vytvorTicket(){
        Integer[] priorita = {1, 2, 3, 4, 5};

        JFrame frame = new JFrame("Ticket");
        try {
            int prio = (Integer) JOptionPane.showInputDialog(frame,
                    "Zadej prio",
                    "Prio",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    priorita,
                    1);
            System.out.println(prio);

            String popis = JOptionPane.showInputDialog("Zadej detail ticketu");
            if (popis != null) {
                String uzivatel = JOptionPane.showInputDialog("Zadej zadavatele ticketu");
                if (uzivatel != null) {
                    Ticket ticket = new Ticket(prio, popis, uzivatel);
                    helpdesk.pridej(ticket);
                    model.refresh();
                }
            }
        } catch (NullPointerException e) {
            model.refresh();
        }
    }
}
