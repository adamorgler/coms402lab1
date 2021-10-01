import java.util.ArrayList;

public class BFSController {

    private Node head;

    private final int[] startState;

    private final int[] goalState;

    private int nodeCount;

    public BFSController(int[] startState, int[] goalState) {
        this.startState = startState;
        this.goalState = goalState;
        init();
    }

    public Node run() {
        init();
        Queue<Node> q = new Queue<Node>();
        q.add(head);
        nodeCount++;
        while(!q.isEmpty()) {
            Node next = q.pop();
            next.setChildren();
            ArrayList<Node> children = next.getChildren();
            nodeCount += children.size();
            for(Node n: children) {
                int[] nextState = n.getState();
                if(checkGoalState(nextState)) {
                    return n;
                } else {
                    q.add(n);
                }
            }
        }
        return null;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    private void init() {
        this.head = new Node(startState);
        this.nodeCount = 0;
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
