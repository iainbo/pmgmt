package org.iainbo.dao;

import org.iainbo.entities.user.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Stateless
public class UserDAO extends BaseDAO<User>{
    public static final String FIND_USER_BY_USERNAME = "select u from User u where u.userName = :userName";

    public UserDAO() {
        super(User.class);
    }

    public UserDAO(EntityManager entityManager) {
        super(User.class);
    }

    public User findByUsername(String userName) throws NoResultException{
        Query query = entityManager.createQuery(FIND_USER_BY_USERNAME);
        query.setParameter("userName", userName);
        return (User) query.getSingleResult();
    }

}
