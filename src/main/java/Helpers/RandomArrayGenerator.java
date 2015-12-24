package Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Rokas on 24/12/2015.
 */
public class RandomArrayGenerator {
    public static int[] createRandomDistinctArray(int size) {
        Random random = new Random(System.nanoTime());
        HashMap<Integer, Boolean> usedValsMap = new HashMap<>(size);
        int[] array = new int[size];
        int arrayIndex = 0;
        while (usedValsMap.size() < size) {
            int newInt = random.nextInt(size);
            if (!usedValsMap.containsKey(newInt)) {
                usedValsMap.put(newInt, true);
                array[arrayIndex] = newInt;
                arrayIndex++;
            }
        }
        return array;
    }
}
