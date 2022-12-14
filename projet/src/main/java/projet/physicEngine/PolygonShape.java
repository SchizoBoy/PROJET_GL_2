package projet.physicEngine;

import projet.physicEngine.common.Point;
import projet.physicEngine.common.Transform;

/**
* Permet de définir des box en forme de polygone convexe
*/
public class PolygonShape extends Shape{
  Point vertex[];
  int nbVertex;

  /**
  * Créer une box sous forme de polygone convexe
  * L'utilisateur doit donner les points dans l'ordre du sens des
  aiguilles d'une montre
  * @param liste des sommets qui définissent le contour de la box
  * @param le nombre de sommets
  */
  public PolygonShape(Point vertex[], int nbVertex){
    super(Shape.ShapeType.POLYGON);
    if(PolygonShape.isConvex(vertex, nbVertex) && nbVertex >= 3){
      this.nbVertex = nbVertex;
      this.vertex = new Point[nbVertex];
      for(int i = 0; i < nbVertex; i++){
        this.vertex[i] = Point.copy(vertex[i]);
      }
    }
  }

  /**
  * Permet de créer une box rectangulaire aligné sur les axes directement avec
    les deux hauteurs
  * Le centre du rectangle est le point (0,0)
  * @param hX la hauteur en x
  * @param hy la hauteur en y
  * @return la box associée
  */
  public static PolygonShape createRectShape(float hx, float hy)
  {
    Point[] v = new Point[4];

    v[0] = new Point(-hx/2f, -hy/2f);
    v[1] = new Point(hx/2f, -hy/2f);
    v[2] = new Point(hx/2f, hy/2f);
    v[3] = new Point(-hx/2f, hy/2f);

    return new PolygonShape(v, 4);
  }

  /**
  * Permet de créer une box rectangulaire sur les axes directement avec
    les deux hauteurs et l'angle de rotation
  * Le centre du rectangle est le point (0,0)
  * @param hX la hauteur en x
  * @param hy la hauteur en y
  * @param a l'angle en radian
  * @return la box associée
  */
  public static PolygonShape createRectShape(float hx, float hy, float a)
  {
    Point[] v = new Point[4];

    v[0] = new Point(-hx/2f, -hy/2f);
    Transform.rotationOrigin(v[0], a);
    v[1] = new Point(hx/2f, -hy/2f);
    Transform.rotationOrigin(v[1], a);
    v[2] = new Point(hx/2f, hy/2f);
    Transform.rotationOrigin(v[2], a);
    v[3] = new Point(-hx/2f, hy/2f);
    Transform.rotationOrigin(v[3], a);

    return new PolygonShape(v, 4);
  }

  /**
  * Permet de créer une box rectangulaire aligné sur les axes directement avec
    les deux hauteurs
  * @param hX la hauteur en x
  * @param hy la hauteur en y
  * @param le point d'origine (point centrale --> Isobarycentre)
  * @return la box associée
  */
  public static PolygonShape createRectShape(float hx, float hy, Point origin)
  {
    Point[] v = new Point[4];

    v[0] = new Point(origin.getX()-hx/2f, origin.getY()-hy/2f);
    v[1] = new Point(origin.getX()+hx/2f, origin.getY()-hy/2f);
    v[2] = new Point(origin.getX()+hx/2f, origin.getY()+hy/2f);
    v[3] = new Point(origin.getX()-hx/2f, origin.getY()+hy/2f);

    return new PolygonShape(v, 4);
  }

  /**
  * Permet de créer une box rectangulaire sur les axes directement avec
    les deux hauteurs et l'angle de rotation
  * @param hX la hauteur en x
  * @param hy la hauteur en y
  * @param a l'angle en radian
  * @param le point d'origine (point centrale --> Isobarycentre)
  * @return la box associée
  */
  public static PolygonShape createRectShape(float hx, float hy, float a, Point origin)
  {
    Point[] v = new Point[4];

    v[0] = new Point(origin.getX()-hx/2f, origin.getY()-hy/2f);
    Transform.rotationOrigin(v[0], a);
    v[1] = new Point(origin.getX()+hx/2f, origin.getY()-hy/2f);
    Transform.rotationOrigin(v[1], a);
    v[2] = new Point(origin.getX()+hx/2f, origin.getY()+hy/2f);
    Transform.rotationOrigin(v[2], a);
    v[3] = new Point(origin.getX()-hx/2f, origin.getY()+hy/2f);
    Transform.rotationOrigin(v[3], a);

    return new PolygonShape(v, 4);
  }


  /**
  * Permet de trouver l'isobarycentre ("centre de gravité") des sommets d'un
    polygone
  * @return l'isobarycentre du polygone courant
  */
  public Point getIsobarycenter(){
    Point isobarycenter = new Point(0,0);
    int i;

    for(i = 0; i < nbVertex; i++){
      isobarycenter.setX(isobarycenter.getX() + getVertex(i).getX());
      isobarycenter.setY(isobarycenter.getY() + getVertex(i).getY());
    }

    isobarycenter.setX(isobarycenter.getX()/nbVertex);
    isobarycenter.setY(isobarycenter.getY()/nbVertex);

    return isobarycenter;
  }



  /**
  * @param le numéro du sommet que l'on souhaite
  * @return le sommet associé au numéro donné
  */
  public Point getVertex(int num){
    if(nbVertex > num && num >= 0){
      return vertex[num];
    }
    return null;
  }

  /**
  * Permet de savoir si un point est dans le polygone courant
  * @param le point en question
  * @return true si p est strictement dans le polygone, false sinon
  */
  public boolean isInside(Point p){
    int i = 0;
    boolean inside = true;
    Point b, c;
      while(i<nbVertex && inside){
        if(i == nbVertex -1){
          b = this.getVertex(nbVertex-1);
          c = this.getVertex(0);
        }else{
          b = this.getVertex(i);
          c = this.getVertex(i+1);
        }

        if(p.angleSign(b,c) == Point.SIAM){
          inside = false;
        }
        i++;
      }

    return inside;
  }

  /**
  * Permet de savoir si un polygone formé par une liste de point est convexe
  * @param liste des sommets du polygone
  * @param nombre de sommet
  * @return true si le polygone est convexe, false sinon
  */
   public static boolean isConvex(Point[] vertex, int nbVertex){
      int i=0;
      Point b, c;
      boolean convex = true;

      while( i < nbVertex && convex ){
        if(i == 0){
          b = vertex[nbVertex -1];
          c = vertex[1];
        }else if(i == nbVertex -1){
          b = vertex[nbVertex -2];
          c = vertex[0];
        }else{
          b = vertex[i-1];
          c = vertex[i+1];
        }

        if((vertex[i]).angleSign(b,c) == Point.SAM){
          convex = false;
        }
        i++;
      }

      return convex;

    }

  /**
  * @return la liste des sommets
  */
  public Point[] getvertexList(){
    return this.vertex;
  }

  /**
  * @return le nombre de sommets
  */
  public int getNbVertex(){
    return this.nbVertex;
  }

  /**
  * Permet de modifier la valeur d'un sommet
  * @param le numéro du sommet
  * @param le nouveau sommet
  */
  public void setVertex(int i, Point p){
    this.vertex[i] = p;
  }

  @Override
  public String toString(){
    int i;
    String s="";
    for(i = 0; i<this.nbVertex; i++){
      s += " "+this.vertex[i]+" ";
    }
    return s;
  }

}
