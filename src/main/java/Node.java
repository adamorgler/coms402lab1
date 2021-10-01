import java.util.ArrayList;
import java.util.Comparator;

public class Node {

    private int[] state;

    private Node parent;

    private ArrayList<Node> children;

    private String lastMove;

    private int priority;

    private int depth;

    public Node(int[] initState) {
        lastMove = "Start";
        this.state = deepCopyState(initState);
        this.children = new ArrayList<>();
    }

    public int[] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    //removes all instances of the node in the child array
    public void removeChild(Node child) {
        while (children.contains(child)) {
            children.remove(child);
        }
    }

    //builds children for this node based on the rules of the game.
    public void setChildren() {
        children.clear();
        Node left = getLeft();
        Node right = getRight();
        Node up = getUp();
        Node down = getDown();
        if (left != null) {
            left.setParent(this);
            left.setLastMove("Left");
            addChild(left);
        }
        if (right != null) {
            right.setParent(this);
            right.setLastMove("Right");
            addChild(right);
        }
        if (up != null) {
            up.setParent(this);
            up.setLastMove("Up");
            addChild(up);
        }
        if (down != null) {
            down.setParent(this);
            down.setLastMove("Down");
            addChild(down);
        }
    }

    public Node getLeft() {
        int zeroPos = getZeroPos(state);
        if (zeroPos == 2 || zeroPos == 5 || zeroPos == 8 ) {
            return null;
        }
        else {
            return new Node(switchPos(state, zeroPos, 1));
        }
    }

    public Node getRight() {
        int zeroPos = getZeroPos(state);
        if (zeroPos == 0 || zeroPos == 3 || zeroPos == 6 ) {
            return null;
        }
        else {
            return new Node(switchPos(state, zeroPos, -1));
        }
    }

    public Node getUp() {
        int zeroPos = getZeroPos(state);
        if (zeroPos == 6 || zeroPos == 7 || zeroPos == 8 ) {
            return null;
        }
        else {
            return new Node(switchPos(state, zeroPos, 3));
        }
    }

    public Node getDown() {
        int zeroPos = getZeroPos(state);
        if (zeroPos == 0 || zeroPos == 1 || zeroPos == 2 ) {
            return null;
        }
        else {
            return new Node(switchPos(state, zeroPos, -3));
        }
    }

    public void printState() {
        System.out.println("Move " + getLevel() + ": " + lastMove);
        System.out.println(formatState(state[0]) + " " + formatState(state[1]) + " " + formatState(state[2]));
        System.out.println(formatState(state[3]) + " " + formatState(state[4]) + " " + formatState(state[5]));
        System.out.println(formatState(state[6]) + " " + formatState(state[7]) + " " + formatState(state[8]));
        System.out.println("");
    }

    public void printChain() {
        if (parent == null) {
            printState();
        } else {
            parent.printChain();
            printState();
        }
    }

    public int getPathLength() {
        if (parent == null) {
            return 0;
        } else {
            return parent.getPathLength() + 1;
        }
    }

    public void printStateShort() {
        String out;
        switch(lastMove) {
            case "Start":
                out = "";
                break;
            case "Down":
                out = "D";
                break;
            case "Up":
                out = "U";
                break;
            case "Left":
                out = "L";
                break;
            case "Right":
                out = "R";
                break;
            default:
                out = "?";
                break;
        }
        System.out.print(out);
    }

    public void printChainShort() {
        if (parent == null) {
            printStateShort();
        } else {
            parent.printChainShort();
            printStateShort();
        }
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public int getLevel() {
        if (parent == null) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    private String formatState(int i) {
        if(i == 0) {
            return "_";
        }
        return Integer.toString(i);
    }

    private int getZeroPos(int[] state) {
        int i = 0;
        for(int s : state) {
            if (s == 0) {
               break;
            }
            i++;
        }
        return i;
    }

    //switch zero state and another states positions: left: 1, right: -1, up: 3, down: -3.
    private int[] switchPos(int[] state, int zeroPos, int dir) {
        int[] output = deepCopyState(state);
        int switchPos = zeroPos + dir;
        output[zeroPos] = output[switchPos];
        output[switchPos] = 0;
        return output;
    }

    private int[] deepCopyState(int[] state) {
        int[] output = new int[state.length];
        for (int i = 0; i < state.length; i++) {
            output[i] = state[i];
        }
        return output;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
