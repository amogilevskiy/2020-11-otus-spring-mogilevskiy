package otus.amogilevskiy.spring.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import otus.amogilevskiy.spring.domain.User;

import java.util.Collection;
import java.util.HashSet;

public class UserAdapter extends User implements UserDetails {

    public UserAdapter(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (var userAuthority : getUserAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(userAuthority.getAuthority()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}