/*
 * Filename: CasAuthorizedClientFinder
 * Created: Nov 19, 2008
 */
package edu.byu.framework.swing.cas;

import edu.byu.cas.CASInvalidCredentialsException;
import edu.byu.cas.CASRuntimeException;
import edu.byu.cas.CASStage;
import edu.byu.cas.CASUnauthorizedException;
import edu.byu.framework.swing.exceptions.ExceptionHandler;
import edu.byu.framework.swing.exceptions.ExceptionListener;
import edu.byu.framework.swing.UserPrefs;
import edu.byu.framework.wsci.*;
import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.ws.namespaces.pro.person_type.v2.GroupType;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.byu.framework.swing.util.DialogUtil.getSelectedWindow;

/**
 *
 * @author tylers2
 */
@Repository("casAuthorizedClientFinder")
public class CasAuthorizedClientFinderImpl implements InitializingBean, CasAuthorizedClientLoginManager, AuthenticationListener, WebSessionAuthenticationListener {

    private final static Logger LOG = Logger.getLogger(CasAuthorizedClientFinderImpl.class);
    private final static String SAVE_PASSWORD_PROPERTY = "cas.login.savePassword";
    @Autowired(required = false)
    private List<CASAuthorizedClient> clients = new ArrayList<CASAuthorizedClient>();
    @Autowired(required = false)
    private List<WebSessionAuthorizedClient> webSessionClients = new ArrayList<WebSessionAuthorizedClient>();
    @Autowired
    private ExceptionHandler exceptionHandler;
    @Autowired
    private PersonClient personClient;
    private final static List<String> groups = new ArrayList<String>();
    private static String currentNetId;
    private static String currentPersonId;
    @Autowired
    private ByuPasswordStore passwordStore;
    private final LoginService loginService;
    private final static int MAX_TRIES = 3;

    private String netId;
    private char[] password;

    public CasAuthorizedClientFinderImpl() {
        LOG.debug("creating login service");
        loginService = new LoginService() {

            @Override
            public boolean authenticate(String username, char[] password, String server) throws Exception {
                try {
                    CasAuthorizedClientFinderImpl.this.netId = username;
                    CasAuthorizedClientFinderImpl.this.password = password;
                    login(username, new String(password));
                } catch (CASUnauthorizedException ue) {
                    return false;
                }
                return true;
            }
        };
        LOG.debug("CasAuthorizedClientFinderImpl created");
    }

    public CasAuthorizedStatus login() {
        LOG.debug("doing login");
        return login(true);
    }

    public CasAuthorizedStatus login(boolean useSavedPassword) {
        LOG.debug("doing login with saved password: " + useSavedPassword);
        if (ServiceAuthenticationRequest.getTicketGrantingTicket() != null) {
            //login(username, password);
            LOG.debug("setting ticket granting ticket in new thread");
            return CasAuthorizedStatus.LOGGED_IN;
        }

        if (netId == null && useSavedPassword && passwordStore != null) {
            LOG.debug("logging in with saved password");
            netId = passwordStore.getSavedUsername();
            password = passwordStore.getSavedPassword();
        }

        if (netId != null && password != null) {
            try {
                boolean succeded = loginService.authenticate(netId, password, null);
                return succeded ? CasAuthorizedStatus.LOGGED_IN : CasAuthorizedStatus.CANCEL;
            } catch (Exception e) {
                return CasAuthorizedStatus.FAILED;
            }
        }

        Frame frame = getSelectedWindow();
        JXLoginPane.Status status = JXLoginPane.showLoginDialog(frame, loginService, passwordStore, null);
        switch (status) {
            case SUCCEEDED:
                return CasAuthorizedStatus.LOGGED_IN;
            case CANCELLED:
                return CasAuthorizedStatus.CANCEL;
        }

        throw new IllegalStateException("Status was: " + status);
    }

    public void logout() {
        if (passwordStore != null) {
            passwordStore.clearPassword(currentNetId);
        }
        currentNetId = null;
        currentPersonId = null;
        groups.clear();
        for (CASAuthorizedClient client : clients) {
            LOG.debug("Logout: " + client);
            client.logout();
        }
        for (WebSessionAuthorizedClient client : webSessionClients) {
            LOG.debug("Logout: " + client);
            client.logout();
        }
    }

    public void login(String username, String password) {
        for (int i = 0; i < MAX_TRIES; i++) {
            try {
                doLogin(username, password);
                return;
            } catch (Throwable t) {
                LOG.warn("Unable to login", t);
				if (t instanceof CASInvalidCredentialsException) {
					break;
				}
            }
        }
        throw new CASRuntimeException("Unable to login after " + MAX_TRIES + " attempts");
    }

    private final void doLogin(String username, String password) {
        currentNetId = username;
        for (CASAuthorizedClient client : clients) {
            LOG.debug("Authorizing: " + client);
            client.authorize(username, password);
        }
        for (WebSessionAuthorizedClient client : webSessionClients) {
            LOG.debug("Authorizing: " + client);
            client.authorize(username, password);
        }

        //populate group
        groups.clear();
        PersonType personType = personClient.getPersonByNetId(username, GeneralRequestFilter.GROUPS, GeneralRequestFilter.IDENTIFIERS);
        List<GroupType> groupTypeList = personType.getGroupSet().getGroup();
        for (GroupType groupType : groupTypeList) {
            String group = groupType.getGroupId();
            groups.add(group);
        }
        currentPersonId = personType.getPersonIdentifier().getPersonId();
    }

    public void needsLogin(CASAuthorizedClient client) {
        if (login() == CasAuthorizedStatus.CANCEL) {
            String message = "Unable to run method - needs to login";
            throw new CASRuntimeException(message);
        }
    }

    public void needsLogin(WebSessionAuthorizedClient client) {
        if (login() == CasAuthorizedStatus.CANCEL) {
            String message = "Unable to run method - needs to login";
            throw new CASRuntimeException(message);
        }
    }

    public void afterPropertiesSet() throws Exception {
        String savePassword = UserPrefs.getPreference(SAVE_PASSWORD_PROPERTY);
        LOG.debug("savePassword: " + savePassword);
        if (savePassword == null || !savePassword.equals("true")) {
            passwordStore = null;
        } else {
            passwordStore = new ByuPasswordStore();
        }

        for (CASAuthorizedClient client : clients) {
            client.setAuthorizationStage(CASStage.PROD);
            client.addAuthenticationListener(this);
            client.setAuthorizationMode(AuthorizationMode.GLOBAL);
        }

        for (WebSessionAuthorizedClient client : webSessionClients) {
            client.setAuthorizationStage(CASStage.PROD);
            client.addWebSessionAuthenticationListener(this);
            client.setAuthorizationMode(AuthorizationMode.GLOBAL);
        }

        LOG.info("Registered " + clients.size() + " cas clients");

        exceptionHandler.addListener(new ExceptionListener() {

            public void handleException(Throwable exception) {
                login();
            }
        });
    }

    public String getCurrentNetId() {
        return currentNetId;
    }

    public List<String> getGroups() {
        return groups;
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }
}

