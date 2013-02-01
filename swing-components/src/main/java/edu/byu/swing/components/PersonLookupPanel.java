/**
 * Name: PersonLookupPanel.java
 * Date Created: Mar 10, 2009
 */
package edu.byu.swing.components;

import edu.byu.framework.swing.exceptions.DebugAppender;
import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.util.BYUTask;
import edu.byu.framework.swing.util.JOptionPaneWrapper;
import edu.byu.framework.swing.util.Retry;
import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.swing.dialogs.PersonLookup;
import edu.byu.swing.icons.Actions;
import edu.byu.swing.icons.IconHelper;
import edu.byu.swing.icons.IconSizes;
import edu.byu.swing.utilities.ComponentUtil;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 * A panel for looking up people via PersonClient/PersonLookup.  Fires an ActionEvent
 * when a new person is selected.
 * @author tylers2
 * @version 1.0.2
 * @since 1.0.0
 * @see PersonLookup
 * @see PersonClient
 */
public class PersonLookupPanel extends JPanel {

	private static final long serialVersionUID = 9216519213240493265L;
	private String personId;
	private PersonLookup personLookup;
	private PersonClient personClient;
	private final JTextField txtNameOrID = new JTextField();
	private final JLabel lblInfo = new JLabel();
	private final JButton btnFind = new JButton(IconHelper.getIcon(Actions.SYSTEM_SEARCH, IconSizes.MEDIUM, "Search"));
	private Frame parentFrame;
	private Logger LOG = Logger.getLogger(PersonLookupPanel.class);
	private ActionListener actionListener;

	/**
	 * Creates a new PersonLookupPanel.
	 * @param parentFrame parent frame
	 * @param personLookup personLookup to use
	 * @param personClient personClient to use
	 */
	public PersonLookupPanel(Frame parentFrame, PersonLookup personLookup, PersonClient personClient) {
		this();
		this.parentFrame = parentFrame;
		this.personLookup = personLookup;
		this.personClient = personClient;
		txtNameOrID.requestFocusInWindow();
	}

	/**
	 * Creates a new PersonLookupPanel
	 */
	public PersonLookupPanel() {
		super();

//        SpringLayout layout = new SpringLayout();
//        setLayout(layout);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		txtNameOrID.setMaximumSize(new Dimension(100, 20));
		txtNameOrID.setColumns(10);
		txtNameOrID.setName("personNameField");

		JLabel lblNameId = new JLabel("Name/ID:");
		lblNameId.setName("personNameIDLabel");
		btnFind.setName("personFindButton");
		btnFind.setMaximumSize(new Dimension(25, 25));
		btnFind.setMinimumSize(new Dimension(20, 20));
		btnFind.setPreferredSize(new Dimension(25, 25));

		lblInfo.setName("personNameLabel");
//        lblInfo.setPreferredSize(new Dimension(200, 20));

		add(lblNameId);
		addSpacing(5);
		add(txtNameOrID);
		add(btnFind);
		addSpacing(5);
		add(lblInfo);
		add(Box.createHorizontalGlue());

		//put label at (5,5)
//        layout.putConstraint(SpringLayout.WEST, lblNameId, 5, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, lblNameId, 8, SpringLayout.NORTH, this);
//
//        layout.putConstraint(SpringLayout.EAST, txtNameOrID, 150, SpringLayout.WEST, lblNameId);
//
//        //everything is positioned relative to the label
//        putConstraint(layout, lblNameId, txtNameOrID);
//        putConstraint(layout, txtNameOrID, btnFind);
//        putConstraint(layout, btnFind, lblInfo);
//
//        //setup the panel's springs
//        layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, lblInfo);
//        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, btnFind);


		//make btnFind the default button when txtNameOrId gets the focus
		txtNameOrID.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				txtNameOrID.setSelectionStart(0);
				txtNameOrID.setSelectionEnd(txtNameOrID.getText().length());
				txtNameOrID.selectAll();
			}

