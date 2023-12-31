package erekir.ui.ingame;

import arc.Core;
import arc.func.*;
import arc.scene.*;
import arc.scene.actions.Actions;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ui.*;
import mindustry.gen.*;
import erekir.room.*;
import erekir.ErkVars;

import static mindustry.Vars.*;

/** UI utility methods. 
 *  Special thanks to @author SMOLKEYS */
public class WorldUI{
   private static int buttonW = 40;
   private static int buttonH = 40;
   private static float range = 38f;
   
   public static void createPickupButton(Building bloc, Drawable icon, Runnable run) {
       Table table = new Table(Styles.none).margin(4f);
       
       table.update(() -> {
           if (state.isMenu() || !bloc.isValid() || bloc.dead()) table.remove();
           Vec2 v = Core.camera.project(bloc.x, bloc.y);
           table.setPosition(v.x, v.y, Align.center);
           
           Unit plr = player.unit();
           Boolp touch = () -> plr.within(bloc.x, bloc.y, range);
           table.visible(() -> {
              boolean shown;
              if (plr != null && !player.dead()) {
                 shown = true;
              } else shown = false;
              return shown;
           });
           if (touch.get()) {
              table.touchable = Touchable.enabled;
           } else table.touchable = Touchable.disabled;
           
           float d = plr.dst(bloc);
           table.actions(Actions.alpha(Core.settings.getString("erekir-expansion-buttonIcon") == "nothingness" ? 0f : 1f - Mathf.clamp(d / range - 0.5f)));
        });
        table.button(icon, run).size(buttonW, buttonH).margin(4f).pad(4f);
        table.pack();
        table.act(0);
        
        //make sure it's at the back
        Core.scene.root.addChildAt(0, table);

        table.getChildren().first().act(0);
   }
   
   public static void createPickupButton(Building bloc, Runnable run) {
       String icon = Core.settings.getString("erekir-expansion-buttonIcon");
       //a custom (almost?) icon
       createPickupButton(bloc, icon == "nothingness" ? new TextureRegionDrawable(Core.atlas.find("erekir-expansion-" + icon)) : ui.getIcon(icon), run);
   }
   
   /*
   public static void createFadingText(BaseRoom room, String text) {
      Table table = new Table(Styles.black3).margin(4f);

      table.touchable = Touchable.disabled;

      table.update(() -> {
         if (state.isMenu() && !ErkVars.generating) table.remove();
         float x = room.x * tilesize, y = room.y * tilesize;
         
         Vec2 v = Core.camera.project(x, y);
         table.setPosition(v.x, v.y, Align.center);
         table.visible(() -> {
            boolean shown;
            //only show after generating, it's also not touchable
            if (ErkVars.generating) {
               shown = false;
            }
            else shown = true;
            return shown;
         });
         
         float d = Mathf.dst(player.unit().x, player.unit().y, x, y);
         table.actions(Actions.alpha(Mathf.clamp(d / range - 0.5f)));
      });
      table.add(text).style(Styles.defaultLabel); //outline
      table.pack();
      table.act(0);
   
      //make sure it's at the back
      Core.scene.root.addChildAt(0, table);

      table.getChildren().first().act(0);
   }
   */
}