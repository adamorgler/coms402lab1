import java.util.ArrayList;

public class IDSController {

    public Node head;

    private int nodeCount;

    private final int[] startState;

    private final int[] goalState;

    private final int maxDepth;

    public IDSController(int[] startState, int[] goalState) {
        this.startState = startState;
        this.goalState = goalState;
        this.maxDepth = Integer.MAX_VALUE;
        init();
    }

    public Node run() {
        Node out = null;
        for (int i = 0; i <= maxDepth; i++) {
            init();
            out = DFS(head, i);
            nodeCount++;
            if (out != null) {
                break;
            }
        }
        return out;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    private Node DFS(Node n, int limit) {
        if (limit == 0) {
            if (checkSameState(n.getState(), goalState)) {
                return n;
            } else {
                return null;
            }
        }
        else {
            n.setChildren();
            ArrayList<Node> children = n.getChildren();
            nodeCount += children.size();
            limit--;
            for(Node c : children) {
                c.setParent(n);
                Node found = DFS(c, limit);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void init() {
        this.head = new Node(startState);
        this.nodeCount = 0;
    }

    private boolean checkSameState(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if(a[i] == b[i]) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }


}
