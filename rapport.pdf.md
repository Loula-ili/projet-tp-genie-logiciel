# Rapport du Projet CV Search

## Introduction

Le projet CV Search est une application de gestion et de sélection de candidatures développée en Java avec JavaFX. L'application permet de rechercher des candidats selon leurs compétences, d'appliquer différentes stratégies de sélection, de gérer une liste restreinte (shortlist), et d'exporter les résultats en différents formats.

L'architecture du projet repose sur des design patterns reconnus pour garantir la maintenabilité, l'extensibilité et la séparation des responsabilités.

---

## 1. Design Patterns

### 1.1 Modèle-Vue-Contrôleur (MVC)

**Motivation** : Le pattern MVC permet de séparer la logique métier (modèle), l'interface utilisateur (vue) et la coordination entre les deux (contrôleur). Cette séparation facilite la maintenance et l'évolution du code.

**Implémentation dans notre projet** :
- **Modèle** : `ApplicantList` contient la liste des candidats et notifie les vues des changements
- **Vue** : `ApplicantListView` et `ApplicantDetailView` affichent les données
- **Contrôleur** : `ApplicantController` gère les interactions utilisateur (recherche, ajout de compétences)

**Flexibilité** : Nous avons implémenté deux vues synchronisées :
- `ApplicantListView` : affiche la liste complète avec nom, compétences et expérience
- `ApplicantDetailView` : affiche les détails d'un candidat sélectionné

Les deux vues observent le même modèle via le pattern Observer et se mettent à jour automatiquement.

```
┌──────────────┐         ┌──────────────────┐
│ Controller   │────────>│  ApplicantList   │
│              │         │    (Modèle)      │
└──────────────┘         └─────────┬────────┘
                                   │ notifie
                         ┌─────────┴─────────┐
                         │                   │
                    ┌────▼─────┐      ┌─────▼────┐
                    │ ListView │      │DetailView│
                    │  (Vue)   │      │  (Vue)   │
                    └──────────┘      └──────────┘
```

### 1.2 Pattern Strategy

**Motivation** : Différentes stratégies de sélection doivent être appliquées sans modifier le code existant. Le pattern Strategy permet de définir une famille d'algorithmes interchangeables.

**Implémentation** :
- Interface `ApplicantStrategy` définit le contrat
- Stratégies concrètes :
  - `BestStrategy` : trie par score décroissant
  - `AverageStrategy` : filtre les candidats avec moyenne ≥ 50%
  - `AllAboveStrategy` : filtre ceux ayant toutes compétences ≥ 60%
  - `ExperienceStrategy` : favorise l'expérience professionnelle

**Exemple de stratégie d'export** :
- `CsvExportStrategy` : export au format CSV
- `JsonExportStrategy` : export au format JSON

Le contrôleur sélectionne dynamiquement la stratégie selon le choix de l'utilisateur.

```
┌─────────────────────┐
│ ApplicantStrategy   │◄─────┐
│   (interface)       │      │
└─────────────────────┘      │
          △                  │
          │                  │
    ┌─────┴─────┬────────────┴─────┬──────────┐
    │           │                  │          │
┌───▼───┐ ┌─────▼──────┐ ┌─────────▼─┐ ┌─────▼────┐
│ Best  │ │  Average   │ │ AllAbove  │ │Experience│
│Strategy│ │  Strategy  │ │ Strategy  │ │ Strategy │
└───────┘ └────────────┘ └───────────┘ └──────────┘
```

### 1.3 Pattern Singleton

**Motivation** : La shortlist doit être unique dans toute l'application pour éviter les incohérences.

**Implémentation** : La classe `Shortlist` utilise le pattern Singleton avec initialisation lazy :

```java
private static Shortlist instance;

public static Shortlist getInstance() {
    if (instance == null) {
        instance = new Shortlist();
    }
    return instance;
}
```

### 1.4 Pattern Observer

**Motivation** : Les vues doivent se mettre à jour automatiquement quand le modèle change, sans couplage fort.

