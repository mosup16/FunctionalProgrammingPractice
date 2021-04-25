package functionalprogramming.practice;

import java.util.List;

public class Session2 {
    public static void main(String[] args) {
        // functional implementation
        System.out.println("#Functional");
        var data = List.of(1, 3, 6, 7, 14, 9);
        data.stream().map(i -> i+1).map(i -> i * i).filter(i -> i > 20).map(i -> i - 10).forEach(System.out::println);

        // imperative
        System.out.println("#Imperative");
        // not really a clean code
        for (int x : data){
            printAndSubtractTenIfGreaterThan(20,square(addOne(x)));
        }
    }


    private static void printAndSubtractTenIfGreaterThan(int compareTo, int num) {
        if (num > compareTo){
            System.out.println(subtractTen(num));
        }
    }

    private static int addOne(int x){
        return x + 1;
    }

    private static int square(int x){
        return x * x;
    }

    private static int subtractTen(int x){
        return x - 10;
    }
}
