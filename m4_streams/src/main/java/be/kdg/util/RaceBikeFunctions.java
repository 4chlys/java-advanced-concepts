package be.kdg.util;

import be.kdg.model.RaceBike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class RaceBikeFunctions {
    public static <T> List<T> filteredList(List<T> raceBikeList, Predicate<T> predicate) {
        return raceBikeList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> Double average(List<T> raceBikeList, ToDoubleFunction<T> mapper) {
        return raceBikeList.stream()
                .mapToDouble(mapper)
                .average()
                .orElse(0.0);
    }

    public static <T> long countIf(List<T> raceBikeList, Predicate<T> predicate) {
        return raceBikeList.stream()
                .filter(predicate)
                .count();
    }
}
