package struct;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author srx
 *
 */
public class Node {
    private int value;
    private boolean matchWay = false;
    private final Queue<Character> direction = new LinkedList<Character>();

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public boolean isMatchWay() {
        return matchWay;
    }

    public void setMatchWay(boolean matchWay) {
        this.matchWay = matchWay;
    }

    public char pollDirection() {
        return direction.poll();
    }

    public char peekDirection() {
        return direction.peek();
    }

    public int sizeDirection() {
        return direction.size();
    }

    public boolean addDirection(final Character c) {
        return direction.add(c);
    }
}
