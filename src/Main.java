public class Main {
    public static void main(String[] args) {

        Substitutions sub = new Substitutions();
        ProofEngine proofEngine = new ProofEngine();
        Axioms axioms = new Axioms();

        Proposition p = new Proposition("p");
        Neg np = new Neg(p);
        Implication mp = new Implication(np, np);

        System.out.println("ще доказваме това: " + mp);

        proofEngine.processProof(mp);

    }
}