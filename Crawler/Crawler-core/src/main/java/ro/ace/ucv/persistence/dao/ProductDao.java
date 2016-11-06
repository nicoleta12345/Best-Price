package ro.ace.ucv.persistence.dao;

import java.util.List;

import ro.ace.ucv.persistence.dao.exception.DaoException;
import ro.ace.ucv.persistence.model.Product;

/**
 * Data access object for Product objects.
 * 
 * @author nicoleta.barbulescu
 *
 */
public interface ProductDao extends GenericDao<Product> {

	/**
	 * Saves a list of products.
	 * 
	 * @param products
	 *            the products list which will be saved
	 */
	void saveAll(List<Product> products);

	/**
	 * Gets a product by code.
	 * 
	 * @param code
	 *            the identifier of the product
	 * @return the product if has been found
	 * @throws DaoException
	 *             thrown if there is a persistence exception
	 */
	Product getByCode(String code) throws DaoException;

}
