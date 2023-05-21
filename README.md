# La carte aux trésors

Dans le cadre du processus de recrutement chez Carbon IT, j'ai réalisé un exercice avant l'entretien technique avec un de leur responsable technique.

# To-Do
- empêcher d'avoir plusieurs aventuriers sur la même case
- empêcher un aventurier de dépasser les frontières de la carte

# Lors de la lecture de l'énoncé

## input file
* données séparées par ` - `
* considérer le fichier comme correct
* pourrait contenir plusieurs aventuriers

## Carte
* cases de même dimension -> rien n'à faire
* est rectangulaire !
* dimensions sur la première ligne d'input

## Montagnes
* `M - X - Y`
* obstacle bloquant pour les aventuries

## Trésors
* `T - X - Y - nb`
 
**Règles** :
* l'aventurier peut aller sur une case de trésor
* `nb--` pour chaque fois qu'un aventurier se déplace sur la case
  * seulement si il n'y était pas déjà sur la case

## Aventurier
est composé de :
- un nom
- une position (x, y)
- une orientation [Nord, Sud, Est, Ouest]
- une séquence de mouvements -> queue FIFO ou garder l'index en mémoire
- nombre de trésors récoltés

  (+ la carte au trésors pour savoir où sont les montagnes et trésors)

**Règles** :
* ne se déplace que d'une case à la fois
* se déplace dans son orientation
* ne ramasse qu'un seul trésor si il visite une case de trésor
* un changement d'orientation ne se fait que de 90°
* est bloqué face à une montagne
* un seul aventurier par case
  * un aventurier se comporte comme une montagne pour un autre aventurier
* l'ordre des aventuriers dans l'input décide de l'ordre d'exécution de leurs tours
