package com.syphan.practice.proxy.gateway.security;

import com.syphan.practice.proxy.gateway.config.JwtTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProperties jwtTokenProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtTokenFromRequest(httpServletRequest);
        if (token != null) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtTokenProperties.getSecret().getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();
                List<String> authorities = claims.get("authorities", List.class);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception ex) {
                logger.warn("Could not set user authentication in security context.", ex);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(jwtTokenProperties.getHeader());
        if (token != null && token.startsWith(jwtTokenProperties.getTokenPrefix())) {
            return token.substring(jwtTokenProperties.getTokenPrefix().length()).trim();
        } else return null;
    }
}
