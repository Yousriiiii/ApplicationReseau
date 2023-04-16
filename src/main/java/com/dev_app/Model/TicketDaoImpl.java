package com.dev_app.Model;

import java.sql.Date;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.dev_app.Controlor.MainControlor;

public class TicketDaoImpl implements TicketDao {

    @Override
    public ArrayList<Ticket> GetAllTickets(JdbcTemplate DriverDB, String id_visitor) {
        
        SqlRowSet tickets = DriverDB.queryForRowSet("SELECT * FROM SMALLDB.TICKET WHERE ID_VISITEUR = '"+ id_visitor +"'");

        ArrayList<Ticket> listTicket = new ArrayList<Ticket>();

        while(tickets.next()){
            String id_visiteur = tickets.getString("ID_VISITEUR");
            int id_attraction =  Integer.parseInt(tickets.getString("ID_ATTRACTION"));
            Date date_reservation = Date.valueOf(tickets.getString("DATE_RESERVATION"));
            double prix_billet = Double.parseDouble(tickets.getString("PRIX_BILLET"));
            Ticket ticket = new Ticket(id_visiteur, id_attraction, date_reservation, prix_billet);
            listTicket.add(ticket);
        }

        return listTicket;

    }

    @Override
    public void saveTicket(Ticket ticket , JdbcTemplate DriverDB) {
        DriverDB.execute("INSERT INTO SmallDB.TICKET (ID_VISITEUR, ID_ATTRACTION, DATE_RESERVATION, PRIX_BILLET) VALUES (" + ticket.getID_VISITEUR() + ", " + ticket.getID_ATTRACTION() + ", '" + ticket.getDATE_RESERVATION() + "', " + ticket.getPRIX_BILLET() +");");
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsOfSession(JdbcTemplate DriverDB, String id_visitor) {
        SqlRowSet tickets = DriverDB.queryForRowSet("SELECT * FROM TEMPORARY_DB.TICKET WHERE ID_VISITEUR = '"+ id_visitor +"'");

        ArrayList<Ticket> listTicket = new ArrayList<Ticket>();

        while(tickets.next()){
            String id_visiteur = tickets.getString("ID_VISITEUR");
            int id_attraction =  Integer.parseInt(tickets.getString("ID_ATTRACTION"));
            Date date_reservation = Date.valueOf(tickets.getString("DATE_RESERVATION"));
            double prix_billet = Double.parseDouble(tickets.getString("PRIX_BILLET"));
            Ticket ticket = new Ticket(id_visiteur, id_attraction, date_reservation, prix_billet);
            listTicket.add(ticket);
        }

        return listTicket;

    }

    @Override
    public void saveTicketOfSession(Ticket ticket, JdbcTemplate DriverDB) {
        DriverDB.execute("INSERT INTO TEMPORARY_DB.TICKET (ID_VISITEUR, ID_ATTRACTION, DATE_RESERVATION, PRIX_BILLET) VALUES (" + ticket.getID_VISITEUR() + ", " + ticket.getID_ATTRACTION() + ", '" + ticket.getDATE_RESERVATION() + "', " + ticket.getPRIX_BILLET() +");");
    }

    @Override
    public void deleteTicketOfSession(Ticket ticket, JdbcTemplate DriverDB) {
        DriverDB.execute("DELETE FROM TEMPORARY_DB.TICKET WHERE ID_ATTRACTION=" + ticket.getID_ATTRACTION() + ";");
    }

    @Override
    public void deleteTicket(Ticket ticket, JdbcTemplate DriverDB) {
        // Méthode qui va supprimer le ticket
        DriverDB.execute("DELETE FROM SMALLDB.TICKET WHERE ID_VISITEUR = '"+ ticket.getID_VISITEUR() +"' AND DATE_RESERVATION = '"+ticket.getDATE_RESERVATION()+"' AND ID_ATTRACTION = "+ticket.getID_ATTRACTION()+" ");
    }

    @Override
    public Ticket GetTicket(JdbcTemplate DriverDB, String id, String date, int type_activite) {
        // Je retourne un ticket
        SqlRowSet tickets = DriverDB.queryForRowSet("SELECT * FROM SMALLDB.TICKET WHERE ID_VISITEUR = '"+ id +"' AND DATE_RESERVATION = '"+date+"' AND ID_ATTRACTION = "+type_activite+" ");
        
        while(tickets.next()){
            String id_visiteur = tickets.getString("ID_VISITEUR");
            int id_attraction =  Integer.parseInt(tickets.getString("ID_ATTRACTION"));
            Date date_reservation = Date.valueOf(tickets.getString("DATE_RESERVATION"));
            double prix_billet = Double.parseDouble(tickets.getString("PRIX_BILLET"));
            Ticket ticket = new Ticket(id_visiteur, id_attraction, date_reservation, prix_billet);
            return ticket;
        }
        
        return null;
    }

    @Override
    public void updateTickets(JdbcTemplate DriverDB , String id, String oldType, String oldDate, String NewType, String newDate, String newNUmber) {
        // La premère étape est de supprimer les tickets en surplus
        this.SetNumberOfTickets(DriverDB, id, oldType, newNUmber, oldDate);
        // Et puis, quand j'ai le bon nombre de ticket, je peut enfin update
        DriverDB.execute("UPDATE SMALLDB.TICKET SET ID_ATTRACTION = '" + MainControlor.dicoOfType.get(NewType.toUpperCase()) + "' , DATE_RESERVATION = '" + newDate + "' WHERE ID_VISITEUR = '" + id + "' AND  ID_ATTRACTION= '" + MainControlor.dicoOfType.get(oldType.toUpperCase()) + "'");
    }

    public void SetNumberOfTickets(JdbcTemplate DriverDB , String id , String oldType , String newNUmber , String oldDate){
        SqlRowSet AllTickets = DriverDB.queryForRowSet("SELECT * FROM SMALLDB.TICKET WHERE ID_VISITEUR ='" + id + "' AND ID_ATTRACTION = '" + MainControlor.dicoOfType.get(oldType.toUpperCase()) + "' AND DATE_RESERVATION = '" + oldDate + "'" ) ;

        AllTickets.last();
        int rowCount = AllTickets.getRow();

        if(rowCount < Integer.parseInt(newNUmber)){
            // Si le nombre de ticket dans la db est plus petit que le nouveau nombre à atteindre, ca signifie que je dois ajouter le nombre de ticket
            int difference = Integer.parseInt(newNUmber) - rowCount;
            for(int i = 0 ; i < difference ; i++){
                Ticket ticket = new Ticket(id, MainControlor.dicoOfType.get(oldType.toUpperCase()), Date.valueOf(oldDate), MainControlor.priceType.get(oldType.toUpperCase()));
                this.saveTicket(ticket, DriverDB);
            }
        }else{
            // Sinon, je dois enlever des tickets
            int difference = rowCount - Integer.parseInt(newNUmber);
            for(int i = 0 ; i < difference ; i++){
                Ticket ticket = new Ticket(id, MainControlor.dicoOfType.get(oldType.toUpperCase()), Date.valueOf(oldDate), MainControlor.priceType.get(oldType.toUpperCase()));
                this.deleteTicket(ticket, DriverDB); // Mauvais bail, ca supprime tous les ticket qui ont cettes info, la prochaine fois, il faut pouvoir identifier chaque ticket même si ca vient d'un client
            }
            // Vu qu'il supprime tous les ticket précédent, faut que j'en créer au temps qu'il en faut, donc la difference en gros
            for(int i = 0 ; i < Integer.parseInt(newNUmber) ; i++){
                Ticket ticket = new Ticket(id, MainControlor.dicoOfType.get(oldType.toUpperCase()), Date.valueOf(oldDate), MainControlor.priceType.get(oldType.toUpperCase()));
                this.saveTicket(ticket, DriverDB);
            }


        }

    }
    
}