**Implémentation** :
- `ApplicantList` et `Shortlist` utilisent `PropertyChangeSupport`
- Les vues s'enregistrent comme `PropertyChangeListener`
- Quand le modèle change, il notifie tous ses observateurs

```java
// Dans le modèle
support.firePropertyChange("applicants", null, applicants);

// Dans la vue
model.addPropertyChangeListener(evt -> updateView());
```

### 1.5 Pattern Builder

**Motivation** : La construction d'objets `Applicant` à partir de fichiers YAML est complexe et doit être isolée.

**Implémentation** : `ApplicantBuilder` encapsule la logique de parsing YAML et construit progressivement un objet `Applicant`.

```java
ApplicantBuilder builder = new ApplicantBuilder("applicant1.yaml");
Applicant applicant = builder.build();
```

### 1.6 Pattern Facade

**Motivation** : Simplifier l'interface d'export en cachant la complexité de la sélection de stratégie et de l'écriture fichier.

**Implémentation** : `ExportService` fournit une interface simple pour exporter :

```java
ExportService.getInstance().exportToFile(
    applicants, 
    new CsvExportStrategy(), 
    "export.csv"
);
```

### 1.7 Principes GRASP et SOLID

**Expert en information** : Chaque classe gère ses propres données (ex: `Applicant` calcule son propre score)

**Faible couplage** : Les classes dépendent d'interfaces, pas d'implémentations concrètes

**Forte cohésion** : Chaque classe a une responsabilité unique et bien définie

**Single Responsibility Principle** : 
- `ApplicantList` : gestion de la liste
- `ApplicantController` : coordination
- `ApplicantStrategy` : logique de filtrage

**Open/Closed Principle** : Nouvelles stratégies ajoutables sans modifier le code existant

**Dependency Inversion** : Le contrôleur dépend de `ApplicantStrategy` (abstraction), pas des stratégies concrètes

---

## 2. Éthique

### 2.1 Risques de discrimination

Notre système de sélection automatique présente plusieurs risques éthiques :

**Biais algorithmiques** :
- Le tri par score favorise les candidats avec de nombreuses compétences listées, ce qui peut désavantager les candidats ayant des compétences transversales non mentionnées
- La pondération par expérience (années × 10%) peut discriminer les jeunes diplômés ou personnes en reconversion

**Exemple concret** : Un candidat senior avec 15 ans d'expérience mais compétence Java à 50% obtiendra un score de 65, tandis qu'un junior avec 0 ans mais compétence à 90% obtiendra 63. Le système favorise l'ancienneté même si la compétence technique est inférieure.

### 2.2 Limitations de l'algorithme

**Cas où un humain ferait mieux** :

1. **Compétences complémentaires** : L'algorithme ne détecte pas qu'un candidat avec React + Node.js peut convenir pour un poste "JavaScript fullstack" même si ces termes exacts ne sont pas cherchés

2. **Surévaluation** : Un candidat peut s'auto-évaluer à 100% sur toutes ses compétences par optimisme, tandis qu'un autre plus humble se note à 70%. L'algorithme favorisera le premier sans détecter la surévaluation

3. **Contexte ignoré** : Un candidat ayant travaillé sur des projets critiques (banque, santé) a une expérience plus valorisable qu'un autre ayant travaillé sur des projets mineurs, mais l'algorithme ne fait pas cette distinction

### 2.3 Mesures pour réduire les biais

**Transparence** : 
- Affichage du score et de son calcul pour permettre la vérification
- Documentation des critères de sélection

**Diversité des stratégies** :
- `AverageStrategy` permet de ne pas éliminer les candidats avec une compétence faible
- `BestStrategy` offre un classement plutôt qu'une élimination binaire

**Intervention humaine** :
- La shortlist permet un tri manuel final
- Système de notation et commentaires pour affiner l'évaluation

**Limitations connues** :
- Notre système est un outil d'aide à la décision, pas de décision automatique
- Les recruteurs doivent toujours faire une évaluation humaine finale

