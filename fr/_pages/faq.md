---
title: FAQ
permalink: /faq/fr
layout: single
toc: true
toc_label: "Sur cette page"
toc_sticky: true
---

## Où se trouve le dossier GazePlay par défaut ?

Voici les chemins d'accès par défaut au dossier `GazePlay` selon votre système d'exploitation :

|         | Emplacement                         |
| ------- |: ---------------------------------- |
| Windows | `C:\Users\<username>\GazePlay`      |
| MacOS   | `/Utilisateurs/<username>/GazePlay` |
| Linux   | `/home/<nomutilisateur>/GazePlay`   |

### Windows

{% include figure image_path="/assets/images/tutorial/this_pc_folder.png" alt="Ce PC" caption="Dans l'Explorateur, accédez à `Ce PC`. Cliquez sur le lecteur `C:`" %}

{% include figure image_path="/assets/images/tutorial/c_disk_folder.png" alt="Lecteur C" caption="Cliquez sur le dossier `Utilisateurs`, puis sélectionnez le dossier avec votre nom d'utilisateur." %}

{% include figure image_path="/assets/images/tutorial/users_folder.png" alt="Dossier utilisateur" caption="Dans votre dossier utilisateur, vous devriez trouver le dossier `GazePlay`. Cliquez pour l'ouvrir." %}

## Comment puis-je ajouter mes propres images à GazePlay ?

### Ajout d'images personnalisées

#### Depuis les options de GazePlay

Dans `GazePlay`, aller dans les options via l'engrenage bleu.<br>

{% include figure image_path="/assets/images/tutorial/imgFolder.png" alt="Option dossier image" caption="Descendre jusqu'à l'option `Répertoire des fichiers` et cliquer sur le bouton `select`." %}

{% include figure image_path="/assets/images/tutorial/WindowsAddImage.png" alt="Fenêtre ajout image" caption="Choisir la catégorie puis ajouter son image." %}

#### A la main

Aller dans le dossier `GazePlay`, ensuite dans le dossier `files` puis  dans `images`.<br>
Si celui-ci n'existe pas alors créer le.

Dans le dossier `images` créer 3 sous-dossiers :
* `blocs` : images utilisées par les jeux blocs et carte à gratter. Choisissez de grandes images, de préférence de la même taille que l'écran.
* `magiccards` : images utilisées par les cartes magiques. Vous pouvez utiliser toutes les tailles d'image.
* `opinions` : images utilisées par le jeu opinion. Vous pouvez utiliser toutes les tailles d'image.
* `portraits` : images utilisées par les jeux creampie, ninja et bulles. Les images doivent être petites (300×300 pixels).

Mettre les images souhaitées dedans pour qu'ils apparaissent dans les jeux GazePlay.

### Ajout de jeux personnalisés à _Où est-ce ?_

Suivez les étapes suivantes pour ajouter vos propres variantes personnalisées au jeu _Où est-ce ?_.

#### Windows

{% include figure image_path="/assets/images/tutorial/gazeplay_folder.png" alt="Dossier GazePlay" caption="Placez-vous dans le dossier `where-is-it`, situé dans le dossier par défaut `GazePlay`." %}

Si ce dossier n'existe pas, créez le.
Vous pouvez modifier l'emplacement ou le nom de ce dossier comme bon vous semble tant que vous changer bien le paramètre correspondant dans le menu de configuration.

Ce dossier doit contenir trois sous-dossiers : `images`, `pictos`, `sounds`.
S'ils n'existent pas, créez les.

Le dossier `images` est le plus important pour le bon fonctionnement du jeu.

{% include figure image_path="/assets/images/tutorial/where-is-it_folder_images.png" alt="Dossier where-is-it" caption ="Cliquez sur `images`." %}

Vous devez créer à l'intérieur autant de sous-dossiers que vous voulez d'élément à deviner.
C'est dans chacun de ces dossiers que vous allez placer vos images personnalisées.
Vous pouvez ainsi mettre plusieurs images pour le même élément dans le dossier correspondant.
Les noms des images à l'intérieur n'ont aucunes importances, c'est uniquement le nom du dossier qui défini l'élément.

{% include figure image_path="/assets/images/tutorial/images_folder.png" alt="Dossier images" caption="Voici un exemple de comment remplir le dossier `images`." %}

Les dossiers vides seront ignorés.
Il est recommandé de créer au moins neuf dossiers non vides pour les éléments à deviner.
Si vous en créez moins, certaines variantes du jeu ne fonctionneront pas dû au manque d'éléments pour remplir la grille.
Par exemple, dans le cas de la grille 3×3 avec moins de 9 éléments à deviner.

Vous pouvez également ajouter vos propres sons personnalisés.
Ceux-ci sont joués lors de l'affichage de la question ou répétés lorsque le joueur se trompe si le paramètre correspondant est activé.

{% include figure image_path="/assets/images/tutorial/where-is-it_folder_sounds.png" alt="Dossier where-is-it" caption ="Revenez au dossier `where-is-it` et cliquez sur `sounds`." %}

