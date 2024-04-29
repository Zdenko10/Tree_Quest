package zdenko;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        // 1,
        // 2 -> 3, 4
        // 5 -> 6 -> 7
        List<Node> list = new ArrayList<>();
        list.add(node(1.0));
        list.add(node(2.0, new Node[]{ node(3.0), node(4.0) }));
        list.add(node(5.0, new Node[]{ node(6.0, new Node[]{ node(7.0) })}));

        // should return 4
        // because (1 + 2 + 3 + 4 + 5 + 6 + 7) / 7 = 4
        System.out.println(getMeanValue(list));
    }

    public static interface Node {
        public double getValue();
        public List<Node> getNodes();
    }

    public static double getMeanValue(List<Node> nodes) {
        double[] sum = {0.0};
        double count = getNodeSum(nodes, sum,1.0);
        return sum[0] / count;
    }

    private static double getNodeSum(List<Node> nodes, double[] sum, double weight) {
        double count = 0.0;
        for (Node node : nodes) {
            sum[0] += node.getValue() * weight;
            count++;

            if (!node.getNodes().isEmpty()) {
                List<Node> subNodes = node.getNodes();
                count += getNodeSum(subNodes, sum, weight * 0.9);
            }
        }
        return count;
    }

    // builders

    public static Node node(double value) {
        return node(value, new Node[]{});
    }

    public static Node node(double value, Node[] nodes) {
        return new Node() {
            public double getValue() {
                return value;
            }
            public List<Node> getNodes() {
                return Arrays.asList(nodes);
            }
        };
    }
}