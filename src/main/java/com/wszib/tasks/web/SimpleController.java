package com.wszib.tasks.web;

import com.wszib.tasks.database.TasksManagementDatabase;
import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class SimpleController {
    @Autowired
    TasksManagementDatabase tasksManagementDatabase;

    @Value("${spring.application.name}")
    String appName;

    // Login form
    @GetMapping("/login")
    //@RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    // Login form with error
    //@RequestMapping("/login-error.html")
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "redirect:/index";
        //return "redirect:/login";
    }

    @GetMapping("/index")
    public String prepareIndexPageContent(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("tasks", tasksManagementDatabase.getAllTasks());
        return "index";
    }

    @GetMapping("/addtaskform")
    public String showAddTaskForm(Task task) {
        return "add-task";
    }

    @PostMapping("/addtask")
    public String addNewTask(@ModelAttribute("task") Task task, BindingResult result, Model model) {
        model.addAttribute("task", new Task());
        if (result.hasErrors()) {
            return "add-task";
        }
        //warehouseDatabase.addNewItem(item);
        return "redirect:/index";
    }

    @GetMapping("/adduserform")
    public String showAddUserForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addNewUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", new User());
        if (result.hasErrors()) {
            return "add-user";
        }
        //warehouseDatabase.addNewItem(item);
        return "redirect:/index";
    }

    @GetMapping("/delete/task/{id}")
    public String deleteTaskById(@PathVariable("id") int id, Model model) {
        tasksManagementDatabase.deleteTaskById(id);
        return "redirect:/index";
    }

    @GetMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable("id") int id, Model model) {
        tasksManagementDatabase.deleteUserById(id);
        return "redirect:/index";
    }

    @GetMapping("/edit/task/{id}")
    public String showUpdateTaskForm(@PathVariable("id") int id, Model model) {
        Task task = tasksManagementDatabase.getTaskById(id);

        model.addAttribute("task", task);
        return "update-task";
    }

    @PostMapping("/update/task/{id}")
    public String updateTask(@PathVariable("id") int id, Task task,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            task.setId(id);
            return "update-task";
        }

        //warehouseDatabase.saveItem(item);
        tasksManagementDatabase.saveUpdatedTask(task);
        return "redirect:/index";
    }

    @GetMapping("/edit/user/{id}")
    public String showUpdateUserForm(@PathVariable("id") int id, Model model) {
        User user = tasksManagementDatabase.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/user/{id}")
    public String updateUser(@PathVariable("id") int id, User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        tasksManagementDatabase.saveUpdatedUser(user);
        return "redirect:/index";
    }

    @ModelAttribute("user")
    public User defaultUserInstance() {
        User user = new User();
        return user;
    }

    @ModelAttribute("task")
    public Task defaultTaskInstance() {
        Task task = new Task();
        return task;
    }


}
