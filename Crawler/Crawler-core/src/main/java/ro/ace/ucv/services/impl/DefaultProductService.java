package ro.ace.ucv.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.ace.ucv.persistence.dao.ProductDao;
import ro.ace.ucv.persistence.dao.exception.DaoException;
import ro.ace.ucv.persistence.dao.exception.EntityDeletedException;
import ro.ace.ucv.persistence.dao.exception.EntityRegisteredException;
import ro.ace.ucv.persistence.model.PricePoint;
import ro.ace.ucv.persistence.model.Product;
import ro.ace.ucv.services.ProductService;
import ro.ace.ucv.services.converters.PricePointConverter;
import ro.ace.ucv.services.converters.ProductReverseConverter;
import ro.ace.ucv.services.dto.PricePointData;
import ro.ace.ucv.services.dto.ProductData;
import ro.ace.ucv.services.exceptions.ProductSavingServiceException;
import ro.ace.ucv.services.exceptions.ServiceException;

/**
 * Default implementation of {@link ProductService} interface.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@Service
public class DefaultProductService implements ProductService {

	/**
	 * Used to make operations with product objects
	 */
	@Autowired
	private ProductDao productDao;

	/**
	 * Used to convert a product data transfer object into a product model.
	 */
	@Autowired
	private ProductReverseConverter productReverseConverter;

	/**
	 * Used to convert a product model into a product data transfer object.
	 */
	@Autowired
	private PricePointConverter pricePointConverter;

	@Override
	@Transactional
	public void saveProduct(ProductData productData) throws ServiceException {
		Product product;

		try {
			product = productDao.getByCode(productData.getCode());
		} catch (DaoException de) {
			throw new ServiceException("Persistence exception");
		}

		if (product != null) {
			PricePoint pricePoint = new PricePoint();
			pricePoint.setDate(new Date());
			pricePoint.setPrice(productData.getPrice());

			product.getPricePoints().add(pricePoint);

			try {
				productDao.update(product);
			} catch (EntityDeletedException e) {
				throw new ProductSavingServiceException(
						String.format("The product with the code %s does not exist.", product.getCode()));
			}
		} else {
			product = productReverseConverter.convert(productData);

			try {
				productDao.create(product);
			} catch (EntityRegisteredException e) {
				throw new ProductSavingServiceException(
						String.format("The product with the code %s is already saved", product.getCode()));
			}
		}
	}

	@Override
	@Transactional
	public void saveProducts(List<ProductData> productsData) throws ServiceException {
		for (ProductData productData : productsData) {
			saveProduct(productData);
		}
	}

	@Override
	@Transactional
	public List<PricePointData> getPriceEvolutionByProductCode(String productCode) throws ServiceException {
		Product product = null;
		try {
			product = productDao.getByCode(productCode);
		} catch (DaoException e) {
			throw new ServiceException("Persistence exception");
		}

		List<PricePointData> pricePointsData = new ArrayList<>();
		List<PricePoint> pricePoints = product.getPricePoints();
		System.out.println("model" + pricePoints);

		for (PricePoint point : pricePoints) {
			pricePointsData.add(pricePointConverter.convert(point));
		}

		System.out.println(pricePointsData);
		return pricePointsData;
	}
}
