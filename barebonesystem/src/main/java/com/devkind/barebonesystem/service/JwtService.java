package com.devkind.barebonesystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final static String SECRET_KEY = "2B4B6250655368566D597133743677397A244226452948404D635166546A576E";

    public String extractUsername(String tokenKey) {
        return extractClaim(tokenKey, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails){

        return Jwts
                .builder()
                .setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public Boolean isTokenValid(String tokenKey, UserDetails userDetails) {
//        if(isBlackListToken(tokenKey)){
//            return false;
//        }
        final String username = extractUsername(tokenKey);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(tokenKey);
    }

//    @Override
//    public Boolean isBlackListToken(String jwtToken) {
//        return repository.findByJwtToken(jwtToken) != null;
//    }

//    public void saveBlackListToken(String jwtToken) {
//        BlackListToken entity = new BlackListToken();
//        String username = extractUsername(jwtToken);
//        entity.setJwtToken(jwtToken);
//        entity.setEmail(username);
//        repository.save(entity);
//    }


    public Date extractExpiration(String tokenKey) {
        return extractClaim(tokenKey, Claims::getExpiration);
    }
    private boolean isTokenExpired(String tokenKey) {
        return extractExpiration(tokenKey).before(new Date());
    }

    public <T> T extractClaim(String tokenKey, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(tokenKey);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String tokenKey) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(tokenKey).getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
