/*
 *
 * <Your licensing text here>
 *
 */package org.entando.entando.plugins.jptrello.aps;


import org.entando.entando.plugins.jptrello.JptrelloConfigTestUtils;

import com.agiletec.ConfigTestUtils;
import com.agiletec.aps.BaseTestCase;

public class JptrelloBaseTestCase extends BaseTestCase {

	@Override
	protected ConfigTestUtils getConfigUtils() {
		return new JptrelloConfigTestUtils();
	}

	
}
