package com.example.demo.finance.model;

import lombok.Data;

import java.util.List;

@Data
public class YahooResponse {
    private Chart chart;

    @Data
    public static class Chart {
        private List<Result> result;
        private Object error;

    }

    @Data
    public static class Result {
        private Meta meta;
        private List<Integer> timestamp;
        private Indicators indicators;

    }

    @Data
    public static class Meta {
        private String currency;
        private String symbol;
        private String exchangeName;
        private String fullExchangeName;
        private String instrumentType;
        private long firstTradeDate;
        private long regularMarketTime;
        private boolean hasPrePostMarketData;
        private int gmtoffset;
        private String timezone;
        private String exchangeTimezoneName;
        private double regularMarketPrice;
        private double fiftyTwoWeekHigh;
        private double fiftyTwoWeekLow;
        private double regularMarketDayHigh;
        private double regularMarketDayLow;
        private long regularMarketVolume;
        private double chartPreviousClose;
        private double previousClose;
        private int scale;
        private int priceHint;
        private CurrentTradingPeriod currentTradingPeriod;
        private List<List<TradingPeriod>> tradingPeriods;
        private String dataGranularity;
        private String range;
        private List<String> validRanges;
    }

    @Data
    public static class CurrentTradingPeriod {
        private TradingPeriod pre;
        private TradingPeriod regular;
        private TradingPeriod post;
    }

    @Data
    public static class TradingPeriod {
        private String timezone;
        private long start;
        private long end;
        private int gmtoffset;
    }

    @Data
    public static class Indicators {
        private List<Quote> quote;
    }

    @Data
    public static class Quote {
        private List<Double> open;
        private List<Integer> volume;
        private List<Double> low;
        private List<Double> close;
        private List<Double> high;
    }
}
