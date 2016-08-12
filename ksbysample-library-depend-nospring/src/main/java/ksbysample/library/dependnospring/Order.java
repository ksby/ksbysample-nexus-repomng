package ksbysample.library.dependnospring;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order {

    private BigDecimal number;

    private BigDecimal price;

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return number.multiply(price)
                .setScale(0, RoundingMode.FLOOR);
    }

}
