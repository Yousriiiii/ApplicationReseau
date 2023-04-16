package com.dev_app.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ClientDaoImpl implements ClientDao{

    @Override
    public boolean CustomerExist(JdbcTemplate DriverDB, String mail, String password) {
        SqlRowSet All_Mails_Password = DriverDB.queryForRowSet("SELECT MAILS,PASSWORD FROM SMALLDB.VISITEURS;");
            
        while(All_Mails_Password.next()){

            if(All_Mails_Password.getString("MAILS").equals(mail) && All_Mails_Password.getString("PASSWORD").equals(password)){
                return true; // Si le client existe je retourne vrai sinon je met à false
            }

        }
        return false;

    }

    @Override
    public String GetId(JdbcTemplate DriverDB, String mail, String password) {
        // jean.dupont@mail.com     	mdp1234
        SqlRowSet ID_User = DriverDB.queryForRowSet("SELECT ID_VISITEUR FROM SMALLDB.VISITEURS WHERE MAILS='" + mail  +  "' AND PASSWORD='"+ password  +"'");

        String id = "";

        while(ID_User.next()){
            id = ID_User.getString("ID_VISITEUR");
        }

        return id;        
    }

    @Override
    public boolean CustomerHaveTicket(JdbcTemplate DriverDB, String id) {
        SqlRowSet ID_Ticket = DriverDB.queryForRowSet("SELECT ID_BILLET FROM SMALLDB.TICKET WHERE ID_VISITEUR = '"+ id +"'");

        while(ID_Ticket.next()){
            return true;
        }

        return false;
    }

    @Override
    public Client GetData(JdbcTemplate DriverDB, String id) {
        
        SqlRowSet sqlRowSetClient = DriverDB.queryForRowSet("SELECT * FROM SMALLDB.VISITEURS WHERE ID_VISITEUR = '"+ id +"'");

        while(sqlRowSetClient.next()){
            return new Client(sqlRowSetClient.getString("ID_VISITEUR"), sqlRowSetClient.getString("NOM_VISITEUR"), sqlRowSetClient.getString("PRENOM_VISITEUR"), sqlRowSetClient.getString("AGE_VISITEUR"), sqlRowSetClient.getString("TAILLE_VISITEUR"), sqlRowSetClient.getString("POIDS_VISITEUR"), sqlRowSetClient.getString("MAILS"), sqlRowSetClient.getString("PASSWORD"));
        }

        return null;

    }
    
}
