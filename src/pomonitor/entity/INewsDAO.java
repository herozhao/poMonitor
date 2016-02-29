package pomonitor.entity;

import java.util.Date;
import java.util.List;

/**
 * Interface for NewsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INewsDAO {
	/**
	 * Perform an initial save of a previously unsaved News entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INewsDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(News entity);

	/**
	 * Delete a persistent News entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INewsDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(News entity);

	/**
	 * Persist a previously saved News entity and return it or a copy of it to
	 * the sender. A copy of the News entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = INewsDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to update
	 * @return News the persisted News entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public News update(News entity);

	public News findById(Integer id);

	/**
	 * Find all News entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the News property to query
	 * @param value
	 *            the property value to match
	 * @return List<News> found by query
	 */
	public List<News> findByProperty(String propertyName, Object value);

	public List<News> findById(Object id);

	public List<News> findByTitle(Object title);

	public List<News> findByUrl(Object url);

	public List<News> findByContent(Object content);

	public List<News> findByWeb(Object web);

	public List<News> findByAllContent(Object allContent);

	public List<News> findByKeyWords(Object keyWords);

	public List<News> findByContentPath(Object contentPath);

	public List<News> findByFailedCount(Object failedCount);

	public List<News> findByIsFinsh(Object isFinsh);

	public List<News> findByIsFailed(Object isFailed);

	public List<News> findByIsWorking(Object isWorking);

	/**
	 * Find all News entities.
	 * 
	 * @return List<News> all News entities
	 */
	public List<News> findAll();
}