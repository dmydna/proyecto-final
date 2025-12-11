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
  @Getter
  private Long id;

  @Getter @Setter
  private String name;
  @Getter @Setter
  private String description;
  @Getter @Setter
  private Double price;
  @Getter @Setter
  private String category;
  @Getter @Setter
  private Integer stock;
  @Getter @Setter
  private Boolean deleted;
  @Getter @Setter
  private LocalDate deletedDate;

}
