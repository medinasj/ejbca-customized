/*************************************************************************
 *                                                                       *
 *  EJBCA: The OpenSource Certificate Authority                          *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/

package org.ejbca.config;

import org.apache.log4j.Logger;

public class WebConfiguration {

	private static final Logger log = Logger.getLogger(WebConfiguration.class);

	/**
	 * The configured server host name
	 */
	public static String getHostName() {
		return ConfigurationHolder.getExpandedString("httpsserver.hostname", "localhost");
	}
	
	/**
	 * Port used by EJBCA public webcomponents. i.e that doesn't require client authentication
	 */
	public static int getPublicHttpPort() {
		int value = 8080;
		try {
			value = Integer.parseInt(ConfigurationHolder.getString("httpserver.pubhttp", ""+value));
		} catch( NumberFormatException e ) {
			log.warn("\"httpserver.pubhttp\" is not a decimal number. Using default value: " + value);
		}
		return value;
	}
	
	/**
	 * Port used by EJBCA private webcomponents. i.e that requires client authentication
	 */
	public static int getPrivateHttpsPort() {
		int value = 8443;
		try {
			value = Integer.parseInt(ConfigurationHolder.getString("httpserver.privhttps", ""+value));
		} catch( NumberFormatException e ) {
			log.warn("\"httpserver.privhttps\" is not a decimal number. Using default value: " + value);
		}
		return value;
	}
	
	/**
	 * Defines the available languages by language codes separated with a comma
	 */
	public static String getAvailableLanguages() {
		return ConfigurationHolder.getExpandedString("web.availablelanguages", "EN,DE,ES,FR,IT,PT,PT_BR,SE,ZH");
	}
	
	/**
	 * The language that should be used internally for logging, exceptions and approval notifications.
	 */
	public static String getInternalResourcesPreferredLanguage() {
		return ConfigurationHolder.getExpandedString("intresources.preferredlanguage", "EN");
	}
	
	/**
	 * The language used internally if a resource not found in the preferred language
	 */
	public static String getInternalResourcesSecondaryLanguage() {
		return ConfigurationHolder.getExpandedString("intresources.secondarylanguage", "SE");
	}
	
	/**
	 * Setting to indicate if the secret information stored on hard tokens (i.e initial PIN/PUK codes) should
	 * be displayed for the administrators. 
	 */
	public static boolean getHardTokenDiplaySensitiveInfo() {
		String value = ConfigurationHolder.getString("hardtoken.diplaysensitiveinfo", "true");
		return "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
	}

	/**
	 * Setting to configure the maximum number of rows to be returned when viewing logs in the admin-GUI.
	 */
	public static int getLogMaxQueryRowCount() {
		int value = 1000;
		try {
			value = Integer.parseInt(ConfigurationHolder.getString("log.maxqueryrowcount", ""+value));
		} catch( NumberFormatException e ) {
			log.warn("\"log.maxqueryrowcount\" is not a decimal number. Using default value: " + value);
		}
		return value;
	}
	
	/**
	 * Show links to the EJBCA documentation.
	 * @return "disabled", "internal" or and URL
	 */
	public static String getDocBaseUri() {
		return ConfigurationHolder.getExpandedString("web.docbaseuri", "internal");
	}
	
	/**
	 * Require administrator certificates to be available in database for revocation checks.
	 */
	public static boolean getRequireAdminCertificateInDatabase() {
		return "true".equalsIgnoreCase(ConfigurationHolder.getExpandedString("web.reqcertindb", "true"));
	}

	/**
	 * Default content encoding used to display JSP pages
	 */
	public static String getWebContentEncoding() {
	   	return ConfigurationHolder.getString ("web.contentencoding", "UTF-8");
	}
	
	/**
	 * 
	 */
	public static String getMailMimeType() {
	   	return "text/plain;charset=" + getWebContentEncoding();
	}

	/**
	 * The request browser certificate renewal web application is deployed
	 */
	public static boolean getRenewalEnabled() {
		return "true".equalsIgnoreCase(ConfigurationHolder.getExpandedString("web.renewalenabled", "false"));
	}

}
