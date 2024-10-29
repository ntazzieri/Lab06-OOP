package it.unibo.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Example class using {@link List} and {@link Map}.
 *
 */
public final class UseListsAndMaps {

    private static final int READ_TEST_ELEMS = 1000;
    private static final int FIRST_TEST_ELEMS = 100000;
    private static final int UPPER_VAL = 2000;
    private static final int LOWER_VAL = 1000;

    private UseListsAndMaps() {
    }

    /**
     * @param s
     *            unused
     */
    public static void main(final String... s) {
        /*
         * 1) Create a new ArrayList<Integer>, and populate it with the numbers
         * from 1000 (included) to 2000 (excluded).
         */
        final ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = LOWER_VAL; i < UPPER_VAL; i++) {
            arrayList.addLast(i);
        }
        /*
         * 2) Create a new LinkedList<Integer> and, in a single line of code
         * without using any looping construct (for, while), populate it with
         * the same contents of the list of point 1.
         */
        final LinkedList<Integer> linkedList = new LinkedList<>(arrayList);
        /*
         * 3) Using "set" and "get" and "size" methods, swap the first and last
         * element of the first list. You can not use any "magic number".
         * (Suggestion: use a temporary variable)
         */
        final Integer tmp = arrayList.get(arrayList.size() - 1);
        arrayList.set(arrayList.size() - 1, arrayList.get(0));
        arrayList.set(0, tmp);
        /*
         * 4) Using a single for-each, print the contents of the arraylist.
         */
        for (final Integer elem : arrayList) {
            System.out.println(elem + " ");
        }
        /*
         * 5) Measure the performance of inserting new elements in the head of
         * the collection: measure the time required to add 100.000 elements as
         * first element of the collection for both ArrayList and LinkedList,
         * using the previous lists. In order to measure times, use as example
         * TestPerformance.java.
         */
        addTestList(arrayList);
        addTestList(linkedList);
        

        /*
         * 6) Measure the performance of reading 1000 times an element whose
         * position is in the middle of the collection for both ArrayList and
         * LinkedList, using the collections of point 5. In order to measure
         * times, use as example TestPerformance.java.
         */
        
         readTestList(arrayList);
         readTestList(linkedList);

        /*
         * 7) Build a new Map that associates to each continent's name its
         * population:
         *
         * Africa -> 1,110,635,000
         *
         * Americas -> 972,005,000
         *
         * Antarctica -> 0
         *
         * Asia -> 4,298,723,000
         *
         * Europe -> 742,452,000
         *
         * Oceania -> 38,304,000
         */

         final Map<String, Long> continents = new HashMap<>();
         continents.put("Africa", 1110635000L);
         continents.put("Americas", 972005000L);
         continents.put("Antartica", 0L);
         continents.put("Europe", 7242452000L);
         continents.put("Oceania", 38304000L);

        /*
         * 8) Compute the population of the world
         */
        Long world_population = 0L;
        for (final Long continent_pop : continents.values()) {
            world_population = world_population + continent_pop;
        }
        System.out.println("World population: " + world_population);
    }

    private static void addTestList(final List<Integer> list) {
        long time = System.nanoTime();
        for(int i = 0; i < FIRST_TEST_ELEMS; i++) {
            list.addFirst(i);
        }
        time = System.nanoTime() - time;
        final var millis = TimeUnit.NANOSECONDS.toMillis(time);
        printTestStats(time, millis);
    }

    private static void readTestList(final List<Integer> list) {
        long time = System.nanoTime();
        for(int i = list.size() / 2; i < READ_TEST_ELEMS; i++) {
            list.get(i);
        }
        time = System.nanoTime() - time;
        final var millis = TimeUnit.NANOSECONDS.toMillis(time);
        printTestStats(time, millis);
    }

    private static void printTestStats(final long time, final long millis) {
        System.out.println(
                   time
                + "ns ("
                + millis
                + "ms)"
        );
    }
}
