package be.kdg.kollections.trees;

import java.util.function.Consumer;

public class BinarySearchTree<V extends Comparable<V>> {
    private static class TreeNode<V extends Comparable<V>> {
        private V value;
        private TreeNode<V> left;
        private TreeNode<V> right;

        public TreeNode(V value) {
            this.value = value;
        }
    }

    private TreeNode<V> root;

    public void add(V value) {
        if (this.root == null) {
            this.root = new TreeNode<V>(value);
        } else {
            add(root, value);
        }
    }

    private void add(TreeNode<V> parent, V value) {
        if (value.compareTo(parent.value) < 0) {
            if (parent.left == null) {
                parent.left = new TreeNode<>(value);
            } else {
                add(parent.left, value);
            }
        } else {
            if (parent.right == null) {
                parent.right = new TreeNode<>(value);
            } else {
                add(parent.right, value);
            }
        }
    }

    public void traverseInOrder(Consumer<V> consumer) {
        traverseInOrder(consumer, root);
    }

    private void traverseInOrder(Consumer<V> consumer, TreeNode<V> node) {
        if (node.left != null) {
            traverseInOrder(consumer, node.left);
        }
        consumer.accept(node.value);
        if (node.right != null) {
            traverseInOrder(consumer, node.right);
        }
    }

}
