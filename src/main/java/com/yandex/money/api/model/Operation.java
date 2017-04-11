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

package com.yandex.money.api.model;

import com.yandex.money.api.util.Constants;
import com.yandex.money.api.util.Enums;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.yandex.money.api.util.Common.checkNotNull;

/**
 * Operation details.
 */
public class Operation {

    /**
     * Operation id.
     */
    public final String operationId;

    /**
     * Status of operation.
     */
    public final Status status;

    /**
     * Pattern id.
     */
    public final String patternId;

    /**
     * Direction of operation.
     */
    public final Direction direction;

    /**
     * Amount.
     */
    public final BigDecimal amount;

    /**
     * Received amount.
     */
    public final BigDecimal amountDue;

    /**
     * Fee.
     */
    public final BigDecimal fee;

    /**
     * Operation datetime.
     */
    public final DateTime datetime;

    /**
     * Title of operation.
     */
    public final String title;

    /**
     * Sender.
     */
    public final String sender;

    /**
     * Recipient.
     */
    public final String recipient;

    /**
     * Type of recipient identifier.
     */
    public final PayeeIdentifierType recipientType;

    /**
     * Message to recipient.
     */
    public final String message;

    /**
     * operation comment
     */
    public final String comment;

    /**
     * {@code true} if operation is protected with a code
     */
    public final Boolean codepro;

    /**
     * Protection code for operation.
     */
    public final String protectionCode;

    /**
     * Protection code expiration datetime.
     */
    public final DateTime expires;

    /**
     * Answer datetime of operation acceptance/revoke.
     */
    public final DateTime answerDatetime;

    /**
     * Label of operation.
     */
    public final String label;

    /**
     * Details of operation.
     */
    public final String details;

    /**
     * {@code true} if operation can be repeated.
     */
    public final Boolean repeatable;

    /**
     * Payment parameters.
     */
    public final Map<String, String> paymentParameters;

    public final Boolean favorite;

    /**
     * Type of operation.
     */
    public final Type type;

    /**
     * Digital goods.
     */
    public final DigitalGoods digitalGoods;

    /**
     * Id of categories
     */
    public final List<Integer> categories;

    /**
     * Type of showcase
     */
    public final String format;


    /**
     * Use {@link com.yandex.money.api.model.Operation.Builder} instead.
     */
    protected Operation(Builder builder) {
        operationId = checkNotNull(builder.operationId, "operationId");
        status = checkNotNull(builder.status, "status");
        type = checkNotNull(builder.type, "type");
        direction = checkNotNull(builder.direction, "direction");
        title = checkNotNull(builder.title, "title");
        patternId = builder.patternId;
        amount = builder.amount;
        amountDue = builder.amountDue;
        fee = builder.fee;
        datetime = builder.datetime;
        sender = builder.sender;
        recipient = builder.recipient;
        recipientType = builder.recipientType;
        message = builder.message;
        comment = builder.comment;
        codepro = builder.codepro;
        protectionCode = builder.protectionCode;
        expires = builder.expires;
        answerDatetime = builder.answerDatetime;
        label = builder.label;
        details = builder.details;
        repeatable = builder.repeatable;
        paymentParameters = Collections.unmodifiableMap(builder.paymentParameters);
        favorite = builder.favorite;
        digitalGoods = builder.digitalGoods;
        categories = Collections.unmodifiableList(checkNotNull(builder.categories, "categories"));
        format = builder.format;
    }

    public boolean isCodepro() {
        return codepro != null && codepro;
    }

    public boolean isRepeatable() {
        return repeatable != null && repeatable;
    }

