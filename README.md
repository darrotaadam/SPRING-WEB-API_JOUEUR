# SPRING-WEB-API_JOUEUR
L’API Joueur est utilisée pour gérer les infos de compte utilisateurs qui sont :
identifiant
level (va de 0 à 50)
experience (commence à 50 pas d’xp pour level up au niveau 1 pour passer niveau 2 puis
est multiplié par 1,1 à chaque niveau : donc il faudra 50 * 1,1 = 55 xp pour passer au niveau 3)
List<monstres> (taille conditionnée par le niveau = commence à 10 puis + 1 pour chaque
level). Ces monstres sont représentés dans cette liste par un id unique.
Elle doit pouvoir gérer :
- la récupération de toutes les informations du profil
- la récupération de la liste de monstre
- la récupération du niveau du joueur
- un gain d’expérience (quantité passée en paramètre) et retourner le nouveau statut
utilisateur
- un gain de niveau (reset l’expérience, augmente le seuil de level up et augmente la taille
max de la liste de monstres) et retourner le nouveau statut utilisateur
- l’acquisition d’un nouveau monstre
- la suppression d’un monstre
