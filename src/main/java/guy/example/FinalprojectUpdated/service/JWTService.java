package guy.example.FinalprojectUpdated.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService {
    private  final JwtEncoder jwtEncoder;
public  String jwtToken(Authentication authentication){
    var now=Instant.now();
    var scope=authentication
            .getAuthorities()
            .stream()
            .map(a->a.getAuthority())
            .collect(Collectors.joining(" "));

       var claims= JwtClaimsSet
               .builder()
               .issuer("self")
               .issuedAt(Instant.now())
               .subject(authentication.getName())
               .expiresAt(now.plus(1, ChronoUnit.HOURS))
               .claim("scope",scope)
               .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

}

}
