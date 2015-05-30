package com.todolist.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Todo entity.
 */
@Entity
public class Todo implements Serializable {

    private long id;
    private long userId;
    private String title;
    private boolean done;
    private Priority priority;
    private Date dueDate;


    public Todo() {
        priority = Priority.LOW;
    }

    public Todo(long userId, String title, boolean done, Priority priority, Date dueDate) {
        this.userId = userId;
        this.title = title;
        this.done = done;
        this.priority = priority;
        this.dueDate = dueDate;
    }


    /**
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return owners id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId new user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return is done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @param done set new done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * getPriority
     *
     * @return priority of the task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority returns pprioruty LOW|MED|HIGH
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * get due date
     *
     * @param dueDate new due date of the task
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
