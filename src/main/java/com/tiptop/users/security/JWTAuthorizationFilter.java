package com.tiptop.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultHeader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods",
                "GET,HEAD,OPTIONS,POST,PUT,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers,"
                +"Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method,"
                +"Access-Control-Request-Headers, Authorization");
                response.addHeader("Access-Control-Expose-Headers","Authorization, Access-ControlAllow-Origin,Access-Control-Allow-Credentials ");

        if (request.getMethod().equals("OPTIONS"))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token==null ||!token.startsWith(SecParams.PREFIX))
        {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(SecParams.PREFIX.length());

        Jwt<DefaultHeader,Claims> jwt=  Jwts.parser().setSigningKey(SecParams.SECRET).parse(token);
        Claims claims = jwt.getBody();


        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SecParams.SECRET)).build();


        String username = (String) claims.get("username");
        String role = (String) claims.get("role");

        List<String> roles = Collections.singletonList(role);

        Collection <GrantedAuthority> authorities = new ArrayList<>();

        for (String r :roles)
            authorities.add(new SimpleGrantedAuthority(r));

        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);

    }

}
