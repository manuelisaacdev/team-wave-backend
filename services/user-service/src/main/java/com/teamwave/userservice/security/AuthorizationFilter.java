package com.teamwave.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
	private final JwtDecoder jwtDecoder;
	public static final String BEARER = "Bearer ";
	public static final String AUTHORIZATION = "authorization";

	public AuthorizationFilter(JwtDecoder jwtDecoder) {
		this.jwtDecoder = jwtDecoder;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
    	try {
			String token = Optional.ofNullable(request.getHeader(AUTHORIZATION)).map(authorization -> authorization.substring(BEARER.length()))
			.orElseThrow(() -> new IllegalArgumentException("Authorization access token is required to access this resource."));
			final Jwt jwt = jwtDecoder.decode(token);
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwt.getSubject(), null,  jwt.<List<String>>getClaim("authorities").stream().map(SimpleGrantedAuthority::new).toList()));
        	chain.doFilter(request, response);
		} catch (Exception e) {
	    	chain.doFilter(request, response);
		}
	}
}
