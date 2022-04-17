package com.example.Restaurant.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;


@Entity
@Table ( name = "users" )
public class User implements Serializable {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column ( nullable = false, length = 45 )
    private String email;

    @Column ( nullable = false, length = 64 )
    private String password;

    @Column ( name = "first_name", length = 30 )
    private String firstName;

    @Column ( name = "last_name", nullable = false, length = 30 )
    private String lastName;

    @NotNull
    private int age;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    @ManyToMany ( fetch = FetchType.EAGER )
    @JoinTable (
            name = "users_roles",
            joinColumns = @JoinColumn (
                    name = "user_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn (
                    name = "role_id", referencedColumnName = "id" ) )
    private Collection<Role> roles;


    @ManyToMany
    @JoinTable (
            name = "users_orders",
            joinColumns = @JoinColumn (
                    name = "user_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn (
                    name = "order_id", referencedColumnName = "id" ) )
    private Collection<Order> orders;
}

