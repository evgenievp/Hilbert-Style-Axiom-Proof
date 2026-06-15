public class Substitutions {

    public Substitutions() {
    }

    public Formula substitutePropositionWithFormula(Formula original, Formula substitution) {
        Formula clonedOriginal = cloneFormula(original);
        substituteLeft(clonedOriginal, substitution);
        substituteRight(clonedOriginal, substitution);
        return clonedOriginal;
    }

    private void substituteLeft(Formula formula, Formula substitution) {
        if (formula == null) return;

        if (formula instanceof Neg) {
            Neg neg = (Neg) formula;
            if (neg.getFormula() instanceof Proposition) {
                // не заместваме p вътре в ~(p) - то си остава
            } else {
                substituteLeft(neg.getFormula(), substitution);
                substituteRight(neg.getFormula(), substitution);
            }
            return;
        }

        if (formula.getLeft() instanceof Proposition && !(formula.getLeft() instanceof Neg)) {
            formula.setLeft(cloneFormula(substitution));
        } else {
            substituteLeft(formula.getLeft(), substitution);
            substituteRight(formula.getLeft(), substitution);
        }
    }

    private void substituteRight(Formula formula, Formula substitution) {
        if (formula == null) return;

        if (formula instanceof Neg) {
            Neg neg = (Neg) formula;
            if (neg.getFormula() instanceof Proposition) {
                // не заместваме p вътре в ~(p) - то си остава
            } else {
                substituteLeft(neg.getFormula(), substitution);
                substituteRight(neg.getFormula(), substitution);
            }
            return;
        }

        if (formula.getRight() instanceof Proposition && !(formula.getRight() instanceof Neg)) {
            formula.setRight(cloneFormula(substitution));
        } else {
            substituteLeft(formula.getRight(), substitution);
            substituteRight(formula.getRight(), substitution);
        }
    }

    private Formula cloneFormula(Formula f) {
        if (f == null) return null;
        if (f instanceof Neg) {
            return new Neg(cloneFormula(((Neg) f).getFormula()));
        }
        if (f.getLeft() == null && f.getRight() == null) {
            return new Proposition(((Proposition) f).getVal());
        }
        return new Implication(cloneFormula(f.getLeft()), cloneFormula(f.getRight()));
    }


    public Formula substituteWithProposition(Formula original, Formula substitute) {
        if (original instanceof Proposition) {
            original = substitute;
        } else if (original instanceof Proposition) {
            ((Proposition) original).setVal(substitute.toString());
        }
        if (original.getLeft() != null) {
            substituteWithProposition(original.getLeft(), substitute);
        }
        if (original.getRight() != null) {
            substituteWithProposition(original.getRight(), substitute);
        }
        return original;
    }


    public Formula replaceFormulaWithFormulaFromFormula(Formula newFormula,
                                                        Formula originalFormula,
                                                        Formula oldFormula) {
        if (originalFormula.equals(oldFormula)) {
            return newFormula;
        }
        if (originalFormula.getLeft() == null && originalFormula.getRight() == null) {
            return originalFormula;
        }

        Formula newLeft = null;
        Formula newRight = null;

        if (originalFormula.getLeft() != null) {
            newLeft = replaceFormulaWithFormulaFromFormula(newFormula, originalFormula.getLeft(), oldFormula);
        }

        if (originalFormula.getRight() != null) {
            newRight = replaceFormulaWithFormulaFromFormula(newFormula, originalFormula.getRight(), oldFormula);
        }

        return reconstructFormula(originalFormula, newLeft, newRight);
    }

    private Formula reconstructFormula(Formula original, Formula newLeft, Formula newRight) {
        if (original instanceof MP) {
            return new Implication(newLeft, newRight);
        } else if (original instanceof Disjunction) {
            return new Disjunction(newLeft, newRight);
        } else if (original instanceof Conjunction) {
            return new Conjunction(newLeft, newRight);
        }

        return original;
    }

    public Formula substituteByName(Formula original, String varName, Formula substitution) {
        Formula cloned = cloneFormula(original);
        substituteByNameHelper(cloned, varName, substitution);
        return cloned;
    }

    private void substituteByNameHelper(Formula formula, String varName, Formula substitution) {
        if (formula == null || formula instanceof Neg) return;

        if (formula.getLeft() instanceof Proposition
                && !(formula.getLeft() instanceof Neg)
                && ((Proposition) formula.getLeft()).getVal().equals(varName)) {
            formula.setLeft(cloneFormula(substitution));
        } else {
            substituteByNameHelper(formula.getLeft(), varName, substitution);
        }

        if (formula.getRight() instanceof Proposition
                && !(formula.getRight() instanceof Neg)
                && ((Proposition) formula.getRight()).getVal().equals(varName)) {
            formula.setRight(cloneFormula(substitution));
        } else {
            substituteByNameHelper(formula.getRight(), varName, substitution);
        }
    }


}
