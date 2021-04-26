package functionalprogramming.practice;

import java.util.*;
import java.util.function.Function;

public class Session2 {


    private static final Comparator<Integer> reverseSortStrategy = (i, j) -> -1 * Integer.compare(i, j);

    public static void main(String[] args) {
        var data = List.of(1, 3, 6, 7, 14, 9);
        System.out.println("#Functional");
        executeFunctionalExample(data, 20, 2);

        System.out.println("#Imperative");
        executeImperativeExample(data, 20, 2);
    }

    private static void executeFunctionalExample(List<Integer> data, int maxAllowed, int limit) {
        data.stream().map(i -> i + 1).map(i -> i * i)
                .filter(i -> i > maxAllowed).sorted(reverseSortStrategy).limit(limit)
                .map(i -> i - 10).forEach(System.out::println);
    }


    private static void executeImperativeExample(List<Integer> data, int maxAllowed, int limit) {

        PriorityQueue<Integer> squaredNums = new PriorityQueue<>(reverseSortStrategy);
        for (int x : data) {
            x = square(addOne(x));
            if (x > maxAllowed) {
                squaredNums.add(subtractTen(x));
            }
        }
        for (int greatestNum : getGreatestNums(squaredNums, limit)) {
            System.out.println(greatestNum);
        }
    }


    private static LinkedList<Integer> getGreatestNums(PriorityQueue<Integer> queue, int limit) {
        int i = 0;
        var list = new LinkedList<Integer>();
        while (!queue.isEmpty() && i < limit) {
            list.add(queue.poll());
            i++;
        }
        return list;
    }

    private static int addOne(int x) {
        return x + 1;
    }

    private static int square(int x) {
        return x * x;
    }

    private static int subtractTen(int x) {
        return x - 10;
    }
}
