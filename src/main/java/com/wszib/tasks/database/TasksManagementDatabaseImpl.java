package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tasksManagementDatabase")
public class TasksManagementDatabaseImpl implements TasksManagementDatabase {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createNewTask(Task task) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.persist(task);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewTask: " + task);
    }

    @Override
    public void createNewUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewUser: " + user);
    }

    @Override
    public void saveUpdatedTask(Task task) {
        System.out.println("TasksManagementDatabaseImpl saveUpdatedTask: "+task);
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.update(task);
        session.flush() ;
        tx.commit();
    }

    @Override
    public void saveUpdatedUser(User user) {
        System.out.println("TasksManagementDatabaseImpl saveUpdatedUser: "+user);
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        session.flush() ;
        tx.commit();
    }

    @Override
    public Task getTaskById(int id) {
        System.out.println("TasksManagementDatabaseImpl getTaskById");
        Task task;
        Session session = this.sessionFactory.getCurrentSession();
        task = session.load(Task.class,id);
        return task;
    }

    @Override
    public User getUserById(int id) {
        System.out.println("TasksManagementDatabaseImpl getUserById");
        User user;
        Session session = this.sessionFactory.getCurrentSession();
        user = session.load(User.class,id);
        return user;
    }

    @Override
    public void deleteTaskById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Task task ;

        task = session.load(Task.class,id);
        session.delete(task);

        session.flush() ;
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl deleteTaskById: "+id);
    }

    @Override
    public void deleteUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        User user ;

        user = session.load(User.class,id);
        session.delete(user);

        session.flush() ;
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl deleteUserById: "+id);
    }

    @Override
    public List getAllTasks() {
        System.out.println("TasksManagementDatabaseImpl getAllTasks");
        List<Task> taskList;
        Session session = this.sessionFactory.getCurrentSession();
        taskList = session.createQuery("from Task").list();
        return taskList;
    }

    @Override
    public List getAllTasksForUser(User user) {
        System.out.println("TasksManagementDatabaseImpl getAllTasksForUser");
        List<Task> taskList;
        Session session = this.sessionFactory.getCurrentSession();

        String hql = "from Task WHERE User = :user";
        Query query = session.createQuery(hql);
        query.setParameter("user",user);
        taskList = query.list();

        return taskList;
    }
}
