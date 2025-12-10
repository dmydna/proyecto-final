package com.techlab.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
    private Boolean deleted;



    public Long  getId(){
        return this.id;
    }

    public void setMail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDeleted(Boolean Deleted){
        this.deleted = Deleted;
    }

    public Boolean getDeleted(){
        return this.deleted;
    }
}
