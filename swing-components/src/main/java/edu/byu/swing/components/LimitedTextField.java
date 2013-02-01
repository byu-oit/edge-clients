package edu.byu.swing.components;

import edu.byu.swing.models.LimitedLengthDocument;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * A text field allowing a limited number of characters.
 * @author jmooreoa
 * @see edu.byu.swing.models.LimitedLengthDocument
 */
public class LimitedTextField extends JTextField {

    private LimitedLengthDocument doc = new LimitedLengthDocument(1);
    // Used for bug workaround
    private boolean finishedInit = false;

    /**
     * Creates a new LimitedTextField
     * @param limit character limit
     * @param text default text
     * @param columns columns to be displayed
     */
    public LimitedTextField(int limit, String text, int columns) {
        super(generateDocument(limit), text, columns);
        doc = (LimitedLengthDocument) super.getDocument();
        finishedInit = true;
    }

    /**
     * Creates a new LimitedTextField
     * @param limit character limit
     * @param columns columns to be displayed
     */
    public LimitedTextField(int limit, int columns) {
        super(generateDocument(limit), null, columns);
        doc = (LimitedLengthDocument) super.getDocument();
        finishedInit = true;
    }

    /**
     * Creates a new LimitedTextField
     * @param limit character limit
     * @param text default text
     */
    public LimitedTextField(int limit, String text) {
        super(generateDocument(limit), text, 0);
        doc = (LimitedLengthDocument) super.getDocument();
        finishedInit = true;
    }

    /**
     * Creates a new LimitedTextField
     * @param limit character limit
     */
    public LimitedTextField(int limit) {
        super(generateDocument(limit), null, 0);
        doc = (LimitedLengthDocument) super.getDocument();
        finishedInit = true;
    }

    /**
     * Creates a new LimitedTextField
     * @param doc document to be used
     * @param text default text
     * @param columns columns to be displayed
     */
    public LimitedTextField(LimitedLengthDocument doc, String text, int columns) {
        super(doc, text, columns);
    }

    private static LimitedLengthDocument generateDocument(int limit) {
         return new LimitedLengthDocument(limit);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setDocument(Document doc) {
        if (!(doc instanceof LimitedLengthDocument) && finishedInit) {
            throw new IllegalArgumentException("Document must be an edu.byu.swing.models.LimitedLengthDocument");
        }
        super.setDocument(doc);
        if (finishedInit) {
            this.doc = (LimitedLengthDocument) doc;
        }
    }

    /**
     *
     * @return current character limit
     */
    public int getLimit() {
        return doc.getLimit();
    }

    /**
     *
     * @param limit new character limit
     */
    public void setLimit(int limit) {
        doc.setLimit(limit);
    }
}
