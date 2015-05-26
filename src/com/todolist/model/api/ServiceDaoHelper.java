package com.todolist.model.api;

/**
 * Created by Boris Borshevsky on 5/26/2015.
 */
public class ServiceDaoHelper {
    public static UserRepository getUserRepository(){
        return UserRepositoryDao.getInstance();
    }

    public static TodoRepository getTodoRepository(){
        return TodoRepositoryDao.getInstance();
    }
}
