package edu.byu.security.common;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;


/**
 * The GrantedAuthorityProvider interface defines a common mechanism for providing GrantedAuthority information based on personId.
 *
 * @param <T> Something that extends GrantedAuthority
 * @author Andrew Largey
 */
public interface GrantedAuthorityProvider<T extends GrantedAuthority> {

	/**
	 * Gets a List of GrantedAuthority objects associated to the given personId.
	 * <p/>
	 * The resulting List must not be null, it should be empty if no GrantedAuthority objects were
	 * found.
	 *
	 * @param personId String personId used to get the GrantedAuthority List.
	 * @return List of GrantedAuthority objects, can be empty but not null.
	 */
	List<T> getGrantedAuthorities(String personId);
}
