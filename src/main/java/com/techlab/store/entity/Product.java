package com.techlab.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private Double price;
  private String category;
  private Integer stock;

  private Boolean deleted;
  private LocalDate deletedDate;

  public Long getId(){
      return this.id;
  }


  public void setStock(Integer stock){
      this.stock = stock;
  }

  public Integer getStock(){
      return this.stock;
  }



  public String getDescription() {
      return this.description = description;
  }

  public void setDescription(String description){
      this.description = description;
  }


  public String getCategory(){
      return this.category;
  }

  public void setCategory(String category){
      this.category = category;
  }


  public void setPrice(Double price){
      this.price = price;
  }

  public Double getPrice(){
      return this.price;
  }

  public String getName() {
      return this.name;
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
