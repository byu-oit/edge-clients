package edu.byu.security.infoArea;

import edu.byu.security.common.ComplexGrantedAuthority;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The InfoAreaGrantedAuthority is a ComplexGrantedAuthority used to represent authorizations granted from the Athens system(Info Areas).
 * <p/>
 * This GrantedAuthority has undergone several revisions, particularly the String representation of the Authority value.
 * There are 3 supported String formats:
 * <p/>
 * Version 1:
 * <p/>
 * INFO_AREA_{Name | Required}_{Update Type[U, D, *] | Optional}
 * INFO_AREA_PERSON - Will expect there to be no update type;
 * INFO_AREA_PERSON_U - will not match for any limitation type or value.
 * INFO_AREA_PERSON_* - will match any update type.
 * <p/>
 * Version 2:
 * <p/>
 * INFO_AREA:{NAME[{name}] | Required, UPDATE_TYPE[update, display, U, D, *] | Optional, LIMITATION_TYPE[{type}, *] | Optional, LIMITATION_VALUE[{value}, *] | Optional}}
 * INFO_AREA:NAME=PERSON
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=update
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=*
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=update;LIMITATION_TYPE=*;LIMITATION_VALUE=* - must match explicit limitation type and value.
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=update;LIMITATION_TYPE=Some Type;LIMITATION_VALUE=* - must match explicit limitation type and value.
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=update;LIMITATION_TYPE=Some Type;LIMITATION_VALUE=Some value - must match explicit limitation type and value.
 * INFO_AREA:NAME=PERSON;UPDATE_TYPE=update;LIMITATION_TYPE=*;LIMITATION_VALUE=Some Value - must match explicit limitation type and value.
 * <p/>
 * Version 3:
 * <p/>
 * IA:{Name | Required}:{Update Type[update, display, U, D, *] | Optional}:{Limitation Type[{type}, *] | Optional}:{Limitation Value[{value}, *] | Optional}
 * IA:PERSON
 * IA:PERSON:U - will not match for any limitation type or value.
 * IA:PERSON:*
 * IA:PERSON:U:* - will match for any limitation type or value.
 * IS:PERSON:U:Some Type - will match for the limitation type 'Some Type' with no value.
 * IA:PERSON:U:Some Type:* - will match for the limitation type 'Some Type' with any value.
 * IA:PERSON:U:Some Type:Some Value - will math for the limitation type 'Some Type' and value 'Some Value'.
 * IA:PERSON:U:*:Some Value - will math for any limitation type with value 'Some Value'.
 *
 * Moving forward please migrate to using Version 3 of the format as the other versions will be deprecated in the future.
 *
 * @author Andrew Largey
 *
 */
public final class InfoAreaGrantedAuthority extends ComplexGrantedAuthority {

	/**
	 * UID of the InfoAreaGrantedAuthority class used for serialization.
	 * <p/>
	 * Since this is stored in the users Session it must be Serializable.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * Seed value for hash code calculation.
	 */
	private static final int HASH_CODE_SEED = 13;

	/**
	 * Multiplier used to calculate the hash code.
	 */
	private static final int HASH_CODE_MULTIPLIER = 3;

	/**
	 * The UpdateType enumeration defines the possible values for UPDATE_TYPE for the InformationalArea.
	 *
	 * @author Andrew Largey
	 *
	 */
	public static enum UpdateType {
		/**
		 * Possible UpdateType values.
		 */
		UPDATE("U"), DISPLAY("D"), NONE("");

		/**
		 * String value of the UpdateType.
		 */
		private String value;

		/**
		 * Constructs an UpdateType with the given value.
		 * @param valueString String value of the UpdateType.
		 */
		private UpdateType(final String valueString) {
			this.value = valueString;
		}

		/**
		 * Getter for the value.
		 * @return String value of the UpdateType.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Converts a String to the appropriate UpdateType.
		 * @param value String value to convert.
		 * @return UpdateType that matches the value, values other than U, and D will return as NONE.
		 */
		public static UpdateType fromValue(final String value) {
			for (UpdateType updateType : values()) {
				if (updateType.getValue().equalsIgnoreCase(value)) {
					return updateType;
				}
			}
			return NONE;
		}

		@Override
		public String toString() {
			return value;
		}

