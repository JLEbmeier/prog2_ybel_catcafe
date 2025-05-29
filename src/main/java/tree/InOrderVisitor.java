package tree;

/**
 * Visitor for Inorder traversal of a binary tree (left -> root -> right).
 */
public class InOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {
    @Override
    public String visit(Empty<T> empty) {
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        String left = node.leftChild().accept(this);
        String current = node.data().toString();
        String right = node.rightChild().accept(this);
        return left + (left.isEmpty() ? "" : ", ") + current + (right.isEmpty() ? "" : ", ") + right;
    }
}
