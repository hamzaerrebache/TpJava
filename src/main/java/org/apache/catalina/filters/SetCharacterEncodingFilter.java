package org.apache.catalina.filters;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class SetCharacterEncodingFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
