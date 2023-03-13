package org.siit.logisticsystem.algorithm;

import java.util.Iterator;

public class IterableConverter {

    public <T> Iterable<Long> iteratorToIterable(Iterator<Long> iterator) {
        return () -> iterator;
    }
}
