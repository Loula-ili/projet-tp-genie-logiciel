<!-- LTEX: language=fr -->
Ce fichier contiendra les nouvelles du cours. Un mail sera envoyé quand le fichier est mis à jour.

# 5/09/2025: correction dans .gitlab-ci.yml

Un reste de l'an dernier s'était glissé dans le fichier `.gitlab-ci.yml`: le chemin vers `setup-mvn-proxy.sh` devait être `cv-search` et non `microblog`. C'est corrigé, vous pouvez récupérer la modification chez vous avec :

```
git remote add moy https://forge.univ-lyon1.fr/matthieu.moy/mif01.git
git pull --no-rebase moy main
git push
```

Il suffit qu'un des membres du binôme fasse cette manipulation.