Placez tous vos sons (par exemple des fichiers `.mp3`) dans ce dossier et donnez-leur le même nom que celui du dossier des images qui leur est associé.

{% include figure image_path="/assets/images/tutorial/sounds_folder.png" alt="Dossier sounds" caption="Voici un exemple de comment remplir le dossier `sounds`." %}

Vous pouvez de la même manière ajouter des pictogrammes personnalisés qui s'afficheront en même temps que la question.
Il suffit pour cela d'ajouter vos pictogrammes dans le dossier `pictos`, sans se soucier de leurs noms.

Enfin, vous devez créer un fichier `questions.csv` qui va principalement contenir les textes des questions à poser au joueur.
Pour ce faire, ouvrez un tableur, comme Microsoft Excel, LibreOffice Calc ou [Google Sheets](https://docs.google.com/spreadsheets).
Ensuite, remplissez un tableau tel que l'exemple ci-dessous :

|           | pictos          | fra                      |
| --------- |: -------------- |: ----------------------- |
| baignoire | `baignoire.png` | Où est la baignoire ? |
| biscuit   | `biscuit.png`   | Où est le biscuit ?   |
| bus       | `bus.png`       | Où est le bus ?       |

Comment remplir les colonnes :
* La première colonne est le nom des dossiers d'images que vous avez créés.
* La colonne `pictos` est le nom du fichier image dans le dossier du même nom.
* Et la colonne `fra` représente la traduction française de la question à poser au joueur.

Vous pouvez passer de français à une autre langue si vous le souhaitez ou également ajouter autant de colonne de langues qu'il vous paraît nécessaire.
Le jeu choisira automatiquement la traduction pour la langue sélectionnée dans le menu configuration.
Les langues prises en charge sont :
`alb`, `ara`, `chn`, `deu`, `ell`, `eng`, `fin`, `fra`, `hrv`, `ind`, `ita`, `jpn`, `pol`, `por`, `zsm`, `nld`, `rus`, `spa`, `vnm`, `bel`, `hin`.

Enregistrez votre feuille de calcul en tant que `questions.csv` et placer le fichier dans le dossier `where-is-it`.

Et voilà, vous venez de créer votre propre variante au jeu Où est-ce ?.

Vous pouvez créer et remplir de la même manière autant de dossier `where-is-it` que vous voulez pour avoir pleins de variantes différentes.
Il vous suffit ensuite de changer dans le menu configuration l'emplacement du dossier pour choisir la variante à laquelle vous désirez jouer.

Voici un exemple complet de dossier `where-it-is` avec lequel vous pouvez jouer ou vous inspirer pour créer vos propres variantes : <a href="https://github.com/GazePlay/GazePlay/raw/gh-pages/assets/example-where-is-it.zip" download>example-where-it-is</a>.

## Comment créer le raccourci d'un jeu ?

1. Lancer GazePlay et aller dans les paramètres via l'engrenage bleu.
{% include figure image_path="/assets/images/tutorial/FR/boutonParamètres.png" alt="Le bouton paramètre de GazePlay." %}

2. Dans la partie 'Paramètre de langue' puis 'Créer des raccourcis vers les jeux', vous pouvez :
* Choisir où mettre le raccourci (par défaut sur le bureau)
* Choisir le jeu que l'on va mettre en raccourci
* Si le jeu choisi a des variantes, on peut aussi choisir sa variante
* Le bouton qui permet de générer le raccourcis

{% include figure image_path="/assets/images/tutorial/FR/paramètres.png" alt="La partie raccourcis des paramètres." %}

Une fois que l'on a choisi ou mettre le raccourci et quel jeu on veut en raccourci, on clique sur le bouton 'Générer le raccourci'. <br>
On obtient ainsi le raccourci du jeu choisi :

{% include figure image_path="/assets/images/tutorial/shortcuts.png" alt="La partie raccourcis des paramètres." %}

## Comment créer un raccourci sous Windows ?

1. Localisez `gazeplay-windows.bat` dans le dossier `/bin` de votre dossier d'installation de GazePlay.
2. Faites un clic droit sur `gazeplay-windows.bat` et sélectionnez 'créer un raccourci'.
3. Déplacez le raccourci sur le bureau.
4. Double-cliquez sur le raccourci pour lancer GazePlay.

## Je clique sur `gazeplay-windows.bat` mais cela ne fonctionne pas. Pourquoi ?

Vous pouvez voir un message d'erreur indiquant que vous n'avez pas de machine virtuelle Java sur votre ordinateur.
Pour résoudre ce problème, vérifiez qu'il existe un dossier `jre` dans votre dossier d'installation de GazePlay.
S'il n'y est pas, vous devrez télécharger à nouveau la dernière version de GazePlay.

Si vous rencontrez d'autres problèmes, envoyez un e-mail à <didier.schwab@univ-grenoble-alpes.fr> ou [posez directement un problème à nos développeurs](https://github.com/GazePlay/GazePlay/issues/new).