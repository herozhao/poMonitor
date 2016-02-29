package pomonitor.entity;

import java.util.List;

/**
 * Interface for EmotionalwordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IEmotionalwordDAO {
	/**
	 * Perform an initial save of a previously unsaved Emotionalword entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IEmotionalwordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Emotionalword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Emotionalword entity);

	/**
	 * Delete a persistent Emotionalword entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * IEmotionalwordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Emotionalword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Emotionalword entity);

	/**
	 * Persist a previously saved Emotionalword entity and return it or a copy
	 * of it to the sender. A copy of the Emotionalword entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = IEmotionalwordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Emotionalword entity to update
	 * @return Emotionalword the persisted Emotionalword entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Emotionalword update(Emotionalword entity);

	public Emotionalword findById(Integer id);

	/**
	 * Find all Emotionalword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Emotionalword property to query
	 * @param value
	 *            the property value to match
	 * @return List<Emotionalword> found by query
	 */
	public List<Emotionalword> findByProperty(String propertyName, Object value);

	public List<Emotionalword> findByWord(Object word);

	public List<Emotionalword> findBySpeech(Object speech);

	public List<Emotionalword> findByPolarity(Object polarity);

	public List<Emotionalword> findByStrength(Object strength);

	/**
	 * Find all Emotionalword entities.
	 * 
	 * @return List<Emotionalword> all Emotionalword entities
	 */
	public List<Emotionalword> findAll();
}