package com.example.Restaurant.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "receipts")
public class Receipt implements Serializable {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column ( name = "menu_id" )
    private Long menuId;

    @Column ( name = "order_id" )
    private Long orderId;

    private double quantity;

    @Column ( name = "total_amount" )
    private double totalAmount;


    @ManyToOne
    @JoinColumn ( name = "menu_id", insertable = false, updatable = false, referencedColumnName = "id" )
    private Menu menu;

    @ManyToOne
    @JoinColumn ( name = "order_id", insertable = false, updatable = false, referencedColumnName = "id" )
    private Order order;

    public Menu getMenu() {
        return menu;
    }

    public Receipt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;

    }

}