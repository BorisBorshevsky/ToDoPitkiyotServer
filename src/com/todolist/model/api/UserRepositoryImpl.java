

package com.todolist.model.api;


import com.todolist.model.User;
import org.hibernate.*;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private SessionFactory getSession() {
        return SessionFactoryDao.getSession();
    }

    private static UserRepositoryImpl instance;
    private UserRepositoryImpl(){}
    public synchronized static UserRepositoryImpl getInstance() {
        if (instance == null){
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User getUserByEmail(String email) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User user where user.email = :email");
            query.setParameter("email", email);
            transaction.commit();
            List<User> users = query.list();
            return (users != null && !users.isEmpty()) ? users.get(0) : null;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in getUserByEmail", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean login(String email, String password) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("from User user where user.email = :email AND user.password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            transaction.commit();
            List<User> users = query.list();
            return (users != null && !users.isEmpty());

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in login", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User create(User user) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in create user", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User update(User user) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return user;
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in update user", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(User user) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in update user", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


//    public boolean login(final String email, final String password) {
//        TypedQuery<User> query = entityManager.createNamedQuery("findUserByEmailAndPassword", User.class);
//        query.setParameter("p_email", email);
//        query.setParameter("p_password", password);
//        List<User> users = query.getResultList();
//        return (users != null && !users.isEmpty());
//    }

}
