package pomonitor.entity;

// default package

import java.util.List;

/**
 * Interface for NegWordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INegWordDAO {
	/**
	 * Perform an initial save of a previously unsaved NegWord entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INegWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            NegWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NegWord entity);

	/**
	 * Delete a persistent NegWord entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INegWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            NegWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NegWord entity);

	/**
	 * Persist a previously saved NegWord entity and return it or a copy of it
	 * to the sender. A copy of the NegWord entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = INegWordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            NegWord entity to update
	 * @return NegWord the persisted NegWord entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NegWord update(NegWord entity);

	public NegWord findById(Integer id);

	/**
	 * Find all NegWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NegWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<NegWord> found by query
	 */
	public List<NegWord> findByProperty(String propertyName, Object value);

	public List<NegWord> findByWord(Object word);

	/**
	 * Find all NegWord entities.
	 * 
	 * @return List<NegWord> all NegWord entities
	 */
	public List<NegWord> findAll();
}