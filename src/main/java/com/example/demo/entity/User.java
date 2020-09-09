package com.example.demo.entity;

import com.example.demo.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable( name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        this.password =  encoder.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
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
        return true;
    }
}
