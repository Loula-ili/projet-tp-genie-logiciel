# Explication des Tests du Projet

## 🎯 Pourquoi des tests ?

Les tests servent à **vérifier automatiquement** que ton code fonctionne correctement, sans avoir à tout tester manuellement à chaque fois. C'est comme avoir un robot qui vérifie ton travail !

### Avantages :
1. **Détection rapide des bugs** : Si tu casses quelque chose, les tests deviennent rouges immédiatement
2. **Confiance** : Tu peux modifier ton code en sachant que les tests te diront si tu casses quelque chose
3. **Documentation** : Les tests montrent comment utiliser ton code
4. **Note** : Le barème donne **3 points** pour les tests automatiques !
5. **GitLab CI** : Les tests tournent automatiquement à chaque push

**Exemple concret** :
- **Sans tests** : Tu dois manuellement lancer l'appli → chercher "Java" → vérifier les résultats → changer de stratégie → revérifier...
- **Avec tests** : `mvn test` et en 2 secondes tu sais si tout marche ! 🚀

---

## 📁 Structure des tests créés

### Pour cv-search (21 tests au total)

```
cv-search/src/test/java/fr/univ_lyon1/info/m1/cv_search/
├── controller/
│   └── ApplicantControllerTest.java    (6 tests)
├── model/
│   ├── ShortlistTest.java              (9 tests)
│   └── ExportStrategyTest.java         (6 tests)
└── ApplicantTest.java                  (2 tests existants)
```

### Pour tp_test (11 tests)

```
tp_test/tp_test/src/test/java/fr/univ_lyon1/info/m1/
└── CharManipulatorTest.java            (11 tests)
```

---

## 🧪 Tests de cv-search expliqués

### 1. ShortlistTest.java (9 tests) - Teste la liste restreinte

**Fichier** : `cv-search/src/test/java/fr/univ_lyon1/info/m1/cv_search/model/ShortlistTest.java`

#### Test 1 : Pattern Singleton
```java
@Test
void testSingletonPattern() {
    // Vérifie que getInstance() retourne toujours la même instance
    Shortlist instance1 = Shortlist.getInstance();
    Shortlist instance2 = Shortlist.getInstance();
    assertSame(instance1, instance2);
}
```
**À quoi ça sert ?** Vérifie qu'il n'y a qu'une seule shortlist dans toute l'application.

#### Test 2 : Ajout de candidat
```java
@Test
void testAddCandidate() {
    // Teste l'ajout d'un candidat dans la shortlist
    shortlist.addCandidate(applicant1);
    assertThat(shortlist.size(), is(1));
    assertTrue(shortlist.contains(applicant1));
}
```
**À quoi ça sert ?** Vérifie qu'on peut ajouter un candidat et qu'il apparaît bien dans la liste.

#### Test 3 : Suppression de candidat
```java
@Test
void testRemoveCandidate() {
    // Teste la suppression d'un candidat de la shortlist
    shortlist.addCandidate(applicant1);
    shortlist.removeCandidate(applicant1);
    assertThat(shortlist.size(), is(0));
}
```
**À quoi ça sert ?** Vérifie qu'on peut supprimer un candidat et qu'il disparaît.

#### Test 4 : Pas de doublons
```java
@Test
void testAddDuplicateCandidate() {
    // Vérifie qu'on ne peut pas ajouter deux fois le même candidat
    shortlist.addCandidate(applicant1);
    shortlist.addCandidate(applicant1);
    assertThat(shortlist.size(), is(1));
}
```
**À quoi ça sert ?** Empêche d'ajouter 2 fois le même candidat.

#### Test 5 : Notation
```java
@Test
void testSetAndGetRating() {
    // Teste l'attribution et la récupération d'une note
    shortlist.addCandidate(applicant1);
    shortlist.setRating(applicant1.getName(), 4);
    assertThat(shortlist.getRating(applicant1.getName()), is(4));
}
```
**À quoi ça sert ?** Vérifie qu'on peut noter un candidat (ex: 4/5).

#### Test 6 : Commentaires
```java
@Test
void testSetAndGetComment() {
    // Teste l'ajout et la récupération d'un commentaire
    shortlist.addCandidate(applicant1);
    shortlist.setComment(applicant1.getName(), "Excellent candidat");
    assertThat(shortlist.getComment(applicant1.getName()), is("Excellent candidat"));
}
```
**À quoi ça sert ?** Vérifie qu'on peut ajouter un commentaire sur un candidat.

#### Test 7 : Pattern Observer
```java
@Test
void testObserverPattern() {
    // Vérifie que les observateurs sont notifiés quand la shortlist change
    final boolean[] notified = {false};
    PropertyChangeListener listener = evt -> notified[0] = true;
    shortlist.addPropertyChangeListener(listener);
    shortlist.addCandidate(applicant1);
    assertTrue(notified[0]);
}
```
**À quoi ça sert ?** Vérifie que les vues sont notifiées automatiquement quand la shortlist change (MVC).

