/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 NBCO Yandex.Money LLC
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

package com.yandex.money.api.time;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;

import static com.yandex.money.api.util.Common.checkNotNull;

/**
 * Simple utility class to parse and format ISO 8601 dates.
 */
public final class Iso8601Format {

    private Iso8601Format() {
    }

    /**
     * Parses string of ISO 8601 date.
     *
     * @param date a string to parse
     * @return parsed date time
     * @throws ParseException if parsing is not possible
     */
    public static DateTime parse(String date) throws ParseException {
        return DateTime.from(ISO8601Utils.parse(date, new ParsePosition(0)));
    }

    /**
     * Formats date time to ISO 8601 string.
     *
     * @param dateTime date time to format
     * @return formatted string
     */
    public static String format(DateTime dateTime) {
        Calendar calendar = checkNotNull(dateTime, "dateTime").getCalendar();
        return ISO8601Utils.format(calendar.getTime(), true, calendar.getTimeZone());
    }
}
