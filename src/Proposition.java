public class Proposition implements Formula {
    private String val;
    private String text;
    private boolean negated;

    public Proposition() {}


    public Proposition(String val) {
        this.val = val;
        this.text = null;
        this.negated = false;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.val;
    }

    @Override
    public Formula getLeft() {
        return null;
    }

    @Override
    public Formula getRight() {
        return null;
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

    }

    @Override
    public void setRight(Formula f) {

    }


}
