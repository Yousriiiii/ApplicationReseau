package com.dev_app.Model;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.jdbc.core.JdbcTemplate;

public interface TicketDao {
    // Cette interface permet de montrer toutes les opérations liée au ticket dont j'aurais besoin 
    ArrayList<Ticket> GetAllTickets(JdbcTemplate DriverDB , String id_visitor); // -> Méthode qui permet d'avoir tous les tickets
    void saveTicket(Ticket ticket, JdbcTemplate DriverDB); // -> Permet de sauvegarder le ticket dans la base de donnée
    
    // Partie qui concerne les tickets sauvegarder selon le suivie de session
    ArrayList<Ticket> GetAllTicketsOfSession(JdbcTemplate DriverDB , String id_visitor);
    void saveTicketOfSession(Ticket ticket, JdbcTemplate DriverDB);
    // Supprime les tickets dans le suivie de session
    void deleteTicketOfSession(Ticket ticket, JdbcTemplate DriverDB);

    // J'envoie un ticket selon l'id du visiteur, la date et le type d'activité
    Ticket GetTicket(JdbcTemplate DriverDB , String id , String date , int type_activite);

    // Supprime le ticket de la base de donnée
    void deleteTicket(Ticket ticket, JdbcTemplate DriverDB);

    // Mise à jour des tickets de l'utilisateur
    void updateTickets(JdbcTemplate DriverDB , String id , String oldType , String oldDate , String NewType , String newDate , String newNUmber);

}