		/**
		 * Determines if this UpdateType is accessible by the target UpdateType.
		 * <p/>
		 * #NONE can only access #NONE
		 * #DISPLAY can only access #DISPLAY
		 * #UPDATE can access #UPDATE and #DISPLAY
		 * @param target UpdateType target to check if it can access this.
		 * @return boolean if the target can access this, and false otherwise.
		 */
		public boolean isAllowed(final UpdateType target) {
			if (target == null) {
				return false;
			}
			if (this.equals(target)) {
				return true;
			}
			switch (target) {
				case NONE:
					return false;
				case DISPLAY:
					return false;
				case UPDATE:
					return this.equals(DISPLAY);
				default:
					return false;
			}
		}

	}

	/**
	 * The StringRepresentation enumeration contains the possible String representation versions supported.
	 *
	 * @author Andrew Largey
	 * @since 1.2.0.0
	 */
	private static enum StringRepresentation {
		/**
		 * String representation values.
		 */
		V3;

	}


	/**
	 * Log4J logger used to log information from this class.
	 * <p/>
	 * The logger name is the class name.
	 */
	private static final Logger LOG = Logger.getLogger(InfoAreaGrantedAuthority.class);

	/**
	 * Version 3 prefix.
	 */
	private static final String INFO_AREA_PREFIX_V3 = "IA";

	/**
	 * Version 3 delimiter.
	 */
	private static final String INFO_AREA_DELIMITER_V3 = ":";

	/**
	 * Version 3 String prefix.
	 */
	private static final String INFO_AREA_STRING_PREFIX_V3 = INFO_AREA_PREFIX_V3 + INFO_AREA_DELIMITER_V3;

	/**
	 * Version 3 max tokens.
	 */
	private static final int INFO_AREA_V3_MAX_TOKEN_LENGTH = 5;

	/**
	 * Version 3 token length if including UpdateType.
	 */
	private static final int INFO_AREA_V3_WITH_UPDATE_TYPE_LENGTH = 3;

	/**
	 * Version 3 index of limitation type.
	 */
	private static final int INFO_AREA_V3_LIMITATION_TYPE_INDEX = 3;

	/**
	 * Version 3 token length if including limitation type.
	 */
	private static final int INFO_AREA_V3_WITH_LIMITATION_TYPE_LENGTH = 4;

	/**
	 * Version 3 index of limitation value.
	 */
	private static final int INFO_AREA_V3_LIMITATION_VALUE_INDEX = 4;

	/**
	 * Wild Card Token.
	 */
	private static final String WILD_CARD_TOKEN = "*";

	/**
	 * Cache of parsed InfoAreaGrantedAuthority.
	 * <p/>
	 * Maps from String to InfoAreaGrantedAuthority where the String is the parsed authority string.
	 */
	private static final ConcurrentMap<String, InfoAreaGrantedAuthority> GRANTED_AUTHORITY_CACHE = new ConcurrentHashMap<String, InfoAreaGrantedAuthority>();

	/**
	 * Determines if the provided String is an InfoAreaGrantedAuthority.
	 * @param authorityString String used to determine if it is an InfoAreaGrantedAuthority String.
	 * @return boolean true if it is a InfoAreaGrantedAuthority String, false otherwise.
	 */
	public static boolean isAthensGrantedAuthority(final String authorityString) {
		return isValidString(authorityString) && (isV3AuthorityString(authorityString.trim()));
	}

	/**
	 * Checks the parameter to see if it is null or empty.
	 * @param parameter String to check.
	 * @return boolean true if it is not null and non-empty, false otherwise.
	 */
	private static boolean isValidString(final String parameter) {
		return parameter != null && parameter.trim().length() > 0;
	}

	/**
	 * Determines if the given String is a Version 3 authorityString.
	 * @param authorityString String to check.
	 * @return boolean true if it starts with #INFO_AREA_STRING_PREFIX_V3.
	 */
	private static boolean isV3AuthorityString(final String authorityString) {
		return authorityString.startsWith(INFO_AREA_STRING_PREFIX_V3);
	}

	/**
	 * Parses the given string into an InfoAreaGrantedAuthority.
	 * @param inputString String to parse.
	 * @return InfoAreaGrantedAuthority representation of the authority string.
	 * @throws InfoAreaGrantedAuthorityParseException when there is an error parsing the authority string.
	 */
	public static InfoAreaGrantedAuthority parseAuthority(final String inputString) throws InfoAreaGrantedAuthorityParseException {
		if (!isAthensGrantedAuthority(inputString)) {
			throw new InfoAreaGrantedAuthorityParseException(inputString);
		}
		String authorityString = inputString.trim();
		if (GRANTED_AUTHORITY_CACHE.containsKey(authorityString)) {
			return GRANTED_AUTHORITY_CACHE.get(authorityString);
		}
		InfoAreaGrantedAuthority perm = doParseV3AuthorityString(authorityString);
		if (perm == null || !perm.isValid()) {
			throw new InfoAreaGrantedAuthorityParseException(inputString);
		}
		GRANTED_AUTHORITY_CACHE.putIfAbsent(authorityString, perm);
		return perm;
	}

