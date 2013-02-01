/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.byu.swing.components;

import java.util.concurrent.ExecutorService;

/**
 *
 * @author wct5
 */
public interface PagerContainer {
	/**
	 *
	 * @return
	 */
	public ExecutorService getExecutorService();
	/**
	 *
	 */
	public void lockContainerInterface();
	/**
	 *
	 */
	public void unlockContainerInterface();
}
