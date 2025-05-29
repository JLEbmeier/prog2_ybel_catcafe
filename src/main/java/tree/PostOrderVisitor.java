package tree;

/**
 * Visitor for Postorder traversal of a binary tree (left -> right -> root).
 */
public class PostOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {
    @Override
    public String visit(Empty<T> empty) {
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        String left = node.leftChild().accept(this);
        String right = node.rightChild().accept(this);
        String current = node.data().toString();
        return left + (left.isEmpty() ? "" : ", ") + right + (right.isEmpty() ? "" : ", ") + current;
    }
}
