package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;

import java.util.List;

public interface TasksManagementDatabase {

    public static String TASK_STATE_NEW = "NEW";
    public static String TASK_STATE_IN_PROGRESS = "IN_PROGRESS";
    public static String TASK_STATE_DONE = "DONE";

    void createNewTask(Task task);
    void createNewTaskForUser(Task task, String userLogin);
    void createNewUser(User user);
    void createNewUserType(UserType userType);
    void saveUpdatedTask(Task task);
    void saveUpdatedUser(User user);
    void startTask(int id);
    void doneTask(int id);
    void assignTaskWithIdToUser(int id, String userLogin, String currentLeader);
    Task getTaskById(int id);
    User getUserById(int id);
    void deleteTaskById(int id);
    void deleteUserById(int id);
    List getAllTasks();
    List getAllTasksForUser(User user);
    List getAllTasksForUserLogin(String userLogin);
    List getUserListByUserType(String userType);
}
