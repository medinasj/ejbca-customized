package org.ejbca.ui.web.admin.publisher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.common.exception.ReferencesToItemExistException;
import org.ejbca.core.model.authorization.AccessRulesConstants;
import org.ejbca.core.model.ca.publisher.LdapPublisher;
import org.ejbca.core.model.ca.publisher.PublisherDoesntExistsException;
import org.ejbca.core.model.ca.publisher.PublisherExistsException;
import org.ejbca.core.model.util.EjbLocalHelper;
import org.ejbca.ui.web.admin.BaseManagedBean;
import org.ejbca.ui.web.admin.configuration.SortableSelectItem;

/**
 * Managed bean to back the list publisher xhtml page.
 * 
 * @version $Id$
 *
 */
@ManagedBean
@SessionScoped
public class ListPublishersManagedBean extends BaseManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ListPublishersManagedBean.class);
    
    private final EjbLocalHelper ejb = new EjbLocalHelper();
    private String selectedPublisherName;
    private String newPublisherName = StringUtils.EMPTY;
    
    public void initAccess() throws Exception {
        // To check access 
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            getEjbcaWebBean().initialize(request, AccessRulesConstants.REGULAR_VIEWPUBLISHER);
        }
    }
    
    public String getSelectedPublisherName() {
        return selectedPublisherName;
    }

    public void setSelectedPublisherName(final String selectedPublisherName) {
        this.selectedPublisherName = selectedPublisherName;
    }

    public String getNewPublisherName() {
        return newPublisherName;
    }

    public void setNewPublisherName(final String newPublisherName) {
        this.newPublisherName = newPublisherName;
    }
    
    public List<SortableSelectItem> getAvailablePublishers() {
        List<SortableSelectItem> availablePublishers = new ArrayList<>();
        getEjbcaWebBean().getAuthorizedPublisherNames().keySet().forEach(publisher -> availablePublishers.add(new SortableSelectItem(publisher, publisher)));
        return availablePublishers;
    }
    
    
    // Actions //
    
    public String editPublisher() {
        return null;
    }
    
    public String deletePublisher() throws AuthorizationDeniedException {
        if (StringUtils.isNotEmpty(selectedPublisherName)) {
            try {
                ejb.getPublisherSession().removePublisher(getAdmin(), selectedPublisherName);
            } catch (ReferencesToItemExistException e) {
                log.info("Error while deleting the publisher " + selectedPublisherName + e);
                addErrorMessage("COULDNTDELETEPUBLISHERDUETOEXISTINGREF");
            }
        } else {
            addErrorMessage("YOUHAVETOSELECTAPUBLISHER");
        }
        newPublisherName = StringUtils.EMPTY;
        return "listpublishers";
    }
    
    public String renamePublisher() throws AuthorizationDeniedException {
        if (StringUtils.isEmpty(selectedPublisherName)) {
            addErrorMessage("YOUHAVETOSELECTAPUBLISHER");
        } else if (StringUtils.isEmpty(StringUtils.trim(newPublisherName))) {
            addErrorMessage("YOUHAVETOENTERAPUBLISHER");
        } else {
            try {
                ejb.getPublisherSession().renamePublisher(getAdmin(), selectedPublisherName, newPublisherName);
            } catch (PublisherExistsException e) {
                log.info("Publisher " + newPublisherName + " already exists!", e);
                addErrorMessage("PUBLISHERALREADYEXISTS", newPublisherName);
            }
        }
        newPublisherName = StringUtils.EMPTY;
        return "listpublishers";
    }
    
    public String addPublisher() throws AuthorizationDeniedException {
        if (StringUtils.isEmpty(StringUtils.trim(newPublisherName))) {
            addErrorMessage("YOUHAVETOENTERAPUBLISHER");
        } else {
            try {
                ejb.getPublisherSession().addPublisher(getAdmin(), newPublisherName, new LdapPublisher());
            } catch (PublisherExistsException e) {
                log.info("Publisher " + newPublisherName + " already exists!", e);
                addErrorMessage("PUBLISHERALREADYEXISTS", newPublisherName);
            }
        }
        newPublisherName = StringUtils.EMPTY;
        return "listpublishers";
    }    
    
    public String clonePublisher() throws AuthorizationDeniedException {
        if (StringUtils.isEmpty(selectedPublisherName)) {
            addErrorMessage("YOUHAVETOSELECTAPUBLISHER");
        } else if (StringUtils.isEmpty(StringUtils.trim(newPublisherName))) {
            addErrorMessage("YOUHAVETOENTERAPUBLISHER");
        } else {            
                try {
                    ejb.getPublisherSession().clonePublisher(getAdmin(), selectedPublisherName, newPublisherName);
                } catch (PublisherDoesntExistsException e) {
                    log.info("Publisher " + selectedPublisherName + " does not exists!", e);
                    addErrorMessage("PUBLISHERDOESNOTEXISTS", selectedPublisherName);
                } catch (PublisherExistsException e) {
                    log.info("Publisher " + newPublisherName + " already exists!", e);
                    addErrorMessage("PUBLISHERALREADYEXISTS", newPublisherName);
                }
        }
        newPublisherName = StringUtils.EMPTY;
        return "listpublishers";
    }
    
    /** 
     * @return true if admin has access to /ca_functionality/edit_publisher/
     */
    public boolean getHasEditRights() {
        return ejb.getAuthorizationSession().isAuthorizedNoLogging(getAdmin(), AccessRulesConstants.REGULAR_EDITPUBLISHER);
    }

}