package com.oanda.exchangerates;

import com.oanda.exchangerates.api.ExchangeRatesClient;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LCC on 2017/11/8.
 */
public class ExchangeRateManager {

    private static final String OANDA_API_KEY = "iv6zTrp0QkmeiAmJ4E3xMgdy";

    private Map<String, String> rateMap = new HashMap<>();

    private ExchangeRatesClient client = null;

    private ExchangeRateManager() {
        init();
    }

    public static final ExchangeRateManager getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final ExchangeRateManager instance = new ExchangeRateManager();
    }

    private void init() {
        client = new ExchangeRatesClient(OANDA_API_KEY);
    }

    /**
     * @param baseCurrency
     * @param quotes
     * @param fields
     * @param decimalPlaces
     * @param date
     * @param start
     * @param end
     * @param dataSet
     */
    public String getRates(String baseCurrency, String quotes, ExchangeRatesClient.RateFields[] fields, String decimalPlaces, String date, String start, String end, String dataSet) throws IOException {
        ExchangeRatesClient.RatesResponse response = client.GetRates(baseCurrency, new String[]{quotes}, fields, decimalPlaces, date, start, end, dataSet);
        if (response.isSuccessful) {
            System.out.println("GetRates: base currency=" + response.base_currency + " date=" + response.meta.effective_params.date
                    + " decimal places=" + response.meta.effective_params.decimal_places + " data_set=" + response.meta.effective_params.data_set);
            for (int i = 0; i < response.meta.effective_params.fields.length; i++) {
                System.out.println("fields[" + i + "]=" + response.meta.effective_params.fields[i]);
            }
            for (int i = 0; i < response.meta.effective_params.quote_currencies.length; i++) {
                System.out.println("quote_currencies[" + i + "]=" + response.meta.effective_params.quote_currencies[i]);
            }
            System.out.println("meta.request_time=" + response.meta.request_time);
            for (int i = 0; i < response.meta.skipped_currencies.length; i++) {
                System.out.println("skipped_currencies[" + i + "]=" + response.meta.skipped_currencies[i]);
            }

            Iterator<String> iterator = response.quotes.keySet().iterator();
            String key = iterator.next();
            ExchangeRatesClient.Quote quote = response.quotes.get(key);
            System.out.println(key + " : ask=" + quote.ask + " bid=" + quote.bid + " spot=" + quote.spot + " date=" + quote.date);
            return quote.bid;
        } else {
            return response.errorMessage;
        }
    }

    public float getExchangeRate(String currencyCode) throws IOException, NumberFormatException {
        String rateStr = rateMap.get(currencyCode);
        if (TextUtils.isEmpty(rateStr)) {
            rateStr = getRates(currencyCode, "CNY", null, null, null, null, null, null);
            float rate;
            try {
                rate = Float.valueOf(rateStr);
            } catch (NumberFormatException e) {
                System.out.println(rateStr);
                throw e;
            }
            rateMap.put(currencyCode, rateStr);
            return rate;
        } else {
            return Float.valueOf(rateStr);
        }
    }

}
