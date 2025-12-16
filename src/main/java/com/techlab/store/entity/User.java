package com.techlab.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "APP_USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
	private Long id;
    @Getter @Setter
	private String name;
    @Getter @Setter
	private String email;
    @Getter @Setter
    private Boolean deleted;

}
