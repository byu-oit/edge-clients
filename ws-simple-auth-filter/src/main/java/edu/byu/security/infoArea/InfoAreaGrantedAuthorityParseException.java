package edu.byu.security.infoArea;

/**
 * The InfoAreaGrantedAuthorityParseException is thrown when there is an error parsing an InfoAreaGrantedAuthority String.
 *
 * @author Andrew Largey
 */
public final class InfoAreaGrantedAuthorityParseException extends Throwable {

	/**
	 * Error message pattern String used to generate error message.
	 */
	private static final String MESSAGE = "The authority string [%s] is not a valid InfoAreaGrantedAuthority String representation.";

	/**
	 * Constructs an InfoAreaGrantedAuthorityParseException with the given invalidAuthorityString.
	 *
	 * @param invalidAuthorityString String that caused the exception.
	 */
	public InfoAreaGrantedAuthorityParseException(final String invalidAuthorityString) {
		super(String.format(MESSAGE, invalidAuthorityString));
	}
}
