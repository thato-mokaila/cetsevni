package com.investec;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

interface HCFAlgorithm {
    /**
     * Determines the greatest common divisor using the
     * euclidean algorithm (modulus operation). It is also
     * possible to archive the same results using the
     * subtraction approach instead.
     * <p>
     * The former is the most efficient as it uses the modulus
     * operator resulting in less iterations.
     * <p>
     * This will only work with two input numbers.
     *
     * @return gcd(a, b) of the given numbers
     */
    int apply(int a, int b);
}

class Euclidean implements HCFAlgorithm {

    @Override
    public int apply(int a, int b) {
        // Validate b is not zero to avoid
        // division by zero exception
        if (b == 0) {
            return a;
        }

        int r = a % b;
        a = b; // copy b before division
        b = r; // get remainder into b

        // exit as soon as b == 0
        if (b != 0) {
            return apply(a, b);
        } else {
            return a;
        }
    }
}

public class App {

    public static void main(String[] args) {

        // Filter out digits and convert to integer array
        // for use with out api.
        int[] source = Arrays.stream(args)
                .map(s -> s.replaceAll("\\D+", ""))
                .filter(s -> !s.equals(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        App app = new App();

        System.out.println();
        System.out.println("################################################");
        System.out.println("Highest Common Factor of "
                + Arrays.toString(source)
                + " is: " + app.highestCommonFactor(source));
        System.out.println("################################################");
        System.out.println();
    }

    public int highestCommonFactor(int[] numbers) {
        List<Integer> values = Arrays.stream(numbers).boxed().collect(Collectors.toList());
        // Qualifies as an IntBinaryOperator to be passed around
        Euclidean euclidean = new Euclidean();
        if (values.size() == 1) {
            return values.get(0);
        } else if (values.size() == 2) {
            return euclidean.apply(values.get(0), values.get(1));
        } else {
            return reducer(values, euclidean);
        }
    }

    /**
     * Utility method that returns an array from int
     * vars args.
     *
     * @param numbers varargs
     * @return array
     */
    static int[] getArray(int... numbers) {
        return numbers;
    }

    /**
     * A custom reducer that walks any array that has more
     * than 2 elements and applies the lambda cumulatively.
     *
     * <p>The following example illustrates the idea in more
     * detail:
     * <p>
     * gcd(a,b,c) = gcd(gcd(a,b),c) = gcd(a,gcd(b,c))
     *
     * @param numbers  source list of numbers
     * @param algorithm function to be applied to the acc value and
     *                 the next int value in the provided list
     * @return gcb of all array elements
     */
    int reducer(List<Integer> numbers, HCFAlgorithm algorithm) {
        Iterator<Integer> it = numbers.iterator();
        int value = it.next();
        while (it.hasNext()) {
            int n = it.next();
            value = algorithm.apply(value, n);
            // System.out.println("applied("+value+", "+n+")");
        }
        return value;
    }
}
