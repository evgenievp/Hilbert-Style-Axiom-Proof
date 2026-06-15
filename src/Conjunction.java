public class Conjunction implements And, Operator, Formula {
    private Formula left;
    private Formula right;
    private boolean negated;

    public Conjunction(Formula left, Formula right) {
        this.left = left;
        this.right = right;
        this.negated = false;
    }

    @Override
    public Formula getLeft() {
        return this.left;
    }

    @Override
    public Formula getRight() {
        return this.right;
    }

    @Override
    public boolean getNegate() {
        return this.negated;
    }

    @Override
    public void negate() {
        this.negated = !negated;
    }

    @Override
    public void setLeft(Formula f) {
        this.left = f;
    }

    @Override
    public void setRight(Formula f) {
        this.right = f;
    }

    @Override
    public String toString() {
        return this.left + "^" + this.right;
    }
}
