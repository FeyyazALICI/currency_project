package com.currency_project.def.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CurrencyConstants {

    private CurrencyConstants() {
        // prevent instantiation
    }

    public static final Set<String> CURRENCY_SET;

    static {
        Set<String> currencies = new HashSet<>();
        currencies.add("AUD");
        currencies.add("CAD");
        currencies.add("CHF");
        currencies.add("CYP");
        currencies.add("CZK");
        currencies.add("DKK");
        currencies.add("EEK");
        currencies.add("EUR");
        currencies.add("GBP");
        currencies.add("HKD");
        currencies.add("HUF");
        currencies.add("ISK");
        currencies.add("JPY");
        currencies.add("KRW");
        currencies.add("LTL");
        currencies.add("LVL");
        currencies.add("MTL");
        currencies.add("NOK");
        currencies.add("NZD");
        currencies.add("PLN");
        currencies.add("ROL");
        currencies.add("SEK");
        currencies.add("SGD");
        currencies.add("SIT");
        currencies.add("SKK");
        currencies.add("TRL");
        currencies.add("USD");
        currencies.add("ZAR");

        CURRENCY_SET = Collections.unmodifiableSet(currencies);
    }
}
