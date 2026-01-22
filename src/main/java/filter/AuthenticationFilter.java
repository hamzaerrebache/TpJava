package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        // Pages publiques (login et ressources statiques)
        boolean isPublicPage = uri.endsWith("login") ||
                uri.endsWith("login.jsp") ||
                uri.contains("/css/") ||
                uri.contains("/js/") ||
                uri.contains("/images/");

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isPublicPage || isLoggedIn) {
            // Vérification des autorisations pour les pages d'administration
            if (isLoggedIn && !isPublicPage) {
                User user = (User) session.getAttribute("user");

                // Visiteur ne peut accéder qu'à la liste des livres
                if (user.isVisiteur() &&
                        (uri.contains("create") || uri.contains("edit") ||
                                uri.contains("delete") || uri.contains("auteur"))) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/livre?action=list");
                    return;
                }
            }
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}