package pomonitor.entity;

// default package

import java.util.List;

/**
 * Interface for IdeaWordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IIdeaWordDAO {
	/**
	 * Perform an initial save of a previously unsaved IdeaWord entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IIdeaWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            IdeaWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(IdeaWord entity);

	/**
	 * Delete a persistent IdeaWord entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IIdeaWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            IdeaWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(IdeaWord entity);

	/**
	 * Persist a previously saved IdeaWord entity and return it or a copy of it
	 * to the sender. A copy of the IdeaWord entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = IIdeaWordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            IdeaWord entity to update
	 * @return IdeaWord the persisted IdeaWord entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public IdeaWord update(IdeaWord entity);

	public IdeaWord findById(Integer id);

	/**
	 * Find all IdeaWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the IdeaWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<IdeaWord> found by query
	 */
	public List<IdeaWord> findByProperty(String propertyName, Object value);

	public List<IdeaWord> findByWord(Object word);

	/**
	 * Find all IdeaWord entities.
	 * 
	 * @return List<IdeaWord> all IdeaWord entities
	 */
	public List<IdeaWord> findAll();
}