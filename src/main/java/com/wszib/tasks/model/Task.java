package com.wszib.tasks.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @JoinColumn(name="user_id", nullable=true)
    private User user;

    private String state;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startTaskTime;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date stopTaskTime;

    @Transient
    private String taskDuration;

    private String leaderUserLogin;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStartTaskTime() {
        return startTaskTime;
    }

    public void setStartTaskTime(Date startTaskTime) {
        this.startTaskTime = startTaskTime;
    }

    public Date getStopTaskTime() {
        return stopTaskTime;
    }

    public void setStopTaskTime(Date stopTaskTime) {
        this.stopTaskTime = stopTaskTime;
    }

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getLeaderUserLogin() {
        return leaderUserLogin;
    }

    public void setLeaderUserLogin(String leaderUserLogin) {
        this.leaderUserLogin = leaderUserLogin;
    }

    @Override
    public String toString() {
        return "Task: id="+id+", name="+name+", state="+state+", user="+user;
    }
}
