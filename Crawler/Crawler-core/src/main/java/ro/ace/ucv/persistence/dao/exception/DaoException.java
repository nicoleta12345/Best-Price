package ro.ace.ucv.persistence.dao.exception;

/**
 * The DaoException class represents the base class for the exceptions thrown in
 * the DAO module.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class DaoException extends ApplicationException {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DaoException() {
	};

	/**
	 * Parameterized constructor.
	 * 
	 * @param message
	 *            a specific message for the exception
	 */
	public DaoException(String message) {
		super(message);
	}

}
