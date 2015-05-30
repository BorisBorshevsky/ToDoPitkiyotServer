package com.todolist.model.api;

import com.todolist.model.Todo;

import java.util.List;

/**
 * Interface for todo repository.
 */
public interface TodoRepository {

    /**
     * Get todo by id.
     *
     * @param id todo's id
     * @return the todo having the given id or null
     */
    Todo getTodoById(final long id) throws TodoDaoException;

    /**
     * Get todo list for the given user.
     *
     * @param userId the user's id
     * @return the todo list for the given user
     */
    List<Todo> getTodoListByUser(final long userId) throws TodoDaoException;

    /**
     * Get todo list by title for the given user.
     *
     * @param title  the todo title
     * @param userId the user identifier
     * @return the todo list containing the 'title' parameter in their title for the given user
     */
    List<Todo> getTodoListByUserAndTitle(final long userId, final String title) throws TodoDaoException;

    /**
     * Create a new todo.
     *
     * @param todo the todo to create
     * @return the created todo
     */
    Todo create(final Todo todo) throws TodoDaoException;

    /**
     * Update a todo.
     *
     * @param todo the todo to update
     * @return the updated todo
     */
    Todo update(Todo todo) throws TodoDaoException;

    /**
     * Remove a todo.
     *
     * @param todo the todo to remove
     */
    void remove(final Todo todo) throws TodoDaoException;

    /**
     * Search todo list by title for the given user.
     *
     * @param title  the todo's title
     * @param userId the user's id
     * @return the todo list containing the 'title'
     */
    List<Todo> searchTodoListByTitle(final long userId, final String title) throws TodoDaoException;


}
