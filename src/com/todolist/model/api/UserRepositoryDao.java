

package com.todolist.model.api;


import com.todolist.model.User;
import org.hibernate.*;

import java.util.List;

public class UserRepositoryDao implements UserRepository {

    private SessionFactory getSession() {
        return SessionFactoryDao.getSession();
    }

    private static UserRepositoryDao instance;

    private UserRepositoryDao() {
    }

    /**
     * singleton
     *
     * @return instance
     */
    public synchronized static UserRepositoryDao getInstance() {
        if (instance == null) {
            instance = new UserRepositoryDao();
        }
        return instance;
    }

    /**
     * Get user by email.
     *
     * @param email user's email
     * @return the user with the given email
     */
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

    /**
     * Check user's email and password.
     *
     * @param email    user's email
     * @param password user's password
     * @return true if user credentials are ok
     */
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
}
