package projet.game;

import projet.kernel.PWorld;
import projet.physicEngine.common.*;
import projet.physicEngine.*;

import projet.graphic_engine.*;
import projet.graphic_engine.drawable.*;

import java.awt.Graphics;
import java.awt.Color;

/**
* Classe définissant le monde de jeu
*/
public class MyWorld extends PWorld {
  private final int WIDTH = 500; // Largeur du monde
  private final int HEIGHT = 500; // Hauteur du mond

  /**
  * @param largeur du jeu
  * @param hauteur du jeu
  */
  public MyWorld(float width, float height){
    super(width, height);

    // Création de mon monde physique
    this.physicWorld = new MyPhysicWorld(width, height);

    //Création de la balle
    Ball ball = new Ball(new Point(150,150), 20f);
    ball.getBody().setVelocity(new Vector2D(0.15f, -0.10f));

    // Création de la première raqutte
    Racket racket1 = new Racket(new Point(25,200), MyEntity.RACKET_A);

    // Création de la deuxième raquette
    Racket racket2 = new Racket(new Point(475,200), MyEntity.RACKET_B);

    //On les ajoute a la liste d'entité
    this.addEntity(ball);
    this.addEntity(racket1);
    this.addEntity(racket2);

  }

  @Override
  public void processPhysic(float dt) {
    int i;
    // On calcule lance l'écouteur de collision
    physicWorld.launchCollisionListener();

    //On calcule le déplacement de chacune des corps des entités
    for(i = 0; i < this.entities.size(); i++){
      entities.get(i).getBody().linearMovement(dt);
    }
  }

  @Override
  public void processGraphic(float dt) {

  for(int i = 0; i < this.entities.size(); i++){
    entities.get(i).getDrawable().setPosition((int)entities.get(i).getBody().getCenter().getX(), (int)entities.get(i).getBody().getCenter().getY());
    this.getStage().add(entities.get(i).getDrawable());
  }


}

  /**
  * @return la racketA du jeu
  */
  public Racket getRacketA(){
    int i;
    for(i = 0; i < this.entities.size(); i++){
      if(this.entities.get(i).getType() == MyEntity.RACKET_A){
        return (Racket)this.entities.get(i);
      }
    }
    return null;
  }

  /**
  * @return la racketB du jeu 
  */
  public Racket getRacketB(){
    int i;
    for(i = 0; i < this.entities.size(); i++){
      if(this.entities.get(i).getType() == MyEntity.RACKET_B){
        return (Racket)this.entities.get(i);
      }
    }
    return null;
  }
}