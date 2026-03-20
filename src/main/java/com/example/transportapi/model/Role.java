package com.example.transportapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // ✅ Defensive constructor
    public Role(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users != null ? new HashSet<>(users) : new HashSet<>();
    }

    // ✅ Defensive getter
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    // ✅ Defensive setter
    public void setUsers(Set<User> users) {
        this.users = users != null ? new HashSet<>(users) : new HashSet<>();
    }
}