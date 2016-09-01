package edu.byu.edge.coreIdentity.security;

import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Scott Hutchings on 7/7/2016.
 */
public class DefaultCoreIdentityUserDetailsService implements UserDetailsService {
	private static final Logger LOG = Logger.getLogger(DefaultCoreIdentityUserDetailsService.class);

	private CoreIdentityClient coreIdentityClient;
	private MemberOfClient memberOfClient;
	private List<String> groupsToCheck;

	public DefaultCoreIdentityUserDetailsService(CoreIdentityClient coreIdentityClient) {
		this.coreIdentityClient = coreIdentityClient;
	}

	public void setMemberOfClient(MemberOfClient memberOfClient) {
		this.memberOfClient = memberOfClient;
	}

	public void setGroupsToCheck(List<String> groupsToCheck) {
		this.groupsToCheck = groupsToCheck;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final CoreIdentity identity;
		try {
			identity = coreIdentityClient.getCoreIdentityByNetId(username);
			List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
			if (memberOfClient != null){
				for (String group : groupsToCheck) {
					if (memberOfClient.isPersonMemberOfGroup(identity.getPersonId(), group)){
						authorities.add(new SimpleGrantedAuthority(group));
					}
				}
			}
			return new CoreIdentityUserDetails(identity, authorities);
		} catch (IOException e) {
			LOG.error("!! IOException", e);
			throw new UsernameNotFoundException("Error trying to load user by username: " + username, e);
		} catch (RestHttpException e) {
			LOG.error("!! RestHttpException", e);
			throw new UsernameNotFoundException("Error trying to load user by username: " + username, e);
		}
	}
}
