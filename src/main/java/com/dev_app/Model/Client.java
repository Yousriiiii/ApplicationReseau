package com.dev_app.Model;

public class Client {
    
    private String ID_VISITEUR;
    private String NOM_VISITEUR;
    private String PRENOM_VISITEUR;
    private String AGE_VISITEUR;
    private String TAILLE_VISITEUR;
    private String POIDS_VISITEUR;
    private String MAILS;
    private String PASSWORD;

    public Client(String ID_VISITEUR , String NOM_VISITEUR, String PRENOM_VISITEUR, String AGE_VISITEUR, String TAILLE_VISITEUR, String POIDS_VISITEUR, String MAILS, String PASSWORD){
        this.ID_VISITEUR=ID_VISITEUR;this.NOM_VISITEUR=NOM_VISITEUR;this.PRENOM_VISITEUR=PRENOM_VISITEUR;this.AGE_VISITEUR=AGE_VISITEUR;this.TAILLE_VISITEUR=TAILLE_VISITEUR;this.POIDS_VISITEUR=POIDS_VISITEUR;this.MAILS=MAILS;this.PASSWORD=PASSWORD;
    }

    /**
     * @return String return the ID_VISITEUR
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
     * @return String return the NOM_VISITEUR
     */
    public String getNOM_VISITEUR() {
        return NOM_VISITEUR;
    }

    /**
     * @param NOM_VISITEUR the NOM_VISITEUR to set
     */
    public void setNOM_VISITEUR(String NOM_VISITEUR) {
        this.NOM_VISITEUR = NOM_VISITEUR;
    }

    /**
     * @return String return the PRENOM_VISITEUR
     */
    public String getPRENOM_VISITEUR() {
        return PRENOM_VISITEUR;
    }

    /**
     * @param PRENOM_VISITEUR the PRENOM_VISITEUR to set
     */
    public void setPRENOM_VISITEUR(String PRENOM_VISITEUR) {
        this.PRENOM_VISITEUR = PRENOM_VISITEUR;
    }

    /**
     * @return String return the AGE_VISITEUR
     */
    public String getAGE_VISITEUR() {
        return AGE_VISITEUR;
    }

    /**
     * @param AGE_VISITEUR the AGE_VISITEUR to set
     */
    public void setAGE_VISITEUR(String AGE_VISITEUR) {
        this.AGE_VISITEUR = AGE_VISITEUR;
    }

    /**
     * @return String return the TAILLE_VISITEUR
     */
    public String getTAILLE_VISITEUR() {
        return TAILLE_VISITEUR;
    }

    /**
     * @param TAILLE_VISITEUR the TAILLE_VISITEUR to set
     */
    public void setTAILLE_VISITEUR(String TAILLE_VISITEUR) {
        this.TAILLE_VISITEUR = TAILLE_VISITEUR;
    }

    /**
     * @return String return the POIDS_VISITEUR
     */
    public String getPOIDS_VISITEUR() {
        return POIDS_VISITEUR;
    }

    /**
     * @param POIDS_VISITEUR the POIDS_VISITEUR to set
     */
    public void setPOIDS_VISITEUR(String POIDS_VISITEUR) {
        this.POIDS_VISITEUR = POIDS_VISITEUR;
    }

    /**
     * @return String return the MAILS
     */
    public String getMAILS() {
        return MAILS;
    }

    /**
     * @param MAILS the MAILS to set
     */
    public void setMAILS(String MAILS) {
        this.MAILS = MAILS;
    }

    /**
     * @return String return the PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * @param PASSWORD the PASSWORD to set
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

}
