package com.example.transportapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // ✅ Defensive copy constructor
    public Role(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users != null ? new HashSet<>(users) : new HashSet<>();
    }

    // ✅ Defensive getter to prevent external modification
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    // ✅ Defensive setter
    public void setUsers(Set<User> users) {
        this.users = users != null ? new HashSet<>(users) : new HashSet<>();
    }
}