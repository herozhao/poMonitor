package pomonitor.entity;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * Sensword entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see pomonitor.entity.Sensword
 * @author MyEclipse Persistence Tools
 */
public class SenswordDAO implements ISenswordDAO {
	// property constants
	public static final String SENSLEVEL = "senslevel";
	public static final String SENSVALUE = "sensvalue";
	public static final String USERID = "userid";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

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
	 * SenswordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Sensword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Sensword entity) {
		EntityManagerHelper.log("saving Sensword instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Sensword entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * SenswordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Sensword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Sensword entity) {
		EntityManagerHelper.log("deleting Sensword instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Sensword.class,
					entity.getSensid());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * entity = SenswordDAO.update(entity);
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
	public Sensword update(Sensword entity) {
		EntityManagerHelper.log("updating Sensword instance", Level.INFO, null);
		try {
			Sensword result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Sensword findById(Integer id) {
		EntityManagerHelper.log("finding Sensword instance with id: " + id,
				Level.INFO, null);
		try {
			Sensword instance = getEntityManager().find(Sensword.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Sensword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Sensword property to query
	 * @param value
	 *            the property value to match
	 * @return List<Sensword> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Sensword> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding Sensword instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Sensword model where model."
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

	public List<Sensword> findBySenslevel(Object senslevel) {
		return findByProperty(SENSLEVEL, senslevel);
	}

	public List<Sensword> findBySensvalue(Object sensvalue) {
		return findByProperty(SENSVALUE, sensvalue);
	}

	public List<Sensword> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	/**
	 * Find all Sensword entities.
	 * 
	 * @return List<Sensword> all Sensword entities
	 */
	@SuppressWarnings("unchecked")
	public List<Sensword> findAll() {
		EntityManagerHelper.log("finding all Sensword instances", Level.INFO,
				null);
		try {
			final String queryString = "select model from Sensword model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}