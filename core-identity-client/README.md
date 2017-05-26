# core-identity-client
This is a few clients for core identity information, such as name, IDs, and roles.

## Clients

### CoreIdentityClient
Looks up names, IDs, date of birth, gender, religion, and restricted (non-public).

### IdentityLookupClient
Retrieves other data, such as job title, department, home town, employee status, email, and addresses.

### MemberOfClient
Looks up membership of a person in a 'GRO' group (role).

---
Notes:

* All three clients use WSO2 oauth authentication. Specifically, using the authentication filters, etc found in [wso2-java-infrastructure](https://github.com/byu-oit/wso2-java-infrastructure)
* The caller (either resource owner or client subscriber) must have appropriate privileges granted in order for the clients to function, including testing. For the MemberOfClient, that means the RO or subscriber must be an administrator of the role(s) being checked, or a super user in GRO.


