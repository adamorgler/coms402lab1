import java.util.concurrent.Callable;

public class AlgoThread implements Callable<String> {

    private BFSController bfs;

    private IDSController ids;

    private AStarController as;

    private long start;

    private long end;

    private Node node;

    private int nodeCount;

    private final String pname;

    private final int[] startState;

    private final int[] goalState;

    public AlgoThread(String pname, int[] startState, int[] goalState) {
        this.pname = pname;
        this.startState = startState;
        this.goalState = goalState;
        this.nodeCount = 0;
        this.bfs = new BFSController(startState, goalState);
        this.ids = new IDSController(startState, goalState);
        this.as = new AStarController(startState, goalState);
    }

    @Override
    public String call() throws Exception {
        System.out.println("running " + pname + " algorithm...");
        switch (pname) {
            case "BFS": {
                start = System.currentTimeMillis();
                node = bfs.run();
                end = System.currentTimeMillis();
                nodeCount = bfs.getNodeCount();
                break;
            }
            case "IDS": {
                start = System.currentTimeMillis();
                node = ids.run();
                end = System.currentTimeMillis();
                nodeCount = ids.getNodeCount();
                break;
            }
            case "H1": {
                start = System.currentTimeMillis();
                node = as.run(1);
                end = System.currentTimeMillis();
                nodeCount = as.getNodeCount();
                break;
            }
            case "H2": {
                start = System.currentTimeMillis();
                node = as.run(2);
                end = System.currentTimeMillis();
                nodeCount = as.getNodeCount();
                break;
            }
            case "H3": {
                start = System.currentTimeMillis();
                node = as.run(3);
                end = System.currentTimeMillis();
                nodeCount = as.getNodeCount();
                break;
            }
        }
        System.out.println("          ==" + pname + "==");
        System.out.println(" Node Count  : " + nodeCount);
        System.out.println(" Time Taken  : " + (end - start) + " ms");
        System.out.println(" Path Length : " + node.getPathLength());
        System.out.print(" Path        : " );
        node.printChainShort();
        return "Done!";
    }
}
