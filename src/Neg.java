public class Neg extends Proposition implements Formula, Negation {
    private Formula formula;
    private boolean negate;

    public Neg(Formula formula) {
        this.formula = formula;
        this.negate = false;
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
        return false;
    }

    @Override
    public void negate() {
        this.negate = !this.negate;
    }

    @Override
    public void setLeft(Formula f) {

    }

    @Override
    public void setRight(Formula f) {

    }

    @Override
    public String toString() {
        this.formula.negate();
        if (this.formula.getNegate()) {
            return "~" + "(" + this.formula + ")";
        }
        else {
            return this.formula.toString();
        }
    }

    public Formula getFormula() {
        return formula;
    }
}
