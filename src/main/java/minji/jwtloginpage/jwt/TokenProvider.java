package minji.jwtloginpage.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String Auth_Token="auth";

    private final String secret;
    private final long tokenValidityInMilliSecond;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity}") long tokenValidity){
        this.secret=secret;
        this.tokenValidityInMilliSecond=tokenValidity*1000;
    }

    // 빈이 생성된 이후 secret값을 base64 디코드 후 key에 할당
    @Override
    public void afterPropertiesSet(){
        byte[] bytes= Decoders.BASE64.decode(secret);
        this.key= Keys.hmacShaKeyFor(bytes);
    }

    //권한정보 이용해 토큰을 발행
    public String getToken(Authentication authentication){
        String authorities=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now=new Date().getTime();
        Date validateTime=new Date(now+this.tokenValidityInMilliSecond);    //만료시간 설정

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(Auth_Token,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validateTime)
                .compact();
    }

    //토큰을 받아 권한객체를 리턴(반대과정)
    public Authentication getAuthentication(String token){
        Claims claims=Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities=
                Arrays.stream(claims.get(Auth_Token).toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        //스프링 시큐리티의 유저 객체
        User user=new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(user,token,authorities);
    }

    //토큰의 유효성 검증
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SignatureException| MalformedJwtException e){
            log.info("잘못된 jwt 서명입니다.");
        }catch(ExpiredJwtException e){
            log.info("만료된 토큰입니다.");
        }catch(UnsupportedJwtException e){
            log.info("지원되지 않는 토큰입니다.");
        }catch(IllegalArgumentException e){
            log.info("올바르지 않은 토큰입니다.");
        }
        return false;
    }
}
