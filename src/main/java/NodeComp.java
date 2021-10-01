import java.util.Comparator;

public class NodeComp implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return Integer.compare(n1.getPriority(), n2.getPriority());
    }
}
