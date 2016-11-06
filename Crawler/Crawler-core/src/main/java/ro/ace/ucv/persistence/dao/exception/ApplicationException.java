package ro.ace.ucv.persistence.dao.exception;

/**
 * The ApplicationException class represents the base class for the exceptions from the application.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class ApplicationException extends Exception {

  /**
   * The default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public ApplicationException() {
  };

  /**
   * Parameterized constructor.
   * 
   * @param message
   */
  public ApplicationException(String message) {
    super(message);
  }

}
