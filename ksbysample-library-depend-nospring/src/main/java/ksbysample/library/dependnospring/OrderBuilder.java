package ksbysample.library.dependnospring;

import org.apache.commons.lang3.builder.Builder;

import java.math.BigDecimal;

/**
 * Order クラスのインスタンスを Builder パターンで生成するためのクラスである。
 */
public class OrderBuilder implements Builder<Order> {

    private Order order;

    /**
     * OrderBuilder クラスの新規インスタンスを生成する。
     */
    public OrderBuilder() {
        this.order = new Order();
    }

    /**
     * 注文数を設定する。
     * @param number 注文数
     * @return OrderBuilder クラスのインスタンス
     */
    public OrderBuilder number(int number) {
        this.order.setNumber(new BigDecimal(Integer.toString(number)));
        return this;
    }

    /**
     * 単価を設定する。
     * @param price 単価(単位:円)
     * @return OrderBuilder クラスのインスタンス
     */
    public OrderBuilder price(int price) {
        this.order.setPrice(new BigDecimal(Integer.toString(price)));
        return this;
    }

    /**
     * Order クラスのインスタンスを生成する。
     * @return 生成された Order クラスのインスタンス
     */
    @Override
    public Order build() {
        return this.order;
    }

}
