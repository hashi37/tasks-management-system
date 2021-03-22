package com.wszib.tasks.database;

import com.wszib.tasks.model.Task;
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
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void startTask(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Task task ;

        task = session.load(Task.class,id);
        task.setState(TasksManagementDatabase.TASK_STATE_IN_PROGRESS);
        session.update(task);
        session.flush() ;
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl startTask: "+id);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void doneTask(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Task task ;

        task = session.load(Task.class,id);
        task.setState(TasksManagementDatabase.TASK_STATE_DONE);
        session.update(task);
        session.flush() ;
        tx.commit();
        System.out.println("TasksManagementDatabaseImpl doneTask: "+id);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void assignTaskWithIdToUser(int id, String userLogin) {
        System.out.println("TasksManagementDatabaseImpl assignTaskWithIdToUser");
        Session session = this.sessionFactory.openSession();

        User user = getUserByUserLogin(userLogin, session);
        Task task = session.load(Task.class,id);

        task.setUser(user);

        Transaction tx = session.beginTransaction();
        session.update(task);
        session.flush() ;
        tx.commit();
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public Task getTaskById(int id) {
        System.out.println("TasksManagementDatabaseImpl getTaskById");
        Task task;
        Session session = this.sessionFactory.openSession();
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
        List<Task> taskList;
        Session session = this.sessionFactory.getCurrentSession();
        taskList = session.createQuery("from Task").list();

        System.out.println("TasksManagementDatabaseImpl getAllTasks: "+taskList);

        return taskList;
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public List getAllTasksForUser(User user) {
        System.out.println("TasksManagementDatabaseImpl getAllTasksForUser");
        List<Task> taskList;
        Session session = this.sessionFactory.openSession();

        String hql = "from Task t WHERE t.user = :user";
        Query query = session.createQuery(hql);
        query.setParameter("user",user);
        taskList = query.list();

        return taskList;
    }

    @Override
    public List getAllTasksForUserLogin(String userLogin) {
        System.out.println("TasksManagementDatabaseImpl getAllTasksForUserLogin userLogin="+userLogin);
        List<Task> taskList;
        Session session = this.sessionFactory.openSession();

        String hql = "from Task t WHERE t.user.userLogin = : userLogin";
        Query query = session.createQuery(hql);
        query.setParameter("userLogin",userLogin);
        taskList = query.list();

        return taskList;
    }

    private User getUserByUserLogin(String userLogin, Session session){
        System.out.println("TasksManagementDatabaseImpl getUserByUserLogin");
        User user;

        String hql = "from User u WHERE u.userLogin = : userLogin";
        Query query = session.createQuery(hql);
        query.setParameter("userLogin",userLogin);

        user = (User)query.uniqueResult();

        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)
    public List getUserListByUserType(String userTypeString){
        Session session = this.sessionFactory.openSession();
        System.out.println("TasksManagementDatabaseImpl getUserListByUserType");
        List<Task> userList;

        String hql = "from User as user WHERE user.userType.type = :userType";
        Query query = session.createQuery(hql);
        query.setParameter("userType", UserType.USER_TYPE_USER);

        userList = query.list();

        return userList;
    }

    private UserType getUserTypeByType(String userTypeString, Session session){
        System.out.println("TasksManagementDatabaseImpl getUserTypeByType");
        UserType userType;

        String hql = "from UserType u WHERE u.type = : userType";
        Query query = session.createQuery(hql);
        query.setParameter("userType", userTypeString);

        userType = (UserType)query.uniqueResult();

        return userType;
    }

}
