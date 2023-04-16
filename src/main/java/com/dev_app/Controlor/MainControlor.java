package com.dev_app.Controlor;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.alibaba.fastjson.JSONObject;
import com.dev_app.Model.*;
import com.dev_app.View.MainView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class MainControlor {
    
    public static Map<String,Integer> priceType = new HashMap<String,Integer>(){{
        put("SKI", 20);
        put("PARACHUTE",50);
        put("PAINTBALL", 30);
    }};
    public static Map<String,Integer> dicoOfType = new HashMap<String,Integer>(){{
        put("SKI", 1);
        put("PARACHUTE",2);
        put("PAINTBALL", 3);
    }};
    public static Map<Integer,String> dicoOfTypeV2 = new HashMap<Integer,String>(){{
        put(1, "Ski");
        put(2,"Parachute");
        put(3, "Paintball");
    }};


    @RestController // Une classe annotée avec @RestController indique qu'il s'agit d'un contrôleur spécialisé pour le développement d'API Web.
                    //@RestController est simplement une annotation qui regroupe @Controller et @ResponseBody.
    public class MyController {
        
        @Autowired private JdbcTemplate jdbcTemplate; // JDBC (java database c?) me permet d'être connecter à la base de donnée H2

        ClientDao clientDao = new ClientDaoImpl();
        TicketDao ticketDao = new TicketDaoImpl();


        @GetMapping("/") // Page de connexion
        public Resource connection(HttpSession session) {

            // Dans le suivie de session, je met déjà ce que va contenir le suivie, car j'aurais besoin de lister des variables
            ArrayList<Ticket> list_ticket = new ArrayList<Ticket>(); // J'ai juste besoin d'une liste de ticket qui reste dans le suivie de session et qui devront être confirmer dans le panier
            // Et ensuite, je le met dans le suivie de session
            session.setAttribute("list_ticket", list_ticket);

            // à la racine, on met directement la page de connexion pour le client
            Resource resource = MainView.SetConnection();
            // Créer une réponse HTTP contenant le contenu du fichier HTML
            return resource;
        }

        // region Je récupére le mail et mdp pour vérifier si le visiteur existe déjà
        // Requête post qui permet de récupéré le mail et mdp du visiteur
        @PostMapping("/") // Je redirige vers la page d'accueil
        public RedirectView redirect_to_pricing(@RequestParam("mail") String mail, @RequestParam("password") String password) throws SQLException {
            // En paramétre, je récupére le mail et le password, mnt il faut que je vois si l'utilisateur existe dans la base de donnée
            // Si l'utilisateur existe, je le redirige vers la page d'accueil
            // Si l'utilisateur ne figure pas dans la base de donnée, je le rajoute dans la base de donnée et je le redirige vers la page d'accueil
            // FLEMME de créer une page html pour juste faire l'inscription
            
            String idClient = "1234"; // Cette variable contient l'id du client

            boolean ClientExist = clientDao.CustomerExist(jdbcTemplate,mail, password);

            // Si le client existe, alors on récupére son id et je redirige vers un nouveau chemin

            if (ClientExist){
                idClient = clientDao.GetId(jdbcTemplate,mail, password);
            }else{
                // Je crée un nouveau client
            }

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://127.0.0.1:8080/reservation?id=" + idClient);
            return redirectView;
        }

        // endregion

        // region Je montre la page d'accueil du visiteur
        // méthode GET de l'api qui montre la page où le choix de réservation est montrer
        @RequestMapping(value="reservation", method = RequestMethod.GET) // Dans cette méthode, je perçois une requête GET dans le chemin /home?id=... => ICI je dois donc récupéré l'id du client
        public ModelAndView Pricing(@RequestParam("id") String id , HttpSession session) throws SQLException {
            return MainView.SetPricing(id);
        }

        // endregion

        // region Je montre la page ou on peut réserver pour faire du ski,paintball, etc

        @GetMapping("/reservation/{id}/SKI")
        public ModelAndView page_ski(@PathVariable("id") String id ){
            return MainView.SetReservation();
        }   
        @GetMapping("/reservation/{id}/PARACHUTE")
        public ModelAndView page_parachute(@PathVariable("id") String id ){
            return MainView.SetReservation();
        }
        @GetMapping("/reservation/{id}/PAINTBALL")
        public ModelAndView page_paintball(@PathVariable("id") String id ){
            return MainView.SetReservation();
            // lucie.martin@mail.com        azerty
        }


        // endregion

        @PostMapping("/reservation/{id}/{type}") // Post -> Création d'une nouvelle réservation mais sans la confirmation donc je le met dans le suivie de session
        public RedirectView show_reservation_page(@PathVariable("id") String id_visiteur , @PathVariable("type") String type , @RequestParam("date") Date date, @RequestParam("number") int number_of_client , HttpSession session){
            
            ArrayList<Ticket> list_ticket = (ArrayList<Ticket>) session.getAttribute("list_ticket");
            if(list_ticket==null || list_ticket.size() == 0){list_ticket=new ArrayList<Ticket>();}
            // Première étape: je crée un nouveau ticket
            int type_activity = MainControlor.dicoOfType.get(type); // -> Je récupére le chiffre qui indique le type d'activité
            int prix_billet = MainControlor.priceType.get(type);
            Ticket ticket = new Ticket(id_visiteur, type_activity , date , prix_billet);
            // Et selon le nombre de participant, j'insére les ticket dans une liste et je le met dans le suivie de session
            for(int i = 0 ; i<number_of_client;i++){
                list_ticket.add(ticket); // Je met les tickets dans cette liste et puis je me met dans le suivie de session
                this.ticketDao.saveTicketOfSession(ticket , this.jdbcTemplate); // Je sauvegarde les ticket mais dans une db où les données sont temporaire
            }
            // Et je remplace la liste des ticket par le nouveau
            session.setAttribute("list_ticket", list_ticket);
            // et je redirige vers la page principal
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://127.0.0.1:8080/reservation?id=" + id_visiteur);
            return redirectView;
        }

        @GetMapping("MesReservation/{id}") // Dans cette page, je montre toutes les réservations
        public ModelAndView show_mesReservation_page(@PathVariable("id") String id){
            // Première étape, je prend toutes les informations du client
            Client client = this.clientDao.GetData(jdbcTemplate, id); // Je prend les informations du client
            ArrayList<Ticket> listTickets = this.ticketDao.GetAllTickets(jdbcTemplate, id);
            if(listTickets == null){// si y a pas de ticket, je vais voir s'il n'y a pas dans la db
                listTickets = ticketDao.GetAllTicketsOfSession(jdbcTemplate, id);
            }
            return MainView.SetMyReservation(client , listTickets);
        }
    
        @RequestMapping(value="panier", method = RequestMethod.GET) // Je montre la page du panier
        public ModelAndView show_panier(@RequestParam("id") String id , HttpSession session){

            // Mnt, je prend la liste de ticket que le client a pris
            ArrayList<Ticket> list_ticket = (ArrayList<Ticket>) session.getAttribute("list_ticket");
            
            if(list_ticket == null || list_ticket.size() == 0){// si y a pas de ticket, je vais voir s'il n'y a pas dans la db
                list_ticket = ticketDao.GetAllTicketsOfSession(jdbcTemplate, id);
            }

            return MainView.SetPanier(id , list_ticket);
        }

        @RequestMapping(value="panier", method = RequestMethod.POST) // Je sauvegarde les réservation faites par le client
        public RedirectView Save_Panier(@RequestParam("id") String id , HttpSession session){
            // La je prend tous les ticket qui est dans la db et je sauvegarde
            ArrayList<Ticket> list_ticket = ticketDao.GetAllTicketsOfSession(jdbcTemplate, id);

            for (Ticket ticket : list_ticket) {
                ticketDao.saveTicket(ticket, jdbcTemplate);
                // Et puis je vide les ticket de la session de la db mais aussi du suivie de session
                ticketDao.deleteTicketOfSession(ticket, jdbcTemplate);
            }

            // Je vide le tableau du suivie de session
            ArrayList<Ticket> list_ticketSuivieSession = (ArrayList<Ticket>) session.getAttribute("list_ticket");
            if(list_ticketSuivieSession != null){
                if(list_ticketSuivieSession.size() != 0){
                    list_ticketSuivieSession.clear(); 
                    session.setAttribute("list_ticket", list_ticketSuivieSession);
                }
            }


            // et je redirige vers la page principal
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://127.0.0.1:8080/reservation?id=" + id);
            return redirectView;
        }

        @RequestMapping(value="MesReservation", method = RequestMethod.DELETE) // La requête delete est ici
        public RedirectView DeleteOneReservation(@RequestParam("id") String id , @RequestParam("date") String date , @RequestParam("type") String type_reservation){
            
            Ticket ticket = this.ticketDao.GetTicket(jdbcTemplate, id, date, MainControlor.dicoOfType.get(type_reservation.toUpperCase()));
            if(ticket != null){ // Je met ca pour être sur d'avoir eu le ticket
                this.ticketDao.deleteTicket(ticket, jdbcTemplate);
            }
            
        
            // et je redirige vers la page principal
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://127.0.0.1:8080/MesReservation?id=" + id);
            return redirectView;
            
        }

        @GetMapping("ModifReservation/{id}/{type}/{date}")
        public ModelAndView Show_Modification_Page(@PathVariable("id") String id , @PathVariable("type") String type_reservation , @PathVariable("date") String date){
            // Je vais montrer la page avec laquelle l'utlisateur va pouvoir changer ca réservation
            return MainView.SetModificationPage(id , type_reservation , date);
        }
    
        @PutMapping("ModifReservation/{id}/{type}/{date}")
        public void Update_Tickets(@PathVariable("id") String id , @PathVariable("type") String type_reservation , @PathVariable("date") String date , HttpServletRequest request,HttpServletResponse response) throws IOException{
            BufferedReader  BF_Body = request.getReader(); // Je lit le buffer du body de la requête http
            String  JSON = BF_Body.readLine(); // Je prend ce qu'il y a en chaîne de caractère
            Map<String,String> jsonParsed = (Map<String, String>) JSONObject.parse(JSON); // Je parse le json
            String newDate = jsonParsed.get("date");
            String newTypeActivity = jsonParsed.get("type");
            String newNumber = jsonParsed.get("number");

            // Avec ces nouvelle information, je vais update les billets
            ticketDao.updateTickets(jdbcTemplate, id, type_reservation, date, newTypeActivity, newDate, newNumber);
        }
    
    }


    // je configure Thymeleaf pour rechercher les fichiers de template HTML dans le répertoire "templates" du classpath
    // ET je dit à spring d'utiliser thymeleaf comme moteur de template
    @Bean
    // region début du bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
 
    private TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
    }
 
    private ITemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
    //endregion
}