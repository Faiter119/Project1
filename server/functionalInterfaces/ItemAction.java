package main.java.server.functionalInterfaces;

/**
 * Created by faiter on 4/24/17.
 */
@FunctionalInterface
public interface ItemAction<T>{

    void doTheThing(Object thing);
}
