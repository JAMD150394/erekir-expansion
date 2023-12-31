package erekir;

import arc.Core;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.Events;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.units.UnitAssembler.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.type.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import erekir.util.*;
import erekir.content.*;
import erekir.ctype.*;
import erekir.graphics.*;
import erekir.util.*;
import erekir.ui.*;
import erekir.ui.ingame.*;
import erekir.gen.*;
import erekir.room.*;
import erekir.world.blocks.gather.*;
import erekir.world.blocks.environment.*;

import static mindustry.type.ItemStack.with;
import static mindustry.Vars.*;

/** Mod's main class, also holds some utility methods. */
public class ErekirExpansion extends Mod{
    public Seq<Block> resupplyBlocks = new Seq<Block>();
    
    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(() -> {
           ErkSounds.load();
           ErekirShaders.load();
        }));
      
        Events.on(ContentInitEvent.class, e -> {
           addToFabricator(
              Blocks.mechFabricator,
              new UnitFactory.UnitPlan(ErkUnitTypes.gem, (float) 40 * Time.toSeconds, with(Items.beryllium, 100, Items.silicon, 50))
           );
           addToFabricator(
              Blocks.shipFabricator,
              Seq.with(
                 new UnitFactory.UnitPlan(ErkUnitTypes.aggregate, (float) 30 * Time.toSeconds, with(Items.beryllium, 85, Items.silicon, 35, Items.graphite, 20)), 
                 new UnitFactory.UnitPlan(ErkUnitTypes.spread, (float) 25 * Time.toSeconds, with(Items.tungsten, 20, Items.silicon, 45, Items.graphite, 30)),
                 new UnitFactory.UnitPlan(ErkUnitTypes.melt, (float) 35 * Time.toSeconds, with(Items.tungsten, 25, Items.silicon, 55, Items.oxide, 15))
              )
           );
           
           //TODO fix the module tier bug
           addToAssembler(
              Blocks.shipAssembler,
              Seq.with(
                 new AssemblerUnitPlan(ErkUnitTypes.attractor, (float) 55 * Time.toSeconds, PayloadStack.list(ErkUnitTypes.aggregate, 4, Blocks.berylliumWallLarge, 8, Blocks.berylliumWall, 4)),
                 new AssemblerUnitPlan(ErkUnitTypes.dissolve, (float) 60 * Time.toSeconds, PayloadStack.list(ErkUnitTypes.spread, 5, Blocks.tungstenWallLarge, 5))
              )
           );
           
           addToReconstructor(Blocks.mechRefabricator, ErkUnitTypes.gem, ErkUnitTypes.geode);
           addToReconstructor(Blocks.shipRefabricator, ErkUnitTypes.aggregate, ErkUnitTypes.agglomerate);
           addToReconstructor(Blocks.shipRefabricator, ErkUnitTypes.spread, ErkUnitTypes.apart);
           addToReconstructor(Blocks.primeRefabricator, ErkUnitTypes.geode, ErkUnitTypes.mineral);
           addToReconstructor(Blocks.primeRefabricator, ErkUnitTypes.agglomerate, ErkUnitTypes.accumulate);
           addToReconstructor(Blocks.primeRefabricator, ErkUnitTypes.apart, ErkUnitTypes.shredder);
        });
       
        Events.on(ClientLoadEvent.class, e -> {
            ErekirSettings.load();
            DropGenerator.handleIcons();
            addToResupply();
        });
        
        Events.on(WorldLoadEvent.class, e -> {
           if (headless) return;
           
           ErkUtil.allDrops(b -> b.addButton());
        });
        
        Events.on(DisposeEvent.class, e -> {
           ErekirShaders.dispose();
        });
        
        Events.run(Trigger.draw, () -> {
           /*
           for (Unit unit : Groups.unit) {
              Floor floor = unit.tileOn() == null ? Blocks.air.asFloor() : unit.tileOn().floor();
              if (floor.isLiquid && floor == ErkBlocks.angryArkyciteFloor) {
                 if (!unit.isFlying() && unit.hovering == false) {
                    float z = Draw.z(); 
                  
                    Draw.z(Layer.debris);
                    Draw.color(Tmp.c1.set(floor.mapColor).mul(1.5f));
                    Lines.stroke(4f);
                    Lines.circle(unit.x, unit.y, unit.type.hitSize * 1.25f * (1f - unit.drownTime));
                    Draw.reset();
                 
                    Draw.z(z);
                 }
              }
           }
           
           for (BaseRoom room : ErkVars.rooms) {
              if (ui.minimapfrag.shown()) {
                 float rx = room.x / (world.width() * tilesize);
                 float ry = room.y / (world.height() * tilesize);

                 renderer.minimap.drawLabel(rx, ry, room.localized(), Pal.accent);
              }
           }
           */
        });
    }
    
    @Override
    public void init() {
       if (!headless) {
          resupplyBlocks = Seq.with(
             Blocks.liquidContainer, Blocks.liquidTank,
             Blocks.reinforcedLiquidContainer, Blocks.reinforcedLiquidTank
          );
       }
    }
    
    @Override
    public void loadContent() {
       DropGenerator.generateDrops();
       //load everything from the array
       for (AltContentList list : erekirContent) list.load();
       AddedErekirTechTree.load();
    }

    private final AltContentList[] erekirContent = {
       new ErkLiquids(),
       new ErkStatusEffects(),
       new ErkBlocks(),
       new ErkUnitTypes(),
       new ErkPlanets(),
       new ErkSectorPresets()
    };
    
    public void addToResupply() {
        resupplyBlocks.each(b -> {
           b.allowResupply = true;
        });
    }
    
    public void addToFabricator(Block bloc, UnitFactory.UnitPlan plan) {
        if (!(bloc instanceof UnitFactory)) return;
        
        UnitFactory factory = (UnitFactory) bloc;
        factory.plans.addAll(plan);
        
        factory.configurable = true;
        factory.init();
    }
    
    public void addToFabricator(Block bloc, Seq<UnitFactory.UnitPlan> plan) {
        if (!(bloc instanceof UnitFactory)) return;
        
        UnitFactory factory = (UnitFactory) bloc;
        factory.plans.addAll(plan.toArray());
        
        factory.configurable = true;
        factory.init();
    }
    
    public void addToReconstructor(Block bloc, UnitType unit, UnitType upgrade) {
        if (!(bloc instanceof Reconstructor)) return;
        
        Reconstructor recon = (Reconstructor) bloc;
        recon.addUpgrade(unit, upgrade);
    }
    
    public void addToAssembler(Block bloc, Seq<AssemblerUnitPlan> stac) {
        if (!(bloc instanceof UnitAssembler)) return;
        
        UnitAssembler assembler = (UnitAssembler) bloc;
        assembler.plans.addAll(stac.toArray());
        
        assembler.init();
    }
}
