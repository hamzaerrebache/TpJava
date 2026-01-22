package model;

import java.util.Date;

public class Livre {
    private int isbn;
    private String titre;
    private String description;
    private Date dateEdition;
    private String editeur;
    private int matricule;
    private Auteur auteur;

    public Livre() {
    }

    public Livre(int isbn, String titre, String description, Date dateEdition,
                 String editeur, int matricule) {
        this.isbn = isbn;
        this.titre = titre;
        this.description = description;
        this.dateEdition = dateEdition;
        this.editeur = editeur;
        this.matricule = matricule;
    }

    // Getters et Setters
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Date dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }
}