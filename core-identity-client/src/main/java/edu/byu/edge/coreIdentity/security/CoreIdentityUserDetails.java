package edu.byu.edge.coreIdentity.security;

import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by Scott Hutchings on 7/7/2016.
 */
public class CoreIdentityUserDetails implements UserDetails {

	private CoreIdentity coreIdentity;
	private List<GrantedAuthority> authorities;

	public CoreIdentityUserDetails(CoreIdentity coreIdentity, List<GrantedAuthority> authorities) {
		this.coreIdentity = coreIdentity;
		this.authorities = authorities;
	}

	public CoreIdentity getCoreIdentity() {
		return coreIdentity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return coreIdentity.getNetId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
