package ro.ace.ucv.services.exceptions;

import ro.ace.ucv.persistence.dao.exception.ApplicationException;

/**
 * The ServiceException class represents the base class for the exceptions
 * thrown in the Services module.
 * 
 * @author Nicoleta Barbulescu
 * 
 */
public class ServiceException extends ApplicationException {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ServiceException() {
	};

	/**
	 * Parameterized constructor.
	 * 
	 * @param message
	 *            a specific message for the exception
	 */
	public ServiceException(String message) {
		super(message);
	}

}
