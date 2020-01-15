package utils;

import model.Helpdesk;
import model.Ticket;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CSVFileHandler {
    private String fileName;
    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public CSVFileHandler(String fileName){
        this.fileName = fileName;
    }

    public void uloz (Helpdesk helpdesk) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        for(int i = 0; i < helpdesk.velikost(); i++){
            String vyrizeno;
            Ticket ticket = helpdesk.vratTicket(i);
            if (ticket.isVyrizeno()) {
                vyrizeno = "1";
            } else {
                vyrizeno = "0";
            }
            out.printf("%s;%s;%s;%s;%s\n", df.format(ticket.getVznik()), ticket.getPrio(), ticket.getPopis(), ticket.getUzivatel(), vyrizeno);
        }
        out.close();
    }

    public void nacti(Helpdesk helpdesk) throws IOException, ParseException {
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        String radek;
        while ((radek = input.readLine())!=null) {
            boolean vyrizeno;
            String[] str = radek.split(";");
            vyrizeno = str[4].equals("1");
            Ticket ticket = new Ticket(df.parse(str[0]), Integer.parseInt(str[1]), str[2], str[3], vyrizeno);
            helpdesk.pridej(ticket);
        }
        input.close();
    }
}
