package collections;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by faiter on 5/3/17.
 */
public class ShelfList<K,V> implements Map<K, V>{

    private Map<K, List<V>> itemListMap = new HashMap<>();


    @Override
    public int size() {

        return itemListMap.keySet().size();
    }

    @Override
    public boolean isEmpty() {

        return itemListMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {

        return itemListMap.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {

        return itemListMap.containsValue(o);
    }

    @Override
    public V get(Object o) {

        return (V) itemListMap.get(o).get(0);
    }

    @Override
    public V put(K k, V v) {

        itemListMap.putIfAbsent(k, new ArrayList<>());

        List<V> vs = itemListMap.get(k);
        vs.add(v);

        itemListMap.put(k, vs);

        return v;
    }

    @Override
    public V remove(Object o) {

        return (V) itemListMap.remove(o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {

        return null;
    }

    @Override
    public Collection<V> values() {

        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        return null;
    }

    @Override
    public V getOrDefault(Object o, V v) {

        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {

    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {

    }

    @Override
    public V putIfAbsent(K k, V v) {

        return null;
    }

    @Override
    public boolean remove(Object o, Object o1) {

        return false;
    }

    @Override
    public boolean replace(K k, V v, V v1) {

        return false;
    }

    @Override
    public V replace(K k, V v) {

        return null;
    }

    @Override
    public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {

        return null;
    }

    @Override
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {

        return null;
    }

    @Override
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {

        return null;
    }

    @Override
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {

        return null;
    }
}
