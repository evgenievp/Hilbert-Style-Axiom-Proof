public class Axioms {
    private Formula first;
    private Formula second;
    private Formula third;


    public Axioms() {
        Proposition p_1 = new Proposition("p");
        Proposition q_1 = new Proposition("q");
        Implication Implication = new Implication(q_1, p_1);
        Implication implication1 = new Implication(p_1, Implication);
        this.first = implication1;

        Proposition p_2 = new Proposition("p");
        Proposition q_2 = new Proposition("q");
        Neg nP = new Neg(p_2);
        Neg nQ = new Neg(q_2);
        Implication Implication1 = new Implication(nQ, nP);
        Implication impl = new Implication(p_2, q_2);
        Implication implication3 = new Implication(Implication1, impl);
        this.second = implication3;

        Proposition p_th = new Proposition("p");
        Proposition q_th = new Proposition("q");
        Proposition r_th = new Proposition("r");
        Implication Implication1_th = new Implication(q_th, r_th);
        Implication Implication2 = new Implication(p_th, Implication1_th);
        Implication Implication3 = new Implication(p_th, q_th);
        Implication Implication4 = new Implication(p_th, r_th);
        Implication Implication5 = new Implication(Implication3, Implication4);
        Implication Implication6 = new Implication(Implication2, Implication5);
        this.third = Implication6;

    }

    public Formula getSecond() {
        return second;
    }

    public Formula getThird() {
        return third;
    }

    public Formula getFirst() {
        return first;
    }
}
