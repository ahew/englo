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
package org.entando.entando.plugins.jpoauth2.aps.system.services.indexer;

public interface IIndexerManager {
	
	/**
	 * Return the name of the manager which handles the service given its ID
	 * @param id
	 * @return
	 */
	public String getManagerById(String id);

	/**
	 * Add a new service
	 * @param id
	 * @param value TODO
	 */
	public void addService(String id, String value);
}
