package org.iainbo.dao;

import org.iainbo.entities.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDAO<T> implements Serializable{

    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> entityClass;

    public BaseDAO(Class<User> entityClass) {
        this.entityClass = (Class<T>) entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    //Base database query methods
    public T find(final Object id){
        return (T) this.entityManager.find(getEntityClass(), id);
    }

    public void delete(final Object id){
        Object deleteMe = this.entityManager.getReference(getEntityClass(), id);
        this.entityManager.remove(deleteMe);
    }

    public T update(T t) {
        return (T) this.entityManager.merge(t);
    }

    public void insert(T t) {
        this.entityManager.persist(t);
    }

    public List findAll(){
        return entityManager.createQuery("Select entity FROM " + getEntityClass().getSimpleName()
                + " entity").getResultList();
    }

    public T create(final T entity){
        entityManager.persist(entity);
        return entity;
    }
}
