package execute;

import outils.*;
import com.mongodb.client.MongoDatabase;


public class App {
    public static void main(String[] args) {
        ConnexionMongoDB connexionMongoDB = new ConnexionMongoDB("Royaume");
        MongoDatabase bdd = connexionMongoDB.getDatabase();
        GestionRessources gestionRessources = new GestionRessources(bdd);
        GestionCitoyens gestionCitoyens = new GestionCitoyens(bdd);
        GestionBatiments gestionBatiments = new GestionBatiments(bdd);
        GestionMissions gestionMissions = new GestionMissions(bdd);
        Gain gain = new Gain();
        gain.calculerGain("entrainement",true,5,gestionRessources,gestionMissions);


        gestionMissions.preparerMission("entrainement",5,10,5,gestionRessources,gestionCitoyens);

        //gestionBatiments.supprimer("immeuble");
        //gestionRessources.ajouter("Bois",500);
        //gestionBatiments.ajouter("immeuble",100,50,"CAF",2,gestionRessources);
        //gestionBatiments.ajouter("ecole",30,100,"primaire",1);
        //gestionBatiments.mettreAJour("immeuble",1,10,10,gestionRessources);
        //gestionCitoyens.ajouter("Paysans",50,"production de nourriture");
        //gestionCitoyens.mettreAJourQuantite("Agriculteur",60);
        //gestionCitoyens.mettreAJourRole("Agriculteur", "agriculture");
        //gestionCitoyens.supprimer("Agriculteur");
        //gestionCitoyens.filtrerParCle("quantite",100);
        //gestionCitoyens.filtrerParCle("role","agriculture");
        //gestionCitoyens.afficher();
        //gestionRessources.ajouter("Or",50);
        //gestionRessources.supprimer("Or");
        //gestionRessources.mettreAJour("Or",700);
        gestionRessources.afficher();
        gestionBatiments.afficher();
        //collection.insertOne(document);

        connexionMongoDB.fermerConnexion();
    }

}