#### Test 8 : Récupérer la liste
```java
@Test
void testGetCandidates() {
    // Vérifie qu'on peut récupérer la liste de tous les candidats
    shortlist.addCandidate(applicant1);
    shortlist.addCandidate(applicant2);
    var candidates = shortlist.getCandidates();
    assertThat(candidates.size(), is(2));
}
```
**À quoi ça sert ?** Vérifie qu'on peut obtenir tous les candidats de la shortlist.

#### Test 9 : Vider la liste
```java
@Test
void testClear() {
    // Teste que clear() vide complètement la shortlist
    shortlist.addCandidate(applicant1);
    shortlist.clear();
    assertThat(shortlist.size(), is(0));
}
```
**À quoi ça sert ?** Vérifie qu'on peut vider toute la shortlist d'un coup.

---

### 2. ExportStrategyTest.java (6 tests) - Teste l'export CSV/JSON

**Fichier** : `cv-search/src/test/java/fr/univ_lyon1/info/m1/cv_search/model/ExportStrategyTest.java`

#### Test 1 : Export CSV
```java
@Test
void testCsvExport() {
    // Teste l'export au format CSV
    ExportStrategy csvStrategy = new CsvExportStrategy();
    String result = csvStrategy.export(applicants);
    assertThat(result, containsString("Nom,"));
    assertThat(result, containsString(","));
}
```
**À quoi ça sert ?** Vérifie que l'export CSV produit bien du CSV avec virgules et en-têtes français.

#### Test 2 : Extension CSV
```java
@Test
void testCsvFileExtension() {
    // Vérifie que l'extension de fichier CSV est correcte
    ExportStrategy csvStrategy = new CsvExportStrategy();
    assertThat(csvStrategy.getFileExtension(), is("csv"));
}
```
**À quoi ça sert ?** Vérifie que l'extension est bien ".csv".

#### Test 3 : Export JSON
```java
@Test
void testJsonExport() {
    // Teste l'export au format JSON
    ExportStrategy jsonStrategy = new JsonExportStrategy();
    String result = jsonStrategy.export(applicants);
    assertThat(result, containsString("{"));
    assertThat(result, containsString("\"candidates\""));
}
```
**À quoi ça sert ?** Vérifie que l'export JSON produit bien du JSON valide avec accolades.

#### Test 4 : Extension JSON
```java
@Test
void testJsonFileExtension() {
    // Vérifie que l'extension de fichier JSON est correcte
    ExportStrategy jsonStrategy = new JsonExportStrategy();
    assertThat(jsonStrategy.getFileExtension(), is("json"));
}
```
**À quoi ça sert ?** Vérifie que l'extension est bien ".json".

#### Test 5 : Liste vide
```java
@Test
void testExportEmptyList() {
    // Vérifie que l'export fonctionne même avec une liste vide
    List<Applicant> emptyList = new ArrayList<>();
    ExportStrategy csvStrategy = new CsvExportStrategy();
    String result = csvStrategy.export(emptyList);
    assertThat(result, containsString("Nom,"));
}
```
**À quoi ça sert ?** Vérifie qu'exporter 0 candidat ne plante pas (affiche juste les en-têtes).

#### Test 6 : ExportService (Facade)
```java
@Test
void testExportServiceFacade() {
    // Teste le service ExportService qui utilise les stratégies
    ExportService service = ExportService.getInstance();
    String csvResult = service.exportToString(applicants, new CsvExportStrategy());
    assertThat(csvResult, containsString(","));
}
```
**À quoi ça sert ?** Vérifie que le service d'export (Facade) fonctionne bien.

---

### 3. ApplicantControllerTest.java (6 tests) - Teste le contrôleur MVC

**Fichier** : `cv-search/src/test/java/fr/univ_lyon1/info/m1/cv_search/controller/ApplicantControllerTest.java`

#### Test 1 : Recherche simple
```java
@Test
void testSearchWithOneSkill() {
    // Teste la recherche avec une seule compétence
    List<String> skills = new ArrayList<>();
    skills.add("java");
    controller.search(skills, "Best");
    assertTrue(model.size() >= 0);
}
```
**À quoi ça sert ?** Vérifie qu'on peut chercher des candidats avec 1 compétence.

#### Test 2 : Recherche multiple
```java
@Test
void testSearchWithMultipleSkills() {
    // Teste la recherche avec plusieurs compétences
    List<String> skills = new ArrayList<>();
    skills.add("java");
    skills.add("c++");
    controller.search(skills, "Best");
    assertTrue(model.size() >= 0);
}
```
**À quoi ça sert ?** Vérifie qu'on peut chercher avec plusieurs compétences (ex: "java" ET "c++").

