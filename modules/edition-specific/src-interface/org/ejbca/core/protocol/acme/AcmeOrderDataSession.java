/*************************************************************************
 *                                                                       *
 *  EJBCA Community: The OpenSource Certificate Authority                *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/
package org.ejbca.core.protocol.acme;

/**
 * @version $Id: AcmeOrderDataSession.java 25797 2017-05-04 15:52:00Z tarmor $
 */
public interface AcmeOrderDataSession {

    /**
     *  
     * @param orderId the ID of the order
     * @return the sought order, or null if none exists
     */
    AcmeOrder getAcmeOrder(final String orderId);
    
    /**
     * Create or update the AcmeOrder.
     *
     * @return the persisted version of the AcmeOrder.
     */
    String persist(final AcmeOrder acmeOrder);
    
}