import java.util.HashSet;
import java.util.List;

public class Calculator {
    private Formula formula;
    private HashSet<Proposition> vars;

    public Calculator(Formula formula) {
        this.formula = formula;
        this.vars = varsCount(new HashSet<>(), this.formula);
    }

    private HashSet<Proposition> varsCount(HashSet<Proposition> vars, Formula f) {
        if (f instanceof Proposition) {
            vars.add((Proposition) f);
            return vars;
        }
        varsCount(vars, f.getLeft());
        varsCount(vars, f.getRight());
        return vars;
    }

    public int getVars() {
        return vars.size();
    }


// no need of truth calculator for now
//    public List<Boolean> calculateFormula(Formula f) {
//        int possibilities = 1;
//        for (int i = 0; i < vars.size(); i++) {
//            possibilities = possibilities * 2;
//        }
//
//
//
//
//
//    }


}
