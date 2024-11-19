package tables;

public class Ressources {
    private String type;
    private int quantite;

    public Ressources(String type, int quantite){
        this.type = type;
        this.quantite = quantite;
    }

    public void AfficherDetails(){
        System.out.println("type : " + type + "\nquantite : " + quantite);
    }

}
