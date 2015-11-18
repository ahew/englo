/*
*
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando software.
* Entando is a free software;
* You can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package org.entando.entando.plugins.jpoauth2.aps.system.utils;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class OAuth2Utils {
	
	/**
	 * Get rid of double // in URL
	 * @param url
	 * @return
	 */
	public static String normalizeUrl(StringBuilder url) {
		String resUrl = null;

		if (StringUtils.isNotBlank(url.toString())) {
			resUrl = url.toString().replace("//", "/");
			resUrl = resUrl.replace(":/", "://");
		}
		return resUrl;
	}
	
	/**
	 * Normalize and encode the URL with the desired encoding
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Throwable
	 */
	public static String normalizeUrl(StringBuilder url, String encoding) throws Throwable {
		String eurl = normalizeUrl(url);
		
		return URLEncoder.encode(eurl, encoding);
	}

}
