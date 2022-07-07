package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestUrlParameterAuthFilter extends AbstractCustomAuthFilter {

    public RestUrlParameterAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("apiSecret");
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
        return request.getParameter("apiKey");
    }
}