	/**
	 * Parses Version 3 authority Strings.
	 * @param authorityString String to parse as a version 3 formatted string.
	 * @return InfoAreaGrantedAuthority parsed from the version 3 string.
	 */
	private static InfoAreaGrantedAuthority doParseV3AuthorityString(final String authorityString) {
		if (!isV3AuthorityString(authorityString)) {
			return null;
		}
		String[] tokens = authorityString.split(INFO_AREA_DELIMITER_V3, -1);
		if (tokens.length > INFO_AREA_V3_MAX_TOKEN_LENGTH) {
			return null;
		}
		InfoAreaGrantedAuthority authority = new InfoAreaGrantedAuthority();
		authority.informationalArea = processWildCard(tokens[1].trim());
		if (tokens.length >= INFO_AREA_V3_WITH_UPDATE_TYPE_LENGTH) {
			authority.updateType = processUpdateType(tokens[2].trim());
			authority.updateTypeProvided = true;
			if (tokens.length == INFO_AREA_V3_WITH_UPDATE_TYPE_LENGTH) {
				if (authority.updateType == null) {
					authority.limitationType = null;
					authority.limitationValue = null;
				}
			} else {
				authority.limitationType = processWildCard(tokens[INFO_AREA_V3_LIMITATION_TYPE_INDEX].trim());
				authority.limitationTypeProvided = true;
				if (tokens.length == INFO_AREA_V3_WITH_LIMITATION_TYPE_LENGTH) {
					if (authority.limitationType == null) {
						authority.limitationValue = null;
					}
				} else {
					authority.limitationValue = processWildCard(tokens[INFO_AREA_V3_LIMITATION_VALUE_INDEX].trim());
					authority.limitationValueProvided = true;
				}
			}
		}
		return authority;
	}

	/**
	 * Converts the given String value to an UpdateType.
	 * @param value String value to convert.
	 * @return UpdateType representing the value.
	 */
	private static UpdateType processUpdateType(final String value) {
		UpdateType updateType;
		if (WILD_CARD_TOKEN.equalsIgnoreCase(value)) {
			updateType = null;
		} else {
			updateType = UpdateType.fromValue(value);
		}
		return updateType;
	}

	/**
	 * Processes a wild card value.
	 * @param value String value that may be a wild chard.
	 * @return null if it is a wild card, the original value if it is not.
	 */
	private static String processWildCard(final String value) {
		if (WILD_CARD_TOKEN.equalsIgnoreCase(value)) {
			return null;
		}
		return value;
	}

	/**
	 * PersonId the InfoAreaGrantedAuthority is granted to.
	 * <p/>
	 * This is used to look up the authorizations from the Database and is populated by Hibernate.
	 */
	private String personId;

	/**
	 * Should always be BYU.
	 * <p/>
	 * Another field from the database.
	 */
	private String creditInstitution;

	/**
	 * Point in time the authorization went into affect.
	 */
	private Date effectiveDate;

	/**
	 * Info Area the authorization grants or requires.
	 */
	private String informationalArea;

	/**
	 * Update type granted or required by this authority.
	 */
	private UpdateType updateType;

	/**
	 * Indicates if the updateType was provided when/if the representation was parsed from a string.
	 */
	private boolean updateTypeProvided = false;

	/**
	 * Limitation type of this authorization.
	 */
	private String limitationType;

	/**
	 * Indicates if the limitationType was provided when/if the representation was parsed from a string.
	 */
	private boolean limitationTypeProvided = false;

	/**
	 * Limitation value of this authorization.
	 */
	private String limitationValue;

	/**
	 * Indicates if the limitationValue was provided when/if the representation was parsed from a string.
	 */
	private boolean limitationValueProvided = false;

	/**
	 * Expiration date of this authority.
	 */
	private Date expiredDate;

	/**
	 * Value returned from a call to #toString.
	 */
	private String toString;

	/**
	 * Indicates the version of the representation of this GrantedAuthority.
	 */
	private StringRepresentation representation;

	/**
	 * Default constructor, used by Hibernate.
	 */
	InfoAreaGrantedAuthority() {
		this.informationalArea = "";
		this.updateType = UpdateType.NONE;
		this.limitationType = "";
		this.limitationValue = "";
		this.representation = InfoAreaGrantedAuthority.StringRepresentation.V3;
	}

