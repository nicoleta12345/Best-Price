package ro.ace.ucv.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.ace.ucv.services.ProductService;
import ro.ace.ucv.services.dto.PricePointData;
import ro.ace.ucv.services.exceptions.ServiceException;

/**
 * Controller used to retrieve products objects.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@RestController()
@RequestMapping("/products")
public class ProductRestController {

	/**
	 * Logger instance used to log information from the AccountServiceImpl.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

	/**
	 * Used to retrieve product information.
	 */
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/search/{productCode}", method = RequestMethod.GET)
	public ResponseEntity<List<PricePointData>> findByCode(@PathVariable("productCode") String productCode) {
		System.out.println(productCode);
		LOGGER.info("Find product with code: " + productCode);
		List<PricePointData> pricePoints = null;

		try {
			pricePoints = productService.getPriceEvolutionByProductCode(productCode);
		} catch (ServiceException e) {
			LOGGER.error(String.format("The product with code %s could not be retrieved!", productCode), e);
		}

		return new ResponseEntity<List<PricePointData>>(pricePoints, HttpStatus.OK);
	}

}
