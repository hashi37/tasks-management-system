package com.wszib.tasks.web;

import com.wszib.tasks.database.TasksManagementDatabase;
import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


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
    }

    @GetMapping("/index")
    public String prepareIndexPageContent(Model model) {
        String currentUser = getCurrentUserName();
        System.out.println("Logged in user="+currentUser);

        model.addAttribute("appName", appName);
        model.addAttribute("alltasks", tasksManagementDatabase.getAllTasks());
        model.addAttribute("tasks", tasksManagementDatabase.getAllTasksForUserLogin(currentUser));
        return "index";
    }

    @GetMapping("/addtaskform")
    public String showAddTaskForm(Task task) {
        return "add-task";
    }

    @PostMapping("/addtask")
    public String addNewTask(@ModelAttribute("task") Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-task";
        }
        tasksManagementDatabase.createNewTask(task);
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

    @GetMapping("/start/task/{id}")
    public String startTaskById(@PathVariable("id") int id, Model model) {
        tasksManagementDatabase.startTask(id);
        return "redirect:/index";
    }

    @GetMapping("/done/task/{id}")
    public String doneTaskById(@PathVariable("id") int id, Model model) {
        tasksManagementDatabase.doneTask(id);
        return "redirect:/index";
    }

    @GetMapping("/assignuserform/{id}")
    public String showAssignUserForm(@PathVariable("id") int id, Model model) {
        Task task = tasksManagementDatabase.getTaskById(id);
        System.out.println("showAssignUserForm Task="+task);
        List users = tasksManagementDatabase.getUserListByUserType(UserType.USER_TYPE_USER);
        model.addAttribute("task", task);
        model.addAttribute("users", users);
        return "assign-task";
    }

    @PostMapping("/assignuser/")
    public String assignUserToTask(@ModelAttribute("task") Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "assign-task";
        }

        int taskId = task.getId();
        String userLogin = task.getUser().getUserLogin();

        System.out.println("assignUserToTask with taskId="+taskId+"and userLogin="+userLogin);

        tasksManagementDatabase.assignTaskWithIdToUser(taskId, userLogin);
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


    private String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        return currentUserName;
    }

}
