package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AuteurDAO;
import model.Auteur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/auteur")
public class AuteurServlet extends HttpServlet {
    private AuteurDAO auteurDAO;

    @Override
    public void init() {
        auteurDAO = new AuteurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listAuteurs(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteAuteur(request, response);
                    break;
                default:
                    listAuteurs(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                insertAuteur(request, response);
            } else if ("update".equals(action)) {
                updateAuteur(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listAuteurs(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Auteur> auteurs = auteurDAO.getAllAuteurs();
        request.setAttribute("auteurs", auteurs);
        request.getRequestDispatcher("/WEB-INF/views/auteur-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auteur-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int matricule = Integer.parseInt(request.getParameter("matricule"));
        Auteur auteur = auteurDAO.getAuteurByMatricule(matricule);
        request.setAttribute("auteur", auteur);
        request.getRequestDispatcher("/WEB-INF/views/auteur-form.jsp").forward(request, response);
    }

    private void insertAuteur(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Auteur auteur = extractAuteurFromRequest(request);
        auteurDAO.createAuteur(auteur);
        response.sendRedirect(request.getContextPath() + "/auteur?action=list");
    }

    private void updateAuteur(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Auteur auteur = extractAuteurFromRequest(request);
        auteur.setMatricule(Integer.parseInt(request.getParameter("matricule")));
        auteurDAO.updateAuteur(auteur);
        response.sendRedirect(request.getContextPath() + "/auteur?action=list");
    }

    private void deleteAuteur(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int matricule = Integer.parseInt(request.getParameter("matricule"));
        auteurDAO.deleteAuteur(matricule);
        response.sendRedirect(request.getContextPath() + "/auteur?action=list");
    }

    private Auteur extractAuteurFromRequest(HttpServletRequest request) {
        Auteur auteur = new Auteur();
        auteur.setNom(request.getParameter("nom"));
        auteur.setPrenom(request.getParameter("prenom"));
        auteur.setGenre(request.getParameter("genre"));
        return auteur;
    }
}