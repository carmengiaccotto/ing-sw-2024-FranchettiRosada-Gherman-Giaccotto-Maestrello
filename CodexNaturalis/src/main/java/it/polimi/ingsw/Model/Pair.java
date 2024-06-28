package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a generic pair of two objects.
 * It implements the Serializable interface, which means it can be serialized and deserialized.
 * This is useful for saving and loading the state of objects.
 *
 * @param <T> the type of the first element in the pair
 * @param <U> the type of the second element in the pair
 */
public class Pair<T, U> implements Serializable {

    /**
     *The first element of the pair
     */
    private T first;

    /**
    *The second element of the pair
     */
    private U second;

    /**
     * Class Constructor
     * Initializes a new Pair with the given first and second elements.
     *
     * @param first The first element of the pair.
     * @param second The second element of the pair.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Setter method for the first element in the pair.
     *
     * @param first The new first element of the pair.
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Setter method for the second element in the pair.
     *
     * @param second The new second element of the pair.
     */
    public void setSecond(U second) {
        this.second = second;
    }

    /**
     * Getter method for the first element in the pair.
     *
     * @return The first element of the pair.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Getter method for the second element in the pair.
     *
     * @return The second element of the pair.
     */
    public U getSecond() {
        return second;
    }

    /**
     * This method returns a string representation of the pair.
     * The string is in the format "(first, second)".
     *
     * @return A string representation of the pair.
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    /**
     * This method checks if this pair is equal to another object.
     * Two pairs are equal if their first and second elements are equal.
     *
     * @param obj The object to compare this pair with.
     * @return true if the objects are the same or if the other object is a pair and its elements are equal to this pair's elements, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

}
