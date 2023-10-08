package listener;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Boolean isAuthenticated = false;
        Boolean sessionExists = session != null;
        Boolean isLoginPage = request.getRequestURI().equals("/rawsite_war/introAut");
        Boolean isRegPage = request.getRequestURI().equals("/rawsite_war/introReg");
        String username = (String) session.getAttribute("username");

        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated == null) {
                isAuthenticated = false;
            }
        }

        if(!isAuthenticated && isLoginPage) {
            filterChain.doFilter(request, response);
        } else if(isAuthenticated && isLoginPage) {
            request.setAttribute("username", username);
            request.getRequestDispatcher("/jsp/choice.jsp").forward(request, response);
        }

        if(isRegPage) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
