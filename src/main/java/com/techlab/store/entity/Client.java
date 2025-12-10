package com.techlab.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Boolean deleted;


    public Long  getId(){
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail(){
        return  this.email;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDeleted(Boolean d){
        this.deleted = d;
    }

}
