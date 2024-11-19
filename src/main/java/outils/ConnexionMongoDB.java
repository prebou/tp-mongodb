package outils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnexionMongoDB {

    private String nomDatabase;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public ConnexionMongoDB(String nomDatabase) {
        this.nomDatabase = nomDatabase;

    }

    public MongoDatabase connect() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase(nomDatabase);
        System.out.println("Connexion réussi à la base de données : " + database.getName());

        return database;
    }


    public MongoDatabase getDatabase() {
        MongoDatabase bdd = connect();
        return bdd;
    }

    // Méthode pour fermer la connexion
    public void fermerConnexion() {
        mongoClient.close();
        System.out.println("Connexion fermée.");
    }


}