	/**
	 * Constructors an InfoAreaGrantedAuthority from the provided information.
	 * @param providedInformationalArea String info area name, required to be not null and non empty.
	 * @param providedUpdateType UpdateType can be null.
	 * @param providedLimitationType String can be null.
	 * @param providedLimitationValue String can be null.
	 * @param providedExpiredDate Date can be null.
	 */
	public InfoAreaGrantedAuthority(final String providedInformationalArea,
			final UpdateType providedUpdateType,
			final String providedLimitationType,
			final String providedLimitationValue,
			final Date providedExpiredDate) {
		this();
		this.informationalArea = providedInformationalArea;
		setUpdateType(providedUpdateType);
		setLimitationType(providedLimitationType);
		setLimitationValue(providedLimitationValue);
		this.expiredDate = providedExpiredDate;
		if (!this.isValid()) {
			throw new IllegalArgumentException("The supplied information does not constitute a valid InfoAreaGrantedAuthority.");
		}
	}

	/**
	 * Getter for personId.
	 * <p/>
	 * Used by Hibernate.
	 * @return String personId.
	 */
	String getPersonId() {
		return this.personId;
	}

	/**
	 * Setter for personId.
	 * <p/>
	 * Used by Hibernate.
	 * @param providedPersonId String value of personId.
	 */
	void setPersonId(final String providedPersonId) {
		this.personId = providedPersonId;
	}

	/**
	 * Getter for creditInstitution.
	 * <p/>
	 * Used by Hibernate.
	 * @return String creditInstitution.
	 */
	String getCreditInstitution() {
		return creditInstitution;
	}

	/**
	 * Setter for creditInstitution.
	 * <p/>
	 * Used by Hibernate.
	 * @param creditInstitutionParameter String creditInstitution.
	 */
	void setCreditInstitution(final String creditInstitutionParameter) {
		this.creditInstitution = creditInstitutionParameter;
	}

	/**
	 * Getter for effectiveDate.
	 * <p/>
	 * Used by Hibernate.
	 * @return Date effectiveDate.
	 */
	Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Setter for effectiveDate.
	 * <p/>
	 * Used by Hibernate.
	 * @param effectiveDateParameter Date effectiveDate
	 */
	void setEffectiveDate(final Date effectiveDateParameter) {
		this.effectiveDate = effectiveDateParameter;
	}

	/**
	 * Getter for info area name.
	 * @return String informationalArea.
	 */
	public String getInformationalArea() {
		return informationalArea;
	}

	/**
	 * Setter for informationalArea.
	 * <p/>
	 * Used by Hibernate.
	 * @param informationalAreaParameter String informationalArea
	 */
	void setInformationalArea(final String informationalAreaParameter) {
		this.informationalArea = informationalAreaParameter;
	}

	/**
	 * Getter for limitationType.
	 * @return String limitationType.
	 */
	public String getLimitationType() {
		return limitationType;
	}

	/**
	 * Setter for limitationType.
	 * <p/>
	 * Used by Hibernate.
	 * @param limitationTypeParamter String limitationType
	 */
	void setLimitationType(final String limitationTypeParamter) {
		if (limitationTypeParamter != null) {
			this.limitationTypeProvided = true;
			this.limitationType = limitationTypeParamter.trim();
		}
	}

	/**
	 * Getter for limitationValue.
	 * @return String limitationValue.
	 */
	public String getLimitationValue() {
		return limitationValue;
	}

	/**
	 * Setter for limitationValue.
	 * <p/>
	 * Used by Hibernate.
	 * @param limitationValueParameter String limitationValue
	 */
	void setLimitationValue(final String limitationValueParameter) {
		if (limitationValueParameter != null) {
			this.limitationValueProvided = true;
			this.limitationValue = limitationValueParameter.trim();
		}
	}

	/**
	 * Getter for updateType.
	 * @return UpdateType updateType.
	 */
	public UpdateType getUpdateType() {
		return updateType;
	}


	/**
	 * Setter for updateType.
	 * <p/>
	 * Used by Hibernate.
	 * @param updateTypeParameter UpdateType updateType.
	 */
	void setUpdateType(final UpdateType updateTypeParameter) {
		this.updateType = updateTypeParameter;
		this.updateTypeProvided = true;
	}

	/**
	 * Getter for the expiredDate.
	 * @return Date expiredDate.
	 */
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	/**
	 * Setter for expiredDate.
	 * <p/>
	 * Used by Hibernate.
	 * @param expiredDateParameter Date expiredDate.
	 */
	void setExpiredDate(final Date expiredDateParameter) {
		this.expiredDate = expiredDateParameter;
	}