### 2.4 Références

- Article "Amazon scraps secret AI recruiting tool that showed bias against women" (Reuters, 2018) montre comment un algorithme de tri de CV a reproduit les biais historiques de l'entreprise
- Étude "Hiring Discrimination Against Black Americans Hasn't Declined in 25 Years" (PNAS, 2017) souligne l'importance de la vigilance même dans les systèmes automatisés
- Les recommandations de la CNIL sur les algorithmes de recrutement insistent sur la transparence et le droit à l'explication

---

## 3. Tests

### 3.1 Tests automatiques

Nous avons implémenté 21 tests unitaires couvrant :

**Tests du modèle (ShortlistTest - 9 tests)** :
- Singleton pattern
- Observer pattern (notification des vues)
- Ajout/suppression de candidats
- Système de notation et commentaires
- Gestion des doublons

**Tests des stratégies (ExportStrategyTest - 6 tests)** :
- Export CSV avec vérification du format
- Export JSON avec structure valide
- Extensions de fichiers correctes
- Gestion des listes vides
- Facade ExportService

**Tests du contrôleur (ApplicantControllerTest - 6 tests)** :
- Recherche avec une ou plusieurs compétences
- Recherche avec liste vide
- Application des stratégies Average et Best
- Tri correct par score décroissant
- Mise à jour du modèle MVC

Les tests utilisent JUnit 5 et Hamcrest pour des assertions lisibles.

### 3.2 Tests manuels effectués

**Test 1 - Synchronisation des vues** :
- Action : Ajouter une compétence "Java" dans la recherche
- Résultat attendu : Les deux vues (liste et détails) se mettent à jour simultanément
- Résultat : ✓ Les deux vues affichent les mêmes candidats

**Test 2 - Changement de stratégie** :
- Action : Chercher "Python" avec stratégie "Best" puis "Average >= 50%"
- Résultat attendu : "Best" trie par score, "Average" filtre les candidats
- Résultat : ✓ Les deux stratégies produisent des résultats différents et corrects

**Test 3 - Persistence de la shortlist** :
- Action : Ajouter 3 candidats à la shortlist, naviguer dans l'interface, revenir
- Résultat attendu : La shortlist conserve les 3 candidats (Singleton)
- Résultat : ✓ Les candidats sont toujours présents

**Test 4 - Export CSV** :
- Action : Exporter 5 candidats en CSV
- Résultat attendu : Fichier CSV valide avec en-têtes français et toutes les données
- Résultat : ✓ Fichier exploitable dans LibreOffice Calc

**Test 5 - Gestion des caractères spéciaux** :
- Action : Candidat nommé "François Müller" avec compétence "C++"
- Résultat attendu : Affichage correct des accents et caractères spéciaux
- Résultat : ✓ Encodage UTF-8 correct dans toute l'application

**Test 6 - Expérience professionnelle** :
- Action : Comparer deux candidats avec même compétence mais expériences différentes (0 ans vs 10 ans)
- Résultat attendu : Le candidat avec 10 ans d'expérience obtient un meilleur score
- Résultat : ✓ Score augmenté correctement (formule : skill × (1 + years × 0.1))

**Test 7 - Interface responsive** :
- Action : Redimensionner la fenêtre, ajouter/retirer candidats de la shortlist
- Résultat attendu : Interface s'adapte sans débordement
- Résultat : ✓ Layout JavaFX gère correctement le redimensionnement

---

## Conclusion

Le projet CV Search démontre l'application de multiples design patterns (MVC, Strategy, Singleton, Observer, Builder, Facade) pour créer une architecture maintenable et extensible. 

Les tests automatiques et manuels garantissent la fiabilité du système, tandis que la réflexion éthique met en lumière les limites inhérentes à tout système de tri automatique de CV.

Le code respecte les principes SOLID et GRASP, avec une couverture de tests satisfaisante et une intégration continue fonctionnelle sur GitLab.
