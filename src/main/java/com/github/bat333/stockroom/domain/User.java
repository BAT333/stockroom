package com.github.bat333.stockroom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "parts")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "emails",nullable = false,unique = true)
    private String email;
    @Column(name = "passcodes",nullable = false,unique = true)
    private String passcode;
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeRoles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == TypeRoles.ADMIN){
            return List.of(new SimpleGrantedAuthority(TypeRoles.ADMIN.getValues()),new SimpleGrantedAuthority(TypeRoles.USER.getValues()));
        }else{
            return List.of(new SimpleGrantedAuthority(TypeRoles.USER.getValues()));
        }
    }

    @Override
    public String getPassword() {
        return this.passcode;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
