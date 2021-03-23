package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DataLoader implements ApplicationRunner {

    private TasksManagementDatabase tasksManagementDatabase;

    @Autowired
    public DataLoader(TasksManagementDatabase tasksManagementDatabase) {
        this.tasksManagementDatabase = tasksManagementDatabase;
    }

    public void run(ApplicationArguments args) {

        UserType userTypeAdmin = new UserType();
        userTypeAdmin.setType("ADMIN");
        UserType userTypeLeader = new UserType();
        userTypeLeader.setType("LEADER");
        UserType userTypeEmployee = new UserType();
        userTypeEmployee.setType("USER");

        User admin = new User();
        admin.setUserType(userTypeAdmin);
        admin.setUserLogin("admin");

        User leader = new User();
        leader.setUserType(userTypeLeader);
        leader.setUserLogin("leader");

        User user1 = new User();
        user1.setUserType(userTypeEmployee);
        user1.setUserLogin("user1");

        User user2 = new User();
        user2.setUserType(userTypeEmployee);
        user2.setUserLogin("user2");

        tasksManagementDatabase.createNewUser(admin);
        tasksManagementDatabase.createNewUser(leader);
        tasksManagementDatabase.createNewUser(user1);
        tasksManagementDatabase.createNewUser(user2);

        Task task1 = new Task();
        task1.setState(TasksManagementDatabase.TASK_STATE_NEW);
        task1.setDescription("Task1 new");
        task1.setName("Task1");

        task1.setUser(leader);
        task1.setLeaderUserLogin(leader.getUserLogin());


        tasksManagementDatabase.createNewTask(task1);

        Task task2 = new Task();
        task2.setState(TasksManagementDatabase.TASK_STATE_IN_PROGRESS);
        task2.setDescription("Task2 in progress");
        task2.setName("Task2");

        task2.setUser(user1);
        task2.setLeaderUserLogin(leader.getUserLogin());

        //set current time to start time
        Timestamp startTaskTime = new Timestamp(System.currentTimeMillis());
        task2.setStartTaskTime(startTaskTime);

        tasksManagementDatabase.createNewTask(task2);

        Task task3 = new Task();
        task3.setState(TasksManagementDatabase.TASK_STATE_DONE);
        task3.setDescription("Task3 done");
        task3.setName("Task3");

        task3.setUser(leader);
        task3.setLeaderUserLogin(leader.getUserLogin());


        tasksManagementDatabase.createNewTask(task3);

        //System.out.println(tasksManagementDatabase.getAllTasks());
        //System.out.println(tasksManagementDatabase.getAllTasksForUser(leader));
        //System.out.println(tasksManagementDatabase.getAllTasksForUser(user1));

    }
}
