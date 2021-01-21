package minji.jwtloginpage.common.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;
    public static final String AUTH_HEADER="authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        String jwt=resolveToken(httpServletRequest);

        if(StringUtils.hasText(jwt)&&tokenProvider.validateToken(jwt)){
            //유효한 토큰인 경우
            Authentication authentication= tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("인증정보 저장 완료");
        }
        //유효하지 않은 경우
        else log.info("잘못된 토큰!");

        chain.doFilter(request,response);
    }

    //header를 통 토큰정보를 가져옴해
    private String resolveToken(HttpServletRequest request){
        String token=request.getHeader(AUTH_HEADER);
        if(StringUtils.hasText(token)&&token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
