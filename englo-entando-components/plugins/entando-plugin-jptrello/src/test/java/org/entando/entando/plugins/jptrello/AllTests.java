package org.entando.entando.plugins.jptrello;

import org.entando.entando.plugins.jptrello.aps.system.services.TestTrelloClientManager;
import org.entando.entando.plugins.jptrello.apsadmin.params.TestParamsAction;
import org.entando.entando.plugins.jptrello.apsadmin.params.TestParamsFinderAction;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Trello junit tests");

		suite.addTestSuite(TestTrelloClientManager.class);
		suite.addTestSuite(TestParamsAction.class);
                suite.addTestSuite(TestParamsFinderAction.class);
		
		return suite;
	}
	
}
