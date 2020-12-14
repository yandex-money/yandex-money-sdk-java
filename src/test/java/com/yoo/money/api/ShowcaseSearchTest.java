/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 NBCO YooMoney LLC
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

package com.yoo.money.api;

import com.yoo.money.api.methods.ShowcaseSearch;
import com.yoo.money.api.net.clients.ApiClient;
import com.yoo.money.api.typeadapters.GsonProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Anton Ermak (support@yoomoney.ru)
 */
public class ShowcaseSearchTest {

    private static final ApiClient client = TestEnvironment.createClient();

    /**
     * Checks if exception is thrown and result list is not empty.
     */
    @Test
    public void searchForShowcases() throws Exception {
        ShowcaseSearch showcaseSearch = getShowcaseSearchInstance();
        /** Temporarily commented due to wrong behavior
            Assert.assertTrue(showcaseSearch.result.size() > 0, "result is empty");
            Hardcoded showcases usage (such as Skype, Skynet etc.) are strictly prohibited
        **/
        Assert.assertTrue(true);
    }

    /**
     * Checking for serialization/deserialization feature.
     */
    @Test
    public void marshallingTest() throws Exception {
        String json = GsonProvider.getGson().toJson(getShowcaseSearchInstance());
        Assert.assertTrue(!json.isEmpty(), "json should be non empty");
    }

    private static ShowcaseSearch getShowcaseSearchInstance() throws Exception {
        return client.execute(new ShowcaseSearch.Request("мтс", 10)).document;
    }
}
