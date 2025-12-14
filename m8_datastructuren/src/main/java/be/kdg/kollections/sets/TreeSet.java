package be.kdg.kollections.sets;

import be.kdg.kollections.lists.ArrayList;
import be.kdg.kollections.lists.List;

public class TreeSet<T extends Comparable<T>> implements Set<T> {
    private static class TreeNode<V extends Comparable<V>> {
        private V value;
        private TreeNode<V> left;
        private TreeNode<V> right;

        public TreeNode(V value) {
            this.value = value;
        }
    }

    private TreeNode<T> root;
    private int size = 0;

    @Override
    public void add(T value) {
        if (this.root == null) {
            this.root = new TreeNode<T>(value);
            size++;
        } else {
            add(root, value);
        }
    }

    private void add(TreeNode<T> parent, T value) {
        int comparison = value.compareTo(parent.value);

        if (comparison == 0) {
            return;
        }

        if (comparison < 0) {
            if (parent.left == null) {
                parent.left = new TreeNode<>(value);
                size++;
            } else {
                add(parent.left, value);
            }
        } else {
            if (parent.right == null) {
                parent.right = new TreeNode<>(value);
                size++;
            } else {
                add(parent.right, value);
            }
        }
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        addToList(list, root);
        return list;
    }

    private void addToList(List<T> list, TreeNode<T> node) {
        if (node.left!=null) {
            addToList(list, node.left);
        }
        list.add(node.value);
        if (node.right!=null) {
            addToList(list, node.right);
        }
    }


    @Override
    public boolean remove(T element) {
        if (root == null) return false;

        if (root.value.equals(element)) {
            root = removeNode(root);
            if (root != null || size > 0) {
                size--;
            }
            return true;
        }

        TreeNode<T> parent = root;
        TreeNode<T> current = null;
        boolean isLeftChild = false;

        while (parent != null) {
            int comparison = element.compareTo(parent.value);

            if (comparison < 0) {
                if (parent.left != null && parent.left.value.equals(element)) {
                    current = parent.left;
                    isLeftChild = true;
                    break;
                }
                parent = parent.left;
            } else if (comparison > 0) {
                if (parent.right != null && parent.right.value.equals(element)) {
                    current = parent.right;
                    isLeftChild = false;
                    break;
                }
                parent = parent.right;
            } else {
                return false;
            }
        }

        if (current == null) return false;

        TreeNode<T> replacement = removeNode(current);

        if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }

        size--;
        return true;
    }

    private TreeNode<T> removeNode(TreeNode<T> node) {
        if (node.left == null && node.right == null) {
            return null;
        }

        if (node.left == null) {
            return node.right;
        }

        if (node.right == null) {
            return node.left;
        }

        TreeNode<T> successorParent = node;
        TreeNode<T> successor = node.right;

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        node.value = successor.value;

        if (successorParent == node) {
            successorParent.right = successor.right;
        } else {
            successorParent.left = successor.right;
        }

        return node;
    }

    @Override
    public boolean contains(T element) {
        return contains(root, element);
    }

    private boolean contains(TreeNode<T> node, T element) {
        if (node == null) return false;

        int comparison = element.compareTo(node.value);

        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return contains(node.left, element);
        } else {
            return contains(node.right, element);
        }
    }

    @Override
    public int size() {
        return size;
    }
}
