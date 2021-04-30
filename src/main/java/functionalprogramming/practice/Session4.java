package functionalprogramming.practice;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Session 4 example that calculates cost after applying a discount Rule(s) on an order only if it was qualified,
 * and if more than one rule is found to be qualified we take the average of the largest three results of them.
 * */
public class Session4 {
    @Data
    @AllArgsConstructor
    static class Order {
        private int id;
        private LocalDate productionData;
        private LocalDate expiryData;
        private int quantity;
        private int unitPrice;
    }

    @Data
    @AllArgsConstructor
    static class Pair<T, R> {
        Predicate<T> qualifier;
        Function<T, R> rule;
    }

    public static void main(String[] args) {
        Stream.of(
                new Order(1, LocalDate.parse("2020-12-01"), LocalDate.parse("2021-12-01"), 12, 12),
                new Order(1, LocalDate.parse("2020-05-01"), LocalDate.parse("2022-05-01"), 3, 60),
                new Order(1, LocalDate.parse("2019-01-01"), LocalDate.parse("2021-05-01"), 31, 2),
                new Order(1, LocalDate.parse("2023-01-01"), LocalDate.parse("2024-05-01"), 22, 6) //with no rules
        ).map(order -> calculateAfterDiscount(order, getRuleQualifierPairs())).forEach(System.out::println);

    }

    private static Float calculateAfterDiscount(Order order, List<Pair<Order, Float>> ruleQualifierPairs) {
        Comparator<Float> floatReverseComparator = (price, price2) -> -1 * price.compareTo(price2);
        return (float) ruleQualifierPairs.stream()
                .map(pair -> pair.qualifier.test(order) ? pair.rule.apply(order) : null)
                .filter(Objects::nonNull)
                .sorted(floatReverseComparator)
                .limit(3)
                .mapToDouble(f -> f)
                .average()
                .orElse(order.getUnitPrice() * order.getQuantity());
    }

    private static List<Pair<Order, Float>> getRuleQualifierPairs() {
        return List.of(
                new Pair<>(Qualifiers.getExpiresInMonth(), Rules.getExpiresInMonthRule()),
                new Pair<>(Qualifiers.getExpiresInYear(), Rules.getExpiresInYearRule()),
                new Pair<>(Qualifiers.getUnitPriceIsGreaterThanTwenty(), Rules.getUnitPriceIsGreaterThanTwentyRule()),
                new Pair<>(Qualifiers.getUnitPriceIsLessThanFive(), Rules.getUnitPriceIsLessThanFiveRule()),
                new Pair<>(Qualifiers.getUnitQuantityIsGreaterThanThirteen(), Rules.getUnitQuantityIsGreaterThanThirteenRule()),
                new Pair<>(Qualifiers.getUnitQuantityIsLessThanTwo(), Rules.getUnitQuantityIsLessThanTwoRule())
        );
    }

    static class Qualifiers {
        static Predicate<Order> getUnitQuantityIsLessThanTwo() {
            return order -> order.getQuantity() < 2;
        }

        static Predicate<Order> getUnitQuantityIsGreaterThanThirteen() {
            return order -> order.getQuantity() > 30;
        }

        static Predicate<Order> getUnitPriceIsLessThanFive() {
            return order -> order.getUnitPrice() < 5;
        }

        static Predicate<Order> getUnitPriceIsGreaterThanTwenty() {
            return order -> order.getUnitPrice() > 20;
        }

        static Predicate<Order> getExpiresInMonth() {
            return order -> order.getExpiryData().isBefore(LocalDate.now().plusMonths(1));
        }

        static Predicate<Order> getExpiresInYear() {
            return order -> order.getExpiryData().isBefore(LocalDate.now().plusYears(1));
        }
    }

    static class Rules {
        static Function<Order, Float> getUnitQuantityIsLessThanTwoRule() {
            return order -> .96f * order.getUnitPrice() * order.getQuantity();
        }

        static Function<Order, Float> getUnitQuantityIsGreaterThanThirteenRule() {
            return order -> .9f * order.getUnitPrice() * order.getQuantity();
        }

        static Function<Order, Float> getUnitPriceIsLessThanFiveRule() {
            return order -> .95f * order.getUnitPrice() * order.getQuantity();
        }

        static Function<Order, Float> getUnitPriceIsGreaterThanTwentyRule() {
            return order -> .7f * order.getUnitPrice() * order.getQuantity();
        }

        static Function<Order, Float> getExpiresInMonthRule() {
            return order -> .8f * order.getUnitPrice() * order.getQuantity();
        }

        static Function<Order, Float> getExpiresInYearRule() {
            return order -> .98f * order.getUnitPrice() * order.getQuantity();
        }

    }
}