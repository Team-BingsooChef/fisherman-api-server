package my.fisherman.fisherman.security.filter.token;

import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    public JwtAuthenticationToken(
        Long userId,
        List<GrantedAuthority> authorities
    ) {
        super(authorities);
        super.setAuthenticated(true);
        super.setDetails(userId);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }
}