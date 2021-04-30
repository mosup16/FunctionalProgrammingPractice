package functionalprogramming.practice;

import java.util.List;
import java.util.function.Function;

public class Session5 {
    public static void main(String[] args) {
        var data = List.of(1, 3, 6, 7, 14, 9);
        data.stream().map(processData()).forEach(System.out::println);
    }

    private static Function<Integer,Integer> processData(){
        return addOne().andThen(square()).andThen(subtractTen());
    }

    private static Function<Integer, Integer> subtractTen() {
        return i -> i - 10;
    }

    private static Function<Integer, Integer> square() {
        return i -> i * i;
    }

    private static Function<Integer, Integer> addOne() {
        return i -> i + 1;
    }
}
