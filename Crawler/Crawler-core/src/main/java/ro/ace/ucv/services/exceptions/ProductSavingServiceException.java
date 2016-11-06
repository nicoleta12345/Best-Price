package ro.ace.ucv.services.exceptions;

/**
 * Thrown if the product could not be saved.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class ProductSavingServiceException extends ServiceException {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ProductSavingServiceException() {
	};

	/**
	 * Parameterized constructor.
	 * 
	 * @param message
	 *            a specific message for the exception
	 */
	public ProductSavingServiceException(String message) {
		super(message);
	}

}
