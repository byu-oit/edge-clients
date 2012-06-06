package edu.byu.edge.client.auth;

/**
 * This exception is thrown when performing operations such as renew session or obtain nonce using an expired WsSession.
 * This exception is not thrown when attempting to logout using an expired session.
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/6/12
 */
public class ExpiredSessionException extends RuntimeException {

}
