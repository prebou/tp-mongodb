package outils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class GestionCitoyens<U> {
    private MongoCollection<Document> collection;

    public GestionCitoyens(MongoDatabase database){
        this.collection = database.getCollection("Citoyens");
    }

    //méthode pour ajouter une ressource
    public void ajouter(String nom, int quantite, String role){
        Document ressource = collection.find(new Document("nom", nom)).first();
        if (ressource != null) {
            if (role.equals(ressource.get("role"))){
                System.out.println();
                int nouvelleQuantite = quantite + ressource.getInteger("quantite", 0);
                mettreAJourQuantite(nom, nouvelleQuantite);
            }

        } else {
            collection.insertOne(new Document("nom", nom).append("quantite",quantite).append("role",role));
        }
        System.out.println(nom + ", " + quantite +", " + " à bien été ajouté !");
    }

    //afficher la base de donnée
    public void afficher(){
        for (Document element : collection.find()){
            System.out.println(element.toJson());
        }
    }

    public void filtrerParCle(String cle, U valeur){
        for (Document element : collection.find(new Document(cle,valeur))){
            System.out.println(element.toJson());
        }
    }

    //mettre a jour la valeur par la clé
    public void mettreAJourQuantite(String nom, int quantite){
        Document types = new Document("nom",nom);
        Document update = new Document("$set",new Document("quantite",quantite));
        collection.updateOne(types,update);
        System.out.println(nom + " mise à jour jour par la quantité : " + quantite);
    }

    //mettre a jour la valeur par la clé
    public void mettreAJourRole(String nom, String role){
        Document types = new Document("nom",nom);
        Document update = new Document("$set",new Document("role",role));
        collection.updateOne(types,update);
        System.out.println(nom + " mise à jour jour par le role : " + role);
    }

    //supprimer une ligne
    public void supprimer(String nom){
        Document delete = new Document("nom",nom);
        collection.deleteOne(delete);
        System.out.println(nom + " a été supprimer !");
    }

    public int getQuantite(String nom) {
        Document ressource = collection.find(new Document("Nom", nom)).sort(Sorts.descending("_id")).first();
        int quantiteDisponible = ressource.getInteger("quantite", 0);
        return quantiteDisponible;
    }
}

