/*
*
* Copyright 2013 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando Enterprise Edition software.
* You can redistribute it and/or modify it
* under the terms of the Entando's EULA
* 
* See the file License for the specific language governing permissions
* and limitations under the License
* 
* 
* 
* Copyright 2013 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package org.entando.entando.plugins.jpbasecamp;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.TestProjectFrontAction;
import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.TestTodolistsFrontAction;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TestBasecampManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TestBasecampManagerConfig;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TestJAXB;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Basecamp connector tests");

		suite.addTestSuite(TestBasecampManagerConfig.class);
		suite.addTestSuite(TestBasecampManager.class);
		suite.addTestSuite(TestProjectFrontAction.class);
		suite.addTestSuite(TestTodolistsFrontAction.class);
		suite.addTestSuite(TestJAXB.class);
		
		return suite;
	}
	
}
