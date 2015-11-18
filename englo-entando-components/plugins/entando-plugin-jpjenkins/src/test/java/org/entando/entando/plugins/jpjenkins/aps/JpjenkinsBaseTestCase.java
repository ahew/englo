/*
 *
 * <Your licensing text here>
 *
 */package org.entando.entando.plugins.jpjenkins.aps;


import org.entando.entando.plugins.jpjenkins.JpjenkinsConfigTestUtils;

import com.agiletec.ConfigTestUtils;
import com.agiletec.aps.BaseTestCase;

public class JpjenkinsBaseTestCase extends BaseTestCase {

	@Override
	protected ConfigTestUtils getConfigUtils() {
		return new JpjenkinsConfigTestUtils();
	}

	
}
