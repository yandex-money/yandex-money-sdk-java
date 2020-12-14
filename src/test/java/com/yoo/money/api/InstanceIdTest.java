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

import com.yoo.money.api.methods.InstanceId;
import com.yoo.money.api.model.Error;
import com.yoo.money.api.model.SimpleStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class InstanceIdTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void testRequestClientIdNull() {
        new InstanceId.Request(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRequestClientIdEmpty() {
        new InstanceId.Request("");
    }

    @Test()
    public void testRequestClient() {
        InstanceId.Request request = new InstanceId.Request(" ");
        Assert.assertNotNull(request);
    }

    @Test
    public void testIsSuccess() {
        InstanceId instanceId = new InstanceId(SimpleStatus.SUCCESS, null, "id");
        Assert.assertTrue(instanceId.isSuccessful());

        instanceId = new InstanceId(SimpleStatus.REFUSED, Error.UNKNOWN, null);
        Assert.assertFalse(instanceId.isSuccessful());
    }
}
