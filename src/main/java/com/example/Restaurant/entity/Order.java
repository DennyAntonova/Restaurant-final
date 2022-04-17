package com.example.Restaurant.entity;

import com.example.Restaurant.RestaurantTable;
import com.example.Restaurant.StatusOrder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table ( name = "orders" )

public class Order implements Serializable {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @CreationTimestamp
    private Timestamp dateAndTime;

    @Enumerated ( EnumType.STRING )
    private RestaurantTable restaurantTable;

    @Enumerated ( EnumType.STRING )
    private StatusOrder status;

    @Column ( name = "user_id" )
    private Long userId;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn ( name = "user_id", insertable = false, updatable = false, referencedColumnName = "id" )
    private User user;

}