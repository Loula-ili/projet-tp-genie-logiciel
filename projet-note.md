<!-- LTeX: language=fr -->
# Rendu du Mini Projet "CV Search"

**Votre travail devra être rendu sous forme d’un projet déposé sur la Forge Lyon 1, les dates limites sont dans le fichier README.md de ce projet.**

<!--
Rendu : Champ « URL Projet » rempli et projet disponible avant la deadline [BAREME: Malus si retard]
Rendu : Malus éventuel pour non-respect des consignes (en plus de la note automatique) comme projet non-clonable, URL mal remplie, etc. [BAREME: Malus jusqu'à -5]

-->

Le mini-projet noté est le fil rouge de tous les TPs. Vous commencez à
travailler sur la base de code au [TP1](TP1-java/), et vous ajoutez
des fonctionnalités tout en améliorant la qualité du code dans la
suite.

Les consignes ci-dessous sont à respecter **impérativement** pour le rendu.

## Fraude

On vous demande un travail original et non une copie. Vous n'êtes pas autorisés
à fournir votre code à d'autres équipes ni à utiliser le code d'autres équipes
(y compris le code écrit les années passées, y compris par vous-même si vous
êtes redoublant). Vous n'êtes pas non plus autorisés à utiliser le code ni le texte généré
par des IA génératives (ChatGPT, Copilot, etc.). Si vous êtes en difficulté,
demandez de l'aide à vos enseignants, mais n'utilisez pas vos difficultés comme
excuse pour frauder.

## Rapport

Votre rendu inclura un rapport, au format PDF (consignes pour le rendu
ci-dessous), en français, qui doit comprendre obligatoirement, en plus d'une
présentation globale du projet (rapide : ne répétez pas l'énoncé), les sections
suivantes :

### Rapport : section « design patterns » [BAREME: 6]

  Cette section donne une motivation des choix
  d’architecture (et des patterns choisis), et leur explication en s’aidant de
  diagrammes appropriés et adaptés au degré de précision et au type
  d’explication. Donc des diagrammes de classe, mais pas que cela, et pas de
  plats de spaghettis générés automatiquement représentant tout le code.

  Inutile de répéter les généralités sur les patterns bien connus, documentez
  les spécificités de votre projet et la motivation de vos choix *dans le
  contexte du projet*. Inutile en particulier de répéter les principes et
  avantages généraux du MVC.
  
### Rapport : section « éthique » [BAREME: 4]
  
  Vous expliquerez les choix que vous avez
  fait en termes de stratégie de sélection de CV : comment vous
  assurez-vous que vous choisissez les « bons » CV, et que vous ne ratez
  pas de candidat intéressant (en particulier, pourrait-on accuser
  votre algorithme de discrimination ?) ? L'algorithme peut-il être
  mis en défaut dans des cas où un humain aurait fait un meilleur
  travail ? Si oui, donnez des exemples et justifiez.
  
  Idéalement, appuyez vos réflexions et affirmations sur des cas
  concrets en citant vos sources. L'outil de sélection de CV que nous
  avons écrit est un jouet, mais les outils de gestion de CV assistés
  par ordinateurs sont une réalité, en particulier dans les grandes
  entreprises et les cabinets de recrutement (pensez à Google qui
  reçoit 75000 CV par semaine ...). L'exercice n'est donc pas si
  artificiel qu'il en a l'air.
  

### Rapport : section « tests » [BAREME: 1]

  Vous décrirez les tests manuels que vous avez
  réalisés. Vos tests automatiques (le code Java des tests et les commentaires
  associés) devraient se suffire à eux-mêmes, il n'est pas nécessaire de les
  re-documenter dans le rapport (sauf si vous avez fait des choses
  extraordinaires qui méritent une documentation externe).

### Autres critères d'évaluation du rapport

- Rapport : qualité globale des explications [BAREME: 3]
- Rapport : malus éventuel pour mauvaise forme et orthographe [BAREME: malus jusqu'à -5]

## Qualité du code

### Style

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`, qui doit lancer checkstyle).

Bien sûr, respecter le style doit se faire en corrigeant (si besoin)
votre code, mais *pas* en modifiant le fichier de configuration de
checkstyle.

### Design-pattern

Assurez-vous d'avoir appliqué toutes les consignes du
[TP 3](TP3-patterns/).

### Tests et intégration continue

Vérifiez que l'intégration continue mise en place au
[TP 2](TP2-outils/) fonctionne toujours.

Les tests automatisés tels que décrits au [TP4](TP4-tests/) doivent
être lancés automatiquement par `mvn tests`, et doivent tous passer
avec succès.

### Portabilité

Clonez, compilez et exécutez votre code **sur une machine vierge**
(c'est-à-dire sur laquelle vous n'avez installé aucune dépendance, ni
configuré le compte utilisateur de façon particulière). Une grande
partie du barème est liée à l'exécution de votre travail. Il est
important que nous arrivions à l'exécuter **directement**. "Ça marche
chez moi" n'est pas une excuse et une démo *a posteriori* ne permet
pas de remonter une note de TP.

<!--
- Rendu : le projet est compilable (mvn compile) [BAREME: 1]
- Rendu : le programme se lance correctement (mvn exec:java) [BAREME: 1]
-->

## Projet Forge et TOMUSS

Les projets seront rendus en binômes. La date limite est indiquée sur
la page d'accueil du cours.

**Ajoutez les utilisateurs @matthieu.moy, @LIONEL.MEDINI et
@ryan.horache avec le niveau de privilège
"reporter" (ou plus, mais *pas* "guest") à votre projet sur la forge**

Dans la feuille TOMUSS de l'UE « Gestion de projet et Génie Logiciel », indiquez l'URL de votre projet dans la case `URL_Projet` (l'URL doit ressembler à
`https://forge.univ-lyon1.fr/<login>/mif01`). Il faut impérativement
**que la commande `git clone <url>` fonctionne, et que cette commande récupère la dernière version de votre projet.**
Autrement dit la branche par défaut doit contenir la dernière version du projet si vous avez
plusieurs branches. Tous les membres du binôme doivent entrer **exactement** la même URL (au caractère près).

Pensez à remplir dès à présent TOMUSS indiquant votre URL.
Le dépôt ne sera relevé qu’après la date de rendu.

Votre dépôt sur la Forge devra contenir :

- un répertoire `cv-search/` (le répertoire doit impérativement avoir exactement ce nom)
- un fichier maven (`cv-search/pom.xml`) pour le build du projet
- les sources (fichiers Java)
- le rapport en PDF (6 pages maximum, format libre. La limitation de pages est indicative, si vous avez vraiment besoin de plus vous pouvez dépasser un peu, mais restez raisonnables et concis), dans un fichier qui doit impérativement s'appeler `rapport.pdf` à la racine du dépôt Git.

<!-- 
Rendu : présence des bons fichiers (rapport.pdf, cv-search/pom.xml, .gitlab-ci.yml) [BAREME: malus de -1 par fichier]
-->

Vous pouvez laisser les autres fichiers et répertoires.

## Barème indicatif (le barème sera ramené à 20), à utiliser comme checklist pour vérifier que vous avez tout fait

<!--
git grep -h '\[BAREME: [^]]*\]' | sed 's/^[#-]*\s*//' | sort | sed 's/^\(.*\)\[BAREME: \([^]]*\)\]\(.*\)$/| \2 | \1\3 |/'
-->

| Points | Critère |
|--------|---------|
| Malus si retard | Rendu : Champ « URL Projet » rempli et projet disponible avant la deadline  |
| Malus jusqu'à -5 | Rendu : Malus éventuel pour non-respect des consignes (en plus de la note automatique) comme projet non-clonable, URL mal remplie, etc.  |
| malus de -1 par fichier | Rendu : présence des bons fichiers (rapport.pdf, cv-search/pom.xml, .gitlab-ci.yml)  |
| 1 | Rendu : le programme se lance correctement (mvn exec:java)  |
| 1 | Rendu : le projet est compilable (mvn compile)  |
| 2 | Outillage : absence d'erreur avec checkstyle  |
| 1 | Outillage : au moins une issue fermée dans GitLab  |
| 1 | Outillage : au moins une merge-request fermée sur GitLab  |
| 1 | Outillage : fichiers ignorés avec .gitignore  |
| Malus -1 | Outillage : Pas de fichiers trackés par git (git add) dans le répertoire target/ |
| 1 | Outillage : intégration continue OK sur GitLab  |
| 1 | Outillage : les tests passent (mvn test -Dcheckstyle.skip)  |
| 3 | Outillage : tests unitaires (automatiques) du projet  |
| 1 | Fonctionnalité : affichage de la liste avec affichage d'autre chose que le nom du candidat  |
| 1 | Fonctionnalité : au moins une autre stratégie de sélection de CV  |
| 3 | Fonctionnalité : autres extensions, meilleure interface, etc.  |
| 1 | Fonctionnalité : modification de l'interface (bouton pour supprimer une compétence de la liste)  |
| 1 | Fonctionnalité : Note pour chaque candidat  |
| 2 | Fonctionnalité : prise en compte de l'expérience professionnelle  |
| 1 | Fonctionnalité : Stratégie de sélection « moyenne >= 50% »  |
| 1 | Fonctionnalité : Stratégie de sélection « tout >= 60% »  |
| 1 | Fonctionnalité : tri de la liste des candidats  |
| 5 | Pattern : au moins 3 autres patterns (création, structure, SOLID, ...) que MVC ou GRASP  |
| 1 | Pattern : flexibilité du modèle MVC : deux vues correctement synchronisées  |
| 3 | Pattern : modèle-Vue-Contrôleur  |
| 1 | Pattern : principes GRASP bien respectés  |
| 1 | Pattern : Qualité et structure globale du code, utilisation de packages  |
| 6 | Rapport : section « design patterns »  |
| 4 | Rapport : section « éthique »  |
| 1 | Rapport : section « tests »  |
| 3 | Rapport : qualité globale des explications  |
| malus jusqu'à -5 | Rapport : malus éventuel pour mauvaise forme et orthographe  |
