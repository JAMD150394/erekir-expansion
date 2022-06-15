package erekir.ui;

import arc.Core;
import arc.scene.ui.*;
import mindustry.ui.dialogs.*;
import erekir.ui.dialogs.*;

import static mindustry.Vars.*;

public class ErekirSettings{
   private static ButtonIconsDialog dialog;

   public static void load() {
      dialog = new ButtonIconsDialog();
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem-full", t -> {
          t.pref(new ButtonSetting());
          
          //also display small images
          t.checkPref(
             "displaySmall", Core.settings.getBool("erekir-expansion-displaySmall"),
             bool -> Core.settings.put("erekir-expansion-displaySmall", bool)
          );
      });
   }
   
   //shamelessly stolen from testing utils
   static class ButtonSetting extends Setting{
      public ButtonSetting(String name) {
         super(name);
         title = "setting." + name + ".name";
      }
      
      @Override
      public void add(SettingsTable table) {
          ImageButton b = table.button("Button icons", Styles.cleari, dialog::show).size(280f, 60f);
          table.row();
          
          addDesc(b);
      }
   }
}