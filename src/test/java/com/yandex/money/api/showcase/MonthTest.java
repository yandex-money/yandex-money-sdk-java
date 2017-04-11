/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 NBCO Yandex.Money LLC
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

package com.yandex.money.api.showcase;

import com.yandex.money.api.model.showcase.components.uicontrols.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.testng.Assert.assertTrue;

/**
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public class MonthTest extends ParameterTest {

    @Test
    public void testValidation() throws ParseException {
        Date.Builder builder = new Date.Builder();
        prepareParameter(builder);
        assertTrue(builder.create().isValid("1987-12-31"));

        builder.setMin(Date.parseDate("2000-01-01", Date.FORMATTER))
                .setMax(Date.parseDate("2010-01-01", Date.FORMATTER));
        Date date = builder.create();
        assertTrue(date.isValid("2000-01-01"));
        assertTrue(date.isValid("2010-01-01"));
        assertTrue(date.isValid("2005-01-01"));
        Assert.assertFalse(date.isValid("1999-12-31"));
        Assert.assertFalse(date.isValid("2010-01-02"));
        Assert.assertFalse(date.isValid("not a date"));

        testEmptyValues(builder);
    }
}
