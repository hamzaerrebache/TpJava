package dao;

import model.Auteur;
import model.Livre;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public List<Livre> getAllLivres() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT l.*, a.nom, a.prenom, a.genre FROM LIVRE l " +
                "LEFT JOIN AUTEUR a ON l.matricule = a.matricule";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Livre livre = extractLivreFromResultSet(rs);
                livres.add(livre);
            }
        }
        return livres;
    }

    public Livre getLivreByIsbn(int isbn) throws SQLException {
        String query = "SELECT l.*, a.nom, a.prenom, a.genre FROM LIVRE l " +
                "LEFT JOIN AUTEUR a ON l.matricule = a.matricule WHERE l.ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractLivreFromResultSet(rs);
            }
        }
        return null;
    }

    public List<Livre> searchLivres(String critere, String valeur) throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String query = "";

        switch (critere) {
            case "titre":
                query = "SELECT l.*, a.nom, a.prenom, a.genre FROM LIVRE l " +
                        "LEFT JOIN AUTEUR a ON l.matricule = a.matricule " +
                        "WHERE l.titre LIKE ?";
                break;
            case "auteur":
                query = "SELECT l.*, a.nom, a.prenom, a.genre FROM LIVRE l " +
                        "LEFT JOIN AUTEUR a ON l.matricule = a.matricule " +
                        "WHERE CONCAT(a.prenom, ' ', a.nom) LIKE ? OR CONCAT(a.nom, ' ', a.prenom) LIKE ?";
                break;
            case "date":
                query = "SELECT l.*, a.nom, a.prenom, a.genre FROM LIVRE l " +
                        "LEFT JOIN AUTEUR a ON l.matricule = a.matricule " +
                        "WHERE YEAR(l.date_edition) = ? OR l.date_edition LIKE ?";
                break;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if ("auteur".equals(critere)) {
                pstmt.setString(1, "%" + valeur + "%");
                pstmt.setString(2, "%" + valeur + "%");
            } else if ("date".equals(critere)) {
                // Essayer de parser comme année ou utiliser comme pattern de date
                try {
                    int annee = Integer.parseInt(valeur);
                    pstmt.setInt(1, annee);
                    pstmt.setString(2, annee + "%");
                } catch (NumberFormatException e) {
                    // Si ce n'est pas une année, chercher par pattern de date
                    pstmt.setInt(1, 0); // Année invalide pour forcer le LIKE
                    pstmt.setString(2, "%" + valeur + "%");
                }
            } else {
                pstmt.setString(1, "%" + valeur + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                livres.add(extractLivreFromResultSet(rs));
            }
        }
        return livres;
    }

    public void createLivre(Livre livre) throws SQLException {
        String query = "INSERT INTO LIVRE (ISBN, titre, description, date_edition, editeur, matricule) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, livre.getIsbn());
            pstmt.setString(2, livre.getTitre());
            pstmt.setString(3, livre.getDescription());
            pstmt.setDate(4, new java.sql.Date(livre.getDateEdition().getTime()));
            pstmt.setString(5, livre.getEditeur());
            pstmt.setInt(6, livre.getMatricule());

            pstmt.executeUpdate();
        }
    }

    public void updateLivre(Livre livre) throws SQLException {
        String query = "UPDATE LIVRE SET titre=?, description=?, date_edition=?, " +
                "editeur=?, matricule=? WHERE ISBN=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getDescription());
            pstmt.setDate(3, new java.sql.Date(livre.getDateEdition().getTime()));
            pstmt.setString(4, livre.getEditeur());
            pstmt.setInt(5, livre.getMatricule());
            pstmt.setInt(6, livre.getIsbn());

            pstmt.executeUpdate();
        }
    }

    public void deleteLivre(int isbn) throws SQLException {
        String query = "DELETE FROM LIVRE WHERE ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, isbn);
            pstmt.executeUpdate();
        }
    }

    private Livre extractLivreFromResultSet(ResultSet rs) throws SQLException {
        Livre livre = new Livre();
        livre.setIsbn(rs.getInt("ISBN"));
        livre.setTitre(rs.getString("titre"));
        livre.setDescription(rs.getString("description"));
        livre.setDateEdition(rs.getDate("date_edition"));
        livre.setEditeur(rs.getString("editeur"));
        livre.setMatricule(rs.getInt("matricule"));

        // Créer l'objet Auteur si présent
        if (rs.getString("nom") != null) {
            Auteur auteur = new Auteur();
            auteur.setMatricule(rs.getInt("matricule"));
            auteur.setNom(rs.getString("nom"));
            auteur.setPrenom(rs.getString("prenom"));
            auteur.setGenre(rs.getString("genre"));
            livre.setAuteur(auteur);
        }

        return livre;
    }
}