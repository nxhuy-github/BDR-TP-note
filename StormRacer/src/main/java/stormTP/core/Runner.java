package stormTP.core;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Runner {

    long id = -1;
    long top = -1;
    int position = -1;
    int nbDevant = -1;
    int nbDerriere = -1;
    int total = -1;
    String rang = "";

    String nom = "";

    public Runner() {

    }

    public Runner(long id, String name, int before, int after, int total, int position, long top) {
        this.id = id;
        this.nom = name;
        this.nbDevant = before;
        this.nbDerriere = after;
        this.total = total;
        this.position = position;
        this.top = top;
    }
    
    public Runner(long id, String name, int total, String rang, long top) {
        this.id = id;
        this.nom = name;
        this.total = total;
        this.rang = rang;
        this.top = top;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTop() {
        return top;
    }

    public void setTop(long top) {
        this.top = top;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getRang() {
        return rang;
    }

    public int getNbDevant() {
        return nbDevant;
    }

    public void setNbDevant(int nbDevant) {
        this.nbDevant = nbDevant;
    }

    public int getNbDerriere() {
        return nbDerriere;
    }

    public void setNbDerriere(int nbDerriere) {
        this.nbDerriere = nbDerriere;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getJSON_V1() {
        JsonObjectBuilder r = null;
        r = Json.createObjectBuilder();
        /* construction de l'objet JSON résultat */
        r.add("id", this.id);
        r.add("top", this.top);
        r.add("nom", this.nom);
        r.add("position", this.position);
        r.add("nbDevant", this.nbDevant);
        r.add("nbDerriere", this.nbDerriere);
        r.add("total", this.total);

        return r.build().toString();
    }
    
    public String getJSON_V2() {
        JsonObjectBuilder r = null;
        r = Json.createObjectBuilder();
        /* construction de l'objet JSON résultat */
        r.add("id", this.id);
        r.add("top", this.top);
        r.add("nom", this.nom);
        r.add("rang", this.rang);
        r.add("total", this.total);

        return r.build().toString();
    }
}
