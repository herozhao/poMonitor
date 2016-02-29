package pomonitor.entity;

// default package

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * SynonyMousWord entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see .SynonyMousWord
 * @author MyEclipse Persistence Tools
 */
public class SynonyMousWordDAO implements ISynonyMousWordDAO {
	// property constants
	public static final String CATEGORY = "category";
	public static final String WORDS = "words";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

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
	 * SynonyMousWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            SynonyMousWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(SynonyMousWord entity) {
		EntityManagerHelper.log("saving SynonyMousWord instance", Level.INFO,
				null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * SynonyMousWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            SynonyMousWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(SynonyMousWord entity) {
		EntityManagerHelper.log("deleting SynonyMousWord instance", Level.INFO,
				null);
		try {
			entity = getEntityManager().getReference(SynonyMousWord.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * entity = SynonyMousWordDAO.update(entity);
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
	public SynonyMousWord update(SynonyMousWord entity) {
		EntityManagerHelper.log("updating SynonyMousWord instance", Level.INFO,
				null);
		try {
			SynonyMousWord result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public SynonyMousWord findById(Integer id) {
		EntityManagerHelper.log("finding SynonyMousWord instance with id: "
				+ id, Level.INFO, null);
		try {
			SynonyMousWord instance = getEntityManager().find(
					SynonyMousWord.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all SynonyMousWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SynonyMousWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<SynonyMousWord> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<SynonyMousWord> findByProperty(String propertyName,
			final Object value) {
		EntityManagerHelper.log(
				"finding SynonyMousWord instance with property: "
						+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from SynonyMousWord model where model."
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

	public List<SynonyMousWord> findByCategory(Object category) {
		return findByProperty(CATEGORY, category);
	}

	public List<SynonyMousWord> findByWords(Object words) {
		return findByProperty(WORDS, words);
	}

	/**
	 * Find all SynonyMousWord entities.
	 * 
	 * @return List<SynonyMousWord> all SynonyMousWord entities
	 */
	@SuppressWarnings("unchecked")
	public List<SynonyMousWord> findAll() {
		EntityManagerHelper.log("finding all SynonyMousWord instances",
				Level.INFO, null);
		try {
			final String queryString = "select model from SynonyMousWord model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}