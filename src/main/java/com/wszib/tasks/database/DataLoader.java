package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.TaskState;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        userTypeEmployee.setType("EMPLOYEE");

        User admin = new User();
        admin.setUserType(userTypeAdmin);
        admin.setUserLogin("admin");

        User leader = new User();
        leader.setUserType(userTypeLeader);
        leader.setUserLogin("leader");

        User employee = new User();
        employee.setUserType(userTypeEmployee);
        employee.setUserLogin("employee");

        tasksManagementDatabase.createNewUser(admin);
        tasksManagementDatabase.createNewUser(leader);
        tasksManagementDatabase.createNewUser(employee);

        TaskState taskStateNew = new TaskState();
        taskStateNew.setName("NEW");
        TaskState taskStateInProgress = new TaskState();
        taskStateInProgress.setName("IN_PROGRESS");
        TaskState taskStateDone = new TaskState();
        taskStateDone.setName("DONE");

        Task task1 = new Task();
        task1.setState(taskStateNew);
        task1.setDescription("Task1 new");
        task1.setName("Task1");

        task1.setUser(leader);

        tasksManagementDatabase.createNewTask(task1);

        Task task2 = new Task();
        task2.setState(taskStateInProgress);
        task2.setDescription("Task2 in progress");
        task2.setName("Task2");

        task2.setUser(employee);

        tasksManagementDatabase.createNewTask(task2);

        Task task3 = new Task();
        task3.setState(taskStateDone);
        task3.setDescription("Task3 done");
        task3.setName("Task3");

        task3.setUser(leader);

        tasksManagementDatabase.createNewTask(task3);

        //System.out.println(tasksManagementDatabase.getAllTasks());
        System.out.println(tasksManagementDatabase.getAllTasksForUser(leader));
        System.out.println(tasksManagementDatabase.getAllTasksForUser(employee));

    }
}
