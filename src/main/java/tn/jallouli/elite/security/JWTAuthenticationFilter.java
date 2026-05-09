package tn.jallouli.elite.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JWTAuthenticationFilter extends OncePerRequestFilter {


    private final JWTGenerator jwtGenerator;

    private final CustomUserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTGenerator jwtGenerator, CustomUserDetailsService userDetailsService) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //extraire le jwt de la requete http en appelant la methode getJwtFromRequest()
        String token = getJwtFromRequest(request);
        try {
            //verifier si le jwt est valide en appelant la methode validateToken() de la
            if (token != null && jwtGenerator.validateToken(token)) {
                //extraire le username du jwt
                String username = jwtGenerator.getUsernameFromToken(token);
                //charger les details de l'utilisateur apartir bd
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //creer un token d'authentification en utilisant les details de l'utilisateur et les autorisation(roles) associes a cet utilisateur
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //mettre l'authentification dans le contexte de securite de spring ce qui indique a spring que l'utilisateur est authentifie pour cette requete
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            // Ignorer l'exception pour que le contexte de sécurité reste vide.
            // Spring Security renverra automatiquement un 401 via JWTAuthEntryPoint.
        }
        //pour activer le filtrage
        filterChain.doFilter(request, response);
    }

    //methode pour extraire le jwt depuis l'entete "Authorization" de la requete http
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}