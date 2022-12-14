package projet.game;

import projet.kernel.PEntity;
import projet.physicEngine.common.Point;
import projet.physicEngine.Body.BodyType;
import projet.physicEngine.*;
import projet.graphic_engine.drawable.*;

import java.awt.Graphics;
import java.awt.Color;

/**
* Classe représentant l'entité balle
*/
public class Ball extends MyEntity{

  /**
  * Classe représentant l'aspect graphique d'une balle
  */
  public class BallTexture extends PFixedTexturedDrawable{
    private Color color;

    public BallTexture(int x, int y, int width, int height, Color color){
      super(x,y,width,height);
    }

    @Override
    public void paint(Graphics g){
      g.setColor(this.color);
      g.fillOval(this.x-this.width, this.y-this.width, this.width*2, this.width*2);
    }
  }

  /**
  * Permet de créer une balle à partir de son centre et de son rayon (taille)
  * @param son centre
  * @param la longueur de son rayon
  * @param la couleur de la balle 
  */
  public Ball(Point center, float ray, Color color){
    super(MyEntity.BALL);
    //On créer son enveloppe
    CircleShape ballShape = new CircleShape(center, ray);
    //On crée son body
    this.body = new Body(center, ballShape, BodyType.DYNAMIC);

    this.body.getFilter().setCategoryBits(MyFilter.BALL_CATEGORY);
    this.body.getFilter().setMaskBits(MyFilter.BALL_MASK);

    // On ajoute une fixture
    BallTexture texture = new BallTexture((int)center.getX(), (int)center.getY(), (int)ray, (int)ray, color);
    this.setDrawable(texture);
  }

}
