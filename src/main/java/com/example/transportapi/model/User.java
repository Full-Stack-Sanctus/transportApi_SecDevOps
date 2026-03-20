package com.example.transportapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user", schema = "transport_schema")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", schema = "transport_schema",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // ✅ Defensive constructor
    public User(Long id, String email, String password, boolean enabled, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    // ✅ Defensive getter
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    // ✅ Defensive setter
    public void setRoles(Set<Role> roles) {
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }
}