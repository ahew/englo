/*
 * The MIT License
 *
 * Copyright 2015 Entando Corporation.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.entando.entando.plugins.jpenglo.aps.system.services.card;

import org.entando.entando.plugins.jpenglo.aps.ApsPluginBaseTestCase;

/**
 *
 * @author entando
 */
public class TestEngloCardManager extends ApsPluginBaseTestCase {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testBoardExistsError() throws Throwable {
        try {
            boolean exists = _cardmanager.boardExists("asòdklasòduq");
            assertFalse(exists);
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void testBoardExists() throws Throwable {
        try {
            boolean exists = _cardmanager.boardExists(TEST_BOARD);
            assertTrue(exists);
        } catch (Throwable t) {
            throw t;
        }
    }
    
    private void init() throws Exception {
        try {
            // init services
            _cardmanager = (IEngloCardManager) this.getService(IEngloCardManager.BEAN_ID);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private IEngloCardManager _cardmanager;
    
    public final static String TEST_BOARD = "jpgithub";
}
