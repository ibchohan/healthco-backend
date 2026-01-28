package com.appointment.entities;

import com.appointment.common.entity.HasUUIDAndIdAndAuditing;
import com.appointment.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "appointment_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends HasUUIDAndIdAndAuditing implements UserDetails {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "is_super_admin", nullable = false)
    private Boolean isSuperAdmin;

    @Column(name = "is_deactivated", nullable = false)
    private boolean isDeactivated = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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

    public void update(UserDto userCreationDto) {
        this.email = userCreationDto.getEmail();
        this.fullName = userCreationDto.getFullName();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void activateUser() {
        this.isDeactivated = false;
    }

    public void deactivateUser() {
        this.isDeactivated = true;
    }

    public boolean isActive() {
        return !isDeactivated;
    }
}
