package com.dev_app.Model;

import java.sql.Date;

public class Ticket {
    
    private String ID_VISITEUR;
    private int ID_ATTRACTION;
    private Date DATE_RESERVATION;
    private double PRIX_BILLET;


    public Ticket( String ID_VISITEUR, int ID_ATTRACTION, Date DATE_RESERVATION, double PRIX_BILLET ){
        // Le constructeur permet d'initialiser ce que contient un ticket -> Au niveau de la DB
        this.ID_VISITEUR=ID_VISITEUR;this.ID_ATTRACTION=ID_ATTRACTION;this.DATE_RESERVATION=DATE_RESERVATION;this.PRIX_BILLET=PRIX_BILLET;
    }

    /**
     * @return int return the ID_VISITEUR
     */
    public String getID_VISITEUR() {
        return ID_VISITEUR;
    }

    /**
     * @param ID_VISITEUR the ID_VISITEUR to set
     */
    public void setID_VISITEUR(String ID_VISITEUR) {
        this.ID_VISITEUR = ID_VISITEUR;
    }

    /**
     * @return String return the DATE_ACHAT
     */
    public Date getDATE_RESERVATION() {
        return DATE_RESERVATION;
    }

    /**
     * @param DATE_ACHAT the DATE_ACHAT to set
     */
    public void setDATE_RESERVATION(Date DATE_ACHAT) {
        this.DATE_RESERVATION = DATE_ACHAT;
    }

    /**
     * @return double return the PRIX_BILLET
     */
    public double getPRIX_BILLET() {
        return PRIX_BILLET;
    }

    /**
     * @param PRIX_BILLET the PRIX_BILLET to set
     */
    public void setPRIX_BILLET(double PRIX_BILLET) {
        this.PRIX_BILLET = PRIX_BILLET;
    }


    /**
     * @return String return the ID_ATTRACTION
     */
    public int getID_ATTRACTION() {
        return ID_ATTRACTION;
    }

    /**
     * @param ID_ATTRACTION the ID_ATTRACTION to set
     */
    public void setID_ATTRACTION(int ID_ATTRACTION) {
        this.ID_ATTRACTION = ID_ATTRACTION;
    }

}
