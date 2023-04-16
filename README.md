# Mufa Park
## Outils utilisés
J'aimerai devenir un déveleppeur Full-Stack et pour cela, j'ai entrepris la création d'un serveur Web en utilisant le framework Spring en Java
J'ai utilisé Pinegrow pour la conception de l'interface utilisateur du site Web.
Enfin, j'ai opté pour H2 comme système de gestion de base de données SQL.

## Contraintes
Les contraintes mises en places sont :
1. Suivre une architecture stricte Modèle-Vue-Contrôleur (MVC)
2. Assurer le suivi de session avec enregistrement en base de données
3. Utiliser le Design Pattern DAO pour le modèle
4. Implémenter une API RESTful

## Résultat
Lorsque l'utilisateur se connecte au site, il arrive sur la page de connexion où il doit entrer son adresse e-mail et son mot de passe.
Cette page est représentée ci-dessous :
<center>
  <img src="./assets_README/IMG/accueil_connexion.png" alt="Image connexion">
</center>
Une fois connecté, l'utilisateur est dirigé vers la page où il peut choisir l'activité qu'il souhaite faire, comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/accueil_site.png" alt="Accueil">
</center>
En cliquant sur le bouton "Réserver", l'utilisateur est redirigé vers une page où il peut choisir la date et le nombre de participants pour l'activité choisie, comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/prise_de_rdv.png" alt="Prise de rdv">
</center>
Après avoir confirmé sa réservation, celle-ci est sauvegardée dans une session et dans une table temporaire en base de données. L'utilisateur peut consulter son panier en cliquant sur l'icône correspondante dans la page d'accueil, comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/panier.png" alt="Panier">
</center>
Lorsque la réservation est confirmée, l'utilisateur est redirigé vers la page d'accueil, où il peut consulter ses réservations en cliquant sur le bouton "Mes réservations", comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/show_mesRDV.png" alt="Panier">
</center>
Sur cette page, il peut supprimer ou modifier ses réservations. En cliquant sur le bouton "Supprimer", toutes les réservations sont supprimées.
En cliquant sur le bouton "Modifier", l'utilisateur est redirigé vers une autre page où il peut modifier sa réservation, comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/modif_rdv.png" alt="Panier">
</center>
Une fois les modifications apportées, une nouvelle page de réservation est affichée, comme illustré ci-dessous :
<center>
  <img src="./assets_README/IMG/show_mesRDV_update.png" alt="Panier">
</center>
