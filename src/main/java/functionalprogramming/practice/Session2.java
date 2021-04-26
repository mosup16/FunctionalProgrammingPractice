package functionalprogramming.practice;

import java.util.*;

public class Session2 {


    public static void main(String[] args) {
        var data = List.of(1, 3, 6, 7, 14, 9);
        System.out.println("#Functional");
        long start = System.currentTimeMillis();
        executeFunctionalExample(data, 20, 2);
        System.out.println(System.currentTimeMillis() - start + "ms");

        System.out.println("#Imperative");
        start = System.currentTimeMillis();
        executeImperativeExample(data, 20, 2);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void executeFunctionalExample(List<Integer> data, int maxAllowed, int limit) {
        Comparator<Integer> reverseSortStrategy = (i, j) -> -1 * Integer.compare(i, j);
        data.stream().map(i -> i + 1).map(i -> i * i)
                .filter(i -> i > maxAllowed).sorted(reverseSortStrategy).limit(limit)
                .map(i -> i - 10).forEach(System.out::println);
    }


    private static void executeImperativeExample(List<Integer> data, int maxAllowed, int limit) {
        Comparator<Integer> reverseSortStrategy = (i, j) -> -1 * Integer.compare(i, j);
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
