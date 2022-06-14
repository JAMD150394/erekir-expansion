package erekir.util;

import arc.func.*;
import arc.math.geom.*;
import mindustry.gen.*;
import erekir.world.blocks.environment.ItemProp.*;

public class ErkUtil{
   
   /** Iterates over every building and checks if it's a drop build. */
   public void allDrops(Cons<ItemProp.ItemBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.ItemBuild) build;
            e.get(drop);
         }
      } 
   }
   
   /* Iterates over every building of a team and checks if they're within a certain range. */
   public void dropsWithin(Team team, float x, float y, float range, Cons<ItemProp.ItemBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.ItemBuild) build;
            
            if (drop.team == team && drop.within(x, y, range)) {
               e.get(drop);
            }
         }
      } 
   }
   
   public void dropsWithin(Team team, Position pos, float range, Cons<ItemProp.ItemBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.ItemBuild) build;
            
            if (drop.team == team && drop.within(pos.x, pos.y, range)) {
               e.get(drop);
            }
         }
      } 
   }
   
   public boolean dropWithin(ItemProp.DropBuild build, float x, float y, float range) {
       return build.within(x, y, range);
   }
   
   public boolean dropWithin(ItemProp.DropBuild build, Position pos, float range) {
       return dropWithin(build, pos.x, pos.y, range)
   }
   
   /* Whenever a building has the overlay button. */
   public boolean containsButton(ItemProp.DropBuild build) {
       return build.containsButton;
   }
}