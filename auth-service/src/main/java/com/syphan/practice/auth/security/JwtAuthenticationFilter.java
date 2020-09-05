package com.syphan.practice.auth.security;

import com.syphan.common.rest.security.JwtTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProperties jwtTokenProperties;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtTokenFromRequest(httpServletRequest);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtTokenProperties.getSecret().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            Integer id = claims.get("user_id", Integer.class);
            UserDetails userDetails = userDetailsService.loadUserById(id);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(jwtTokenProperties.getHeader());
        if (token != null) {
            return token.startsWith(jwtTokenProperties.getTokenPrefix())
                    ? token.substring(jwtTokenProperties.getTokenPrefix().length()).trim() : token.trim();
        } else return null;
    }
}
