package edu.byu.security.gro;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public final class GroGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 2l;

	public static final String GRO_AUTHORITY_PREFIX = "GRO_";

	private String authority = null;
	private String personId;
	private String groupName;

	GroGrantedAuthority() {

	}

	public GroGrantedAuthority(final String groupName) {
		Assert.hasText(groupName, "A 'groupName' is required to create a GroGrantedAuthority.");
		this.groupName = groupName;
		createAuthorityString();
	}

	String getPersonId() {
		return personId;
	}

	void setPersonId(String personId) {
		this.personId = personId;
	}

	String getGroupName() {
		return groupName;
	}

	void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String getAuthority() {
		if (authority == null) {
			createAuthorityString();
		}
		return authority;
	}

	private void createAuthorityString() {
		if (groupName == null) {
			throw new IllegalStateException("A groupName is required to create a GroGrantedAuthority.");
		}
		authority = String.format("%s%s", GRO_AUTHORITY_PREFIX, this.groupName.toUpperCase());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GroGrantedAuthority that = (GroGrantedAuthority) o;

		if (!groupName.equals(that.groupName)) return false;
		if (!personId.equals(that.personId)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = personId.hashCode();
		result = 31 * result + groupName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return getAuthority();
	}
}
