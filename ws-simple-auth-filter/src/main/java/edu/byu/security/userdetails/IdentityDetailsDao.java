package edu.byu.security.userdetails;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 05/10/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 05/10/2013
 */
public interface IdentityDetailsDao extends AuthenticationUserDetailsService, UserDetailsService {
}
