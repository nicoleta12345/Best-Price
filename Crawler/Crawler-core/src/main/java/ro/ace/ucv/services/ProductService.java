package ro.ace.ucv.services;

import java.util.List;

import ro.ace.ucv.services.dto.PricePointData;
import ro.ace.ucv.services.dto.ProductData;
import ro.ace.ucv.services.exceptions.ProductSavingServiceException;
import ro.ace.ucv.services.exceptions.ServiceException;

/**
 * 
 * Business service for handling products.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public interface ProductService {

	/**
	 * Saves a product object.
	 * 
	 * @param product
	 *            the product which will be saved
	 * @throws ProductSavingServiceException
	 *             thrown if the product is already saved
	 * @throws ServiceException
	 *             throw if there was a persistence problem
	 */
	void saveProduct(ProductData product) throws ProductSavingServiceException, ServiceException;

	/**
	 * Saves a list of products.
	 * 
	 * @param productsData
	 *            the products list which will be saved
	 * @throws ServiceException
	 *             thrown if there is a persistence problem
	 */
	void saveProducts(List<ProductData> productsData) throws ServiceException;

	/**
	 * Gets a list of points composed by price and date specific to a product
	 * identified by a code.
	 * 
	 * @param productCode
	 *            the identifier of the product
	 * @return a list of price points
	 * @throws ServiceException
	 *             throw if there was a persistence problem
	 */
	List<PricePointData> getPriceEvolutionByProductCode(String productCode) throws ServiceException;

}
