package ksbysample.library.dependnospring;

import org.apache.commons.lang3.builder.Builder;

import java.math.BigDecimal;

public class OrderBuilder implements Builder<Order> {

    private Order order;

    public OrderBuilder() {
        this.order = new Order();
    }

    public OrderBuilder number(int number) {
        this.order.setNumber(new BigDecimal(Integer.toString(number)));
        return this;
    }

    public OrderBuilder price(int price) {
        this.order.setPrice(new BigDecimal(Integer.toString(price)));
        return this;
    }

    @Override
    public Order build() {
        return this.order;
    }

}
