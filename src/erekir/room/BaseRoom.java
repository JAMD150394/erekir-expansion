package erekir.room;

import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.Vars;

/** An empty room/platform. */
public class BaseRoom extends Room{
   public Floor groundFloor = Blocks.metalFloor.asFloor();
   
   public BaseRoom(int px, int py, int w, int h) {
      super(px, py, w, h);
   }
   
   public BaseRoom(int x, int y, int w, int h, Floor ground) {
      super(x, y, w, h);
      groundFloor = ground;
   }
   
   public void generate() {
      for (int w = x - width; w <= x + width; w++) {
         for (int h = y - height; h <= y + height; h++) {
            Tile tile = Vars.world.tile(w, h);
            if (tile != null) tile.setFloor(groundFloor);
         }
      }
   }
}