package tables;

public class Citoyens {
    private String nom;
    private int quantite;
    private String role;

    public Citoyens(String nom, int quantite, String role) {
        this.nom = nom;
        this.quantite = quantite;
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Citoyens{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", quantite=").append(quantite);
        sb.append(", role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
