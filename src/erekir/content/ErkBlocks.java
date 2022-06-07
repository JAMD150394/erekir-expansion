package erekir.content;

import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.ctype.*;
import erekir.world.blocks.environment.ItemProp;
import erekir.world.blocks.defense.BlockUpkeeper;

import static mindustry.type.ItemStack.with;

public class ErkBlocks implements AltContentList{
    public static Block
    
    //Environment
    berylDrop, tungDrop, 
    
    //Defense
    berylUpkeeper
     
    ;
    
    @Override
    public void load() {
      berylDrop = new ItemProp("berylDrop", Items.beryllium){{
          region = Items.beryllium.fullIcon;
      }};
      
      tungDrop = new ItemProp("tungDrop", Items.tungsten){{
          region = Items.tungsten.fullIcon;
      }};
      
      berylUpkeeper = new BlockUpkeeper("berylUpkeeper"){{
          size = 2;
          lanes = 4;
          range = 12;
          requirements(Category.defense, BuildVisibility.berylliumOnly, with(Items.beryllium, 100, Items.graphite, 65, Items.tungsten, 40));
      }};
    }
}