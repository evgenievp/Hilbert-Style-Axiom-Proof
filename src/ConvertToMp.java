public class ConvertToMp {
    public ConvertToMp() {}

    // ~(p -> ~q)
    public Formula convert(Formula f) {
        if (f instanceof Disjunction) {
            Neg n = new Neg(f.getLeft());
            return new Implication(n, f.getRight());
        }
        Neg n = new Neg(f.getRight()); // ~q
        Implication mp = new Implication(f.getLeft(), n); // (p->~q)
        return new Neg(mp);

    }
}
