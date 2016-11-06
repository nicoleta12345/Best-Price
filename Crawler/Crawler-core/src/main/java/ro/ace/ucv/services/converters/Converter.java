package ro.ace.ucv.services.converters;

/**
 * Generic converter interface.
 * 
 * @author Nicoleta barbulescu
 *
 * @param <S>
 *            source
 * @param <T>
 *            target
 */
public interface Converter<S, T> {

	/**
	 * Converts an object from S to T.
	 * 
	 * @param source
	 *            the source of the information which will be stored into the
	 *            target object
	 * @return the converted object of type T
	 */
	T convert(S source);

}
