package edu.byu.security.userdetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The IdentityDetails class is the BYU implementation of the UserDetails interface.
 *
 * @author Andrew Largey
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 */
public final class IdentityDetails implements UserDetails {

	/**
	 * UID of the IdentityDetails class used for serialization.
	 * <p/>
	 * Since this is stored in the users Session it must be Serializable.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * Person Id of the Identity these details represent.
	 * <p/>
	 * All Identities will have a personId.
	 */
	private String personId;

	/**
	 * Net Id of the Identity these details represent.
	 * <p/>
	 * Non-Person Identities are not guaranteed to have a netId. In fact they should not, though some do.
	 */
	private String netId;

	/**
	 * BYU ID of the Identity these details represent.
	 */
	private String byuId;

	/**
	 * First Name of the Identity these details represent.
	 */
	private String firstName;

	/**
	 * Surname of the Identity these details represent.
	 */
	private String surname;

	/**
	 * Position of the Surname for the Identity these details represent.
	 */
	private String surnamePosition;

	/**
	 * Full Name of the Identity these details represent.
	 */
	private String name;

	/**
	 * Email Address of the Identity these details represent.
	 * <p/>
	 * Identities are not required to have an Email Address.
	 */
	private String emailAddress;

	/**
	 * A map of attributes associated with this Identity.
	 * <p/>
	 * Identities are not required to have a attributes.
	 */
	private Map attributes;

	/**
	 * The Set of Granted Authorities that have been granted this this Identity.
	 * <p/>
	 * This represents the Authorizations for this Identity and will be used in Authorization
	 * decisions.
	 * @see org.springframework.security.core.GrantedAuthority
	 */
	private Set<GrantedAuthority> grantedAuthorities;

	/**
	 * Default Constructor.
	 * <p/>
	 * This constructor is used by Hibernate. The grantedAuthorities Set is populated with an Empty Set.
	 * @see java.util.Collections#emptySet()
	 */
	IdentityDetails() {
		this.grantedAuthorities = Collections.emptySet();
	}

	/**
	 * Constructor used to create the IdentityDetails.
	 * <p/>
	 * All the information is copied into the associated fields. The personId is validated, it can not be null and can not be empty. The set of
	 * GrantedAuthority objects are copied into an immutable set and put into the grantedAuthorities member.
	 *
	 * @param identityPersonId String personId of the Identity.
	 * @param identityNetId String netId of the Identity.
	 * @param identityByuId String BYU-Id of the Identity.
	 * @param identityName String name of the Identity.
	 * @param identityEmailAddress String emailAddress of the Identity.
	 * @param identityGrantedAuthorities Set of GrantedAuthority objects representing the authorizations granted to the Identity.
	 * @throws IllegalArgumentException if the personId supplied is null or empty.
	 * @see java.util.Collections#unmodifiableSet(java.util.Set)
	 */
	public IdentityDetails(final String identityPersonId, final String identityNetId, final String identityByuId, final String identityName, final String identityEmailAddress, final Set<GrantedAuthority> identityGrantedAuthorities) {
		Assert.hasLength(identityPersonId, "A person-id is required in an IdentityDetails.");
		this.personId = identityPersonId;
		this.netId = identityNetId;
		this.byuId = identityByuId;
		this.name = identityName;
		this.emailAddress = identityEmailAddress;
		if (identityGrantedAuthorities != null && identityGrantedAuthorities.size() > 0) {
			this.grantedAuthorities = Collections.unmodifiableSet(identityGrantedAuthorities);
		} else {
			this.grantedAuthorities = Collections.emptySet();
		}
	}

	/**
	 * Gets the personId of this IdentityDetails.
	 *
	 * @return String personId.
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * Sets the personId of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identityPersonId String personId of this IdentityDetails.
	 */
	void setPersonId(final String identityPersonId) {
		this.personId = identityPersonId;
	}

	/**
	 * Gets the netId of this IdentityDetails.
	 *
	 * @return String netId.
	 */
	public String getNetId() {
		return netId;
	}

	/**
	 * Sets the netId of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identityNetId String netId of this IdentityDetails.
	 */
	void setNetId(final String identityNetId) {
		this.netId = identityNetId;
	}

	/**
	 * Gets the byuId of this IdentityDetails.
	 *
	 * @return String byuId.
	 */
	public String getByuId() {
		return byuId;
	}

	/**
	 * Sets the byuId of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identityByuId String byuId of this IdentityDetails.
	 */
	void setByuId(final String identityByuId) {
		this.byuId = identityByuId;
	}

	/**
	 * Gets the firstName of this IdentityDetails.
	 * <p/>
	 * This is here for Hibernate, call #getName() to get the name of this Identity.
	 *
	 * @return String firstName.
	 */
	String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identityFirstName String firstName of this IdentityDetails.
	 */
	void setFirstName(final String identityFirstName) {
		this.firstName = identityFirstName;
	}

	/**
	 * Gets the surname of this IdentityDetails.
	 * <p/>
	 * This is here for Hibernate, call #getName() to get the name of this Identity.
	 *
	 * @return String surname.
	 */
	String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identitySurname String surname of this IdentityDetails.
	 */
	void setSurname(final String identitySurname) {
		this.surname = identitySurname;
	}

