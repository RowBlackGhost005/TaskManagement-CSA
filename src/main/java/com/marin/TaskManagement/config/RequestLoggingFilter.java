package com.marin.TaskManagement.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String client = "Anon";
        if(((HttpServletRequest) request).getUserPrincipal() != null){
            client = ((HttpServletRequest) request).getUserPrincipal().getName();
        }
        logger.info("Incoming request: {} {} client: {}", req.getMethod(), req.getRequestURI() , client);
        chain.doFilter(request, response);
    }

}
