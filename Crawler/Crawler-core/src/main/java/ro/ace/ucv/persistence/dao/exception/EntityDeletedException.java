package ro.ace.ucv.persistence.dao.exception;

/**
 * Thrown if the entity does not exist.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class EntityDeletedException extends DaoException {

	/**
	 * The default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EntityDeletedException() {
	};

	/**
	 * Parameterized constructor.
	 * 
	 * @param message
	 *            a specific message for the exception
	 */
	public EntityDeletedException(String message) {
		super(message);
	}

}
