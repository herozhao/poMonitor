package pomonitor.entity;

// default package

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * LeverWord entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see .LeverWord
 * @author MyEclipse Persistence Tools
 */
public class LeverWordDAO implements ILeverWordDAO {
	// property constants
	public static final String WORD = "word";
	public static final String SCORE = "score";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved LeverWord entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * LeverWordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LeverWord entity) {
		EntityManagerHelper.log("saving LeverWord instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LeverWord entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * LeverWordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LeverWord entity) {
		EntityManagerHelper
				.log("deleting LeverWord instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(LeverWord.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LeverWord entity and return it or a copy of it
	 * to the sender. A copy of the LeverWord entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = LeverWordDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            LeverWord entity to update
	 * @return LeverWord the persisted LeverWord entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LeverWord update(LeverWord entity) {
		EntityManagerHelper
				.log("updating LeverWord instance", Level.INFO, null);
		try {
			LeverWord result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public LeverWord findById(Integer id) {
		EntityManagerHelper.log("finding LeverWord instance with id: " + id,
				Level.INFO, null);
		try {
			LeverWord instance = getEntityManager().find(LeverWord.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all LeverWord entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LeverWord property to query
	 * @param value
	 *            the property value to match
	 * @return List<LeverWord> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LeverWord> findByProperty(String propertyName,
			final Object value) {
		EntityManagerHelper.log("finding LeverWord instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from LeverWord model where model."
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

	public List<LeverWord> findByWord(Object word) {
		return findByProperty(WORD, word);
	}

	public List<LeverWord> findByScore(Object score) {
		return findByProperty(SCORE, score);
	}

	/**
	 * Find all LeverWord entities.
	 * 
	 * @return List<LeverWord> all LeverWord entities
	 */
	@SuppressWarnings("unchecked")
	public List<LeverWord> findAll() {
		EntityManagerHelper.log("finding all LeverWord instances", Level.INFO,
				null);
		try {
			final String queryString = "select model from LeverWord model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}