package de.borekking.configSystem.util;

import java.util.Arrays;

public class JavaUtils {

    // Function for copying a subsequence of an array
    public static <T> T[] arrayInRange(T[] arr, int from, int to) {
        return Arrays.copyOfRange(arr, from, to);
    }

    // Function for getting an array with removed elements in the end
    public static <T> T[] arrayRemoveEnd(T[] arr, int amount) {
        return arrayInRange(arr, 0, arr.length - amount);
    }
}