    public boolean isFavorite() {
        return favorite != null && favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (!operationId.equals(operation.operationId)) return false;
        if (status != operation.status) return false;
        if (patternId != null ? !patternId.equals(operation.patternId) : operation.patternId != null) return false;
        if (direction != operation.direction) return false;
        if (!amount.equals(operation.amount)) return false;
        if (amountDue != null ? !amountDue.equals(operation.amountDue) : operation.amountDue != null) return false;
        if (fee != null ? !fee.equals(operation.fee) : operation.fee != null) return false;
        if (!datetime.isEqual(operation.datetime)) return false;
        if (!title.equals(operation.title)) return false;
        if (sender != null ? !sender.equals(operation.sender) : operation.sender != null) return false;
        if (recipient != null ? !recipient.equals(operation.recipient) : operation.recipient != null) return false;
        if (recipientType != operation.recipientType) return false;
        if (message != null ? !message.equals(operation.message) : operation.message != null) return false;
        if (comment != null ? !comment.equals(operation.comment) : operation.comment != null) return false;
        if (codepro != null ? !codepro.equals(operation.codepro) : operation.codepro != null) return false;
        if (protectionCode != null ? !protectionCode.equals(operation.protectionCode) : operation.protectionCode != null)
            return false;
        if (expires != null ? !expires.isEqual(operation.expires) : operation.expires != null) return false;
        if (answerDatetime != null ? !answerDatetime.isEqual(operation.answerDatetime) : operation.answerDatetime != null)
            return false;
        if (label != null ? !label.equals(operation.label) : operation.label != null) return false;
        if (details != null ? !details.equals(operation.details) : operation.details != null) return false;
        if (repeatable != null ? !repeatable.equals(operation.repeatable) : operation.repeatable != null) return false;
        if (!paymentParameters.equals(operation.paymentParameters)) return false;
        if (favorite != null ? !favorite.equals(operation.favorite) : operation.favorite != null) return false;
        if (type != operation.type) return false;
        if (digitalGoods != null ? !digitalGoods.equals(operation.digitalGoods) : operation.digitalGoods != null)
            return false;
        if (categories != null ? !categories.equals(operation.categories) : operation.categories != null) return false;
        return format != null ? format.equals(operation.format) : operation.format == null;

    }

