package erekir.room;

import arc.util.*;
import arc.math.geom.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.game.*;
import mindustry.Vars;

/** A room with ruined beam drills. */
public class MiningRoom extends BaseRoom{
   /** Mineral dimensions. */
   public int mWidth = 2, mHeight = 2;
   
   public @Nullable Block ore;
   public Floor belowFloor = Blocks.sand.asFloor();
   
   public MiningRoom(int x, int y, int w, int h) {
      super(x, y, w, h);
   }
   
   public MiningRoom(int x, int y, int w, int h, Block ore) {
      super(x, y, w, h);
      this.ore = ore;
   }
   
   @Override
   public void generate() {
      super.generate();
      for (int w = x - mWidth; w <= x + mWidth; w++) for (int h = y - mHeight; h <= y + mHeight; h++) {
         Tile tile = Vars.world.tile(w, h);
         if (tile != null) {
            tile.setFloor(belowFloor);
            if (ore != null) {
               if (!(ore instanceof StaticWall)) {
                  if (((OreBlock) ore).wallOre) {
                     tile.setBlock(belowFloor.wall != null ? belowFloor.wall : Blocks.darkMetal);
                  }
                  tile.setOverlay(ore);
               } else {
                  tile.setBlock(ore);
               }
            }
         }
      }
      
      //drill ruins
      int i = 0;
      for (Point2 point : Geometry.d4) {
         int dx = point.x * mWidth * 3, dy = point.y * mHeight * 3;
         Tile tile = Vars.world.tile(x + dx, y + dy);
         if (tile != null) {
            tile.setBlock(Blocks.plasmaBore, Team.derelict, i > 1 ? i - 2 : i + 2);
         }
         i++;
      }
   }
   
   @Override
   public String bundleName() {
      return "room.erekir-expansion-miningRoom";
   }
}