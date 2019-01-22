package io.redspark.thot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private final ThotUserDetailsService thotUserDetailsService;

    public JWTAuthenticationFilter(ThotUserDetailsService thotUserDetailsService) {
        this.thotUserDetailsService = thotUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        Authentication authentication = TokenAuthenticationService.getAuthentication(httpServletRequest, thotUserDetailsService);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(servletRequest, servletResponse);
    }
}
