<!-- LTeX: language=fr -->
<!-- dates :
grep '^## .. ' README.md | sed 's/[^(]*(//; s/,.*//' | while read line; do echo "$line"; echo "$line" | sed 's@[^ ]* \(.*\)/\(.*\)/\(.*\)@\2/\1/\3@' | date -f -; echo; done
-->
# Gestion de Projet et Génie Logiciel, M1, département informatique, Lyon 1, 2025-2026

## Dates importantes

<!-- [Cf. ADE](http://adelb.univ-lyon1.fr/direct/index.jsp?projectId=4&days=0,1,2,3,4&resources=33140&weeks=4,5). -->

* Emploi du temps : Cf. ADE.
  Utilisez la ressource "M1 informatique" **plus les groupes de TD** pour voir l'emploi du temps. Contrairement à la licence vous n'avez pas d'emploi du temps personnalisé il faut regarder celui de votre groupe.

* Rendu du TP noté : vous devez avoir rempli correctement (URL clonable, projet non-public, binôme formé) le champ URL de TOMUSS avant le 12 septembre 2025. Il y aura un malus sur la note si ce n'est pas fait sans une raison valable. Le rendu final est le mercredi 10/12/2025 23h59. Voir
  [projet-note.md](projet-note.md) pour un récapitulatif des consignes.

* Examen : mardi 6/1/2026, 14h00, voir amphi sur TOMUSS. 1h30 (sauf tiers-temps) Consignes : Seules 5
  feuilles A4 recto verso (donc 10 pages au total) **manuscrites** sont autorisées à
  l’examen. Les photocopies ou impressions sont interdites.
  Les annales de l'examen sont dans le répertoire [exam/](https://matthieu-moy.fr/cours/mif01/exam/)
  et [sur l'ancienne page du
  cours](http://www.tabard.fr/cours/2017/mif01/). Prévoyez un stylo
  bleu foncé ou noir et un blanc correcteur.

* Session 2 : mêmes consignes que pour la session 1, sauf la durée qui sera 1h plus tiers-temps.

Barème : 40% examen / 10% QCM en amphi / 50% TP (une note pour le rapport, une note pour le code). Les absences sont remplacées par la note 0/20. La session 2 remplace l'examen **ou** le QCM (la note retenue est le maximum des moyennes en remplaçant l'examen ou le QCM par la session 2).

## Enseignants et contacts

* [Matthieu Moy](https://matthieu-moy.fr/) (responsable du cours)
* [Lionel Medini](https://perso.liris.cnrs.fr/lionel.medini/)
* [Ryan Horache](https://liris.cnrs.fr/page-membre/ryan-horache)
* [Abdelhakim Boudraa](https://www.linkedin.com/in/hakimbdr/)

## Enseignement présentiel, vidéos et messagerie instantanée

En complément des séances en présentiel, les étudiants qui le souhaitent peuvent utiliser les vidéos préparées en 2020 pour les cours magistraux (disponibles sur la plateforme My Video Lyon 1, et sur la [playlist youtube](https://www.youtube.com/playlist?list=PL6-YbcqXawf5ED3NHDZYejWJaAschnrO0)). Attention, le contenu du cours a un peu évolué depuis, regarder les vidéos ne dispense pas de venir en CM !

Vous pouvez interagir avec les enseignants via le système d'issues de GitLab (qui n'est pas vraiment prévu pour cela, mais peut être détourné pour en faire un petit forum) : https://forge.univ-lyon1.fr/matthieu.moy/mif01/-/issues. Pour poser une question, créez une nouvelle issue (une issue par question). Il y a une entrée dédiée « cherche binôme » si vous êtes à la recherche d'un binôme. Pour être informé de l'activité (nouveaux commentaires, etc), réglez les notifications sur « watch » en cliquant sur la cloche sur la page d'accueil du projet :

![Notifications sur Gitlab](fig/gitlab-watch.png)

## Nouvelles du cours

Les informations de dernière minute sont disponibles ici :
[NEWS.md](NEWS.md). Les informations importantes seront envoyées par
email, ce fichier en contient une copie.

## CM 1 (mardi 2/9/2025, 14h) : Introduction

### Intro du cours

* Vidéo : <a target="_blank" title="Introduction du cours" href="https://myvideo.univ-lyon1.fr/permalink/v126648321336prac6d7/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f63c7665cu1ddhhdumjvjfkrdr/thumb_link.png" alt="Introduction du cours"/></a>

* Transparents : [00-intro-cours-slides.pdf](https://matthieu-moy.fr/cours/mif01/00-intro-cours-slides.pdf) (version imprimable : [00-intro-cours-handout.pdf](https://matthieu-moy.fr/cours/mif01/00-intro-cours-handout.pdf))

### Introduction au génie logiciel

* Vidéo : <a target="_blank" title="Introduction au génie logiciel" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fbadfanih3/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3b1081xmi26ty70pk23mhvs/thumb_link.png" alt="Introduction au génie logiciel"/></a>

* Transparents : [01-introduction-slides.pdf](https://matthieu-moy.fr/cours/mif01/01-introduction-slides.pdf) (version imprimable : [01-introduction-handout.pdf](https://matthieu-moy.fr/cours/mif01/01-introduction-handout.pdf))
  
## TP 1 (mardi 2/9/2025, 15h45) : Mise en route Java

* Énoncé : [TP1-java/README.md](TP1-java/README.md)

* Salles :
    - Matthieu Moy circule entre les salles
    - Nautibus TD001, Nautibus TD005, Nautibus TP101                 : Abdelhakim Boudraa
    - Nautibus TP103, Nautibus TP105, Nautibus TP109                 : Ryan Horache
    - Nautibus TP114, Nautibus TP115, Nautibus TD116, Nautibus TP123 : Lionel Medini


## CM 2 (vendredi 5/9/2025, 9h45) : Outillage : Maven, la forge Gitlab, intégration continue, coding style ... 

### Maven, Forge, Intégration Continue

* Vidéo : <a target="_blank" title="Maven, Forge, Intégration Continue" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fb8hrl6hos/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3a7f0fkr7ooladan4vxrmp1/thumb_link.png" alt="Maven, Forge, Intégration Continue"/></a>

* Transparents : [02-cm-maven-forge-ic-slides.pdf](https://matthieu-moy.fr/cours/mif01/02-cm-maven-forge-ic-slides.pdf)
  (Version imprimable : [02-cm-maven-forge-ic-handout.pdf](https://matthieu-moy.fr/cours/mif01/02-cm-maven-forge-ic-handout.pdf))

## TP 2 (vendredi 5/9/2025, 11h30) : Outillage

* Énoncé : [TP2-outils/README.md](TP2-outils/README.md)

* Salles :
    - Matthieu Moy circule entre les salles
    - Nautibus TD001, Nautibus TD005, Nautibus TP103                 : Abdelhakim Boudraa
    - Nautibus TP105, Nautibus TP107, Nautibus TP108                 : Ryan Horache
    - Nautibus TP109, Nautibus TD116, Nautibus TD124, Nautibus TD126 : Lionel Medini

## TP 3 (mardi 9/9/2025, 15h45) : Outillage (suite)

* Énoncé : continuer [TP2-outils/README.md](TP2-outils/README.md)

* Salles :
    - Matthieu Moy circule entre les salles
    - Nautibus TP105, Nautibus TP107, Nautibus TP108 : Abdelhakim Boudraa
    - Nautibus TP109, Nautibus TP110                 : Ryan Horache
    - Nautibus TD116, Nautibus TD124, Nautibus TD126 : Lionel Medini

## CM 3 (mardi 9/9/2025, 17h30, par Lionel Medini) : Coding style, Design patterns (début)

### Coding style

* Vidéo : <a target="_blank" title="Coding Style" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fbdwej3119/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3b82e5qhb6vdjbfdziizhq7/thumb_link.png" alt="Coding Style"/></a>

* Transparents : [03-coding-style-slides.pdf](https://matthieu-moy.fr/cours/mif01/03-coding-style-slides.pdf)
  (version imprimable : [03-coding-style-handout.pdf](https://matthieu-moy.fr/cours/mif01/03-coding-style-handout.pdf))

### Design patterns

* Slides : [CM-patterns.pdf](https://perso.liris.cnrs.fr/lionel.medini/enseignement/M1IF01/CM-patterns.pdf)

* Vidéo du mardi 8/9/2020 sur Youtube : [https://youtu.be/oal57dd73nY](https://youtu.be/oal57dd73nY)

* Vidéo du mercredi 9/9/2020 sur Youtube : [https://youtu.be/9oV0xDSrXes](https://youtu.be/9oV0xDSrXes)

## CM 4 (vendredi 12/9/2025, 11h30, par Lionel Medini) : Design patterns (suite)

## TD 1 (mardi 16/9/2025, 14h) : Design patterns

* [TD1-design-patterns](https://matthieu-moy.fr/cours/mif01/TD1-patterns/TD-design-patterns.html)

* Salles :
  - Groupe A, Nautibus TD126 : Matthieu Moy
  - Groupe B, Nautibus TD005 : Abdelhakim Boudraa
  - Groupe C, Nautibus TD124 : Ryan Horache
  - Groupe D, Nautibus TD116 : Lionel Medini

## TP 4 (mardi 16/9/2025, 15h45) : Design patterns & refactoring

* [TP3-patterns/README.md](TP3-patterns/README.md) et de l'aide avec [MVC](TP3-patterns/mvc.md)

* Salles :
    - Nautibus TP103, Nautibus TP105, Nautibus TP107 : Abdelhakim Boudraa
    - Nautibus TP108, Nautibus TP109 : Ryan Horache
    - Nautibus TD116, Nautibus TD124, Nautibus TD126 : Lionel Medini

## CM 5 (vendredi 19/9/2025, 9h45) : Métaprogrammation, spécifications et cas d'utilisation

### Métaprogrammation

* Vidéo : <a target="_blank" title="Métaprogrammation" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc5hrmy3g1/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e59e89b1kkvlmtrymwi6ngkv/thumb_link.png" alt="Métaprogrammation"/></a>

* Transparents [04-metaprogramming-slides.pdf](https://matthieu-moy.fr/cours/mif01/04-metaprogramming-slides.pdf)
  (version imprimable : [04-metaprogramming-handout.pdf](https://matthieu-moy.fr/cours/mif01/04-metaprogramming-handout.pdf))

### Spécifications

* Vidéo : <a target="_blank" title="Spécifications et cas d'utilisations" href="https://myvideo.univ-lyon1.fr/permalink/v1266483213e2log0vyw/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64046e89yvikpjhr6bdcklhii/thumb_link.png" alt="Spécifications et cas d'utilisations"/></a>

* [05-UML-CU.pdf](https://matthieu-moy.fr/cours/mif01/05-UML-CU.pdf)


## TP 5 (vendredi 19/9/2025, 11h30): Design patterns & refactoring, suite

* Continuer avec : [TP3-patterns/README.md](TP3-patterns/README.md) et de l'aide avec [MVC](TP3-patterns/mvc.md)

* Salles :
    - Nautibus TP103
    - Nautibus TP105
    - Nautibus TP107
    - Nautibus TP108
    - Nautibus TP109
    - Nautibus TD116
    - Nautibus TD124
    - Nautibus TD126

## CM 6 (mardi 7/10/2025, 14h) : Test

* Transparents de Sandrine Gouraud, lien disponible sur TOMUSS.

## TP 6 (mardi 7/10/2025, 15h45) : Tests

* Énoncé : [TP4-tests/README.md](TP4-tests/README.md)

* Salles :
    - Nautibus TP103
    - Nautibus TP105
    - Nautibus TP107
    - Nautibus TP108
    - Nautibus TP109
    - Nautibus TP114
    - Nautibus TD116
    - Nautibus TD124

## CM 7 (mardi 14/10/2025, 14h) : introduction à l'agilité

### Introduction à l'Agilité

* Vidéo : <a target="_blank" title="Introduction à l'agilité" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc19hggacu/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e59e5038qrz03jom0i42z6ey/thumb_link.png" alt="Introduction à l'agilité"/></a>

* Transparents : [07-agilite-slides.pdf](https://matthieu-moy.fr/cours/mif01/07-agilite-slides.pdf)
  (version imprimable : [07-agilite-handout.pdf](https://matthieu-moy.fr/cours/mif01/07-agilite-handout.pdf))

## TD 2 (mardi 14/10/2025, 15h45) : Coding styles, spécifications et agilité

* [TD2-uc-style/TD-usecase-et-style.pdf](https://matthieu-moy.fr/cours/mif01/TD2-uc-style/TD-usecase-et-style.pdf)
  (corrigé : [TD2-uc-style/TD-usecase-et-style-corrige.pdf](https://matthieu-moy.fr/cours/mif01/TD2-uc-style/TD-usecase-et-style-corrige.pdf))

<!-- * Salles :
  - Groupe A : Nautibus TD 1, 15h45 : Matthieu Moy
  - Groupe B : Nautibus TD 2, 15h45 : Mathieu Hilaire
  - Groupe C : Nautibus TD 3, 15h45 : Lionel Medini
  - Groupe D : Nautibus TD 1, 11h30 : Matthieu Moy
  -->

## CM 8 (mardi 4/11/2025, 14h) : Agilité (suite)

### Fin du cours sur l'agilité

* Vidéo : <a target="_blank" title="Conclusion sur l'agilité" href="https://myvideo.univ-lyon1.fr/permalink/v1266483214d42el9dn3/iframe/#start=2"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64407b3aqquizvskjxrimpz6n/thumb_link.png" alt="Conclusion sur l'agilité"/></a>

* Transparents : [07-agilite-slides.pdf](https://matthieu-moy.fr/cours/mif01/07-agilite-slides.pdf)
  (version imprimable : [07-agilite-handout.pdf](https://matthieu-moy.fr/cours/mif01/07-agilite-handout.pdf))

### Agilité et passage à l'échelle : la méthode Spotify (transparents de Levent Acar)

* Vidéo : <a target="_blank" title="La méthode spotify" href="https://myvideo.univ-lyon1.fr/permalink/v1266483214d7t0dnl7u/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64408f14upw2wztzw9kmg6j85/thumb_link.png" alt="La méthode spotify"/></a>

* Transparents : [Présentation_spotify_v2.pdf](https://matthieu-moy.fr/cours/mif01/Présentation_spotify_v2.pdf)

## TP 7 (mardi 4/11/2025, 15h45) : Tests (suite)

* Sujet : continuer avec [TP4-tests/README.md](TP4-tests/README.md)

* Salles :
    - Nautibus TP103
    - Nautibus TP105
    - Nautibus TP107
    - Nautibus TP108
    - Nautibus TP109
    - Nautibus TD116
    - Nautibus TD124
    - Nautibus TD126

## CM 9 (mardi 2/12/2025, 14h) : Éthique

### Éthique

* Vidéo : <a target="_blank" title="Éthique et numérique" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc91hpkb0g/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e878599ekgr4ire6ac5wv8q0/thumb_link.png" alt="Éthique et numérique"/></a>

* Transparents : [08-ethics-slides.pdf](https://matthieu-moy.fr/cours/mif01/08-ethics-slides.pdf)
  (version imprimable : [08-ethics-handout.pdf](https://matthieu-moy.fr/cours/mif01/08-ethics-handout.pdf))

## TP 8 (mardi 2/12/2025, 15h45) : fin du projet

Cette dernière séance devrait vous permettre de boucler votre projet, qui est à rendre quelques jours plus tard.

* Salles :
    - Nautibus TP103
    - Nautibus TP105
    - Nautibus TP107
    - Nautibus TP108
    - Nautibus TP109
    - Nautibus TD116
    - Nautibus TD124
    - Nautibus TD126
  
## CM 10 (mardi 3/12/2025, 14h) : Ethique, suite.
