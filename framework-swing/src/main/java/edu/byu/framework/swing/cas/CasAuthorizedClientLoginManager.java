/*
 * Filename: CasAuthorizedClientFinder
 * Created: Nov 19, 2008
 */
package edu.byu.framework.swing.cas;

import java.util.List;

/**
 *
 * @author tylers2
 */
public interface CasAuthorizedClientLoginManager {

  public void login(String username, String password);

  /**
   * same as login(boolean useSavedPassword) with useSavedPassword=true
   * @return
   */
  public CasAuthorizedStatus login();

  /**
   * If useSavedPassword is true an attempt is made to login 
   * using the saved username/password.  If no username/password is found
   * (or they are an invalid combination), a dialog is shown requesting 
   * this information.
   *
   * @param useSavedPassword
   * @return
   */
  public CasAuthorizedStatus login(boolean useSavedPassword);

  public void logout();

  public String getCurrentNetId();

  public List<String> getGroups();

  public String getCurrentPersonId();
}
