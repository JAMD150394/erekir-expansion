package erekir.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;
import erekir.graphics.*;

public class ErkFx {
  public static final Effect
  
  //when all the effects use the same color method
  gemHit = new Effect(14f, e -> {
     Draw.color(Color.white, ErkPal.greenishBeryl, e.fin());
     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 4, 1f + e.fin() * 15f, e.rotation, 50f, (x, y) -> {
         float ang = Mathf.angle(x, y);
         Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 3.5f + 1.4f);
     });
  }),
  
  berylMissileHit = new Effect(14f, e -> {
     Draw.color(Color.white, ErkPal.greenishBeryl, e.fin());

     e.scaled(7f, s -> {
         Lines.stroke(1f + s.fout());
         Lines.circle(e.x, e.y, s.fin() * 23f);
     });

     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 7, e.fin() * 24f, (x, y) -> {
         float ang = Mathf.angle(x, y);
         Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 4.5f + 1.4f);
     });

     Drawf.light(e.x, e.y, 30f, ErkPal.greenishBeryl, 0.6f * e.fout());
  });
}