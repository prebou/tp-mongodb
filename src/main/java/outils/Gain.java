package outils;

import org.bson.Document;

public class Gain {


    public Gain(){

    }

    // Méthode pour calculer le gain en fonction de la mission
    public void calculerGain(String nomMission, boolean missionReussie, int soldatsEnvoyes,GestionRessources gestionRessources, GestionMissions gestionMissions) {

        if (missionReussie) {
            // Calcul des gains en fonction des soldats envoyés (exemple)
            int gainBois = soldatsEnvoyes * 20; // 20 unités de bois par soldat envoyé
            int gainNourriture = soldatsEnvoyes * 10; // 10 unités de nourriture par soldat envoyé

            // Mise à jour des ressources
            gestionRessources.mettreAJour("Bois", gainBois);
            gestionRessources.mettreAJour("Nourriture", gainNourriture);

            System.out.println("Mission réussie ! Gain : " + gainBois + " Bois, " + gainNourriture + " Nourriture.");
        } else {
            System.out.println("Mission échouée. Aucun gain obtenu.");
        }

        // Mettre à jour le statut de la mission en fonction du résultat
        gestionMissions.mettreAJour(nomMission, new Document("status", missionReussie ? "Réussie" : "Échouée"));
    }

}
