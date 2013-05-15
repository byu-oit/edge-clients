package edu.byu.security.common;

import org.springframework.security.core.GrantedAuthority;

/**
 * The ComplexGrantedAuthority defines a GrantedAuthority that requires a more complex comparison for Authorization than a String representation provides.
 * <p/>
 * GrantedAuthority objects are compared using their String representations by default, by implementing this class a GrantedAuthority can be compared against
 * another ComplexGrantedAuthority as an Object instead of just a String. To use this a voter must be created that will evaluate the ComplexGrantedAuthority
 * objects against each other.
 *
 * @author Andrew Largey
 */
public abstract class ComplexGrantedAuthority implements GrantedAuthority {

	/**
	 * Checks if the given ComplexGrantedAuthority should be authorized based on the value of this ComplexGrantedAuthority.
	 *
	 * @param requiredGrantedAuthority ComplexGrantedAuthority to be checked against this ComplexGrantedAuthority.
	 * @return boolean authorization result. Returns true if the given ComplexGrantedAuthority is authorized based on the value of this
	 *         ComplexGrantedAuthority, otherwise false is returned.
	 */
	public abstract boolean hasAuthorization(ComplexGrantedAuthority requiredGrantedAuthority);
}
