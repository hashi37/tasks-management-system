package com.wszib.tasks.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id", nullable = true)
    private TaskState state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task: id="+id+", name="+name+", state="+state+", user="+user;
    }
}
