package edu.byu.security.common;

import org.springframework.security.core.GrantedAuthority;

import java.util.Comparator;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 05/15/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 05/15/2013
 */
public class GrantedAuthorityComparator implements Comparator<GrantedAuthority> {
	@Override
	public int compare(final GrantedAuthority o1, final GrantedAuthority o2) {
		if (o1 == o2) return 0;
		if (o1 != null && o2 != null) return o1.toString().compareTo(o2.toString());
		if (o1 == null && o2 != null) return 1;
		return -1;
	}
}
