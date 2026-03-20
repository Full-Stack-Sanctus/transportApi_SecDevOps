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

    // Safe getter
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    // Safe setter
    public void setUsers(Set<User> users) {
        this.users = new HashSet<>(users);
    }
}