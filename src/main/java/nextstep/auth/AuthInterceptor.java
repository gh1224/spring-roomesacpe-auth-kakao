package nextstep.auth;

import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(AUTHORIZATION);
        String token = jwtTokenProvider.parseTokenFromHeader(authHeader);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
