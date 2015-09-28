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

import com.yandex.money.api.model.AllowedMoneySource;
import com.yandex.money.api.model.showcase.components.Component;
import com.yandex.money.api.model.showcase.components.Parameter;
import com.yandex.money.api.model.showcase.components.container.Group;
import com.yandex.money.api.model.showcase.components.uicontrol.Select;
import com.yandex.money.api.net.BaseApiRequest;
import com.yandex.money.api.net.HostsProvider;
import com.yandex.money.api.typeadapters.showcase.ShowcaseTypeAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Aleksandr Ershov (asershov@yamoney.com)
 */
public final class Showcase {

    public final String title;
    public final Map<String, String> hiddenFields;
    public final Group form;
    public final Set<AllowedMoneySource> moneySources;
    public final List<Error> errors;

    private Showcase(Builder builder) {

        if (builder.title == null) {
            throw new NullPointerException("title is null");
        }
        if (builder.hiddenFields == null) {
            throw new NullPointerException("hiddenFields is null");
        }
        if (builder.form == null) {
            throw new NullPointerException("form is null");
        }
        if (builder.moneySources == null) {
            throw new NullPointerException("moneySources is null");
        }
        if (builder.errors == null) {
            throw new NullPointerException("errors is null");
        }
        this.title = builder.title;
        this.hiddenFields = Collections.unmodifiableMap(builder.hiddenFields);
        this.form = builder.form;
        this.moneySources = Collections.unmodifiableSet(builder.moneySources);
        this.errors = Collections.unmodifiableList(builder.errors);
    }

    /**
     * See Showcases class.
     *
     * @return key-value pairs of payment parameters
     */
    public Map<String, String> getPaymentParameters() {
        Map<String, String> params = new HashMap<>();
        params.putAll(hiddenFields);
        fillPaymentParameters(params, form);
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Showcase showcase = (Showcase) o;

        return title.equals(showcase.title)
                && hiddenFields.equals(showcase.hiddenFields)
                && form.equals(showcase.form)
                && moneySources.equals(showcase.moneySources)
                && errors.equals(showcase.errors);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + hiddenFields.hashCode();
        result = 31 * result + form.hashCode();
        result = 31 * result + moneySources.hashCode();
        result = 31 * result + errors.hashCode();
        return result;
    }

    private static void fillPaymentParameters(Map<String, String> parameters, Group group) {
        for (Component component : group.items) {
            if (component instanceof Group) {
                fillPaymentParameters(parameters, (Group) component);
            } else if (component instanceof Parameter) {
                Parameter parameter = (Parameter) component;
                parameters.put(parameter.getName(), parameter.getValue());
                if (component instanceof Select) {
                    Group selectedGroup = ((Select) component).getSelectedOption().group;
                    if (selectedGroup != null) {
                        fillPaymentParameters(parameters, selectedGroup);
                    }
                }
            }
        }
    }

    public static class Builder {

        private String title;
        private Map<String, String> hiddenFields;
        private Group form;
        private Set<AllowedMoneySource> moneySources;
        private List<Error> errors;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setHiddenFields(Map<String, String> hiddenFields) {
            this.hiddenFields = hiddenFields;
            return this;
        }

        public Builder setForm(Group form) {
            this.form = form;
            return this;
        }

        public Builder setMoneySources(Set<AllowedMoneySource> moneySources) {
            this.moneySources = moneySources;
            return this;
        }

        public Builder setErrors(List<Error> errors) {
            this.errors = errors;
            return this;
        }

        public Showcase create() {
            return new Showcase(this);
        }
    }

    public static class Error {

        public final String name;
        public final String alert;

        public Error(String name, String alert) {
            if (alert == null) {
                throw new NullPointerException("alert is null");
            }
            this.name = name;
            this.alert = alert;
        }
    }

    /**
     * Requests showcase.
     */
    public static final class Request extends BaseApiRequest<Showcase> {

        private final long scid;
        private final String url;

        /**
         * Constructor.
         *
         * @param scid showcase id
         */
        public Request(long scid) {
            this(scid, null, null);
        }

        /**
         * Constructor.
         *
         * @param url    url
         * @param params post params
         */
        public Request(String url, Map<String, String> params) {
            this(-1l, url, params);
        }

        private Request(long scid, String url, Map<String, String> params) {
            super(Showcase.class, ShowcaseTypeAdapter.getInstance());
            if (url != null) {
                if (params == null) {
                    throw new NullPointerException("params is null");
                } else {
                    addParameters(params);
                }
            }
            this.scid = scid;
            this.url = url;
        }

        @Override
        public Method getMethod() {
            return url == null ? Method.GET : Method.POST;
        }

        @Override
        public String requestUrl(HostsProvider hostsProvider) {
            return url == null ? hostsProvider.getMoneyApi() + "/showcase/" + scid : url;
        }
    }
}