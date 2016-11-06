package ro.ace.ucv.services.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import ro.ace.ucv.persistence.model.PricePoint;
import ro.ace.ucv.persistence.model.Product;
import ro.ace.ucv.services.dto.ProductData;

/**
 * Converts a Product model object into a Product data transfer object.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@Component
public class ProductReverseConverter implements Converter<ProductData, Product> {

	@Override
	public Product convert(ProductData source) {
		Product product = new Product();
		product.setCode(source.getCode());
		product.setName(source.getName());
		product.setUrl(source.getUrl());
		product.setImageUrl(source.getImageUrl());

		List<PricePoint> pricePoints = new ArrayList<>();
		product.setPricePoints(pricePoints);

		PricePoint pricePoint = new PricePoint();
		pricePoint.setDate(new Date());
		pricePoint.setPrice(source.getPrice());
		pricePoints.add(pricePoint);

		return product;
	}

}