			public void focusLost(FocusEvent e) {
			}
		});

		ActionListener findActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				findId();
			}
		};

		txtNameOrID.addActionListener(findActionListener);

		btnFind.addActionListener(findActionListener);

		ExceptionInternalHandler.addAppender(new DebugDumper());
	}

	private class DebugDumper implements DebugAppender {

		@Override
		public CharSequence getDebugData(Throwable t) {
			if (!PersonLookupPanel.this.isVisible()) {
				return null;
			}
			StringBuilder sb = new StringBuilder("Debug info for PersonLookupPanel\n");
			sb.append(String.format("Component Name: &s\n", getName()));
			sb.append(String.format("Path to component %s\n", ComponentUtil.getNamedPathToComponent(PersonLookupPanel.this)));
			sb.append(String.format("Current personId: %s\n", personId));
			sb.append(String.format("Text Field Contents: %s\n", txtNameOrID.getText()));

			return sb;
		}
	}

	/**
	 * Adds an action listener to the panel.
	 * @param l listener to add
	 */
	public void addActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.add(actionListener, l);
	}

	/**
	 * Removes an action listener
	 * @param l listener to remove
	 */
	public void removeActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.remove(actionListener, l);
	}

	/**
	 * Gets the parent frame of this panel
	 * @return the parent frame of this panel
	 */
	public Frame getParentFrame() {
		return parentFrame;
	}

	/**
	 * Sets the parent frame of this panel
	 * Note: This method may not only be called if the parentFrame has not been
	 * set.
	 * @param parent the parent frame of this panel
	 */
	public void setParentFrame(Frame parent) {
		if (this.parentFrame != null) {
			throw new IllegalArgumentException("parentFrame has already been set");
		}
		this.parentFrame = parent;
	}

	@Override
	public void setEnabled(boolean enabled) {
		txtNameOrID.setEnabled(enabled);
		btnFind.setEnabled(enabled);
	}

	private void fireActionPerformed(final ActionEvent e) {
		e.setSource(this);
		if (actionListener != null) {
			Runnable r = new Runnable() {

				@Override
				public void run() {
					actionListener.actionPerformed(e);
				}
			};
			if (SwingUtilities.isEventDispatchThread()) {
				r.run();
			} else {
				SwingUtilities.invokeLater(r);
			}
		}
	}

	private void putConstraint(SpringLayout layout, Component leftComponent, Component rightComponent) {
		layout.putConstraint(SpringLayout.WEST, rightComponent, 5, SpringLayout.EAST, leftComponent);
		layout.putConstraint(SpringLayout.NORTH, rightComponent, 5, SpringLayout.NORTH, this);
	}

	/**
	 * Sets the personClient
	 * @param personClient
	 */
	public void setPersonClient(PersonClient personClient) {
		this.personClient = personClient;
	}

	/**
	 * Sets the personLookup
	 * @param personLookup
	 */
	public void setPersonLookup(PersonLookup personLookup) {
		this.personLookup = personLookup;
	}
	private boolean loadingExternal;

	/**
	 * Sets the personId for the panel.
	 * @param personId
	 */
	public void setPersonId(final String personId) {
		if (personId == null) {
			doPersonIdChange(null);
			return;
		}
		loadingExternal = true;
		new BYUTask() {

			private PersonType personType;

			@Override
			@Retry
			protected void doInBackground() {
				personType = personClient.getPersonByPersonId(personId,
						GeneralRequestFilter.IDENTIFIERS, GeneralRequestFilter.NAME_INFORMATION);
			}

			@Override
			protected void tearDown() {
				updatePersonId(personType);
			}
		}.run();
	}

	private void doPersonIdChange(String personId) {
		String oldPersonId = this.personId;
		this.personId = personId;
		firePropertyChange("personId", oldPersonId, personId);
		fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "lookup"));
	}

	public void clear() {
		txtNameOrID.setText("");
		lblInfo.setText("");
		doPersonIdChange(null);
	}

	/**
	 * Returns the currently-selected personId
	 * @return the currently-selected personId
	 */
	public String getPersonId() {
		return personId;
	}

	//Check to see if valid ID
	private void findId() {
		new BYUTask() {

			private String id;
			private PersonType personType;

			@Override
			protected void setup() {
				super.setup();
				id = txtNameOrID.getText().trim();
				if (id.charAt(0) >= '0' && id.charAt(0) <= '9') {
					id = id.replace("-", "");
				}
			}

			@Retry
			@Override
			protected void doInBackground() {
				personType = personLookup.lookupPerson(PersonLookupPanel.this, id,
						GeneralRequestFilter.IDENTIFIERS, GeneralRequestFilter.NAME_INFORMATION);	
			}

			@Override
			protected void tearDown() {
				super.tearDown();
				if (personType == null) {
					JOptionPaneWrapper.showMessageDialog("Person not found with net id: " + id, "Unable to find person", JOptionPane.ERROR_MESSAGE);
					return;
				}
				updatePersonId(personType);
			}
		}.run();
	}

	private void updatePersonId(PersonType personType) {
		if (personType == null) {
			doPersonIdChange(null);
			return;
		}
		if (personType.getPersonDemographics() != null && personType.getPersonDemographics().isRestricted()) {
			lblInfo.setForeground(Color.red);
		} else {
			lblInfo.setForeground(Color.black);
		}
		String foundPersonId = personType.getPersonIdentifier().getPersonId();

		String name = personType.getPersonName().getSortName();
		String byuId = personType.getPersonIdentifier().getByuId();

		StringBuffer sb = new StringBuffer();
		sb.append("Found: ");
		sb.append(name);
		sb.append(" (");
		sb.append(byuId);
		sb.append(")");
		if (personType.getPersonDemographics() != null && personType.getPersonDemographics().isRestricted()) {
			sb.append(" RESTRICTED");
		}

		lblInfo.setText(sb.toString());

		if (loadingExternal) {
			txtNameOrID.setText(personType.getPersonIdentifier().getByuId());
			loadingExternal = false;
		}

		doPersonIdChange(foundPersonId);
	}

	@Override
	public boolean requestFocusInWindow() {
		return txtNameOrID.requestFocusInWindow();
	}

	private void addSpacing(int i) {
		add(Box.createHorizontalStrut(i));
	}
}
