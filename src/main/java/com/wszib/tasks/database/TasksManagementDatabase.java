package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.TaskState;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;

import java.util.List;

public interface TasksManagementDatabase {

    void createNewTask(Task task);
    void createNewTaskState(TaskState taskState);
    void createNewUser(User user);
    void createNewUserType(UserType userType);
    void saveUpdatedTask(Task task);
    void saveUpdatedUser(User user);
    Task getTaskById(int id);
    User getUserById(int id);
    void deleteTaskById(int id);
    void deleteUserById(int id);
    List getAllTasks();
    List getAllTasksForUser(User user);

}
