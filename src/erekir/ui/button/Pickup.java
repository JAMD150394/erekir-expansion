package erekir.ui.button;

import arc.Core;
import arc.scene.*;
import arc.scene.actions.Actions;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ui.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class Pickup{
   private static int buttonW = 40;
   private static int buttonH = 40;
   
   public static void createPickupButton(Position pos, Drawable icon, Runnable run) {
       Table table = new Table(Styles.none).margin(4f);
       table.update(() -> {
           if (state.isMenu() || pos == null) table.remove();
           Vec2 v = Core.camera.project(pos.x, pos.y);
           table.setPosition(v.x, v.y, Align.center);
           
           Unit plr = player.unit();
           if (plr != null) {
              float d = plr.dst(pos);
              table.actions(Actions.alpha(1f - Mathf.clamp(d / 16f - 1.5f)));
              if (plr.within(pos.x, pos.y, 20f)) {
                 table.touchable = Touchable.enabled;
              }
              else table.touchable = Touchable.disabled;
           }
        });
        table.button(icon, run).size(buttonW, buttonH).margin(4f).pad(4f);
        table.pack();
        table.act(0);
        
        //make sure it's at the back
        Core.scene.root.addChildAt(0, table);

        table.getChildren().first().act(0);
   }
   
   public static void createPickupButton(Position pos, Runnable run) {
       createPickupButton(pos, Icon.download, run);
   }
}