package pomonitor.entity;

import java.util.List;

/**
 * Interface for SenswordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ISenswordDAO {
	/**
	 * Perform an initial save of a previously unsaved Sensword entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ISenswordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Sensword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Sensword entity);

	/**
	 * Delete a persistent Sensword entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ISenswordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Sensword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Sensword entity);

	/**
	 * Persist a previously saved Sensword entity and return it or a copy of it
	 * to the sender. A copy of the Sensword entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ISenswordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Sensword entity to update
	 * @return Sensword the persisted Sensword entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Sensword update(Sensword entity);

	public Sensword findById(Integer id);

	/**
	 * Find all Sensword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Sensword property to query
	 * @param value
	 *            the property value to match
	 * @return List<Sensword> found by query
	 */
	public List<Sensword> findByProperty(String propertyName, Object value);

	public List<Sensword> findBySenslevel(Object senslevel);

	public List<Sensword> findBySensvalue(Object sensvalue);

	public List<Sensword> findByUserid(Object userid);

	/**
	 * Find all Sensword entities.
	 * 
	 * @return List<Sensword> all Sensword entities
	 */
	public List<Sensword> findAll();
}