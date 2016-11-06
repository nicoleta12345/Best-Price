package ro.ace.ucv.persistence.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ro.ace.ucv.persistence.dao.ProductDao;
import ro.ace.ucv.persistence.dao.exception.DaoException;
import ro.ace.ucv.persistence.model.Product;

/**
 * Default implementation of {@link ProductDao}data access object interface.
 */
@Repository
public class DefaultProductDao extends GenericDaoImpl<Product> implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveAll(List<Product> products) {
		entityManager.persist(products);
	}

	@Override
	public Product getByCode(String code) throws DaoException {
		Object result = null;

		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> cq = cb.createQuery(Product.class);
			Root<Product> root = cq.from(Product.class);
			cq.where(cb.equal(root.get("code"), code));
			Query q = entityManager.createQuery(cq);

			result = q.getSingleResult();
		} catch (NoResultException e) {
			// stay silent, null will be returned
		} catch (PersistenceException e) {
			throw new DaoException("Persistence Failure!");
		}

		return (Product) result;
	}

}
