package com.todolist.model.api;

/**
 * Created by Boris Borshevsky on 5/26/2015.
 */
public class ServiceDaoHelper {
    /**
     * returns intance of UserRepository
     * @return returns intance of UserRepository
     */
    public static UserRepository getUserRepository(){
        return UserRepositoryDao.getInstance();
    }

    /**
     * returns intance of TodoRepository
     * @return returns intance of TodoRepository
     */
    public static TodoRepository getTodoRepository(){
        return TodoRepositoryDao.getInstance();
    }
}
