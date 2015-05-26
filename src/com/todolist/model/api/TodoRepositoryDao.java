
package com.todolist.model.api;


import com.todolist.model.Todo;
import org.hibernate.*;

import java.util.List;

/**
 * Implementation of {@link TodoRepository} using JPA.
 */

public class TodoRepositoryDao implements TodoRepository {

    private SessionFactory getSession() {
        return SessionFactoryDao.getSession();
    }

    private static TodoRepositoryDao instance;

    private TodoRepositoryDao() {
    }

    public synchronized static TodoRepositoryDao getInstance() {
        if (instance == null) {
            instance = new TodoRepositoryDao();
        }
        return instance;
    }

    @Override
    public Todo getTodoById(long id) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            Todo todo = (Todo) session.get(Todo.class, id);
            transaction.commit();
            return todo;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in getting task", he);
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    @Override
    public List<Todo> getTodoListByUser(long userId) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Todo t where t.userId = :userId order by t.dueDate");
            query.setParameter("userId", userId);
            transaction.commit();
            List<Todo> tasks = query.list();
            return tasks;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in getting user specific Tasks tasks", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Todo> getTodoListByUserAndTitle(long userId, String title) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT t FROM Todo t where t.userId = :userId and upper(t.title) like :likeExp order by t.dueDate");
            query.setParameter("userId", userId);
            query.setParameter("likeExp", "%" + title.toUpperCase() + "%");
            transaction.commit();
            List<Todo> tasks = query.list();
            return tasks;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in getting user specific Tasks tasks", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Todo create(Todo todo) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.save(todo);
            transaction.commit();
            return todo;

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in create todo", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Todo update(Todo todo) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.update(todo);
            transaction.commit();
            return todo;
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in update todo", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Todo todo) throws TodoDaoException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession().openSession();
            transaction = session.beginTransaction();
            session.delete(todo);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TodoDaoException("Exception in remove user", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Todo> searchTodoListByTitle(long userId, String title) throws TodoDaoException {
        return getTodoListByUserAndTitle(userId, title);
    }
}
