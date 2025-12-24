package kr.ac.jbnu.ksm.assignment2.security;

import kr.ac.jbnu.ksm.assignment2.user.entity.User;
import kr.ac.jbnu.ksm.assignment2.user.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Integer id;
    private final String loginId;
    private final String password;
    private final Role role;
    private final boolean active;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.active = user.isActive();
    }

    /** 컨트롤러에서 쓰기 위한 메서드 */
    public Integer getId()
    {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_USER / ROLE_ADMIN
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
