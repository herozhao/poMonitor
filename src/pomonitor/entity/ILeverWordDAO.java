package pomonitor.entity;

// default package

import java.util.List;

/**
 * Interface for LeverWordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILeverWordDAO {
	/**
	 * Perform an initial save of a previously unsaved LeverWord entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ILeverWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LeverWord entity);

	/**
	 * Delete a persistent LeverWord entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ILeverWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LeverWord entity);

	/**
	 * Persist a previously saved LeverWord entity and return it or a copy of it
	 * to the sender. A copy of the LeverWord entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ILeverWordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to update
	 * @return LeverWord the persisted LeverWord entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LeverWord update(LeverWord entity);

	public LeverWord findById(Integer id);

	/**
	 * Find all LeverWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LeverWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<LeverWord> found by query
	 */
	public List<LeverWord> findByProperty(String propertyName, Object value);

	public List<LeverWord> findByWord(Object word);

	public List<LeverWord> findByScore(Object score);

	/**
	 * Find all LeverWord entities.
	 * 
	 * @return List<LeverWord> all LeverWord entities
	 */
	public List<LeverWord> findAll();
}