package be.kdg.kollections.trees;

import be.kdg.kollections.lists.ArrayList;
import be.kdg.kollections.lists.List;

import java.util.function.Consumer;

public class Tree<V> {
    public static class TreeNode<V>{
        private List<TreeNode<V>> children;
        private V value;
        private TreeNode<V> parent;
        private Tree<V> hostTree;

        private TreeNode(V value, TreeNode<V> parent, Tree<V> hostTree) {
            this.value = value;
            this.parent = parent;
            this.hostTree = hostTree;
            this.children = new ArrayList<>();
        }

        public List<TreeNode<V>> getChildren() {
            return children;
        }

        public V getValue() {
            return value;
        }

        public TreeNode<V> getParent() {
            return parent;
        }

        public Tree<V> getHostTree() {
            return hostTree;
        }
    }

    private TreeNode<V> root;

    public TreeNode<V> getRoot() {
        return root;
    }

    public void setRoot(V value) {
        this.root = new TreeNode<>(value, null, this);
    }

    public TreeNode<V> add(TreeNode<V> parent, V value) {
        //check if parent belongs to tree
        if (parent.getHostTree().equals(this)) {
            TreeNode<V> newNode = new TreeNode<>(value, parent, this);
            parent.getChildren().add(newNode);
            return newNode;
        } else {
            throw new IllegalArgumentException("Parent does not belong to this tree");
        }
    }

    public void traverseDepthFirst(Consumer<V> consumer) {
        traverseDepthFirst(consumer, root);
    }

    private void traverseDepthFirst(Consumer<V> consumer, TreeNode<V> node) {
        consumer.accept(node.getValue());
        List<TreeNode<V>> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            traverseDepthFirst(consumer, children.get(i));
        }
    }

    public void traverseBreadthFirst(Consumer<V> consumer) {
        List<TreeNode<V>> queue = new ArrayList<>();
        queue.add(root);
        while (queue.size()>0) {
            TreeNode<V> current = queue.remove(0);
            consumer.accept(current.getValue());
            List<TreeNode<V>> children = current.getChildren();
            for (int i=0;i<children.size();i++) {
                queue.add(children.get(i));
            }
        }
    }
}
