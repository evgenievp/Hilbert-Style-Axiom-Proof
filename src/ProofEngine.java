import java.util.*;

public class ProofEngine {
    ConvertToMp convertToMp;
    Axioms axioms;
    public ProofEngine() {
        this.convertToMp = new ConvertToMp();
        this.axioms = new Axioms();
    }

    public Optional<Formula> tryDeduce(Formula implication, Formula antecedent) {
        if (implication.getLeft() == null || implication.getRight() == null) {
            return Optional.empty();
        }
        if (implication.getLeft().toString().equals(antecedent.toString())) {
            return Optional.of(implication.getRight());
        }
        return Optional.empty();
    }

    public void processProof(Formula toProve) {
        Substitutions sub = new Substitutions();
        LinkedList<Formula> proven = new LinkedList<>();
        Map<String, String> justification = new LinkedHashMap<>();

        proven.add(axioms.getFirst());
        justification.put(axioms.getFirst().toString(), "Аксиома 1");
        proven.add(axioms.getSecond());
        justification.put(axioms.getSecond().toString(), "Аксиома 2");
        proven.add(axioms.getThird());
        justification.put(axioms.getThird().toString(), "Аксиома 3");

        int maxLength = 100;
        int maxLevels = 5;

        for (int level = 0; level < maxLevels; level++) {
            System.out.println("=== Ниво " + level + ", proven: " + proven.size() + " ===");
            LinkedList<Formula> newFormulas = new LinkedList<>();
            Map<String, String> newJustifications = new LinkedHashMap<>();

            List<Formula> baseAxioms = List.of(
                    axioms.getFirst(), axioms.getSecond(), axioms.getThird()
            );

            List<Formula> targets = new ArrayList<>();
            targets.add(toProve);
            collectSubFormulas(toProve, targets);

            for (Formula axiom : baseAxioms) {
                String axiomName = justification.get(axiom.toString());

                for (Formula t : targets) {
                    addWithJustification(newFormulas, newJustifications,
                            sub.substituteByName(axiom, "p", t), maxLength,
                            axiomName + " [p:=" + t + "]");
                    addWithJustification(newFormulas, newJustifications,
                            sub.substituteByName(axiom, "q", t), maxLength,
                            axiomName + " [q:=" + t + "]");
                    addWithJustification(newFormulas, newJustifications,
                            sub.substituteByName(axiom, "r", t), maxLength,
                            axiomName + " [r:=" + t + "]");
                }

                for (Formula f : targets) {
                    for (Formula g : targets) {
                        Formula s;
                        s = sub.substituteByName(axiom, "p", f);
                        addWithJustification(newFormulas, newJustifications,
                                sub.substituteByName(s, "q", g), maxLength,
                                axiomName + " [p:=" + f + ", q:=" + g + "]");

                        s = sub.substituteByName(axiom, "p", f);
                        addWithJustification(newFormulas, newJustifications,
                                sub.substituteByName(s, "r", g), maxLength,
                                axiomName + " [p:=" + f + ", r:=" + g + "]");

                        s = sub.substituteByName(axiom, "q", f);
                        addWithJustification(newFormulas, newJustifications,
                                sub.substituteByName(s, "r", g), maxLength,
                                axiomName + " [q:=" + f + ", r:=" + g + "]");
                    }
                }

                for (Formula f : targets) {
                    for (Formula g : targets) {
                        for (Formula h : targets) {
                            Formula s = sub.substituteByName(axiom, "p", f);
                            s = sub.substituteByName(s, "q", g);
                            addWithJustification(newFormulas, newJustifications,
                                    sub.substituteByName(s, "r", h), maxLength,
                                    axiomName + " [p:=" + f + ", q:=" + g + ", r:=" + h + "]");
                        }
                    }
                }
            }

            // MP
            List<Formula> all = new ArrayList<>(proven);
            all.addAll(newFormulas);
            for (Formula f : all) {
                for (Formula g : all) {
                    tryDeduce(f, g).ifPresent(result -> {
                        if (result.toString().length() <= maxLength) {
                            String just = "MP: " + g + " и " + f;
                            addWithJustification(newFormulas, newJustifications,
                                    result, maxLength, just);
                        }
                    });
                }
            }

            // дедупликация + добавяне на обосновки
            for (Formula f : newFormulas) {
                String key = f.toString();
                boolean exists = proven.stream().anyMatch(p -> p.toString().equals(key));
                if (!exists) {
                    proven.add(f);
                    justification.put(key, newJustifications.getOrDefault(key, "?"));
                }
            }

            // провери целта
            for (Formula f : proven) {
                if (f.toString().equals(toProve.toString())) {
                    System.out.println("\n✓ Доказано: " + toProve);
                    printProof(toProve, justification, proven);
                    return;
                }
            }
        }
        System.out.println("✗ Не можа да се докаже");
    }

    private void addWithJustification(LinkedList<Formula> formulas,
                                      Map<String, String> justifications,
                                      Formula f, int maxLength, String just) {
        if (f != null && f.toString().length() <= maxLength) {
            String key = f.toString();
            if (!justifications.containsKey(key)) {
                formulas.add(f);
                justifications.put(key, just);
            }
        }
    }

    private void printProof(Formula target, Map<String, String> justification,
                            LinkedList<Formula> proven) {
        System.out.println("\n=== Доказателство ===");
        Set<String> needed = new LinkedHashSet<>();
        collectNeeded(target.toString(), justification, needed);
        int step = 1;
        for (String f : needed) {
            System.out.println(step++ + ". " + f + "   [" + justification.get(f) + "]");
        }
    }

    private void collectNeeded(String target, Map<String, String> justification,
                               Set<String> needed) {
        if (needed.contains(target)) return;
        String just = justification.get(target);
        if (just == null) return;

        if (just.startsWith("MP:")) {
            // извади двете формули от "MP: A и B"
            String rest = just.substring(4);
            int idx = rest.lastIndexOf(" и ");
            if (idx >= 0) {
                String antecedent = rest.substring(0, idx);
                String implication = rest.substring(idx + 3);
                collectNeeded(antecedent, justification, needed);
                collectNeeded(implication, justification, needed);
            }
        }
        needed.add(target);
    }

    private void collectSubFormulas(Formula f, List<Formula> result) {
        if (f == null) return;
        if (f.getLeft() != null) {
            result.add(f.getLeft());
            collectSubFormulas(f.getLeft(), result);
        }
        if (f.getRight() != null) {
            result.add(f.getRight());
            collectSubFormulas(f.getRight(), result);
        }
    }
    private void tryAdd(LinkedList<Formula> list, Formula f, int maxLength) {
        if (f != null && f.toString().length() <= maxLength) {
            list.add(f);
        }
    }

}
