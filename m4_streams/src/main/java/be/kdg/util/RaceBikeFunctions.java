package be.kdg.util;

import be.kdg.model.RaceBike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class RaceBikeFunctions {
    public static <T> List<T> filteredList(List<T> raceBikeList, Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (T raceBike : raceBikeList) {
            if (predicate.test(raceBike)) {
                filtered.add(raceBike);
            }
        }
        return filtered;
    }

    public static <T> Double average (List<T> raceBikeList, ToDoubleFunction<T> mapper) {
        double average = 0;
        for (T raceBike : raceBikeList) {
            average += mapper.applyAsDouble(raceBike);
        }
        return average / raceBikeList.size();
    }

    public static <T> long countIf(List<T> raceBikeList, Predicate<T> predicate) {
        long count = 0;
        for (T raceBike : raceBikeList) {
            if (predicate.test(raceBike)) {
                count++;
            }
        }
        return count;
    }
}
