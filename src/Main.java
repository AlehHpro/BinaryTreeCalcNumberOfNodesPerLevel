import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        int[] values = new int[] { 3, 6, 8, 43, 21, 98 };

        Node tree = buildTree(values);

        //tree.calculateNumberOfNodesPerLevel(0);
        //tree.calculateNumberOfNodesPerLevel2();
        tree.calculateNumberOfNodesPerLevel3();

        for(Integer key : Main.Node.myMap.keySet()) {
            System.out.println(key + ":" + Main.Node.myMap.get(key));
        }


    }

    public static Node buildTree(int[] arr) {
        Node root = new Node(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            //root.addNode(arr[i]);
            root.addNodeAlt(arr[i]);
        }
        return root;
    }

    public static class Node {
        int value;
        Node left;
        Node right;
        int nodeDepth;

        public Node(int value) {
            this.value = value;
        }

        public void addNode(int value) {
            if (this.value > value) {
                if (this.left == null) {
                    this.left = new Node(value);
                } else {
                    this.left.addNode(value);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(value);
                } else {
                    this.right.addNode(value);
                }
            }

        }

        //every recursion can be written as cycle, but not vice versa
        public void addNodeAlt(int value) {
            boolean insertCompleted = false;
            Node currNode = this;
            while (!insertCompleted) {
                if (value < currNode.value) {
                    if (currNode.left == null) {
                        currNode.left = new Node(value);
                        insertCompleted = true;
                    } else {
                        currNode = currNode.left;
                    }
                } else {
                    if (currNode.right == null) {
                        currNode.right = new Node(value);
                        insertCompleted = true;
                    } else {
                        currNode = currNode.right;
                    }
                }
            }

        }

        static final Map<Integer, Integer> myMap = new HashMap<>();

        public void calculateNumberOfNodesPerLevel(int currLevel) {
            if(myMap.containsKey(currLevel)) {
                myMap.put(currLevel, myMap.get(currLevel) + 1);
            } else {
                myMap.put(currLevel, 1);
            }

            if (this.left != null) {
                this.left.calculateNumberOfNodesPerLevel(currLevel + 1);
            }
            if (this.right != null) {
                this.right.calculateNumberOfNodesPerLevel(currLevel + 1);
            }

        }

        public void calculateNumberOfNodesPerLevel2() {
            int depth = 0;
            Deque<Node> myQueue = new ArrayDeque<>();
            myQueue.add(this);
            myMap.put(0, 1);

            while (!myQueue.isEmpty()) {
                myMap.put(depth, myQueue.size()); //!!!!!!!
                Deque<Node> nextQueue = new ArrayDeque<>();
                depth++;
                for (Node node : myQueue) {
                    if (node.left != null) {
                        nextQueue.add(node.left);
                    }
                    if (node.right != null) {
                        nextQueue.add(node.right);
                    }
                }
                myQueue = nextQueue;
            }

        }

        public void calculateNumberOfNodesPerLevel3() {
            Deque<Node> myStack = new ArrayDeque<>();
            this.nodeDepth = 1;
            myStack.push(this);

            while(!myStack.isEmpty()) {
                Node currentNode = myStack.pop();

                if(myMap.containsKey(currentNode.nodeDepth)) {
                    myMap.put(currentNode.nodeDepth, myMap.get(currentNode.nodeDepth) + 1);
                } else {
                    myMap.put(currentNode.nodeDepth, 1);
                }
                if(currentNode.right != null) {
                    currentNode.right.nodeDepth = currentNode.nodeDepth + 1;
                    myStack.push(currentNode.right);
                }
                if(currentNode.left != null) {
                    currentNode.left.nodeDepth = currentNode.nodeDepth + 1;
                    myStack.push(currentNode.left);
                }
            }
        }

    }

}
