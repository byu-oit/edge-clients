package edu.byu.swing.components.table;

import edu.byu.pro.wsci.InformationRequestFilter;
import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.AddressRequestFilter;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.pro.wsci.filters.PhoneRequestFilter;
import edu.byu.ws.namespaces.pro.person_type.v2.AddressType;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import edu.byu.ws.namespaces.pro.person_type.v2.PhoneType;
import edu.byu.ws.namespaces.pro.typelibrary.v1.AddressTypeType;
import edu.byu.ws.namespaces.pro.typelibrary.v1.PhoneTypeType;
import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;
import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

/**
 * Special cell renderer for displaying data about a person, based off of a personId
 * in a table.
 * @author jmooreoa
 * @since 1.0.0
 * @version 1.1.0
 */
public class PersonCellRenderer extends SubstanceDefaultTableCellRenderer {

    private static final ConcurrentMap<String, PersonType> PERSON_CACHE = new ConcurrentHashMap<String, PersonType>();
    private static final Logger LOG = Logger.getLogger(PersonCellRenderer.class);
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private static final InformationRequestFilter[] FILTERS;
    private static final Set<String> BLACKLIST = Collections.synchronizedSet(new HashSet<String>());
    private static final Set<String> PROCESSING = Collections.synchronizedSet(new HashSet<String>());
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);
    private static final Set<JTable> TABLES = Collections.synchronizedSet(new HashSet<JTable>());
    
    private final PersonClient CLIENT;

    private DisplayType type;

    static {
        List<InformationRequestFilter> list = new ArrayList<InformationRequestFilter>();
        list.add(GeneralRequestFilter.DEMOGRAPHICS);
        list.add(GeneralRequestFilter.EMAIL_ADDRESS);
        list.add(GeneralRequestFilter.IDENTIFIERS);
        list.add(GeneralRequestFilter.NAME_INFORMATION);

        list.addAll(AddressRequestFilter.ALL);
        list.addAll(PhoneRequestFilter.ALL);
        FILTERS = list.toArray(new InformationRequestFilter[]{});
    }

    /**
     * Creates a new PersonCellRenderer using the specified client.  By default,
     * will display the person's sort name
     * @param client PersonClient to use
     */
    public PersonCellRenderer(PersonClient client) {
        this(client, DisplayType.SORT_NAME);
    }

    /**
     * Creates a new PersonCellRenderer using the specified client and displaying
     * the requested data type
     * @param client PersonClient to use
     * @param type type of data to display
     */
    public PersonCellRenderer(PersonClient client, DisplayType type) {
        this.CLIENT = client;
        this.type = type;
    }

    /**
     * Gets current display data type
     * @return current data type
     */
    public DisplayType getType() {
        return type;
    }

    /**
     * Sets display data type
     * @param type new data type
     */
    public void setType(DisplayType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot have a null display type!");
        }
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (CLIENT == null) {
            throw new IllegalStateException("personClient is null");
        }
        storeTable(table);
        return super.getTableCellRendererComponent(table, getDisplayValue(value), isSelected, hasFocus, row, column);
    }

    private String getDisplayValue(Object o) {
        if (o == null || !(o instanceof String)) {
            LOG.debug("received value is null or not a string: " + o);
            return null;
        }
        String personId = (String) o;
        return findValueForPersonId(personId);
    }

    private static final Pattern VALID_ID = Pattern.compile("[\\d]{9}");

    private String findValueForPersonId(String personId) {
        LOG.debug("Finding information for personId: " + personId);
        if (BLACKLIST.contains(personId)) {
            LOG.debug("personId " + personId + " has been blacklisted");
            return personId;
        }
        if (!VALID_ID.matcher(personId).matches()) {
            LOG.debug("personId " + personId + " is not a valid personId");
            blacklist(personId);
            return personId;
        }
        LOG.debug("Checking cache for personId: " + personId);
        if (!PERSON_CACHE.containsKey(personId)) {
            LOG.debug("personId not in cache");
            fetchPersonData(personId);
            return "Loading...";
        }
        PersonType person = PERSON_CACHE.get(personId);
        if (person == null) {
            blacklist(personId);
            return personId;
        }
        return type.valueOf(person);
    }

    private void fetchPersonData(String personId) {
        if (!PROCESSING.add(personId)) {
            return;
        }
        EXECUTOR.execute(new PersonFetcher(personId));
    }

    private static void blacklist(String personId) {
        LOG.debug("Blacklisting personId " + personId);
        BLACKLIST.add(personId);
    }

    private static void storeTable(JTable table) {
        TABLES.add(table);
    }

    private static void processed(String personId) {
        PROCESSING.remove(personId);
    }

    private static synchronized void notifyTables() {
        for (JTable each : TABLES) {
            each.repaint();
        }
    }

    private static void cachePerson(String personId, PersonType person) {
        if (person == null) {
            LOG.debug("Could not find information for personId: " + personId);
            blacklist(personId);
        } else {
            LOG.debug("Cacheing information for personId: " + personId);
            PERSON_CACHE.putIfAbsent(personId, person);

        }
    }

    private PersonType findPersonType(String personId) {
        return CLIENT.getPersonByPersonId(personId, FILTERS);
    }

    private class PersonFetcher implements Runnable {

        private String personId;

        public PersonFetcher(String personId) {
            this.personId = personId;
        }

        @Override
        public void run() {
            LOG.debug("Finding information for personId: " + personId);
            PersonType person = findPersonType(personId);
            cachePerson(personId, person);
            processed(personId);
            notifyTables();
        }
    }

    public enum DisplayType {

        /** Emergency Contact Address */
        ADDRESS_EMG ("Emergency Address") {

            String valueOf(PersonType person) {
                return findAddressByType(AddressTypeType.EMG, person);
            }
        },
        /** Mailing Address */
        ADDRESS_MAL ("Mailing Address") {

            String valueOf(PersonType person) {
                return findAddressByType(AddressTypeType.MAL, person);
            }
        },
        /** Permanent Address */
        ADDRESS_PRM ("Permanent Address") {

            String valueOf(PersonType person) {
                return findAddressByType(AddressTypeType.PRM, person);
            }
        },
        /** Residential Address */
        ADDRESS_RES ("Residential Address") {

            String valueOf(PersonType person) {
                return findAddressByType(AddressTypeType.RES, person);
            }
        },
        /** Work Address */
        ADDRESS_WRK ("Work Address") {

            String valueOf(PersonType person) {
                return findAddressByType(AddressTypeType.WRK, person);
            }
        },
        /** BYU ID */
        BYU_ID ("BYU ID") {

            String valueOf(PersonType person) {
                return person.getPersonIdentifier().getByuId();
            }
        },
        /** Email Address */
        EMAIL ("E-Mail") {

            String valueOf(PersonType person) {
                if (person.getEmailAddress() == null)
                    return null;
                return person.getEmailAddress().getEmailAddress();
            }
        },
        /** Date of Birth */
        DATE_OF_BIRTH ("Date of Birth") {
            String valueOf(PersonType person) {
                if (person.getPersonDemographics().getDateOfBirth() == null) {
                    return null;
                }
                return DATE_FORMAT.format(
                        person.getPersonDemographics().getDateOfBirth().toGregorianCalendar().getTime());
            }
        },
        /**
         * Emplyment Status
         * @deprecated use EMPLOYMENT_STATUS instead
         */
        @Deprecated
        EMPLOYMENT ("Employment") {
            String valueOf(PersonType person) {
                return EMPLOYMENT_STATUS.valueOf(person);
            }
        },
        EMPLOYMENT_STATUS("Employment Status") {
            String valueOf(PersonType person) {
                return person.getPersonDemographics().getEmploymentStatus();
            }
        },
        GENDER ("Gender") {
            String valueOf(PersonType person) {
                return person.getPersonDemographics().getGender().toString();
            }
        }
        ,
        /** Marital Status Code */
        MARITAL_STATUS ("Marital Status") {

            String valueOf(PersonType person) {
                return person.getPersonDemographics().getMaritalStatus().value();
            }
        },
        /** Religion Code */
        RELIGION ("Religion") {

            String valueOf(PersonType person) {
                return person.getPersonDemographics().getReligionCode();
            }
        },
        /** Social Security Number */
        SSN ("SSN") {

            String valueOf(PersonType person) {
                return person.getPersonIdentifier().getSSN();
            }
        },
        /** Emergency Phone */
        PHONE_EMG ("Emergency Phone") {

            String valueOf(PersonType person) {
                return findPhoneByType(PhoneTypeType.EMG, person);
            }
        },
        /** Mailing Address Phone */
        PHONE_MAL ("Mailing Address Phone") {

            String valueOf(PersonType person) {
                return findPhoneByType(PhoneTypeType.MAL, person);
            }
        },
        /** Permanent Address Phone */
        PHONE_PRM ("Permanent Phone") {

            String valueOf(PersonType person) {
                return findPhoneByType(PhoneTypeType.PRM, person);
            }
        },
        /** Residental Address Phone */
        PHONE_RES ("Residential Phone") {

            String valueOf(PersonType person) {
                return findPhoneByType(PhoneTypeType.RES, person);
            }
        },
        /** Work Phone */
        PHONE_WRK ("Work Phone") {

            String valueOf(PersonType person) {
                return findPhoneByType(PhoneTypeType.WRK, person);
            }
        },
        /** BYU netId */
        NET_ID ("NetID"){

            String valueOf(PersonType person) {
                return person.getPersonIdentifier().getNetId();
            }
        },
        /** Sort Name */
        SORT_NAME ("Name") {

            String valueOf(PersonType person) {
                return person.getPersonName().getSortName();
            }
        },
        /** Preferred First Name */
        PREFERRED_FIRST_NAME ("Preferred Name") {

            String valueOf(PersonType person) {
                return person.getPersonName().getPreferredFirstName();
            }
        },
        /** Prefix */
        NAME_PREFIX ("Prefix") {

            String valueOf(PersonType person) {
                return person.getPersonName().getPrefix();
            }
        },
        /** Suffix */
        NAME_SUFFIX ("Suffix") {

            String valueOf(PersonType person) {
                return person.getPersonName().getSuffix();
            }
        },
        /** Rest of name */
        REST_OF_NAME("First Name") {

            String valueOf(PersonType person) {
                return person.getPersonName().getRestOfName();
            }
        },
        /** Surname */
        SURNAME("Surname") {

            String valueOf(PersonType person) {
                return person.getPersonName().getSurname();
            }
        },
        STUDENT_STATUS ("Student Status") {
            String valueOf(PersonType person) {
                return person.getPersonDemographics().getStudentStatus();
            }
        };

        private final String displayName;

        private DisplayType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        abstract String valueOf(PersonType person);
    }

    private static String findPhoneByType(PhoneTypeType type, PersonType person) {
        List<PhoneType> list = person.getPhoneSet().getPhone();
        PhoneType found = null;
        for (PhoneType each : list) {
            if (each.getType().equals(type)) {
                found = each;
                break;
            }
        }
        if (found == null) {
            return null;
        }
        return found.getPhoneNumber();
    }

    private static String findAddressByType(AddressTypeType type, PersonType person) {
        List<AddressType> list = person.getAddressSet().getAddress();
        AddressType found = null;
        for (AddressType each : list) {
            if (each.getType().equals(type)) {
                found = each;
                break;
            }
        }
        if (found == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(found.getAddressLine1() + (found.getAddressLine1() != null ? " " : ""));
        builder.append(found.getAddressLine2() + (found.getAddressLine2() != null ? " " : ""));
        builder.append(found.getAddressLine3() + (found.getAddressLine3() != null ? " " : ""));
        builder.append(found.getAddressLine4() + (found.getAddressLine4() != null ? " " : ""));
        /*builder.append(found.getCity() + (found.getStateCode() != null ? ", " : ""));
        builder.append(found.getStateCode() + (found.getPostalCode() != null ? " " : ""));
        builder.append(found.getPostalCode() + (found.getCountryCode() != null ? " " : ""));*/
        builder.append(found.getCountryCode());

        return builder.toString();
    }
}
