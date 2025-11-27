<!-- LTEX: language=fr -->
Ce fichier contiendra les nouvelles du cours. Un mail sera envoyé quand le fichier est mis à jour.

# 22/09/2025: QCM noté et salles pour le TD du 14 octobre

Deux informations pour la demi-journée du 14 octobre prochain :

* Nous ferons un QCM noté en début d'amphi. Le programme est sur la page du cours. Soyez à l'heure, les retardataires auront moins de temps pour composer.

* Pour le TD, les affectations de salles sont les mêmes que pour le premier TD, vous avez les salles sur TOMUSS (la notion de « groupe de TD » n'est utilisée dans cette UE que pour vous répartir équitablement entre salles de TD).

# 5/09/2025: correction dans .gitlab-ci.yml

Un reste de l'an dernier s'était glissé dans le fichier `.gitlab-ci.yml`: le chemin vers `setup-mvn-proxy.sh` devait être `cv-search` et non `microblog`. C'est corrigé, vous pouvez récupérer la modification chez vous avec :

```
git remote add moy https://forge.univ-lyon1.fr/matthieu.moy/mif01.git
git pull --no-rebase moy main
git push
```

Il suffit qu'un des membres du binôme fasse cette manipulation.
