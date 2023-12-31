package erekir.content;

import arc.func.*;
import mindustry.type.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.ctype.*;
import erekir.gen.*;

import static mindustry.content.Planets.*;

public class ErkPlanets implements AltContentList{
    public Planet testStash, scattered;
   
    @Override
    public void load() {
       testStash = makeStash("testStash", erekir, 0.5f);
       scattered = makeStash("scattered", erekir, 0.5f, planet -> {
          planet.generator = new ScatteredGenerator();
       });
    }

    private Planet makeStash(String name, Planet parent, float scale) {
       return new Planet(name, parent, 0.12f){{
          hasAtmosphere = false;
          updateLighting = false;
          sectors.add(new Sector(this, Ptile.empty));
          camRadius = 0.68f * scale;
          minZoom = 0.6f;
          drawOrbit = false;
          accessible = true;
          clipRadius = 2f;
          defaultEnv = Env.terrestrial;
          orbitSpacing = 1.1f;
          
          generator = new StashGenerator();
      }};
   }
   
   private Planet makeStash(String name, Planet parent, float scale, Cons<Planet> cons) {
       Planet stash = new Planet(name, parent, 0.12f){{
          hasAtmosphere = false;
          updateLighting = false;
          sectors.add(new Sector(this, Ptile.empty));
          camRadius = 0.68f * scale;
          minZoom = 0.6f;
          drawOrbit = false;
          accessible = true;
          clipRadius = 2f;
          defaultEnv = Env.terrestrial;
          orbitSpacing = 1.1f;
          
          generator = new StashGenerator();
      }};
      cons.get(stash);
      
      return stash;
   }
}