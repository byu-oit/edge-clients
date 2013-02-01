package edu.byu.swing.components;

import edu.byu.framework.swing.exceptions.DebugAppender;
import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.util.BYUTask;
import edu.byu.framework.swing.util.JOptionPaneWrapper;
import edu.byu.framework.swing.util.Retry;
import edu.byu.pro.wsci.InformationRequestFilter;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author jmooreoa
 * @version 1.1.0
 * @since 1.1.0
 */
public class SmallPersonLookupPanel extends JPanel {

    private PersonLookup personLookup;
    private PersonClient personClient;

    private CardLayout cardLayout = new CardLayout();
    private JTextField txtPerson = new JTextField(10);
    private JPanel cardPanel = new JPanel(cardLayout);
    private JLabel lblName = new JLabel();
    private JButton btnLookupPerson = new JButton();

    private PersonType person;
    private volatile boolean showingName = false;
    private boolean autoLookup;
    private boolean doingLookup;

    private ActionListener listener;

    private static final InformationRequestFilter[] FILTERS = new InformationRequestFilter[] {GeneralRequestFilter.NAME_INFORMATION, GeneralRequestFilter.IDENTIFIERS, GeneralRequestFilter.DEMOGRAPHICS};

    private final String FIELD_KEY = "person",  NAME_KEY = "name";

    public void setPersonClient(PersonClient personClient) {
        this.personClient = personClient;
    }

    public void setPersonLookup(PersonLookup personLookup) {
        this.personLookup = personLookup;
    }

    public SmallPersonLookupPanel() {
        setLayout(new BorderLayout());

        txtPerson.setColumns(15);
        txtPerson.setName("SearchField");
        txtPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                doLookup();
            }
        });
        cardPanel.add(txtPerson, FIELD_KEY);
        cardPanel.add(lblName, NAME_KEY);

        add(cardPanel);
