

package com.todolist.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User entity.
 */
@Entity
public class User implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email
     *
     * @param email new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