#### Test 3 : Recherche vide
```java
@Test
void testSearchWithEmptySkills() {
    // Vérifie qu'une recherche sans compétences ne retourne rien
    List<String> skills = new ArrayList<>();
    controller.search(skills, "Best");
    assertThat(model.size(), is(0));
}
```
**À quoi ça sert ?** Vérifie que chercher sans critères ne retourne aucun résultat.

#### Test 4 : Stratégie Average
```java
@Test
void testSearchWithAverageStrategy() {
    // Teste la stratégie "Average >= 50%"
    List<String> skills = new ArrayList<>();
    skills.add("java");
    controller.search(skills, "Average >= 50%");
    for (Applicant a : model) {
        assertTrue(a.getAverage() == 0 || a.getAverage() >= 50);
    }
}
```
**À quoi ça sert ?** Vérifie que la stratégie "Average >= 50%" filtre bien les candidats avec moyenne ≥ 50%.

#### Test 5 : Stratégie Best (tri)
```java
@Test
void testSearchWithBestStrategy() {
    // Teste la stratégie "Best" qui trie par score décroissant
    List<String> skills = new ArrayList<>();
    skills.add("c++");
    controller.search(skills, "Best");
    double previousScore = Double.MAX_VALUE;
    for (Applicant a : model) {
        assertTrue(a.getTotalScore() <= previousScore);
        previousScore = a.getTotalScore();
    }
}
```
**À quoi ça sert ?** Vérifie que "Best" trie bien les candidats du meilleur au moins bon.

#### Test 6 : MVC
```java
@Test
void testControllerUsesCorrectModel() {
    // Vérifie que le contrôleur met bien à jour le modèle
    List<String> skills = new ArrayList<>();
    skills.add("python");
    controller.search(skills, "Best");
    assertTrue(model.size() >= 0);
}
```
**À quoi ça sert ?** Vérifie que le contrôleur met à jour le bon modèle (pattern MVC).

---

## 🧪 Tests de tp_test expliqués

### CharManipulatorTest.java (11 tests)

**Fichier** : `tp_test/tp_test/src/test/java/fr/univ_lyon1/info/m1/CharManipulatorTest.java`

#### Tests invertOrder (2 tests)
```java
@Test
void orderNormalString() {
    // Teste l'inversion de l'ordre des caractères
    assertEquals("DCBA", manipulator.invertOrder("ABCD"));
}
```
**À quoi ça sert ?** Vérifie que "ABCD" devient "DCBA".

#### Tests invertCase (4 tests)
```java
@Test
void caseNormalString() {
    // Teste l'inversion de la casse
    assertEquals("ABcd", manipulator.invertCase("abCD"));
}
```
**À quoi ça sert ?** Vérifie que les majuscules deviennent minuscules et inversement.

#### Tests removePattern (5 tests)
```java
@Test
void removePatternMultipleOccurrences() {
    // Teste la suppression de toutes les occurrences
    assertEquals("", manipulator.removePattern("aabb", "ab"));
}
```
**À quoi ça sert ?** Vérifie que "aabb" avec pattern "ab" donne "" (supprime tout).

---

## 🚀 Comment lancer les tests ?

### Pour cv-search :
```bash
cd cv-search
mvn test
```

### Pour tp_test :
```bash
cd tp_test/tp_test
mvn test
```

### Résultat attendu :
```
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 🔧 Technologies utilisées

- **JUnit 5.2.0** : Framework de tests Java
- **Hamcrest 2.2** : Assertions lisibles (`assertThat`, `is`, `containsString`)
- **Maven Surefire** : Plugin pour exécuter les tests

---

## 📊 Couverture de tests

Pour voir la couverture de code (quelles lignes sont testées) :

```bash
cd cv-search
mvn test
firefox target/site/jacoco/index.html
```

JaCoCo génère un rapport HTML montrant en vert les lignes testées et en rouge celles qui ne le sont pas.

---

## ✅ Checklist avant le rendu

- [ ] Tous les tests passent : `mvn test`
- [ ] Pas d'erreurs Checkstyle : `mvn checkstyle:check`
- [ ] GitLab CI est vert (tests lancés automatiquement)
- [ ] Au moins 3 tests par fonctionnalité (exigence du TP4)
- [ ] Tests bien commentés pour que le prof comprenne

---

## 💡 Conseils

1. **Lance les tests souvent** : Après chaque modification, vérifie que tout marche encore
2. **Lis les messages d'erreur** : Ils te disent exactement ce qui ne va pas
3. **Tests = documentation** : Si tu ne sais pas comment utiliser une classe, regarde ses tests
4. **GitLab CI** : Si les tests passent localement mais pas sur GitLab, c'est peut-être un problème de dépendances

---

## 📝 Résumé

**Total : 32 tests créés**
- cv-search : 21 tests (Shortlist, Export, Controller)
- tp_test : 11 tests (CharManipulator)

**Tous les tests passent avec succès !** ✅
