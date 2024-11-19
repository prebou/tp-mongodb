package outils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestionBatiments {


    private MongoCollection<Document> collection;

    public GestionBatiments(MongoDatabase database) {
        this.collection = database.getCollection("Batiments");
    }

    //méthode pour ajouter un batiment
    public void ajouter(String type, int coutBois, int coutPierre, String fonction, int niveau, GestionRessources gestionRessources) {
//        ConnexionMongoDB connexionMongoDB = new ConnexionMongoDB("Royaume");
//        MongoDatabase bdd = connexionMongoDB.getDatabase();
//        GestionRessources gestionRessources = new GestionRessources(bdd);
        boolean bois = gestionRessources.verifierRessource("Bois", coutBois);
        boolean pierre = gestionRessources.verifierRessource("Pierre", coutPierre);
        int dispoBois = gestionRessources.getQuantite("Bois");
        int dispoPierre = gestionRessources.getQuantite("Pierre");
        if (bois && pierre){
            gestionRessources.mettreAJour("Bois",dispoBois - coutBois);
            gestionRessources.mettreAJour("Pierre",dispoPierre - coutPierre);
            collection.insertOne(new Document("type", type).append("cout_bois", coutBois)
                    .append("cout_pierre",coutPierre).append("fonction",fonction).append("niveau",niveau));
        }
        else {
            int boisManquant = dispoBois - coutBois;
            int pierreMAnquant = dispoPierre - coutPierre;
            System.out.println("Pas assez de ressources :\n encore " + boisManquant
                    + " bois necessaires, \nencore " + pierreMAnquant + " pierre necessaires.");
        }
    }

    //afficher la base de donnée
    public void afficher() {
        for (Document element : collection.find()) {
            System.out.println(element.toJson());
        }
    }

    //mettre a jour la valeur par la clé
    public void mettreAJour(String type, int niveau, int coutBois, int coutPierre,GestionRessources gestionRessources) {

//        ConnexionMongoDB connexionMongoDB = new ConnexionMongoDB("Royaume");
//        MongoDatabase bdd = connexionMongoDB.getDatabase();
//        GestionRessources gestionRessources = new GestionRessources(bdd);
        int dispoBois = gestionRessources.getQuantite("Bois");
        int dispoPierre = gestionRessources.getQuantite("Pierre");
        if (dispoBois >= coutBois && dispoPierre >= coutPierre){
            gestionRessources.mettreAJour("Bois",dispoBois - coutBois);
            gestionRessources.mettreAJour("Pierre",dispoPierre - coutPierre);

            Document types = new Document("type", type);
            Document update = new Document("$inc", new Document("niveau", niveau).append("cout_bois", coutBois).append("cout_pierre",coutPierre));
            collection.updateOne(types, update);
            System.out.println(type + " mise à jour jour par la valeur : " + niveau);
        }
        else {
            int boisManquant = dispoBois - coutBois;
            int pierreMAnquant = dispoPierre - coutPierre;
            System.out.println("Pas assez de ressources :\n encore " + boisManquant
                    + " bois necessaires, \nencore " + pierreMAnquant + " pierre necessaires.");
        }

    }

    //supprimer une ligne
    public void supprimer(String type) {
        Document delete = new Document("type", type);
        collection.deleteOne(delete);
        System.out.println(type + " a été supprimer !");
    }


}
