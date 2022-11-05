package projet.physicEngine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import projet.physicEngine.common.Point;
import projet.physicEngine.*;
// import projet.physicEngine.PolygonShape;


public class TestCollisionListener{

  @Test
  void testAreInIntersectionForCircles(){
    CollisionListener cl = new CollisionListener();

    CircleShape cs = new CircleShape(new Point(0,0), 1f);
    // cs2 dans cs
    CircleShape cs2 = new CircleShape(new Point(0,0), 3f);
    // cs3 et cs en intersection
    CircleShape cs3 = new CircleShape(new Point(1,0), 0.5f);
    // cs4 et cs bordure en commun
    CircleShape cs4 = new CircleShape(new Point(2,0), 1f);
    // cs5 et cs aucunes intersections
    CircleShape cs5 = new CircleShape(new Point(10,0), 1f);

    Body f = new Body(cs);
    Body f2 = new Body(cs2);
    Body f3 = new Body(cs3);
    Body f4 = new Body(cs4);
    Body f5 = new Body(cs5);

    assertThat(cl.areInCollision(f, f2)).isNotNull();
    assertThat(cl.areInCollision(f, f3)).isNotNull();
    assertThat(cl.areInCollision(f, f4)).isNotNull();
    assertThat(cl.areInCollision(f, f5)).isNull();

  }

  @Test
  void testAreInIntersectionForCiclePolygon(){
    CollisionListener cl = new CollisionListener();

    CircleShape cs = new CircleShape(new Point(0,0), 5f);
    CircleShape cs2 = new CircleShape(new Point(10,3), 1f);

    Point[] vertex = new Point[4];
    vertex[0] = new Point(1,2);
    vertex[1] = new Point(3,3);
    vertex[2] = new Point(3,6);
    vertex[3] = new Point(0,5);

    Point[] vertex2 = new Point[7];
    vertex2[0] = new Point(1,7);
    vertex2[1] = new Point(5,7);
    vertex2[2] = new Point(6,8);
    vertex2[3] = new Point(6,9);
    vertex2[4] = new Point(5,10);
    vertex2[5] = new Point(2,10);
    vertex2[6] = new Point(1,9);

    Point[] vertex3 = new Point[3];
    vertex3[0] = new Point(8,0);
    vertex3[1] = new Point(12,0);
    vertex3[2] = new Point(8,5);


    PolygonShape ps = new PolygonShape(vertex, 4);
    PolygonShape ps2 = new PolygonShape(vertex2, 7);
    PolygonShape ps3 = PolygonShape.createRectShape(1f, 1f);
    PolygonShape ps4 = new PolygonShape(vertex3, 3);

    Body fc = new Body(cs);
    Body fc2 = new Body(cs2);
    Body fp = new Body(ps);
    Body fp2 = new Body(ps2);
    Body fp3 = new Body(ps3);
    Body fp4 = new Body(ps4);

    //Un sommet de ps dans cs
    assertThat(cl.areInCollision(fc, fp)).isNotNull();
    //Pas d'intersection
    assertThat(cl.areInCollision(fc, fp2)).isNull();
    //Polygone dans cercle
    assertThat(cl.areInCollision(fc, fp3)).isNotNull();

    //Pas de sommets dans le cercle, mais un segment traverse le cercle
    assertThat(cl.areInCollision(fc2, fp4)).isNotNull();

  }

  @Test
  void testAreInIntersectionForPolygons(){
    CollisionListener cl = new CollisionListener();

    Point[] vertex = new Point[4];
    vertex[0] = new Point(4,2);
    vertex[1] = new Point(6,2);
    vertex[2] = new Point(5,8);
    vertex[3] = new Point(1,5);

    Point[] vertex2 = new Point[3];
    vertex2[0] = new Point(6,2);
    vertex2[1] = new Point(8,5);
    vertex2[2] = new Point(5,8);

    Point[] vertex3 = new Point[4];
    vertex3[0] = new Point(3,1);
    vertex3[1] = new Point(3,5);
    vertex3[2] = new Point(1,2);
    vertex3[3] = new Point(2,1);

    Point[] vertex4 = new Point[4];
    vertex4[0] = new Point(3,4);
    vertex4[1] = new Point(5,4);
    vertex4[2] = new Point(5,6);
    vertex4[3] = new Point(3,6);


    PolygonShape ps = new PolygonShape(vertex, 4);
    PolygonShape ps2 = new PolygonShape(vertex2, 3);
    PolygonShape ps3 = new PolygonShape(vertex3, 4);
    PolygonShape ps4 = new PolygonShape(vertex4, 4);

    Body fp = new Body(ps);
    Body fp2 = new Body(ps2);
    Body fp3 = new Body(ps3);
    Body fp4 = new Body(ps4);

    assertThat(cl.areInCollision(fp,fp3)).isNotNull();
    assertThat(cl.areInCollision(fp,fp2)).isNotNull();
    assertThat(cl.areInCollision(fp,fp4)).isNotNull();
    assertThat(cl.areInCollision(fp3,fp2)).isNull();
  }

}
