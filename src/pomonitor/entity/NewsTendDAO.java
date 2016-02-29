package pomonitor.entity;

// default package

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * A data access object (DAO) providing persistence and search support for
 * NewsTend entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see .NewsTend
 * @author MyEclipse Persistence Tools
 */
public class NewsTendDAO implements INewsTendDAO {

	// property constants
	public static final String NEWS_ID = "newsId";
	public static final String WEB = "web";
	public static final String TENDCLASS = "tendclass";
	public static final String TENDSCORE = "tendscore";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

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
	 * NewsTendDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            NewsTend entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NewsTend entity) {
		EntityManagerHelper.log("saving NewsTend instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent NewsTend entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * NewsTendDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            NewsTend entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NewsTend entity) {
		EntityManagerHelper.log("deleting NewsTend instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(NewsTend.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * entity = NewsTendDAO.update(entity);
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
	public NewsTend update(NewsTend entity) {
		EntityManagerHelper.log("updating NewsTend instance", Level.INFO, null);
		try {
			NewsTend result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public NewsTend findById(Integer id) {
		EntityManagerHelper.log("finding NewsTend instance with id: " + id,
				Level.INFO, null);
		try {
			NewsTend instance = getEntityManager().find(NewsTend.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all NewsTend entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NewsTend property to query
	 * @param value
	 *            the property value to match
	 * @return List<NewsTend> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<NewsTend> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding NewsTend instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from NewsTend model where model."
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

	public List<NewsTend> findByNewsId(Object newsId) {
		return findByProperty(NEWS_ID, newsId);
	}

	public List<NewsTend> findByWeb(Object web) {
		return findByProperty(WEB, web);
	}

	public List<NewsTend> findByTendclass(Object tendclass) {
		return findByProperty(TENDCLASS, tendclass);
	}

	public List<NewsTend> findByTendscore(Object tendscore) {
		return findByProperty(TENDSCORE, tendscore);
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
	public List<NewsTend> findBetweenDate(String startDateStr, String endDateStr) {
		try {
			// final String queryString =
			// "select model from NewsTend model where (model.date between "
			// + "'" + startDateStr + "' and '" + endDateStr + "')";
			final String queryString = "select model from NewsTend model where (model.date between ?1 and ?2)";
			Query query = getEntityManager().createQuery(queryString);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(startDateStr);
				endDate = sdf.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println();
			query.setParameter(1, startDate, TemporalType.DATE);
			query.setParameter(2, endDate, TemporalType.DATE);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all NewsTend entities.
	 * 
	 * @return List<NewsTend> all NewsTend entities
	 */
	@SuppressWarnings("unchecked")
	public List<NewsTend> findAll() {
		EntityManagerHelper.log("finding all NewsTend instances", Level.INFO,
				null);
		try {
			final String queryString = "select model from NewsTend model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
			
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
 		}
		 
	}

}
