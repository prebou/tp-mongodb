package outils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class GestionRessources {
    private MongoCollection<Document> collection;

    public GestionRessources(MongoDatabase database) {
        this.collection = database.getCollection("Ressources");
    }

    //méthode pour ajouter une ressource
    public void ajouter(String type, int quantite) {
        ;
        Document ressource = collection.find(new Document("type", type)).first();
        if (ressource != null) {
            int nouvelleQuantite = quantite + ressource.getInteger("quantite", 0);
            mettreAJour(type, nouvelleQuantite);
        } else {
            collection.insertOne(new Document("type", type).append("quantite", quantite));
        }

        System.out.println(type + " et " + quantite + " à bien été ajouté !");
    }

    //afficher la base de donnée
    public void afficher() {
        for (Document element : collection.find()) {
            System.out.println(element.toJson());
        }
    }

    //mettre a jour la valeur par la clé
    public void mettreAJour(String type, int quantite) {
        Document types = new Document("type", type);
        Document update = new Document("$set", new Document("quantite", quantite));
        collection.updateOne(types, update);
        System.out.println(type + " mise à jour jour par la valeur : " + quantite);
    }

    //supprimer une ligne
    public void supprimer(String type) {
        Document delete = new Document("type", type);
        collection.deleteOne(delete);
        System.out.println(type + " a été supprimer !");
    }

    public boolean verifierRessource(String type, int quantiteNecessaire) {
        Document ressource = collection.find(new Document("type", type))
                .sort(Sorts.descending("_id")) // Récupérer le document le plus récent
                .first();
        if (ressource != null) {
            int quantiteDisponible = ressource.getInteger("quantite", 0);
            return quantiteDisponible >= quantiteNecessaire;
        }
        return false;
    }

    public int getQuantite(String type) {
        Document ressource = collection.find(new Document("type", type)).sort(Sorts.descending("_id")).first();
        int quantiteDisponible = ressource.getInteger("quantite", 0);
        return quantiteDisponible;

    }

}
