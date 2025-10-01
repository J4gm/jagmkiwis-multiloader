package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(KiwiMod.MOD_ID)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint(IEventBus eventBus) {

    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID)
    public static class CommonModEventHandler{

        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {
            event.register(Registries.ITEM, helper -> KiwiModItems.ITEMS_COMMON.forEach((name, item) -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), item)));
            event.register(Registries.SOUND_EVENT, helper -> KiwiModSounds.SOUNDS_COMMON.forEach((name, soundEvent) -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), soundEvent)));
            event.register(Registries.ENTITY_TYPE, helper -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_NAME), KiwiModEntities.KIWI));
            event.register(Registries.ENTITY_TYPE, helper -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.LASER_BEAM_NAME), KiwiModEntities.LASER_BEAM));
            event.register(Registries.ENTITY_TYPE, helper -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_EGG_NAME), KiwiModEntities.KIWI_EGG));
        }

        @SubscribeEvent
        public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
            event.put(KiwiModEntities.KIWI, KiwiEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(KiwiModEntities.KIWI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            KiwiMod.addDispenserBehaviour();
        }

    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID)
    public static class CommonGameEventHandler{

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if(event.getEntity() instanceof Cat cat){
                KiwiModEntities.addCatGoal(cat, cat.targetSelector);
            }
        }

        @SubscribeEvent
        public static void onFinalizeSpawn(FinalizeSpawnEvent event){
            if(event.getEntity() instanceof Zombie zombie){
                event.setSpawnData(KiwiModEntities.finalizeZombieSpawn(zombie, event.getLevel(), event.getSpawnData(), event.getDifficulty()));
            }
        }

    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEventHandler{

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(KiwiModEntities.KIWI, KiwiRenderer::new);
            event.registerEntityRenderer(KiwiModEntities.LASER_BEAM, LaserBeamRenderer::new);
            event.registerEntityRenderer(KiwiModEntities.KIWI_EGG, ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(KiwiModel.KIWI_LAYER, KiwiModel::createBodyLayer);
            event.registerLayerDefinition(KiwiModel.BABY_KIWI_LAYER, () -> KiwiModel.createBodyLayer().apply(KiwiModel.BABY_TRANSFORMER));
        }

        @SubscribeEvent
        public static void onFillCreativeTabs(BuildCreativeModeTabContentsEvent event){
            if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
                event.accept(KiwiModItems.KIWI_FRUIT);
                event.accept(KiwiModItems.PAVLOVA);
            }
            else if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
                event.accept(KiwiModItems.KIWI_SPAWN_EGG);
            }
            else if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
                event.accept(KiwiModItems.KIWI_EGG);
            }
            else if(event.getTabKey() == CreativeModeTabs.COMBAT){
                event.accept(KiwiModItems.KIWI_EGG);
            }
        }

    }

}