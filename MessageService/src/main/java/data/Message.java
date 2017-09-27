package data;

/**
 * Created by faiter on 8/9/17.
 */
public class Message<T> {

    private T message;

    public Message(T message) {
        this.message = message;
    }

    public T getMessage() {

        return message;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message<?> message1 = (Message<?>) o;

        return message != null ? message.equals(message1.message) : message1.message == null;
    }

    @Override
    public int hashCode() {

        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {

        return "Message{" + "message=" + message + '}';
    }
}
