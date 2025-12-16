package com.techlab.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private String email;
    @Setter @Getter
    private Boolean deleted;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("client")
    @Getter @Setter
    private Set<Order> orders = new HashSet<>();

}
