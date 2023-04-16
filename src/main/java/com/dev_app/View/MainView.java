package com.dev_app.View;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;

import com.dev_app.Controlor.MainControlor;
import com.dev_app.Model.Client;
import com.dev_app.Model.Ticket;

import jakarta.servlet.http.HttpSession;

public class MainView {
    
    public static Resource SetConnection(){
        Resource resource = new ClassPathResource("static/sign-in.html"); 
        // static/pricing.html
        return resource;
    }

    public static ModelAndView SetPricing(String id){// J'ai l'identifiant du visiteur, je peut donc voir s'il a déjà des réservations
        
        ModelAndView mav = new ModelAndView("pricing");

        mav.addObject("id_visiteur", id);

        return mav;

    }

    public static ModelAndView SetReservation(){

        ModelAndView mav = new ModelAndView("reservation");

        return mav;

    }

    public static ModelAndView SetMyReservation(Client client , ArrayList<Ticket> listTickets){
        ModelAndView mav = new ModelAndView("offcanvas");
        mav.addObject("id_visiteur", client.getID_VISITEUR());
        mav.addObject("prenom", client.getPRENOM_VISITEUR());
        mav.addObject("nom", client.getNOM_VISITEUR());
        mav.addObject("age_visiteur", client.getAGE_VISITEUR());
        mav.addObject("taille_visiteur", client.getTAILLE_VISITEUR());
        mav.addObject("poids_visiteur", client.getPOIDS_VISITEUR());

        // Je crée un dictionnaire qui va me permettre de mapper les tickets dans ma liste
        ArrayList<Map<String,String>> listToSend = new ArrayList<Map<String,String>>();
        int iteration = 1;
        for (Ticket ticket : listTickets) {
            Map<String,String> dicoToSend = new HashMap<String,String>();
            dicoToSend.put("iteration", String.valueOf(iteration++));
            dicoToSend.put("date_reservation", String.valueOf(ticket.getDATE_RESERVATION()));
            dicoToSend.put("prix_reservation", String.valueOf(ticket.getPRIX_BILLET()));
            dicoToSend.put("type_reservation", MainControlor.dicoOfTypeV2.get(ticket.getID_ATTRACTION()));
            listToSend.add(dicoToSend);
        }

        mav.addObject("tickets", listToSend);

        mav.addObject("test", "yyyoooo");

        return mav;
    }

    public static ModelAndView SetPanier(String id , ArrayList<Ticket> list_ticket){
        ModelAndView mav = new ModelAndView("Panier");

        
        // Je crée une liste qui va contenir 3 dictionnaires, un pour le ski, parachute et paintball
        ArrayList<Map<String,String>> List_Ski_Parachute_Paintball = new ArrayList<Map<String,String>>();
        if( list_ticket.size()!=0){
            // Prémière étape, je trie ma liste => A la fin je dois avoir une liste de même ticket avec la même date
            ArrayList<ArrayList<Ticket>> list_ticket_sorted = new ArrayList<ArrayList<Ticket>>();

            Date Temp_Date = list_ticket.get(0).getDATE_RESERVATION();

            ArrayList<Ticket> Temp_List_Of_One_Reservation = new ArrayList<Ticket>();

            for (Ticket ticket_have_to_checked : list_ticket) {

                if(Temp_Date.equals(ticket_have_to_checked.getDATE_RESERVATION())){ // Si c'est la même date, c'est que c'est la même réservation
                    Temp_List_Of_One_Reservation.add(ticket_have_to_checked);
                }else{
                    // Sinon c'est un nouveau type de réservation donc on change Temp_Dtae
                    Temp_Date = ticket_have_to_checked.getDATE_RESERVATION();
                    // Je met la liste temporaire dans la liste trié
                    list_ticket_sorted.add(Temp_List_Of_One_Reservation);
                    // J'efface le contenue de la liste temporaire
                    Temp_List_Of_One_Reservation = new ArrayList<Ticket>();
                    Temp_List_Of_One_Reservation.add(ticket_have_to_checked);
                }
            }
            // à la fin de la boucle je met la liste qui n'a pas étaot mis dans la liste trié
            list_ticket_sorted.add(Temp_List_Of_One_Reservation);


            int iteration = 1;
            for (ArrayList<Ticket> arrayList : list_ticket_sorted) {
                Map<String,String> dico_1Reservation = new HashMap<String,String>();
                dico_1Reservation.put("iteration", String.valueOf(iteration++));
                dico_1Reservation.put("activity", MainControlor.dicoOfTypeV2.get(arrayList.get(0).getID_ATTRACTION()));
                dico_1Reservation.put("nbr_billet", String.valueOf(arrayList.size()));
                dico_1Reservation.put("date_reservation", String.valueOf(arrayList.get(0).getDATE_RESERVATION()));
                // Je calcule le prix totale
                String type_activity = MainControlor.dicoOfTypeV2.get(arrayList.get(0).getID_ATTRACTION());
                double prix_activity = (double) MainControlor.priceType.get(type_activity.toUpperCase());

                double somme = (double) prix_activity * arrayList.size();

                dico_1Reservation.put("prix_totale", String.valueOf(somme));

                List_Ski_Parachute_Paintball.add(dico_1Reservation);
            }
        }

        mav.addObject("id_visiteur", id);
        mav.addObject("tickets", List_Ski_Parachute_Paintball);

        return mav;
    }

    public static ModelAndView SetModificationPage(String id , String type , String date){

        ModelAndView mav = new ModelAndView("ModifReservation");

        mav.addObject("id", id);
        mav.addObject("type", type);
        mav.addObject("date", date);
        return mav;
    }
}
