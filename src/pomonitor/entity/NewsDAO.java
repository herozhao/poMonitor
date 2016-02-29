package pomonitor.entity;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for News
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see pomonitor.entity.News
 * @author MyEclipse Persistence Tools
 */
public class NewsDAO implements INewsDAO {
	// property constants
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String URL = "url";
	public static final String CONTENT = "content";
	public static final String WEB = "web";
	public static final String ALL_CONTENT = "allContent";
	public static final String KEY_WORDS = "keyWords";
	public static final String CONTENT_PATH = "contentPath";
	public static final String FAILED_COUNT = "failedCount";
	public static final String IS_FINSH = "isFinsh";
	public static final String IS_FAILED = "isFailed";
	public static final String IS_WORKING = "isWorking";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

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
	 * NewsDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(News entity) {
		EntityManagerHelper.log("saving News instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent News entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * NewsDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(News entity) {
		EntityManagerHelper.log("deleting News instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(News.class,
					entity.getRelId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * entity = NewsDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            News entity to update
	 * @return News the persisted News entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public News update(News entity) {
		EntityManagerHelper.log("updating News instance", Level.INFO, null);
		try {
			News result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public News findById(Integer id) {
		EntityManagerHelper.log("finding News instance with id: " + id,
				Level.INFO, null);
		try {
			News instance = getEntityManager().find(News.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all News entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the News property to query
	 * @param value
	 *            the property value to match
	 * @return List<News> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<News> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding News instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from News model where model."
					+ propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<News> findById(Object id) {
		return findByProperty(ID, id);
	}

	public List<News> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<News> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List<News> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<News> findByWeb(Object web) {
		return findByProperty(WEB, web);
	}

	public List<News> findByAllContent(Object allContent) {
		return findByProperty(ALL_CONTENT, allContent);
	}

	public List<News> findByKeyWords(Object keyWords) {
		return findByProperty(KEY_WORDS, keyWords);
	}

	public List<News> findByContentPath(Object contentPath) {
		return findByProperty(CONTENT_PATH, contentPath);
	}

	public List<News> findByFailedCount(Object failedCount) {
		return findByProperty(FAILED_COUNT, failedCount);
	}

	public List<News> findByIsFinsh(Object isFinsh) {
		return findByProperty(IS_FINSH, isFinsh);
	}

	public List<News> findByIsFailed(Object isFailed) {
		return findByProperty(IS_FAILED, isFailed);
	}

	public List<News> findByIsWorking(Object isWorking) {
		return findByProperty(IS_WORKING, isWorking);
	}

	/**
	 * 查询指定时间段内的新闻记录
	 * 
	 * @param startDateStr
	 *            开始时间, 例如 : "2012-10-10"
	 * @param endDateStr
	 *            结束时间,例如 : "2013-10-10"
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<News> findBetweenDate(String startDateStr, String endDateStr) {
		try {
			final String queryString = "select model from News model where (model.time between "
					+ "'" + startDateStr + "' and '" + endDateStr + "')";
			Query query = getEntityManager().createQuery(queryString);
			// query.setParameter("startDateStr", startDateStr);
			// query.setParameter("endDateStr", endDateStr);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all News entities.
	 * 
	 * @return List<News> all News entities
	 */
	@SuppressWarnings("unchecked")
	public List<News> findAll() {
		EntityManagerHelper.log("finding all News instances", Level.INFO, null);
		try {
			final String queryString = "select model from News model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}