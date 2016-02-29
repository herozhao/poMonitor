package pomonitor.entity;

// default package

import java.util.List;

/**
 * Interface for SynonyMousWordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ISynonyMousWordDAO {
	/**
	 * Perform an initial save of a previously unsaved SynonyMousWord entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ISynonyMousWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            SynonyMousWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(SynonyMousWord entity);

	/**
	 * Delete a persistent SynonyMousWord entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ISynonyMousWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            SynonyMousWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(SynonyMousWord entity);

	/**
	 * Persist a previously saved SynonyMousWord entity and return it or a copy
	 * of it to the sender. A copy of the SynonyMousWord entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ISynonyMousWordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            SynonyMousWord entity to update
	 * @return SynonyMousWord the persisted SynonyMousWord entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public SynonyMousWord update(SynonyMousWord entity);

	public SynonyMousWord findById(Integer id);

	/**
	 * Find all SynonyMousWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SynonyMousWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<SynonyMousWord> found by query
	 */
	public List<SynonyMousWord> findByProperty(String propertyName, Object value);

	public List<SynonyMousWord> findByCategory(Object category);

	public List<SynonyMousWord> findByWords(Object words);

	/**
	 * Find all SynonyMousWord entities.
	 * 
	 * @return List<SynonyMousWord> all SynonyMousWord entities
	 */
	public List<SynonyMousWord> findAll();
}