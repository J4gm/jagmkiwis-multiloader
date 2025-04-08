package jagm.jagmkiwis;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        KiwiMod.init();
        KiwiModItems.ITEMS_COMMON.forEach((name, itemSupplier) -> Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KiwiMod.MOD_ID, name), itemSupplier.get()));
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(KiwiMod.MOD_ID, KiwiModEntities.KIWI_NAME), KiwiModEntities.KIWI.get());
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(KiwiMod.MOD_ID, KiwiModEntities.LASER_BEAM_NAME), KiwiModEntities.LASER_BEAM.get());
        KiwiModSounds.SOUNDS_COMMON.forEach((name, soundSupplier) -> Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(KiwiMod.MOD_ID, name), soundSupplier.get()));
        FabricDefaultAttributeRegistry.register(KiwiModEntities.KIWI.get(), KiwiEntity.createAttributes());
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.CHERRY_GROVE), MobCategory.CREATURE, KiwiModEntities.KIWI.get(), 10, 3, 4);
        SpawnPlacements.register(KiwiModEntities.KIWI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverLevel) -> {
            if(entity instanceof Cat cat){
                KiwiModEntities.addCatGoal(cat, cat.targetSelector);
            }
        });
    }

}
