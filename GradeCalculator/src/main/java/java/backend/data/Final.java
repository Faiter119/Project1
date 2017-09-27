package backend.data;

/**
 * Created by faiter on 5/30/17.
 */
public class Final<T> {

    private T thing;

    public Final(T thing) {

        this.thing = thing;
    }

    public T get() {

        return thing;
    }

    public void set(T thing) {

        this.thing = thing;
    }
}
