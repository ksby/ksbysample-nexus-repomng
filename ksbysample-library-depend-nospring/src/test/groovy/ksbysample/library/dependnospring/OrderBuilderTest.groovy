package ksbysample.library.dependnospring

import spock.lang.Specification
import spock.lang.Unroll

class OrderBuilderTest extends Specification {

    @Unroll
    def "OrderBuilder(#number, #price) --> #total"() {
        given:
        Order order = new OrderBuilder()
                .number(number)
                .price(price)
                .build()

        expect:
        order.number == number
        order.price == price
        order.total == total

        where:
        number            | price             || total
        0                 | 1                 || 0
        1                 | 0                 || 0
        1                 | 1                 || 1
        Integer.MAX_VALUE | 1                 || Integer.MAX_VALUE
        1                 | Integer.MAX_VALUE || Integer.MAX_VALUE
        Integer.MAX_VALUE | Integer.MAX_VALUE || 4611686014132420609
    }

}
