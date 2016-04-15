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

package com.yandex.money.api.net;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.utils.HttpHeaders;
import com.yandex.money.api.utils.MimeTypes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

import static com.yandex.money.api.utils.Common.checkNotNull;

/**
 * Abstract session that provides convenience methods to work with requests.
 *
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public abstract class AbstractSession<T extends ApiRequest> {

    private static final Logger LOGGER = Logger.getLogger(OAuth2Session.class.getName());

    protected final ApiClient client;

    private boolean debugLogging = false;

    /**
     * Constructor.
     *
     * @param client API client used to perform operations
     */
    protected AbstractSession(ApiClient client) {
        this.client = checkNotNull(client, "client");
    }

    /**
     * Set requests and responses logging.
     *
     * @param debugLogging {@code true} if logging is required
     */
    public final void setDebugLogging(boolean debugLogging) {
        this.debugLogging = debugLogging;
    }

    protected final Call prepareCall(T request) {
        return prepareCall(prepareRequestBuilder(request));
    }

    protected final Call prepareCall(Request.Builder builder) {
        return client.getHttpClient()
                .newCall(checkNotNull(builder, "builder").build());
    }

    protected abstract Request.Builder prepareRequestBuilder(T request);

    /**
     * Gets input stream from response. Logging can be applied if
     * {@link com.yandex.money.api.net.OAuth2Session#setDebugLogging(boolean)} is set to
     * {@code true}.
     *
     * @param response response reference
     * @return input stream
     */
    protected final InputStream getInputStream(Response response) throws IOException {
        InputStream stream = response.body().byteStream();
        return debugLogging ? new ResponseLoggingInputStream(stream) : stream;
    }

    /**
     * Processes connection errors.
     *
     * @param response response reference
     * @return WWW-Authenticate field of connection
     */
    protected final String processError(Response response) throws IOException {
        String field = response.header(HttpHeaders.WWW_AUTHENTICATE);
        LOGGER.warning("Server has responded with a error: " + getError(response) + "\n" +
                HttpHeaders.WWW_AUTHENTICATE + ": " + field);
        LOGGER.warning(response.body().string());
        return field;
    }

    protected <T, E> T parseResponse(ApiRequest<T, E> request, Response response) throws IOException,
            InvalidRequestException, InvalidTokenException, InsufficientScopeException {

        InputStream inputStream = null;
        try {
            switch (response.code()) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_ACCEPTED:
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    inputStream = getInputStream(response);
                    if (isJsonType(response)) {
                        return request.parseResponse(inputStream);
                    } else {
                        throw new InvalidRequestException(processError(response));
                    }
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    throw new InvalidTokenException(processError(response));
                case HttpURLConnection.HTTP_FORBIDDEN:
                    throw new InsufficientScopeException(processError(response));
                default:
                    throw new IOException(processError(response));
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private boolean isJsonType(Response response) {
        String field = response.header(HttpHeaders.CONTENT_TYPE);
        return field != null && (field.startsWith(MimeTypes.Application.JSON) || field.startsWith(MimeTypes.Text.JSON));
    }

    private String getError(Response response) {
        return "HTTP " + response.code() + " " + response.message();
    }
}
