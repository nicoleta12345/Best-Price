package ro.ace.ucv.persistence.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.ace.ucv.persistence.dao.GenericDao;
import ro.ace.ucv.persistence.dao.exception.EntityDeletedException;
import ro.ace.ucv.persistence.dao.exception.EntityRegisteredException;

/**
 * The GenericDaoImpl class implements CRUD operations on the database for any T entity type.
 * 
 * @author Nicoleta Barbulescu
 *
 * @param <T>
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

  /**
   * EntityManager is used to do operations with the database.
   */
  @PersistenceContext
  private EntityManager entityManager;

  /**
   * The class object of the entity at runtime.
   */
  private Class<T> classType;

  /**
   * Parameterized constructor. Sets the runtime class of the entity type.
   */
  @SuppressWarnings("unchecked")
  public GenericDaoImpl() {
    classType = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  @Override
  public void create(T t) throws EntityRegisteredException {
    try {
      entityManager.persist(t);
    } catch (EntityExistsException e) {
      throw new EntityRegisteredException("The entity already exists!");
    }
  }

  @Override
  public T read(Object id) {
    return entityManager.find(classType, id);
  }

  @Override
  public T update(T t) throws EntityDeletedException {
    T entity = null;

    try {
      t = entityManager.merge(t);
    } catch (IllegalArgumentException e) {
      throw new EntityDeletedException("This entity is already removed!");
    }

    return entity;
  }

  @Override
  public void delete(T t) {
    // TODO Auto-generated method stub
  }

}
