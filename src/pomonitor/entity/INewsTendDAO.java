package pomonitor.entity;

// default package

import java.util.Date;
import java.util.List;

/**
 * Interface for NewsTendDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INewsTendDAO {
	/**
	 * Perform an initial save of a previously unsaved NewsTend entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INewsTendDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            NewsTend entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NewsTend entity);

	/**
	 * Delete a persistent NewsTend entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * INewsTendDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            NewsTend entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NewsTend entity);

	/**
	 * Persist a previously saved NewsTend entity and return it or a copy of it
	 * to the sender. A copy of the NewsTend entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = INewsTendDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            NewsTend entity to update
	 * @return NewsTend the persisted NewsTend entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NewsTend update(NewsTend entity);

	public NewsTend findById(Integer id);

	/**
	 * Find all NewsTend entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NewsTend property to query
	 * @param value
	 *            the property value to match
	 * @return List<NewsTend> found by query
	 */
	public List<NewsTend> findByProperty(String propertyName, Object value);

	public List<NewsTend> findByNewsId(Object newsId);

	public List<NewsTend> findByWeb(Object web);

	public List<NewsTend> findByTendclass(Object tendclass);

	public List<NewsTend> findByTendscore(Object tendscore);

	/**
	 * Find all NewsTend entities.
	 * 
	 * @return List<NewsTend> all NewsTend entities
	 */
	public List<NewsTend> findAll();
}