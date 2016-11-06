package ro.ace.ucv.persistence.dao.exception;

/**
 * Thrown if the entity is already saved.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class EntityRegisteredException extends DaoException {

	/**
	 * The default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EntityRegisteredException() {
	};

	/**
	 * Parameterized constructor.
	 * 
	 * @param message
	 *            a specific message for the exception
	 */
	public EntityRegisteredException(String message) {
		super(message);
	}

}
