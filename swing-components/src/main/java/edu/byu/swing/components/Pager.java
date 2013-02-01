/*
 * Pager.java
 *
 * Created on Aug 11, 2009, 10:20:52 AM
 */
package edu.byu.swing.components;

import edu.byu.swing.models.pager.PagerModel;
import edu.byu.framework.swing.util.BYUTask;
import edu.byu.framework.swing.util.Retry;
import edu.byu.swing.models.pager.JListPagerModel;
import static edu.byu.swing.icons.Actions.*;
import edu.byu.swing.icons.IconHelper;
import static edu.byu.swing.icons.IconSizes.*;
import edu.byu.swing.models.MutableListComboBoxModel;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @param <T>
 * @author tylers2
 */
public class Pager<T> extends JPanel {

	private final static Logger LOG = Logger.getLogger(Pager.class);
	private final static Icon NEXT_ICON = IconHelper.getIcon(GO_NEXT, MEDIUM);
	private final static Icon PREVIOUS_ICON = IconHelper.getIcon(GO_PREVIOUS, MEDIUM);
	private static final long serialVersionUID = -6174006851088960580L;
	private final PagerModel<T, ? extends Component> model;
	private int pageCount;
	private final MutableListComboBoxModel<Integer> pageModel = new MutableListComboBoxModel<Integer>();
	private PagerContainer parent;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		final List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < 24; i++) {
			data.add(i);
		}
		JListPagerModel<Integer> model = new JListPagerModel<Integer>() {

			@Override
			public List<Integer> getDataset(int start, int end) {
				return data.subList(start, end);
			}

			public int getTotal() {
				return data.size();
			}

			public int getPageSize() {
				return 12;
			}
		};
		Pager<Integer> pager = new Pager<Integer>(model);
		frame.getContentPane().add(pager);
		frame.setVisible(true);
	}

	/** Creates new form Pager
	 * @param model
	 */
	public Pager(PagerModel<T, ? extends Component> model) {
		this(model, null);
	}

	/** Creates new form Pager
	 * @param model
	 */
	public Pager(PagerModel<T, ? extends Component> model, PagerContainer parent) {
		initComponents();

		this.model = model;
		if (this.model.getComponent() == null) {
			throw new IllegalStateException("dataComponent cannot be null");
		}

		LOG.debug("setting viewPort");
		scrollPane.setViewportView(this.model.getComponent());

		this.parent = parent;

		refreshData();
	}

	public void refreshData() {
		BigDecimal total = new BigDecimal(model.getTotal());
		BigDecimal count = total.divide(new BigDecimal(model.getPageSize()), RoundingMode.CEILING);
		this.pageCount = count.intValue();

		doOnEventDispatchThread(new Runnable() {

			@Override
			public void run() {
				lblTotalPages.setText(" of " + Pager.this.pageCount);
			}
		});

		pageModel.clear();
		if (pageCount == 0) {
			enableAll(false);
			doOnEventDispatchThread(new Runnable() {
				@Override
				public void run() {
					pbStatus.setVisible(false);
				}
			});
			model.clear();
			return;
		}

		LOG.debug("pageCount: " + pageCount);
		List<Integer> list = new ArrayList<Integer>(pageCount);
		for (int i = 1; i <= pageCount; i++) {
			list.add(i);
		}
		pageModel.addAll(list);

		doOnEventDispatchThread(new Runnable() {
			@Override
			public void run() {
				cbPage.setSelectedItem(1);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return model.getSelectedItem();
	}

	public int getIndex() {
		Integer i = (Integer) cbPage.getSelectedItem();
		if (i == null) {
			return 0;
		}
		return i;
	}

	public void setIndex(Integer index) {
		LOG.debug("index: " + index);
		if (index == null || index > pageCount || index == 0) {
			return;
		}

		updateData();
	}

	private void updateData() {
		final int index = getIndex();

		if (index == 0) {
			LOG.debug("index == 0");
			return;
		}

		Runnable r = new BYUTask() {

			@Override
			protected void setup() {
				if (parent != null) {
					parent.lockContainerInterface();
				}
				enableAll(false);
				pbStatus.setVisible(true);
			}

			@Override
			protected void tearDown() {
				try {
					enableAll(true);
					btnNext.setEnabled(getIndex() != pageCount);
					btnPrevious.setEnabled(getIndex() != 1);
					pbStatus.setVisible(false);
				} finally {
					if (parent != null) {
						parent.unlockContainerInterface();
					}
				}
			}

			@Override
			@Retry
			protected void doInBackground() {
				int end = index * model.getPageSize();
				if (model.getTotal() < end) {
					end = model.getTotal();
				}
				int start = (index - 1) * model.getPageSize();
				LOG.info("Loading records " + start + " through " + end);

				assert start >= 0;
				assert end <= model.getTotal();

				model.loadDataset(start, end);
				LOG.info("Loaded records");
			}
		};
		doOnEventDispatchThread(r);
	}

	private void enableAll(final boolean enable) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				btnNext.setEnabled(enable);
				btnPrevious.setEnabled(enable);
				cbPage.setEnabled(enable);
				model.getComponent().setEnabled(enable);
			}
		};
		doOnEventDispatchThread(r);
	}

	private void doOnEventDispatchThread(Runnable r) {
		try {
			if (!SwingUtilities.isEventDispatchThread()) {
				if (parent != null && parent.getExecutorService() != null) {
					parent.getExecutorService().submit(r);
				} else {
					SwingUtilities.invokeAndWait(r);
				}
			} else {
				r.run();
			}
		} catch (Exception e) {
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lblStatus = new javax.swing.JLabel();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        pbStatus = new javax.swing.JProgressBar();
        cbPage = new javax.swing.JComboBox();
        lblTotalPages = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel2.setName("jPanel2"); // NOI18N

        lblStatus.setText("Page");
        lblStatus.setName("lblStatus"); // NOI18N

        btnPrevious.setIcon(PREVIOUS_ICON);
        btnPrevious.setText("Previous");
        btnPrevious.setName("btnPrevious"); // NOI18N
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setIcon(NEXT_ICON);
        btnNext.setText("Next");
        btnNext.setName("btnNext"); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        pbStatus.setIndeterminate(true);
        pbStatus.setName("pbStatus"); // NOI18N

        cbPage.setModel(pageModel);
        cbPage.setName("cbPage"); // NOI18N
        cbPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPageActionPerformed(evt);
            }
        });

        lblTotalPages.setText("1");
        lblTotalPages.setName("lblTotalPages"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalPages, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(pbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrevious)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStatus)
                        .addComponent(btnNext)
                        .addComponent(btnPrevious)
                        .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalPages))
                    .addComponent(pbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane.setName("scrollPane"); // NOI18N
        jPanel1.add(scrollPane);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
		cbPage.setSelectedItem(getIndex() + 1);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
		cbPage.setSelectedItem(getIndex() - 1);
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void cbPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPageActionPerformed
		updateData();
    }//GEN-LAST:event_cbPageActionPerformed

	public final PagerModel<T, ? extends Component> getModel() {
		return model;
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JComboBox cbPage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTotalPages;
    private javax.swing.JProgressBar pbStatus;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
