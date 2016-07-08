package edu.byu.edge.coreIdentity.security;

import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.IdentityServiceException;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Scott Hutchings on 7/7/2016.
 */
public class DefaultCoreIdentityUserDetailsService implements UserDetailsService {

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
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		final CoreIdentity identity = coreIdentityClient.getCoreIdentityByNetId(s);
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		if (memberOfClient != null){
			for (String group : groupsToCheck) {
				try {
					if (memberOfClient.isPersonMemberOfGroup(identity.getPersonId(), group)){
						authorities.add(new SimpleGrantedAuthority(group));
					}
				} catch (IdentityServiceException e) {
					e.printStackTrace();
					throw new UsernameNotFoundException("Error trying to verify if is member of group", e);
				}
			}
		}
		return new CoreIdentityUserDetails(identity, authorities);
	}
}
