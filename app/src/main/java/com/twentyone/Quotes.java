package com.twentyone;

public class Quotes {

    int quotesId;
    String quotes;

    public Quotes(int quotesId, String quotes) {
        this.quotesId = quotesId;
        this.quotes = quotes;
    }

    public int getQuotesId() {
        return quotesId;
    }

    public void setQuotesId(int quotesId) {
        this.quotesId = quotesId;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
