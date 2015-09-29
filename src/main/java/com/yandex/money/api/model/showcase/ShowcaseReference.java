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

package com.yandex.money.api.model.showcase;

import com.yandex.money.api.methods.ShowcaseSearch;
import com.yandex.money.api.utils.Strings;

import java.util.Collections;
import java.util.Map;

/**
 * Element of {@link ShowcaseSearch} class.
 *
 * @author Anton Ermak (ermak@yamoney.ru)
 */
public final class ShowcaseReference {

    /**
     * Showcase ID.
     */
    public final long scid;

    /**
     * Title.
     */
    public final String title;

    /**
     * Index of an item in list (lower is higher ranking). May be {@code null}
     */
    public final Integer topIndex;

    /**
     * URL to submit params of the first step if applicable. May be {@code null}.
     */
    public final String url;

    /**
     * Showcase parameters.
     */
    public final Map<String, String> params;

    /**
     * Showcase format. JSON or unknown one.
     */
    public final Format format;

    /**
     * Constructor.
     *
     * @param scid showcase ID
     * @param title    title of an item
     * @param topIndex index of an item in list (lower values mean higher ranking, if values are
     *                 equal it is recommended to sort {@link ShowcaseReference}'s by
     *                 title), can be null
     * @param format   showcase format
     */
    public ShowcaseReference(long scid, String title, Integer topIndex, Format format) {
        this(scid, title, topIndex, null, format, Collections.<String, String>emptyMap());
    }

    /**
     * Constructor.
     *
     * @param scid showcase ID
     * @param title    title of an item
     * @param topIndex index of an item in list (lower values mean higher ranking, if values are
     *                 equal it is recommended to sort {@link ShowcaseReference}'s by
     *                 title), can be null
     * @param url      url to submit {@code params} of the first step if applicable, can be null
     * @param format   showcase format
     * @param params   showcase parameters of the first step, can be null
     */
    public ShowcaseReference(long scid, String title, Integer topIndex, String url,
                             Format format, Map<String, String> params) {
        this.scid = scid;
        if (Strings.isNullOrEmpty(title)) {
            throw new IllegalArgumentException("title is null or empty");
        }
        if (format == null) {
            throw new NullPointerException("format is null");
        }
        this.title = title;
        this.topIndex = topIndex;
        this.url = url;
        this.format = format;
        this.params = Collections.unmodifiableMap(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShowcaseReference that = (ShowcaseReference) o;

        return scid == that.scid && title.equals(that.title)
                && !(topIndex != null ? !topIndex.equals(that.topIndex) : that.topIndex != null)
                && !(url != null ? !url.equals(that.url) : that.url != null)
                && params.equals(that.params) && format == that.format;

    }

    @Override
    public int hashCode() {
        int result = (int) (scid ^ (scid >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + (topIndex != null ? topIndex.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + params.hashCode();
        result = 31 * result + format.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ShowcaseReference{" +
                "scid=" + scid +
                ", title='" + title + '\'' +
                ", topIndex=" + topIndex +
                ", url='" + url + '\'' +
                ", params=" + params +
                ", format=" + format +
                '}';
    }

    /**
     * Format.
     */
    public enum Format {

        /**
         * JSON.
         */
        JSON("json"),

        /**
         * Unknown.
         */
        UNKNOWN("unknown");

        public final String code;

        Format(String code) {
            this.code = code;
        }

        public static Format parse(String code) {
            if (code == null) {
                return UNKNOWN;
            }
            for (Format format : values()) {
                if (format.code.equals(code)) {
                    return format;
                }
            }
            return UNKNOWN;
        }
    }
}