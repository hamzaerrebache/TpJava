package dao;

import model.Auteur;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteurDAO {

    public List<Auteur> getAllAuteurs() throws SQLException {
        List<Auteur> auteurs = new ArrayList<>();
        String query = "SELECT * FROM AUTEUR ORDER BY nom, prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Auteur auteur = extractAuteurFromResultSet(rs);
                auteurs.add(auteur);
            }
        }
        return auteurs;
    }

    public Auteur getAuteurByMatricule(int matricule) throws SQLException {
        String query = "SELECT * FROM AUTEUR WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, matricule);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractAuteurFromResultSet(rs);
            }
        }
        return null;
    }

    public void createAuteur(Auteur auteur) throws SQLException {
        String query = "INSERT INTO AUTEUR (nom, prenom, genre) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, auteur.getNom());
            pstmt.setString(2, auteur.getPrenom());
            pstmt.setString(3, auteur.getGenre());

            pstmt.executeUpdate();
        }
    }

    public void updateAuteur(Auteur auteur) throws SQLException {
        String query = "UPDATE AUTEUR SET nom=?, prenom=?, genre=? WHERE matricule=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, auteur.getNom());
            pstmt.setString(2, auteur.getPrenom());
            pstmt.setString(3, auteur.getGenre());
            pstmt.setInt(4, auteur.getMatricule());

            pstmt.executeUpdate();
        }
    }

    public void deleteAuteur(int matricule) throws SQLException {
        String query = "DELETE FROM AUTEUR WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, matricule);
            pstmt.executeUpdate();
        }
    }

    private Auteur extractAuteurFromResultSet(ResultSet rs) throws SQLException {
        Auteur auteur = new Auteur();
        auteur.setMatricule(rs.getInt("matricule"));
        auteur.setNom(rs.getString("nom"));
        auteur.setPrenom(rs.getString("prenom"));
        auteur.setGenre(rs.getString("genre"));
        return auteur;
    }
}