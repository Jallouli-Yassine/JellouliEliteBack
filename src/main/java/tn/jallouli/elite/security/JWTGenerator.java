package tn.jallouli.elite.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import tn.jallouli.elite.modules.user.entity.UserEntity;
import tn.jallouli.elite.modules.user.repository.UserRepository;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
// howa el mere mtaa3 les annotations lkol
// This annotation indicates that this class is a Spring component,
// which allows it to be automatically detected and managed by the Spring container.
public class JWTGenerator {
    //bch nekho les info mta lues w nasnaa fibom l token
    private final UserRepository userRepo;
    private final Key key;

    public JWTGenerator(UserRepository userRepo, @Value("${jwt.secret}") String secret){
        this.userRepo=userRepo;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    //3 methods
    //1- generateToken: to create a JWT token based on the user's information
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION); // 24 hours in milliseconds
        //extract roles
        //declare une variable 'authorities' de type collection qui peut contenir des objets de type 'GrantedAuthority'.
        //le type '?' signifie n'importe quel type qui etend 'GrantedAuthority'.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // authentication howa yepresente l'utilisateur authentifié et contient des informations sur lui
        List<String> roles = authorities.stream()//traiter les éléments d'une collection de manière fonctionnelle.
                .map(GrantedAuthority::getAuthority)//transformer chaque élément d'une collection en un autre élément.
                .toList();//collecter les éléments d'un flux (stream) dans une liste.
        UserEntity user = userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        //creation du token :
        return Jwts.builder()
                .setSubject(username) //lentete
                .claim("user", Map.of(
                        "username",user.getUsername(),
                        "email",user.getEmail()
                ))
                .claim("roles", roles) // Ajouter les rôles en tant que revendication personnalisée
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact(); //bch nasn3ouha

    }


    //2- getUsernameFromToken: to extract the username from a valid JWT token.
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    //3- validateToken: to check if a given JWT token is valid and not expired.
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is expired or invalid",e.fillInStackTrace());
        }
    }

}