package com.code.community.auth.dto;

import com.code.community.comment.base.constant.FiledConstant;
import com.code.community.user.model.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String id;
    private String userName;
    private String password;
    private Boolean enabled;
    private String clientId;

    private Collection<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.enabled = (user.getState() == FiledConstant.STATE_EFFECTIVE);
        //TODO clientId authorities
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
