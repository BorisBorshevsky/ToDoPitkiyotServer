package com.todolist.model.api;

import com.todolist.model.User;

/**
 * Interface for user repository.
 */
public interface UserRepository {

    /**
     * Get user by email.
     *
     * @param email user's email
     * @return the user with the given email
     */
    User getUserByEmail(final String email) throws TodoDaoException;

    /**
     * Check user's email and password.
     *
     * @param email    user's email
     * @param password user's password
     * @return true if user credentials are ok
     */
    boolean login(final String email, final String password) throws TodoDaoException;

    /**
     * Create a user.
     *
     * @param user the user to create
     * @return the created user
     */
    User create(final User user) throws TodoDaoException;

    /**
     * Update a user.
     *
     * @param user the user to update.
     * @return the updated user
     */
    User update(User user) throws TodoDaoException;

    /**
     * Remove a user.
     *
     * @param user the user to remove
     */
    void remove(final User user) throws TodoDaoException;

}
