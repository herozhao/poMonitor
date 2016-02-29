package pomonitor.entity;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * Emotionalword entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see pomonitor.entity.Emotionalword
 * @author MyEclipse Persistence Tools
 */
public class EmotionalwordDAO implements IEmotionalwordDAO {
	// property constants
	public static final String WORD = "word";
	public static final String SPEECH = "speech";
	public static final String POLARITY = "polarity";
	public static final String STRENGTH = "strength";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

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
	 * EmotionalwordDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Emotionalword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Emotionalword entity) {
		EntityManagerHelper.log("saving Emotionalword instance", Level.INFO,
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
	 * Delete a persistent Emotionalword entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * EmotionalwordDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Emotionalword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Emotionalword entity) {
		EntityManagerHelper.log("deleting Emotionalword instance", Level.INFO,
				null);
		try {
			entity = getEntityManager().getReference(Emotionalword.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

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
	 * entity = EmotionalwordDAO.update(entity);
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
	public Emotionalword update(Emotionalword entity) {
		EntityManagerHelper.log("updating Emotionalword instance", Level.INFO,
				null);
		try {
			Emotionalword result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Emotionalword findById(Integer id) {
		EntityManagerHelper.log(
				"finding Emotionalword instance with id: " + id, Level.INFO,
				null);
		try {
			Emotionalword instance = getEntityManager().find(
					Emotionalword.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Emotionalword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Emotionalword property to query
	 * @param value
	 *            the property value to match
	 * @return List<Emotionalword> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Emotionalword> findByProperty(String propertyName,
			final Object value) {
		EntityManagerHelper.log(
				"finding Emotionalword instance with property: " + propertyName
						+ ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Emotionalword model where model."
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

	public List<Emotionalword> findByWord(Object word) {
		return findByProperty(WORD, word);
	}

	public List<Emotionalword> findBySpeech(Object speech) {
		return findByProperty(SPEECH, speech);
	}

	public List<Emotionalword> findByPolarity(Object polarity) {
		return findByProperty(POLARITY, polarity);
	}

	public List<Emotionalword> findByStrength(Object strength) {
		return findByProperty(STRENGTH, strength);
	}

	/**
	 * Find all Emotionalword entities.
	 * 
	 * @return List<Emotionalword> all Emotionalword entities
	 */
	@SuppressWarnings("unchecked")
	public List<Emotionalword> findAll() {
		EntityManagerHelper.log("finding all Emotionalword instances",
				Level.INFO, null);
		try {
			final String queryString = "select model from Emotionalword model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}