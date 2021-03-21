package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
import com.wszib.tasks.model.TaskState;
import com.wszib.tasks.model.User;
import com.wszib.tasks.model.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("tasksManagementDatabase")
public class TasksManagementDatabaseImpl implements TasksManagementDatabase {

    @Autowired
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public void createNewTask(Task task) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(task);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewTask: " + task);
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public void createNewTaskState(TaskState taskState) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(taskState);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewTaskState: " + taskState);
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public void createNewUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(user);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewUser: " + user);
    }

    @Override
    @Transactional(readOnly = true, propagation=Propagation.NOT_SUPPORTED)
    public void createNewUserType(UserType userType) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(userType);
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl createNewUserType: " + userType);
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public void saveUpdatedTask(Task task) {
        System.out.println("TasksManagementDatabaseImpl saveUpdatedTask: "+task);
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.update(task);
        session.flush() ;
        tx.commit();
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public void saveUpdatedUser(User user) {
        System.out.println("TasksManagementDatabaseImpl saveUpdatedUser: "+user);
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        session.flush() ;
        tx.commit();
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public Task getTaskById(int id) {
        System.out.println("TasksManagementDatabaseImpl getTaskById");
        Task task;
        Session session = this.sessionFactory.getCurrentSession();
        task = session.load(Task.class,id);
        return task;
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public User getUserById(int id) {
        System.out.println("TasksManagementDatabaseImpl getUserById");
        User user;
        Session session = this.sessionFactory.getCurrentSession();
        user = session.load(User.class,id);
        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
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
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
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
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public List getAllTasks() {
        System.out.println("TasksManagementDatabaseImpl getAllTasks");
        List<Task> taskList;
        Session session = this.sessionFactory.getCurrentSession();
        taskList = session.createQuery("from Task").list();
        return taskList;
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public List getAllTasksForUser(User user) {
        System.out.println("TasksManagementDatabaseImpl getAllTasksForUser");
        List<Task> taskList;
        Session session = this.sessionFactory.getCurrentSession();

        String hql = "from Task t WHERE t.user = :user";
        Query query = session.createQuery(hql);
        query.setParameter("user",user);
        taskList = query.list();

        return taskList;
    }
}
