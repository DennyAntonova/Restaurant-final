package com.example.Restaurant.entity;


import com.example.Restaurant.MenuType;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Entity
@Table(name = "menu")

public class Menu implements Serializable {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
     private Long id;

    @Length ( message = "Please enter correct name", min = 2, max = 255 )
    @NotNull
    private String name;

    @Column ( nullable = false )
    @NumberFormat ( pattern = "000.00", style = NumberFormat.Style.NUMBER )
    @Positive ( message = "Please enter positive number for price!" )
    private double price;

    @Enumerated ( EnumType.STRING )
    @Column ( name = "menu_type" )
    private MenuType menuType;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }
  }
