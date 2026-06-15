public interface Operator {
    Formula getLeft();
    Formula getRight();
    void setLeft(Formula f);
    void setRight(Formula f);
}
