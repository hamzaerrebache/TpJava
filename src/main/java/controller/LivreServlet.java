package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AuteurDAO;
import dao.LivreDAO;
import model.Livre;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/livre")
public class LivreServlet extends HttpServlet {
    private LivreDAO livreDAO;
    private AuteurDAO auteurDAO;

    @Override
    public void init() {
        livreDAO = new LivreDAO();
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
                    listLivres(request, response);
                    break;
                case "search":
                    searchLivres(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteLivre(request, response);
                    break;
                default:
                    listLivres(request, response);
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
                insertLivre(request, response);
            } else if ("update".equals(action)) {
                updateLivre(request, response);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException(e);
        }
    }

    private void listLivres(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Livre> livres = livreDAO.getAllLivres();
        request.setAttribute("livres", livres);
        request.getRequestDispatcher("/WEB-INF/views/livre-list.jsp").forward(request, response);
    }

    private void searchLivres(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String critere = request.getParameter("critere");
        String valeur = request.getParameter("valeur");

        List<Livre> livres;
        if (valeur != null && !valeur.isEmpty()) {
            // Pour la recherche par date, s'assurer que le format est correct
            if ("date".equals(critere)) {
                // Le format vient du input type="date" en yyyy-MM-dd, on le garde tel quel
                System.out.println("Recherche par date: " + valeur);
            }
            livres = livreDAO.searchLivres(critere, valeur);
        } else {
            livres = livreDAO.getAllLivres();
        }

        request.setAttribute("livres", livres);
        request.setAttribute("critere", critere);
        request.setAttribute("valeur", valeur);
        request.getRequestDispatcher("/WEB-INF/views/livre-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("auteurs", auteurDAO.getAllAuteurs());
        request.getRequestDispatcher("/WEB-INF/views/livre-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        Livre livre = livreDAO.getLivreByIsbn(isbn);
        request.setAttribute("livre", livre);
        request.setAttribute("auteurs", auteurDAO.getAllAuteurs());
        request.getRequestDispatcher("/WEB-INF/views/livre-form.jsp").forward(request, response);
    }

    private void insertLivre(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Livre livre = extractLivreFromRequest(request);
        livreDAO.createLivre(livre);
        response.sendRedirect(request.getContextPath() + "/livre?action=list");
    }

    private void updateLivre(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        Livre livre = extractLivreFromRequest(request);
        livreDAO.updateLivre(livre);
        response.sendRedirect(request.getContextPath() + "/livre?action=list");
    }

    private void deleteLivre(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        livreDAO.deleteLivre(isbn);
        response.sendRedirect(request.getContextPath() + "/livre?action=list");
    }

    private Livre extractLivreFromRequest(HttpServletRequest request) throws ParseException {
        Livre livre = new Livre();
        livre.setIsbn(Integer.parseInt(request.getParameter("isbn")));
        livre.setTitre(request.getParameter("titre"));
        livre.setDescription(request.getParameter("description"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        livre.setDateEdition(sdf.parse(request.getParameter("dateEdition")));

        livre.setEditeur(request.getParameter("editeur"));
        livre.setMatricule(Integer.parseInt(request.getParameter("matricule")));

        return livre;
    }
}