package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(KiwiMod.MOD_ID)
public class ForgeEntrypoint {

    static final DeferredRegister<Item> ITEMS_FORGE = DeferredRegister.create(ForgeRegistries.ITEMS, KiwiMod.MOD_ID);
    static final DeferredRegister<EntityType<?>> ENTITIES_FORGE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KiwiMod.MOD_ID);
    static final DeferredRegister<SoundEvent> SOUNDS_FORGE = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KiwiMod.MOD_ID);

    public ForgeEntrypoint(FMLJavaModLoadingContext context) {
        KiwiMod.init();
        IEventBus eventBus = context.getModEventBus();
        KiwiModItems.ITEMS_COMMON.forEach(ITEMS_FORGE::register);
        ENTITIES_FORGE.register(KiwiModEntities.KIWI_NAME, KiwiModEntities.KIWI);
        ENTITIES_FORGE.register(KiwiModEntities.LASER_BEAM_NAME, KiwiModEntities.LASER_BEAM);
        ENTITIES_FORGE.register(KiwiModEntities.KIWI_EGG_NAME, KiwiModEntities.KIWI_EGG);
        KiwiModSounds.SOUNDS_COMMON.forEach(SOUNDS_FORGE::register);
        ITEMS_FORGE.register(eventBus);
        ENTITIES_FORGE.register(eventBus);
        SOUNDS_FORGE.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEventHandler{

        @SubscribeEvent
        public static void createDefaultAttributes(EntityAttributeCreationEvent event){
            event.put(KiwiModEntities.KIWI.get(), KiwiEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(KiwiModEntities.KIWI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.REPLACE);
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
            event.registerEntityRenderer(KiwiModEntities.KIWI.get(), KiwiRenderer::new);
            event.registerEntityRenderer(KiwiModEntities.LASER_BEAM.get(), LaserBeamRenderer::new);
            event.registerEntityRenderer(KiwiModEntities.KIWI_EGG.get(), ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(KiwiModel.KIWI_LAYER, KiwiModel::createBodyLayer);
            event.registerLayerDefinition(KiwiModel.BABY_KIWI_LAYER, () -> KiwiModel.createBodyLayer().apply(KiwiModel.BABY_TRANSFORMER));
        }

        @SubscribeEvent
        public static void onFillCreativeTabs(BuildCreativeModeTabContentsEvent event){
            if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
                event.accept(KiwiModItems.KIWI_FRUIT.get());
                event.accept(KiwiModItems.PAVLOVA.get());
            }
            else if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
                event.accept(KiwiModItems.KIWI_SPAWN_EGG.get());
            }
            else if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
                event.accept(KiwiModItems.KIWI_EGG.get());
            }
            else if(event.getTabKey() == CreativeModeTabs.COMBAT){
                event.accept(KiwiModItems.KIWI_EGG.get());
            }
        }

    }

}