	/**
	 * Gets the surnamePosition of this IdentityDetails.
	 * <p/>
	 * This is here for Hibernate, call #getName() to get the name of this Identity.
	 *
	 * @return String surnamePosition.
	 */
	String getSurnamePosition() {
		return surnamePosition;
	}

	/**
	 * Sets the surnamePosition of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identitySurnamePosition String surnamePosition of this IdentityDetails.
	 */
	void setSurnamePosition(final String identitySurnamePosition) {
		this.surnamePosition = identitySurnamePosition;
	}

	/**
	 * Gets the name of this IdentityDetails.
	 *
	 * @return String name.
	 */
	public String getName() {
		if (this.name == null) {
			this.name = calculateName();
		}
		return name;
	}

	/**
	 * Calculates the name for this IdentityDetails.
	 * <p/>
	 * The firstName, surname, and surnamePosition are used to build the name
	 * of this IdentityDetails.
	 * @return String calculated name.
	 */
	private String calculateName() {
		if (firstName == null || surname == null || surnamePosition == null) {
			return null;
		}
		StringBuilder nameBuilder = new StringBuilder(firstName);
		if ("F".equalsIgnoreCase(surnamePosition)) {
			nameBuilder.insert(0, " ").insert(0, surname);
		} else {
			nameBuilder.append(" ").append(surname);
		}
		return nameBuilder.toString();
	}

	/**
	 * Gets the emailAddress of this IdentityDetails.
	 *
	 * @return String emailAddress.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the emailAddress of this IdentityDetails.
	 * <p/>
	 * This is used by Hibernate.
	 * @param identityEmailAddress String emailAddress of this IdentityDetails.
	 */
	void setEmailAddress(final String identityEmailAddress) {
		this.emailAddress = identityEmailAddress;
	}

	/**
	 * Get the Map of attributes for this IdentityDetails.
	 * @return Map attributes
	 */
	public Map getAttributes() {
		return attributes;
	}

	/**
	 * Sets the attributes for this IdentityDetails.
	 * @param identityAttributes Map attributes of this IdentityDetails
	 */
	public void setAttributes(final Map identityAttributes) {
		this.attributes = identityAttributes;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}

	/**
	 * Sets the grantedAuthorities for this IdentityDetails.
	 * <p/>
	 * The Set of GrantedAuthority objects replace any current grantedAuthorities
	 * and are wrapped in an immutable set. This method meant to be used by an
	 * UserDetailsService implementation to add the appropriate GrantedAuthority objects
	 * to the IdentityDetails.
	 *
	 * @param identityGrantedAuthorities Set of GrantedAuthority objects for this IdentityDetails.
	 */
	void setAuthorities(final Set<GrantedAuthority> identityGrantedAuthorities) {
		this.grantedAuthorities = Collections.unmodifiableSet(identityGrantedAuthorities);
	}


	/**
	 * Gets the password of the IdentityDetails.
	 *
	 * @return String always returns "**********" as the password is never stored in this object.
	 */
	@Override
	public String getPassword() {
		return "**********";
	}

	/**
	 * Gets the username of the IdentityDetails.
	 *
	 * @return String netId of the IdentityDetails.
	 */
	@Override
	public String getUsername() {
		return this.netId;
	}

	/**
	 * @return boolean always true.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @return boolean always true.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @return boolean always true.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @return boolean always true.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Gets the current IdentityDetails as stored in the SecurityContextHolder.
	 * <p/>
	 * Convenience method for getting the current IdentityDetails, is any, as stored in the
	 * SecurityContextHolder.
	 *
	 * @return IdentityDetails currently stored in the SecurityContextHolder.
	 * @see org.springframework.security.core.context.SecurityContextHolder
	 */
	public static IdentityDetails getCurrentIdentityDetails() {
		IdentityDetails identityDetails = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				Object principal = authentication.getPrincipal();
				if (principal != null && IdentityDetails.class.isAssignableFrom(principal.getClass())) {
					identityDetails = (IdentityDetails) principal;
				}
			}
		}
		return identityDetails;
	}

	/**
	 * Determines if the given Object is equal to this IdentityDetails.
	 * <p/>
	 * The personId of the IdentityDetails is the only thing that is used
	 * in this comparison.
	 * @param o Object to compare this IdentityDetails.
	 * @return boolean if this.personId.equals(o.personId)
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IdentityDetails that = (IdentityDetails) o;

		if (!personId.equals(that.personId)) {
			return false;
		}

		return true;
	}

	/**
	 * Generates the hashCode of this IdentityDetails.
	 * <p/>
	 * The hashCode is generated from the personId.
	 * @return int hashCode of the personId.
	 */
	@Override
	public int hashCode() {
		return personId.hashCode();
	}

	/**
	 * Generates a String representation of this IdentityDetails.
	 * <p/>
	 * The personId and name are the only information output by a call
	 * to #toString().
	 *
	 * @return String representation of this IdentityDetails.
	 */
	@Override
	public String toString() {
		return new StringBuilder("IdentityDetails{personId='")
				.append(personId).append('\'').append(", name='")
				.append(getName()).append('\'').append('}').toString();
	}
}
