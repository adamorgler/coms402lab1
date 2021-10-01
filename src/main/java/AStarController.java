import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarController {

    private Node head;

    private PriorityQueue<Node> open;

    private ArrayList<Node> closed;

    private int nodeCount;

    private final int[] startState;

    private final int[] goalState;

    public AStarController(int[] startState, int[] goalState) {
        this.startState = startState;
        this.goalState = goalState;
        init();
    }

    public Node run(int hCode) {
        init();
        open.add(head);
        nodeCount++;
        while(!open.isEmpty()) {
            Node n = open.poll();
            n.setChildren();
            ArrayList<Node> children = n.getChildren();
            nodeCount += children.size();
            for (Node c : children) {
                c.setParent(n);
                if(checkGoalState(c.getState())) {
                    return c;
                }
                int g = n.getDepth() + 1;
                c.setDepth(g);
                int h = getH(hCode, c);
                c.setPriority(g + h);
                open.add(c);
            }
        }
        return null;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    private void init() {
        this.open = new PriorityQueue<Node>(1, new NodeComp());
        this.closed = new ArrayList<Node>();
        this.head = new Node(startState);
        this.head.setDepth(0);
        this.head.setPriority(0);
    }

    private int getH(int hCode, Node n) {
        switch (hCode) {
            case 1:
                return misplacedTiles(n);
            case 2:
                return manhattan(n);
            case 3:
                return outOfLine(n);
            default:
                return 0;
        }
    }

    private int misplacedTiles(Node n) {
        int[] state = n.getState();
        int c = 0;
        for(int i = 0; i < state.length; i++) {
            if(goalState[i] != state[i]) {
                c++;
            }
        }
        return c;
    }

    private int manhattan(Node n) {
        int[] state = n.getState();
        int m = 0;
        for(int i = 0; i < state.length; i++) {
            m += mdist(state[i], i + 1);
        }
        return m;
    }

    private int mdist(int var, int loc) {
        int[] pos = array1Dto2D(loc); // position of element
        int[] req = array1Dto2D(var); // goal position of element
        int xdist = Math.abs(pos[0] - req[0]);
        int ydist = Math.abs(pos[1] - req[1]);
        return xdist + ydist;
    }

    private int[] array1Dto2D(int var) {
        int[] out = new int[2];
        switch(var) {
            case 1:
                out[0] = 0;
                out[1] = 0;
                break;
            case 2:
                out[0] = 1;
                out[1] = 0;
                break;
            case 3:
                out[0] = 2;
                out[1] = 0;
                break;
            case 4:
                out[0] = 0;
                out[1] = 1;
                break;
            case 5:
                out[0] = 1;
                out[1] = 1;
                break;
            case 6:
                out[0] = 2;
                out[1] = 1;
                break;
            case 7:
                out[0] = 0;
                out[1] = 2;
                break;
            case 8:
                out[0] = 1;
                out[1] = 2;
                break;
            default:
                out[0] = 2;
                out[1] = 2;
                break;
        }
        return out;
    }

    // returns the number of tiles out of row plus the number out of column
    private int outOfLine(Node n) {
        int c = 0;
        int[] states = n.getState();
        for(int i = 0; i < states.length; i++) {
            int[] pos = array1Dto2D(i + 1); // current position of tile
            int[] req = array1Dto2D(states[i]); // goal position of tile
            if (pos[0] != req[0]) {
                c++;
            }
            if (pos[1] != req[1]) {
                c++;
            }
        }
        return c;
    }

    private boolean checkGoalState(int[] state) {
        for(int i = 0; i < state.length; i++) {
            if(state[i] != goalState[i]) {
                return false;
            }
        }
        return true;
    }
}