	/**
	 * Determines if this InfoAreaGrantedAuthority is expired.
	 * @return boolean true if the expiredDate has passed, false otherwise.
	 */
	public boolean isExpired() {
		return this.expiredDate != null && this.expiredDate.before(new Date());
	}

	/**
	 * Determines if the InfoAreaGrantedAuthority is valid.
	 * @return boolean if it is valid, false otherwise.
	 */
	private boolean isValid() {
		return isValidString(this.informationalArea);
	}

	@Override
	public String getAuthority() {
		return toString();
	}

	@Override
	public boolean hasAuthorization(final ComplexGrantedAuthority requiredGrantedAuthority) {
		if (requiredGrantedAuthority == null || !InfoAreaGrantedAuthority.class.isAssignableFrom(requiredGrantedAuthority.getClass())) {
			return false;
		}

		final InfoAreaGrantedAuthority requiredAuthority = (InfoAreaGrantedAuthority) requiredGrantedAuthority;

		if (requiredAuthority.isExpired()) {
			return false;
		}

		if (!this.informationalArea.equalsIgnoreCase(requiredAuthority.informationalArea)) {
			return false;
		}

		if (this.updateType != null && !this.updateType.isAllowed(requiredAuthority.updateType)) {
			return false;
		}

		if (this.limitationType != null && !limitationType.equalsIgnoreCase(requiredAuthority.limitationType)) {
				return false;
		}

		return !(this.limitationValue != null && !this.limitationValue.equalsIgnoreCase(requiredAuthority.limitationValue));
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}
		final InfoAreaGrantedAuthority other = (InfoAreaGrantedAuthority) obj;
		if (!this.informationalArea.equals(other.informationalArea)) {
			return false;
		}
		if (!areEqual(this.updateType, other.updateType)) {
			return false;
		}
		if (!areEqual(this.limitationType, other.limitationType)) {
			return false;
		}
		if (!areEqual(this.limitationValue, other.limitationValue)) {
			return false;
		}
		if (!areEqual(this.expiredDate, other.expiredDate)) {
			return false;
		}
		return true;
	}

	/**
	 * Determines if the two objects are equal.
	 * @param object1 Object first parameter to be compared.
	 * @param object2 Object second parameter to be compared.
	 * @return boolean true if they are equal, false otherwise.
	 */
	private boolean areEqual(final Object object1, final Object object2) {
		return object1 == object2 || (object1 != null && object1.equals(object2));
	}

	@Override
	public int hashCode() {
		int hash = HASH_CODE_SEED;
		hash = HASH_CODE_MULTIPLIER * hash + this.informationalArea.hashCode();
		if (this.updateType != null) {
			hash = HASH_CODE_MULTIPLIER * hash + this.updateType.hashCode();
		}
		if (this.limitationType != null) {
			hash = HASH_CODE_MULTIPLIER * hash + this.limitationType.hashCode();
		}
		if (this.limitationValue != null) {
			hash = HASH_CODE_MULTIPLIER * hash + this.limitationValue.hashCode();
		}
		if (this.expiredDate != null) {
			hash = HASH_CODE_MULTIPLIER * hash + this.expiredDate.hashCode();
		}
		return hash;
	}

	@Override
	public String toString() {
		if (toString == null) {
			StringBuilder sb = new StringBuilder(INFO_AREA_STRING_PREFIX_V3).append(informationalArea);
			if (updateTypeProvided || (limitationTypeProvided || limitationValueProvided)) {
				sb.append(":");
				addWildCardValue(updateType, sb);
			}
			if (limitationTypeProvided || limitationValueProvided) {
				sb.append(":");
				addWildCardValue(limitationType, sb);
			}
			if (limitationValueProvided) {
				sb.append(":");
				addWildCardValue(limitationValue, sb);
			}
			toString = sb.toString();
		}
		return toString;
	}

	/**
	 * Adds the toString value of the object to the stringBuilder or the #WILD_CARD_TOKEN if it is null.
	 * @param value Object to append to the stringBuilder.
	 * @param stringBuilder StringBuilder to add either the value to or the #WILD_CARD_TOKEN.
	 */
	private void addWildCardValue(final Object value, final StringBuilder stringBuilder) {
		if (value == null) {
			stringBuilder.append(WILD_CARD_TOKEN);
		} else {
			stringBuilder.append(value.toString());
		}
	}
}
