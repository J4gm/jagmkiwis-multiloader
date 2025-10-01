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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

@Mod(KiwiMod.MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint(FMLJavaModLoadingContext context) {

    }

    @Mod.EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEventHandler{

        @SubscribeEvent
        public static void registerEvent(RegisterEvent event) {
            event.register(Registries.ITEM, helper -> KiwiModItems.ITEMS_COMMON.forEach((name, item) -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), item)));
            event.register(Registries.SOUND_EVENT, helper -> KiwiModSounds.SOUNDS_COMMON.forEach((name, soundEvent) -> helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name), soundEvent)));
            event.register(Registries.ENTITY_TYPE, helper -> {
                helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_NAME), KiwiModEntities.KIWI);
                helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.LASER_BEAM_NAME), KiwiModEntities.LASER_BEAM);
                helper.register(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KiwiModEntities.KIWI_EGG_NAME), KiwiModEntities.KIWI_EGG);
            });
        }

        @SubscribeEvent
        public static void createDefaultAttributes(EntityAttributeCreationEvent event){
            event.put(KiwiModEntities.KIWI, KiwiEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(KiwiModEntities.KIWI, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event){
            KiwiMod.addDispenserBehaviour();
        }

    }

    @Mod.EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CommonGameEventHandler{

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if(event.getEntity() instanceof Cat cat){
                KiwiModEntities.addCatGoal(cat, cat.targetSelector);
            }
        }

        @SubscribeEvent
        public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event){
            if(event.getEntity() instanceof Zombie zombie){
                event.setSpawnData(KiwiModEntities.finalizeZombieSpawn(zombie, event.getLevel(), event.getSpawnData(), event.getDifficulty()));
            }
        }

    }

    @Mod.EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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