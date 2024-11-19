package outils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class GestionMissions {

    private MongoCollection<Document> collection;

    public GestionMissions(MongoDatabase database) {
        this.collection = database.getCollection("Missions");
    }

    public void preparerMission(String nomMission, int soldatsNecessaires, int boisNecessaire, int nourritureNecessaire, GestionRessources gestionRessources, GestionCitoyens gestionCitoyens) {
        int dispoBois = gestionRessources.getQuantite("Bois");
        int dispoNourriture = gestionRessources.getQuantite("Nourriture");
        int dispoSoldat = gestionCitoyens.getQuantite("Soldats");

        Document ressource = collection.find(new Document("nom_mission", nomMission)).sort(Sorts.descending("_id")).first();

        if (ressource != null) {
            mettreAJour(nomMission, soldatsNecessaires, boisNecessaire, nourritureNecessaire, gestionRessources, gestionCitoyens);
            System.out.println("mise à jour reussie");
        } else {

            if (dispoBois >= boisNecessaire && dispoNourriture >= nourritureNecessaire && dispoSoldat >= soldatsNecessaires) {
                gestionRessources.mettreAJour("Bois", dispoBois - boisNecessaire);
                gestionRessources.mettreAJour("Nourriture", dispoNourriture - nourritureNecessaire);
                gestionCitoyens.mettreAJourQuantite("Soldat", dispoSoldat - soldatsNecessaires);
                collection.insertOne(new Document("nom_mission", nomMission).append("soldats_necessaires", soldatsNecessaires)
                        .append("bois_necessaires", boisNecessaire).append("nourriture_necessaires", nourritureNecessaire));
                System.out.println("Mission : " + nomMission + " a bien été ajouter");
            } else {
                int boisManquant = dispoBois - boisNecessaire;
                int nourritureManquant = dispoNourriture - nourritureNecessaire;
                int soldatManquant = dispoSoldat - soldatsNecessaires;
                System.out.println("Pas assez de ressources :\n encore " + boisManquant
                        + " bois necessaires, \nencore " + soldatManquant + " soldats necessaires, \nencore " + nourritureManquant + " nourritures necessaires.");
            }
        }

    }

    public void mettreAJour(String nomMission, int soldatsNecessaires, int boisNecessaire, int nourritureNecessaire, GestionRessources gestionRessources, GestionCitoyens gestionCitoyens) {
        int dispoBois = gestionRessources.getQuantite("Bois");
        int dispoNourriture = gestionRessources.getQuantite("Nourriture");
        int dispoSoldat = gestionCitoyens.getQuantite("Soldats");

        if (dispoBois >= boisNecessaire && dispoNourriture >= nourritureNecessaire && dispoSoldat >= soldatsNecessaires) {
            gestionRessources.mettreAJour("Bois", dispoBois - boisNecessaire);
            gestionRessources.mettreAJour("Nourriture", dispoNourriture - nourritureNecessaire);
            gestionCitoyens.mettreAJourQuantite("Soldat", dispoSoldat - soldatsNecessaires);

            Document types = new Document("nom_mission", nomMission);
            Document update = new Document("$inc", new Document("soldat_necessaires", soldatsNecessaires).append("bois_necessaires", boisNecessaire).append("nourriture_necessaires", nourritureNecessaire));
            collection.updateOne(types, update);

            System.out.println("Mission : " + nomMission + " a bien été modifier");
        } else {
            int boisManquant = dispoBois - boisNecessaire;
            int nourritureManquant = dispoNourriture - nourritureNecessaire;
            int soldatManquant = dispoSoldat - soldatsNecessaires;
            System.out.println("Pas assez de ressources :\n encore " + boisManquant
                    + " bois necessaires, \nencore " + soldatManquant + " soldats necessaires, \nencore " + nourritureManquant + " nourritures necessaires.");
        }
    }

    public void mettreAJour(String nomMission,Document document){
        Document types = new Document("nom_mission", nomMission);
        Document update = new Document("$set", document);
        collection.updateOne(types, update);
    }


}
