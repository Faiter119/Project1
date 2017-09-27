package stuff.backend.util;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by OlavH on 02-Feb-17.
 * Map based bag that auto increments the count of items
 * Copy pasta from Lib cause I cant be bothered with Spring dependencies
 */
public class Shelf<T> implements Collection<T> {

    private Map<T, Integer> itemCountMap = new HashMap<>();

    public Shelf() {
    }

    public Shelf(Collection<T> collection) {

        addAll(collection);
    }


    public int size() {
        return itemCountMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int uniqueElements() {
        return itemCountMap.keySet().size();
    }

    /**
     * @return COPY of the internal map
     */
    public Map<T, Integer> getItemCountMap() {

        return Collections.unmodifiableMap(itemCountMap);
    }

    @Override
    public boolean add(T t) {

        if (itemCountMap.containsKey(t)) {

            itemCountMap.put(t, itemCountMap.get(t) + 1);
            return true;
        }
        else {
            itemCountMap.put(t, 1);
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {

        if (itemCountMap.containsKey(o)) {

            if (itemCountMap.get(o) == 1) { // only one left

                itemCountMap.remove(o); // removes the final object
                return true;
            }
            else {
                itemCountMap.put((T) o, itemCountMap.get(o) - 1); // removes one object
                return true;
            }
        }
        else {

            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return uniqueElements() == 0;
    }

    @Override
    public boolean contains(Object o) {

        return itemCountMap.containsKey(o);

    }

    @Override
    public Iterator<T> iterator() {
        return itemCountMap.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {


    }

    @Override
    public Object[] toArray() {

        return itemCountMap.values().toArray();

    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        return itemCountMap.keySet().containsAll(c);

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        c.forEach(this::remove);
        return true;

    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {

        Set<T> collect = itemCountMap.keySet().stream().filter(filter).collect(Collectors.toSet());

        return removeAll(collect);

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

        itemCountMap.clear();
    }

    @Override
    public Spliterator<T> spliterator() {

        return null;
    }

    @Override
    public Stream<T> stream() {

        return Stream.of((T) itemCountMap);
    }

    @Override
    public Stream<T> parallelStream() {
        return stream().parallel();
    }

    public static void main(String[] args) {

        Shelf<DayOfWeek> dayOfWeekShelf = new Shelf<>();

        dayOfWeekShelf.add(DayOfWeek.FRIDAY);
        dayOfWeekShelf.add(DayOfWeek.FRIDAY);

        dayOfWeekShelf.add(DayOfWeek.MONDAY);

        System.out.println(dayOfWeekShelf.size());
        System.out.println(dayOfWeekShelf.uniqueElements());
        System.out.println(dayOfWeekShelf.getItemCountMap());

        dayOfWeekShelf.add(DayOfWeek.THURSDAY);
        dayOfWeekShelf.add(DayOfWeek.FRIDAY);

        System.out.println(dayOfWeekShelf.size());
        System.out.println(dayOfWeekShelf.uniqueElements());
        System.out.println(dayOfWeekShelf.getItemCountMap());

        dayOfWeekShelf.remove(DayOfWeek.FRIDAY);

        System.out.println(dayOfWeekShelf.size());
        System.out.println(dayOfWeekShelf.uniqueElements());
        System.out.println(dayOfWeekShelf.getItemCountMap());
    }
}
