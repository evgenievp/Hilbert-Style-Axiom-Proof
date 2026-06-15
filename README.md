  Hilbert-Style Proof Generator

A Java-based automated theorem prover for propositional logic using the Hilbert-style axiomatic system (3-axiom formulation).

## Overview

This project implements a forward proof search engine that attempts to prove theorems in propositional logic using only modus ponens and three axiom schemas:

1. **Axiom 1:** `p → (q → p)`
2. **Axiom 2:** `(p → (q → r)) → ((p → q) → (p → r))`
3. **Axiom 3:** `(¬p → ¬q) → (q → p)` 

The engine works by:
- Starting from the three axioms
- Systematically applying substitutions (replacing variables with subformulas of the target theorem)
- Applying Modus Ponens between all known formulas
- Iterating until the target formula is found or a depth limit is reached

## Current Features

-  Forward chaining proof search with configurable depth
-  Substitution of propositional variables (`p`, `q`, `r`) with arbitrary formulas
-  Automatic collection of relevant subformulas from the target theorem
-  Modus Ponens application between all derived formulas
-  Deduplication to prevent exponential explosion
-  Justification tracking (which axiom + substitution, or which MP step)
-  Minimal proof extraction (only necessary dependencies)
-  Length-based filtering to control complexity (otherwise too many formulas would be generated)

## Limitations

- No deduction theorem support (cannot prove `Γ ⊢ A → B` by assuming `A`)
- No primitive negation/⊥ handling (though can be encoded)
- String-based substitution (may miss some unifications)
- Fixed search limits (`maxLevels`, `maxLength`) – not adaptive
- No lemma caching or theorem reuse
- No formula parser – requires pre-built `Formula` objects


## How It Works

### Step 1: Initialize
Start with the three axioms as "proven" facts.

### Step 2: Extract Targets
Collect all subformulas from the theorem you want to prove. These will be used as substitution candidates.

### Step 3: Substitute
For each axiom and each target formula, substitute:
- Single variable (`p`, `q`, or `r`)
- Double variable combinations
- Triple variable combinations

### Step 4: Apply Modus Ponens
For every pair of formulas `(A, A→B)` currently known, derive `B`.

### Step 5: Iterate
Repeat steps 3-4 for `maxLevels` iterations or until the target is found.

### Step 6: Extract Proof
Trace back through the justification map to find the minimal set of formulas needed to derive the target.

## Example Usage

```java
Formula target = new Formula("(p → q) → ((q → r) → (p → r))");
ProofEngine engine = new ProofEngine();
engine.processProof(target);
```

**Expected output:**
```
✓ Доказано: (p → q) → ((q → r) → (p → r))

=== Доказателство ===
1. (p → (q → p))   [Аксиома 1]
2. ((p → (q → r)) → ((p → q) → (p → r)))   [Аксиома 2]
3. ...
7. (p → q) → ((q → r) → (p → r))   [MP: ...]
```

## Configuration

Adjust these parameters inside `processProof()`:

```java
int maxLength = 100;    // Skip formulas longer than this
int maxLevels = 5;      // How many search iterations
```

## Future Improvements (Roadmap)

1. **Deduction Theorem** – automatically convert `A ⊢ B` into `⊢ A → B`
2. **Negation/False** – add `⊥` and define `¬P := P → ⊥`
3. **Lemma Caching** – reuse proved theorems as new "axioms"
4. **Formula Parser** – parse from strings like `"(p→q)→((q→r)→(p→r))"`


## Building & Running
- just clone repo, start Main.java file and it will try to proof the given formula in the Main.
