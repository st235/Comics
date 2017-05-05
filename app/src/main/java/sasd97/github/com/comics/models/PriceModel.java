package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 05/05/2017.
 */

public class PriceModel {

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("value")
    @Expose
    private double value;

    public String getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }

    public String obtainPretty() {
        return String.format("%.2f %s", value, currency);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PriceModel{");
        sb.append("currency='").append(currency).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
