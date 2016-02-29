package pomonitor.entity;

// default package

import java.util.List;

/**
 * Interface for WordPropertyDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IWordPropertyDAO {
	/**
	 * Perform an initial save of a previously unsaved WordProperty entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IWordPropertyDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            WordProperty entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(WordProperty entity);

	/**
	 * Delete a persistent WordProperty entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IWordPropertyDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            WordProperty entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(WordProperty entity);

	/**
	 * Persist a previously saved WordProperty entity and return it or a copy of
	 * it to the sender. A copy of the WordProperty entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = IWordPropertyDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            WordProperty entity to update
	 * @return WordProperty the persisted WordProperty entity instance, may not
	 *         be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public WordProperty update(WordProperty entity);

	public WordProperty findById(Integer id);

	/**
	 * Find all WordProperty entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the WordProperty property to query
	 * @param value
	 *            the property value to match
	 * @return List<WordProperty> found by query
	 */
	public List<WordProperty> findByProperty(String propertyName, Object value);

	public List<WordProperty> findByProperty(Object property);

	/**
	 * Find all WordProperty entities.
	 * 
	 * @return List<WordProperty> all WordProperty entities
	 */
	public List<WordProperty> findAll();
}