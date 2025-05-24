package my.project.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JWTValidatorFilter extends OncePerRequestFilter {

    private JWTConfig jwtUtils;

    public JWTValidatorFilter(JWTConfig jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verificar si el token es válido antes de procesarlo
        if (token == null || !token.startsWith("Bearer ") || token.length() <= 7) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        try {
            DecodedJWT decodedJWT = jwtUtils.validateToken(token);
            String username = jwtUtils.extractStringUserName(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities") != null
                    ? jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString()
                    : "";

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            SecurityContextHolder.clearContext(); // Limpiar contexto en caso de error
        }

        filterChain.doFilter(request, response);
    }
}
