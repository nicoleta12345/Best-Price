package ro.ace.ucv.persistence.dao;

import ro.ace.ucv.persistence.dao.exception.EntityDeletedException;
import ro.ace.ucv.persistence.dao.exception.EntityRegisteredException;

/**
 * The GenericDao interface defines the CRUD operations for any type of entity.
 * 
 * @author Nicoleta Barbulescu
 *
 * @param <T>
 */
public interface GenericDao<T> {

  /**
   * Creates a new entry for the given object into the database.
   * 
   * @param t the object which will be inserted into the database
   * @throws EntityRegisteredException if the entity already exists into the database
   */
  void create(T t) throws EntityRegisteredException;

  /**
   * Reads an object of type T with the primary key id, from the database.
   * 
   * @param id the primary key of the object
   * @return an object of type T identified by its id
   */
  T read(Object id);

  /**
   * Updates an entry into the database with the updated informations given.
   * 
   * @param t the object which contains the updated details
   * @return the updated object
   * @throws EntityDeletedException if the entity doesn't exist
   */
  T update(T t) throws EntityDeletedException;

  /**
   * Deletes an object from the database.
   * 
   * @param t the object of type T corresponding to the entry form the database which will be deleted
   */
  void delete(T t);

}
