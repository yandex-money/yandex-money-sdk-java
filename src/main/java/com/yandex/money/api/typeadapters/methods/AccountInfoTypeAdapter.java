/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 NBCO Yandex.Money LLC
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

package com.yandex.money.api.typeadapters.methods;

import com.google.gson.*;
import com.yandex.money.api.methods.AccountInfo;
import com.yandex.money.api.model.*;
import com.yandex.money.api.typeadapters.BaseTypeAdapter;
import com.yandex.money.api.typeadapters.StringTypeAdapter;
import com.yandex.money.api.typeadapters.model.*;
import com.yandex.money.api.util.Currency;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import static com.yandex.money.api.typeadapters.JsonUtils.getBigDecimal;
import static com.yandex.money.api.typeadapters.JsonUtils.getString;

/**
 * Type adapter for {@link AccountInfo}.
 *
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public final class AccountInfoTypeAdapter extends BaseTypeAdapter<AccountInfo> {

    private static final AccountInfoTypeAdapter INSTANCE = new AccountInfoTypeAdapter();

    private static final String MEMBER_ACCOUNT = "account";
    private static final String MEMBER_AVATAR = "avatar";
    private static final String MEMBER_BALANCE = "balance";
    private static final String MEMBER_BALANCE_DETAILS = "balance_details";
    private static final String MEMBER_CARDS_LINKED = "cards_linked";
    private static final String MEMBER_CURRENCY = "currency";
    private static final String MEMBER_SERVICES_ADDITIONAL = "services_additional";
    private static final String MEMBER_STATUS = "account_status";
    private static final String MEMBER_TYPE = "account_type";
    private static final String MEMBER_YANDEX_MONEY_CARDS = "ymoney_cards";
    private static final String MEMBER_VIRTUAL_CARDS = "virtual_cards";

    private AccountInfoTypeAdapter() {
    }

    /**
     * @return instance of this class
     */
    public static AccountInfoTypeAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public AccountInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject object = json.getAsJsonObject();

        Currency currency = null;
        try {
            String c = getString(object, MEMBER_CURRENCY);
            if (c != null) {
                int parsed = Integer.parseInt(c);
                currency = Currency.parseNumericCode(parsed);
            }
        } catch (NumberFormatException e) {
            // see code below
        }
        if (currency == null) {
            currency = Currency.XXX;
        }

        Avatar avatar = null;
        if (object.has(MEMBER_AVATAR)) {
            avatar = AvatarTypeAdapter.getInstance().fromJson(object.get(MEMBER_AVATAR));
        }

        BigDecimal balance = getBigDecimal(object, MEMBER_BALANCE);
        BalanceDetails balanceDetails = BalanceDetailsTypeAdapter.getInstance()
                .fromJson(object.get(MEMBER_BALANCE_DETAILS));

        if (balanceDetails == null) {
            balanceDetails = new BalanceDetails.Builder()
                    .setTotal(balance)
                    .setAvailable(balance)
                    .create();
        }

        List<Card> linkedCards = CardTypeAdapter.getInstance().fromJson(object.getAsJsonArray(MEMBER_CARDS_LINKED));

        List<String> additionalServices = StringTypeAdapter.getInstance()
                .fromJson(object.getAsJsonArray(MEMBER_SERVICES_ADDITIONAL));

        List<YandexMoneyCard> yandexMoneyCards = YandexMoneyCardTypeAdapter.getInstance()
                .fromJson(object.getAsJsonArray(MEMBER_YANDEX_MONEY_CARDS));

        List<VirtualCard> virtualCards = VirtualCardTypeAdapter.getInstance()
                .fromJson(object.getAsJsonArray(MEMBER_VIRTUAL_CARDS));

        return new AccountInfo.Builder()
                .setAccount(getString(object, MEMBER_ACCOUNT))
                .setBalance(balance)
                .setCurrency(currency)
                .setAccountStatus(AccountStatus.parse(getString(object, MEMBER_STATUS)))
                .setAccountType(AccountType.parse(getString(object, MEMBER_TYPE)))
                .setAvatar(avatar)
                .setBalanceDetails(balanceDetails)
                .setLinkedCards(toEmptyListIfNull(linkedCards))
                .setAdditionalServices(toEmptyListIfNull(additionalServices))
                .setYandexMoneyCards(toEmptyListIfNull(yandexMoneyCards))
                .setVirtualCards(virtualCards)
                .create();
    }

    @Override
    public JsonElement serialize(AccountInfo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty(MEMBER_ACCOUNT, src.account);
        object.addProperty(MEMBER_BALANCE, src.balance);
        object.addProperty(MEMBER_CURRENCY, src.currency.numericCode.toString());
        object.addProperty(MEMBER_STATUS, src.accountStatus.code);
        object.addProperty(MEMBER_TYPE, src.accountType.code);

        object.add(MEMBER_AVATAR, AvatarTypeAdapter.getInstance().toJsonTree(src.avatar));
        object.add(MEMBER_BALANCE_DETAILS, BalanceDetailsTypeAdapter.getInstance().toJsonTree(src.balanceDetails));

        if (!src.linkedCards.isEmpty()) {
            object.add(MEMBER_CARDS_LINKED, CardTypeAdapter.getInstance().toJsonArray(src.linkedCards));
        }

        if (!src.additionalServices.isEmpty()) {
            object.add(MEMBER_SERVICES_ADDITIONAL, StringTypeAdapter.getInstance().toJsonArray(src.additionalServices));
        }

        if (!src.yandexMoneyCards.isEmpty()) {
            object.add(MEMBER_YANDEX_MONEY_CARDS, YandexMoneyCardTypeAdapter.getInstance().toJsonArray(src.yandexMoneyCards));
        }

        if (!src.virtualCards.isEmpty()) {
            object.add(MEMBER_VIRTUAL_CARDS, VirtualCardTypeAdapter.getInstance().toJsonArray(src.virtualCards));
        }

        return object;
    }

    @Override
    protected Class<AccountInfo> getType() {
        return AccountInfo.class;
    }
}