    @Override
    public int hashCode() {
        int result = operationId.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (patternId != null ? patternId.hashCode() : 0);
        result = 31 * result + direction.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + (amountDue != null ? amountDue.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + datetime.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (recipientType != null ? recipientType.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (codepro != null ? codepro.hashCode() : 0);
        result = 31 * result + (protectionCode != null ? protectionCode.hashCode() : 0);
        result = 31 * result + (expires != null ? expires.hashCode() : 0);
        result = 31 * result + (answerDatetime != null ? answerDatetime.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (repeatable != null ? repeatable.hashCode() : 0);
        result = 31 * result + paymentParameters.hashCode();
        result = 31 * result + (favorite != null ? favorite.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (digitalGoods != null ? digitalGoods.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationId='" + operationId + '\'' +
                ", status=" + status +
                ", patternId='" + patternId + '\'' +
                ", direction=" + direction +
                ", amount=" + amount +
                ", amountDue=" + amountDue +
                ", fee=" + fee +
                ", datetime=" + datetime +
                ", title='" + title + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientType=" + recipientType +
                ", message='" + message + '\'' +
                ", comment='" + comment + '\'' +
                ", codepro=" + codepro +
                ", protectionCode='" + protectionCode + '\'' +
                ", expires=" + expires +
                ", answerDatetime=" + answerDatetime +
                ", label='" + label + '\'' +
                ", details='" + details + '\'' +
                ", repeatable=" + repeatable +
                ", paymentParameters=" + paymentParameters +
                ", favorite=" + favorite +
                ", type=" + type +
                ", digitalGoods=" + digitalGoods +
                ", categories=" + categories +
                ", format='" + format + '\'' +
                '}';
    }

    /**
     * Status of operation.
     */
    public enum Status implements Enums.WithCode<Status> {
        /**
         * Operation succeeded.
         */
        SUCCESS(Constants.Status.SUCCESS),
        /**
         * Operation refused.
         */
        REFUSED(Constants.Status.REFUSED),
        /**
         * Operation is in progress, e.g. P2P with protection code has not been received.
         */
        IN_PROGRESS(Constants.Status.IN_PROGRESS);

        public final String code;

        Status(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Status[] getValues() {
            return values();
        }

        public static Status parseOrThrow(String code) {
            return Enums.parseOrThrow(SUCCESS, code);
        }
    }

    /**
     * Type of operation.
     */
    public enum Type implements Enums.WithCode<Type> {
        /**
         * Payment to a shop.
         */
        PAYMENT_SHOP("payment-shop"),
        /**
         * Outgoing transfer.
         */
        OUTGOING_TRANSFER("outgoing-transfer"),
        /**
         * Incoming transfer.
         */
        INCOMING_TRANSFER("incoming-transfer"),
        /**
         * Incoming transfer with protection code.
         */
        INCOMING_TRANSFER_PROTECTED("incoming-transfer-protected"),
        /**
         * Deposition.
         */
        DEPOSITION("deposition");

        public final String code;

        Type(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Type[] getValues() {
            return values();
        }

        public static Type parseOrThrow(String code) {
            return Enums.parseOrThrow(PAYMENT_SHOP, code);
        }
    }

    /**
     * Direction of operation.
     */
    public enum Direction implements Enums.WithCode<Direction> {
        /**
         * Incoming.
         */
        INCOMING("in"),
        /**
         * Outgoing.
         */
        OUTGOING("out");

        public final String code;

        Direction(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Direction[] getValues() {
            return values();
        }

        public static Direction parseOrThrow(String code) {
            return Enums.parseOrThrow(INCOMING, code);
        }
    }

    /**
     * Creates {@link com.yandex.money.api.model.Operation}.
     */
    public static class Builder {
        private String operationId;
        private Status status;
        private String patternId;
        private Direction direction;
        private BigDecimal amount = BigDecimal.ZERO;
        private BigDecimal amountDue;
        private BigDecimal fee;
        private DateTime datetime = DateTime.now();
        private String title;
        private String sender;
        private String recipient;
        private PayeeIdentifierType recipientType;
        private String message;
        private String comment;
        private Boolean codepro;
        private String protectionCode;
        private DateTime expires;
        private DateTime answerDatetime;
        private String label;
        private String details;
        private Boolean repeatable;
        private Map<String, String> paymentParameters = Collections.emptyMap();
        private Boolean favorite;
        private Type type;
        private DigitalGoods digitalGoods;
        private List<Integer> categories = Collections.emptyList();
        private String format;

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setPatternId(String patternId) {
            this.patternId = patternId;
            return this;
        }

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = checkNotNull(amount, "amount");
            return this;
        }

        public Builder setAmountDue(BigDecimal amountDue) {
            this.amountDue = amountDue;
            return this;
        }

        public Builder setFee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public Builder setDatetime(DateTime datetime) {
            this.datetime = checkNotNull(datetime, "datetime");
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder setRecipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder setRecipientType(PayeeIdentifierType recipientType) {
            this.recipientType = recipientType;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setCodepro(Boolean codepro) {
            this.codepro = codepro;
            return this;
        }

        public Builder setProtectionCode(String protectionCode) {
            this.protectionCode = protectionCode;
            return this;
        }

        public Builder setExpires(DateTime expires) {
            this.expires = expires;
            return this;
        }

        public Builder setAnswerDatetime(DateTime answerDatetime) {
            this.answerDatetime = answerDatetime;
            return this;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setDetails(String details) {
            this.details = details;
            return this;
        }

        public Builder setRepeatable(Boolean repeatable) {
            this.repeatable = repeatable;
            return this;
        }

        public Builder setPaymentParameters(Map<String, String> paymentParameters) {
            this.paymentParameters = checkNotNull(paymentParameters, "paymentParameters");
            return this;
        }

        public Builder setFavorite(Boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setDigitalGoods(DigitalGoods digitalGoods) {
            this.digitalGoods = digitalGoods;
            return this;
        }

        public Builder setCategories(List<Integer> categories) {
            this.categories = checkNotNull(categories, "categories");
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Operation create() {
            return new Operation(this);
        }
    }
}
