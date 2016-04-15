package com.domain.order;

public enum Currency {
    USD("USD");

    private final String text;

    private Currency(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}