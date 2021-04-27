package functionalprogramming.practice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;


/**
 * Session 3 - in this example we calculate the discount for an Order ,
 * as we are using parameters in our calculations and this parameters depends on the order's ProductType.
 */
public class Session3 {

    private enum ProductType {
        Food, Beverage, Raw
    }

    @Data
    @AllArgsConstructor
    static class Order {
        private int id;
        private ProductType type;
        private int quantity;
        private int unitPrice;
    }

    @Data
    @AllArgsConstructor
    private static class ProductDiscountParams {
        private float unitParam;
        private float quantityParam;
    }

    public static void main(String[] args) {
        Order food = new Order(12, ProductType.Food, 10, 15);
        Order beverage = new Order(20, ProductType.Beverage, 1, 25);
        Order raw = new Order(14, ProductType.Raw, 2, 20);

        Stream.of(food, beverage, raw)
                .map(order -> calculateAfterDiscount(order, getDiscountParamsDeterminer().apply(order.type)))
                .forEach(System.out::println);

    }

    /**
     * @return float representing the new price after discount
     * @apiNote the inner logic doesn't have any intended meaning.
     * it solely exists for the sake of the example.
     */
    private static float calculateAfterDiscount(Order order, Supplier<ProductDiscountParams> discountParamsDeterminer) {
        var params = discountParamsDeterminer.get();
        float discount = params.getQuantityParam() * order.getQuantity() * order.getUnitPrice() * params.getUnitParam();
        int price = order.getUnitPrice() * order.getQuantity();
        return price - discount;
    }

    private static Function<ProductType, Supplier<ProductDiscountParams>> getDiscountParamsDeterminer() {
        return productType ->
                productType.equals(ProductType.Food) ? getFoodProductParamDeterminer() :
                        productType.equals(ProductType.Beverage) ? getBeverageProductParamDeterminer() : getRawProductParamDeterminer();
    }

    private static Supplier<ProductDiscountParams> getRawProductParamDeterminer() {
        return () -> new ProductDiscountParams(1 / 3f, 1 / 14f);
    }

    private static Supplier<ProductDiscountParams> getBeverageProductParamDeterminer() {
        return () -> new ProductDiscountParams(1 / 3f, 1 / 11f);
    }

    private static Supplier<ProductDiscountParams> getFoodProductParamDeterminer() {
        return () -> new ProductDiscountParams(1 / 2f, 1 / 10f);
    }


}
