package be.kdg.kollections.sets;

import be.kdg.kollections.Collection;
import be.kdg.kollections.lists.List;

public interface Set<E> extends Collection<E> {
    List<E> toList();
}
