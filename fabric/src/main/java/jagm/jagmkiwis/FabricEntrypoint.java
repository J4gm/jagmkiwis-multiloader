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
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        KiwiModItems.ITEMS_COMMON.forEach((name, item) -> Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), item));
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_NAME), KiwiModEntities.KIWI);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.LASER_BEAM_NAME), KiwiModEntities.LASER_BEAM);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_EGG_NAME), KiwiModEntities.KIWI_EGG);
        KiwiModSounds.SOUNDS_COMMON.forEach((name, soundEvent) -> Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), soundEvent));
        FabricDefaultAttributeRegistry.register(KiwiModEntities.KIWI, KiwiEntity.createAttributes());
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.CHERRY_GROVE, Biomes.DARK_FOREST), MobCategory.CREATURE, KiwiModEntities.KIWI, 10, 3, 4);
        SpawnPlacements.register(KiwiModEntities.KIWI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverLevel) -> {
            if(entity instanceof Cat cat){
                KiwiModEntities.addCatGoal(cat, cat.targetSelector);
            }
        });
        KiwiMod.addDispenserBehaviour();
    }

}
