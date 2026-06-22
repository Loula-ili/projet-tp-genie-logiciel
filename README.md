# MIF01 — CV Search · Génie Logiciel M1, Lyon 1, 2025-2026

Mini-projet fil rouge du cours MIF01 : outil de sélection automatique de CV (candidatures à une offre d'emploi), développé en Java avec JavaFX. Le projet évolue sur quatre TPs en ajoutant progressivement qualité de code, design patterns, tests et outils de gestion.

## Structure du dépôt

\mif-01-g-logiciel/
├── cv-search/                   # Application principale (projet Maven)
│   ├── src/main/java/…
│   │   ├── App.java             # Point d'entrée JavaFX
│   │   ├── model/               # Modèle MVC
│   │   │   ├── Applicant / ApplicantList / ApplicantBuilder
│   │   │   ├── Experience
│   │   │   ├── Shortlist
│   │   │   ├── MatchingStrategy  # Pattern Strategy — critères de sélection
│   │   │   ├── ExportStrategy    # Pattern Strategy — export CSV / JSON
│   │   │   ├── StrategyFactory   # Pattern Factory
│   │   │   └── PdfCvImporter
│   │   ├── controller/
│   │   │   └── ApplicantController
│   │   └── view/
│   │       ├── JfxView           # Vue principale JavaFX
│   │       └── ShortlistView
│   ├── src/test/java/…           # Tests JUnit 5
│   └── pom.xml
├── homemade-junit/              # Implémentation maison d'un framework de test
├── TP1-java/                    # Remise en route Java — POO de base
├── TP2-outils/                  # Maven, Git, Forge Lyon 1
├── TP3-patterns/                # Design patterns (MVC, Strategy, Builder…)
├── TP4-tests/                   # Tests unitaires avec JUnit 5
├── tp_test/                     # Squelette d'exercice sur les tests
├── projet-note.md               # Consignes et barème du projet noté
└── rapport.pdf                  # Rapport final rendu
\
## Fonctionnalités de cv-search

- Chargement de candidatures depuis des fichiers YAML et PDF.
- **Stratégies de matching** interchangeables (pattern Strategy) :
  - \AllOver50\, \AllOver60\, \AverageOver50\, \MaxSkillAbove70\, \WeightedExperience\…
- **Export** de la shortlist en CSV ou JSON (pattern Strategy + Factory).
- Interface graphique JavaFX (pattern MVC).
- Pattern Builder pour la construction des candidats et listes.

## Lancement

\\ash
cd cv-search
mvn javafx:run
\
## Tests

\\ash
cd cv-search
mvn test
\
## Dépendances

- Java 17+, Maven 3.8+
- JavaFX 17
- JUnit 5
