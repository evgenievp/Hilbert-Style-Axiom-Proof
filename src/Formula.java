public interface Formula {

    Formula getLeft();
    Formula getRight();
    boolean getNegate();
    void negate();
    void setLeft(Formula f);
    void setRight(Formula f);
}
