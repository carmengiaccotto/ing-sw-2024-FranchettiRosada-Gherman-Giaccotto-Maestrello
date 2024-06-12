package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.Objects;

/** Class that represents a generic pair of two objects.
 *
 * @param <T> the type of the first element in the pair
 * @param <U> the type of the second element in the pair
 */

public class Pair<T, U> implements Serializable {
    private T first;
    private U second;

    /**Class Constructor*/
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * First element in the pair setter Method
     *
     * @param first
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Second element in the pair setter Method
     *
     * @param second
     */
    public void setSecond(U second) {
        this.second = second;
    }

    /**
     * getter method for the first element in the pair.
     *
     * @return first
     */
    public T getFirst() {
        return first;
    }

    /**
     * getter method for the second element in the pair.
     *
     * @return second
     */
    public U getSecond() {
        return second;
    }


    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

}
