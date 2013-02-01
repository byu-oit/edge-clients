/**
 * Name: PersonConverter.java
 * Date Created: Feb 20, 2009
 */
package edu.byu.swing.binding;

import edu.byu.pro.wsci.PersonClient;
import static edu.byu.pro.wsci.filters.GeneralRequestFilter.NAME_INFORMATION;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import edu.byu.swing.renderers.PersonRenderer;
import edu.byu.swing.renderers.PersonRendererBase;
import edu.byu.swing.renderers.DefaultPersonRendererBean;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author tylers2
 */
public class PersonConverter extends Converter<String, String> {

    private final PersonRenderer renderer;

    public PersonConverter(final DefaultPersonRendererBean rendererBean) {
        this.renderer = rendererBean.getRenderer();
    }

    public PersonConverter(final PersonRenderer renderer) {
        this.renderer = renderer;
    }

    public PersonConverter(final PersonClient client) {
        renderer = new PersonRendererBase() {
            @Override
            protected String doRetreiveValue(String personId) {
                if (personId == null) {
                    return null;
                }
                PersonType personType = client.getPersonByPersonId(personId, NAME_INFORMATION);
                if (personType == null) {
                    return personId;
                }
                return personType.getPersonName().getSortName();
            }
        };
    }

    @Override
    public String convertForward(String personId) {
		if (!personId.matches("\\d{9}")) {
			return personId;
		}
        return renderer.retreiveValue(personId);
    }

    @Override
    public String convertReverse(String arg0) {
        return null;
    }
}