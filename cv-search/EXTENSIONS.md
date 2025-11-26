# Extensions et Améliorations - CV Search Application

## Fonctionnalités ajoutées pour la partie "Extensions" (3 points)

### 1. Système de Shortlist (Liste de Favoris) - **Pattern : Singleton + Observer**

**Classe** : `Shortlist.java`

#### Fonctionnalités :
- ✅ Gestion d'une liste de candidats favoris
- ✅ Système de notation par étoiles (1-5 étoiles) pour chaque candidat
- ✅ Ajout de commentaires personnalisés pour chaque candidat
- ✅ Notification automatique des changements (Pattern Observer)
- ✅ Instance unique partagée dans toute l'application (Pattern Singleton)

#### Patterns appliqués :
- **Singleton** : Une seule instance de shortlist partagée dans toute l'application
- **Observer** : Utilisation de `PropertyChangeSupport` pour notifier les vues des modifications
- **GRASP - Information Expert** : La shortlist gère ses propres candidats et leurs métadonnées

#### Code illustratif :
```java
Shortlist shortlist = Shortlist.getInstance();
shortlist.addCandidate(applicant);
shortlist.setRating(applicant.getName(), 5);
shortlist.setComment(applicant.getName(), "Excellent profil");
```

---

### 2. Système d'Export Multi-Format - **Pattern : Strategy + Facade + Singleton**

**Classes** : 
- `ExportStrategy.java` (interface)
- `CsvExportStrategy.java` (implémentation concrète)
- `JsonExportStrategy.java` (implémentation concrète)
- `ExportService.java` (Facade + Singleton)

#### Fonctionnalités :
- ✅ Export des résultats de recherche en CSV
- ✅ Export des résultats de recherche en JSON
- ✅ Export de la shortlist en CSV ou JSON
- ✅ Architecture extensible pour ajouter d'autres formats (XML, PDF, etc.)

#### Patterns appliqués :
- **Strategy** : Différentes stratégies d'export interchangeables
- **Facade** : `ExportService` simplifie l'utilisation des stratégies d'export
- **Singleton** : Instance unique du service d'export
- **Open/Closed Principle (SOLID)** : Ouvert à l'extension (nouveaux formats), fermé à la modification

#### Code illustratif :
```java
ExportService.getInstance().exportToFile(
    applicants, 
    file, 
    new CsvExportStrategy()
);
```

---

### 3. Interface Utilisateur Moderne et Professionnelle

**Fichier CSS** : `style.css`
**Classe Vue** : `ShortlistView.java`

#### Améliorations de l'interface :
- ✅ **Design moderne** avec CSS personnalisé (gradients, ombres, animations)
- ✅ **Vue détaillée des candidats** avec cartes stylisées
- ✅ **Boutons contextuels** avec couleurs sémantiques (succès, danger, etc.)
- ✅ **Fenêtre dédiée à la shortlist** avec gestion complète
- ✅ **Affichage enrichi** : scores, expériences, compétences formatées
- ✅ **Interactions intuitives** : notation par étoiles cliquables, commentaires

#### Patterns appliqués :
- **MVC** : Séparation stricte entre vue, modèle et contrôleur
- **Observer** : Les vues s'actualisent automatiquement lors des changements du modèle
- **GRASP - Low Coupling** : Les vues dépendent uniquement des interfaces du modèle

#### Fonctionnalités visuelles :
```css
/* Boutons modernes avec gradients */
.button {
    -fx-background-color: linear-gradient(to bottom, #4a90e2, #357abd);
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);
}

/* Cartes de candidats avec ombres */
.result-card {
    -fx-background-color: white;
    -fx-border-radius: 6px;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 4, 0, 0, 2);
}
```

---

### 4. Fonctionnalités Utilisateur Avancées

**Classe modifiée** : `JfxView.java`

#### Nouvelles fonctionnalités :
- ✅ **Bouton "Shortlist"** dans la toolbar avec compteur en temps réel
- ✅ **Boutons d'export** CSV et JSON directement dans l'interface
- ✅ **Ajout rapide à la shortlist** depuis les résultats de recherche
- ✅ **Indicateur visuel** montrant si un candidat est déjà dans la shortlist
- ✅ **Affichage enrichi** : expérience totale, nombre d'entreprises, scores pondérés
- ✅ **Vue détaillée/compacte** avec checkbox pour basculer

#### Améliorations UX :
```java
// Bouton dynamique selon l'état
Button addBtn = new Button(
    shortlist.contains(a) ? "✓ Dans shortlist" : "+ Shortlist"
);

// Mise à jour automatique du compteur
shortlist.addPropertyChangeListener(evt -> 
    shortlistBtn.setText("📋 Shortlist (" + shortlist.size() + ")")
);
```

---

## Récapitulatif des Patterns Utilisés

### Patterns de Création :
1. **Singleton** - Shortlist, ExportService, StrategyFactory
2. **Builder** - ApplicantBuilder, ApplicantListBuilder (déjà présents)

### Patterns de Structure :
3. **Facade** - ExportService simplifie l'utilisation des stratégies d'export

### Patterns Comportementaux :
4. **Strategy** - MatchingStrategy (recherche), ExportStrategy (export)
5. **Observer** - PropertyChangeSupport dans Shortlist et ApplicantList

### Principes SOLID :
6. **Single Responsibility** - Chaque classe a une responsabilité unique
7. **Open/Closed** - Extensible sans modification (nouveaux formats d'export)
8. **Liskov Substitution** - Les stratégies sont interchangeables
9. **Interface Segregation** - Interfaces petites et spécifiques
10. **Dependency Inversion** - Dépendance aux abstractions (interfaces)

### Principes GRASP :
11. **Information Expert** - Shortlist gère ses candidats
12. **Low Coupling** - Couplage faible entre vue, modèle et contrôleur
13. **High Cohesion** - Chaque classe a un objectif clair et cohérent
14. **Controller** - ApplicantController orchestre les actions
15. **Creator** - Factory patterns pour créer les objets

---

## Qualité du Code

### Points forts :
- ✅ **Javadoc complète** sur toutes les classes et méthodes publiques
- ✅ **Nomenclature claire** et cohérente
- ✅ **Packages bien organisés** : model, view, controller
- ✅ **Pas de duplication de code** grâce aux patterns
- ✅ **Code extensible** et maintenable
- ✅ **Séparation des responsabilités** stricte

### Compilation :
```bash
mvn clean compile
# [INFO] BUILD SUCCESS
```

---

## Utilisation

### Lancer l'application :
```bash
cd cv-search
mvn javafx:run
```

### Tester les fonctionnalités :
1. **Rechercher des candidats** : Ajouter des compétences et cliquer sur "Search"
2. **Ajouter à la shortlist** : Cliquer sur "+ Shortlist" sur un candidat
3. **Voir la shortlist** : Cliquer sur le bouton "📋 Shortlist" dans la toolbar
4. **Noter un candidat** : Cliquer sur les étoiles dans la shortlist
5. **Ajouter un commentaire** : Taper dans la zone de texte sous chaque candidat
6. **Exporter** : Cliquer sur "Export CSV" ou "Export JSON"

---

## Conclusion

Cette extension apporte **trois fonctionnalités majeures** professionnelles :

1. **Gestion de shortlist** avec notation et commentaires
2. **Export multi-format** extensible et bien architecturé
3. **Interface moderne** avec CSS professionnel et UX améliorée

Tout en respectant et en appliquant de nombreux **design patterns** et **principes SOLID/GRASP**, garantissant un **code propre, maintenable et extensible**.
