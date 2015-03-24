package org.iainbo.dao;

import org.iainbo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UserDAO extends BaseDAO<User>{
    public static final String FIND_USER_BY_USERNAME = "select u from User u where u.userName = :userName";

    public UserDAO() {
        super(User.class);
    }

    public UserDAO(EntityManager entityManager) {
        super(User.class);
    }

    public User findByUsername(String userName) {
        Query query = entityManager.createQuery(FIND_USER_BY_USERNAME);
        query.setParameter("userName", userName);
        return (User) query.getSingleResult();
    }

}