//        add(Box.createHorizontalGlue());

        btnLookupPerson.setIcon(IconHelper.getIcon(Actions.SYSTEM_SEARCH, IconSizes.MEDIUM));
        btnLookupPerson.setMaximumSize(new Dimension(25, 25));
        btnLookupPerson.setMinimumSize(new Dimension(20, 20));
        btnLookupPerson.setPreferredSize(new Dimension(25, 25));
        btnLookupPerson.setName("LookupButton");
        btnLookupPerson.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (doingLookup) {
                    return;
                }
                if (showingName) {
                    showLookup();
                } else {
                    doLookup();
                }
            }
        });

        add(btnLookupPerson, BorderLayout.EAST);

        txtPerson.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                if (!autoLookup || doingLookup) {
                    return;
                }
                doLookup();
            }
        });
        ExceptionInternalHandler.addAppender(new DebugDumper());
    }

    public SmallPersonLookupPanel(PersonLookup personLookup, PersonClient personClient) {
        this();
        this.personLookup = personLookup;
        this.personClient = personClient;
    }

    private class DebugDumper implements DebugAppender {

        @Override
        public CharSequence getDebugData(Throwable t) {
            if (!SmallPersonLookupPanel.this.isVisible()) {
                return null;
            }
            StringBuilder sb = new StringBuilder("Debug data for SmallPersonLookupPanel\n");
            sb.append(String.format("Component Name: %s\n", SmallPersonLookupPanel.this.getName()));
            sb.append(String.format("Named path to component %s\n",
                    ComponentUtil.getNamedPathToComponent(SmallPersonLookupPanel.this)));
            sb.append(String.format("Current personId: %s\n",
                    (person != null) ? person.getPersonIdentifier().getPersonId() : null));
            sb.append(String.format("Doing lookup: %b\n", doingLookup));
            sb.append(String.format("Do Auto Lookup: %b\n", autoLookup));
            sb.append(String.format("Showing Name: %b\n", showingName));
            sb.append(String.format("Field Contents: %s\n", txtPerson.getText()));
            
            return sb;
        }

    }

	@Override
	public boolean requestFocusInWindow() {
		return txtPerson.requestFocusInWindow();
	}

	@Override
	public boolean requestFocus(boolean temporary) {
		return txtPerson.requestFocus(temporary);
	}

	@Override
	public void requestFocus() {
		txtPerson.requestFocus();
	}

	@Override
	protected boolean requestFocusInWindow(boolean temporary) {
		txtPerson.requestFocusInWindow();
		return super.requestFocusInWindow(temporary);
	}

	/**
     * Adds an action listener to the panel.
     * @param l listener to add
     */
    public void addActionListener(ActionListener l) {
        listener = AWTEventMulticaster.add(listener, l);
    }

    /**
     * Removes an action listener
     * @param l listener to remove
     */
    public void removeActionListener(ActionListener l) {
        listener = AWTEventMulticaster.remove(listener, l);
    }

    /**
     * Gets the <code>autoLookup</code> property.
     *
     * @return the value of the <code>autoLookup</code> property
     * @see #setAutoLookup
     */
    public boolean getAutoLookup() {
        return autoLookup;
    }

    /**
     * If set to true, this component will perform a lookup on the current contents
     * of the text field whenever the component loses focus.
     *
     * {@code false} by default.
     * @param autoLookup
     */
    public void setAutoLookup(boolean autoLookup) {
        this.autoLookup = autoLookup;
    }
    
    private void showLookup() {
        Runnable r = new Runnable() {
            public void run() {
                if (person != null) {
                    txtPerson.setText(person.getPersonIdentifier().getNetId());
                } else {
                    txtPerson.setText("");
                }
                cardLayout.show(cardPanel, FIELD_KEY);
                showingName = false;
            }
        };
        SwingUtilities.invokeLater(r);
    }

    public PersonType getPerson() {
        return person;
    }

    public void setPerson(final PersonType person) {
        final PersonType old = this.person;
        this.person = person;
        if (person != null) {
            showName();
        } else {
            showLookup();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                firePropertyChange("person", old, person);
                final String oldId = (old != null) ? old.getPersonIdentifier().getPersonId() : null;
                final String newId = (person != null) ? person.getPersonIdentifier().getPersonId() : null;
				if ((oldId != null && !old.equals(newId)) || (newId != null && !newId.equals(oldId))) {
					firePropertyChange("personId", oldId, newId);
				}
            }
        });
    }

    public String getPersonId() {
        return (person != null) ? person.getPersonIdentifier().getPersonId() : null;
    }

    public void clear() {
        setPerson(null);
    }

    public void setPersonId(final String personId) {
        if (personId == null) {
            setPerson(null);
            return;
        }
        new BYUTask() {
            @Override
            @Retry
            protected void doInBackground() {
                PersonType result = personClient.getPersonByPersonId(personId, FILTERS);
                
                setPerson(result);
            }
        }.run();
    }

    private void showName() {
        Runnable r = new Runnable() {

            public void run() {
                if(person.getPersonDemographics() != null && person.getPersonDemographics().isRestricted()){
                    lblName.setForeground(Color.RED);
                    lblName.setText(person.getPersonName().getSortName() + "(RESTRICTED)");
                }else if(person.getPersonDemographics() != null){
                    lblName.setForeground(Color.BLACK);
                    lblName.setText(person.getPersonName().getSortName());
                }
                cardLayout.show(cardPanel, NAME_KEY);
                showingName = true;
            }
        };
        SwingUtilities.invokeLater(r);
    }

    public void doLookup() {
        if (doingLookup) {
            return;
        }
        doingLookup = true;
        Runnable r = new LookupTask();
        r.run();
    }

    private class LookupTask extends BYUTask {
        @Override
            protected void tearDown() {
                doingLookup = false;
            }

            @Retry
            @Override
            protected void doInBackground() throws InterruptedException {
                String id = txtPerson.getText().trim();
                PersonType person = personLookup.lookupPerson(SmallPersonLookupPanel.this, id, FILTERS);
                setPerson(person);
                if (person == null) {
                    JOptionPaneWrapper.showMessageDialog(btnLookupPerson, "No people found for: " + id);
                    return;
                }
                fireActionPerformed();
                showName();
                Thread.sleep(250);
            }
    }

    protected void fireActionPerformed() {
        final ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "personFound");
        if (listener != null) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    listener.actionPerformed(event);
                }
            };
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                SwingUtilities.invokeLater(r);
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.txtPerson.setEnabled(enabled);
        this.btnLookupPerson.setEnabled(enabled);
    }
	
	public String getText() {
		return txtPerson.getText();
	}
	
	public void setText(String text) {
		txtPerson.setText(text);
	}
}
