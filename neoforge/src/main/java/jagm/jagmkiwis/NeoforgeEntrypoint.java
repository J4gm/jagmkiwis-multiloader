package jagm.jagmkiwis;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(KiwiMod.MOD_ID)
public class NeoforgeEntrypoint {

    static final DeferredRegister.Items ITEMS_NEOFORGE = DeferredRegister.createItems(KiwiMod.MOD_ID);
    static final DeferredRegister<EntityType<?>> ENTITIES_NEOFORGE = DeferredRegister.create(Registries.ENTITY_TYPE, KiwiMod.MOD_ID);
    static final DeferredRegister<SoundEvent> SOUNDS_NEOFORGE = DeferredRegister.create(Registries.SOUND_EVENT, KiwiMod.MOD_ID);

    public NeoforgeEntrypoint(IEventBus eventBus) {
        KiwiMod.init();
        KiwiModItems.ITEMS_COMMON.forEach(ITEMS_NEOFORGE::register);
        ENTITIES_NEOFORGE.register(KiwiModEntities.KIWI_NAME, KiwiModEntities.KIWI);
        ENTITIES_NEOFORGE.register(KiwiModEntities.LASER_BEAM_NAME, KiwiModEntities.LASER_BEAM);
        KiwiModSounds.SOUNDS_COMMON.forEach(SOUNDS_NEOFORGE::register);
        ITEMS_NEOFORGE.register(eventBus);
        ENTITIES_NEOFORGE.register(eventBus);
        SOUNDS_NEOFORGE.register(eventBus);
    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModEventHandler{

        @SubscribeEvent
        public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
            event.put(KiwiModEntities.KIWI.get(), KiwiEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(KiwiModEntities.KIWI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class CommonGameEventHandler{

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if(event.getEntity() instanceof Cat cat){
                KiwiModEntities.addCatGoal(cat, cat.targetSelector);
            }
        }

        @SubscribeEvent
        public static void finalizeSpawnEvent(FinalizeSpawnEvent event){
            if(event.getEntity() instanceof Zombie zombie){
                event.setSpawnData(KiwiModEntities.finalizeZombieSpawn(zombie, event.getLevel(), event.getSpawnData(), event.getDifficulty()));
            }
        }

    }

    @EventBusSubscriber(modid = KiwiMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEventHandler{

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(KiwiModEntities.KIWI.get(), KiwiRenderer::new);
            event.registerEntityRenderer(KiwiModEntities.LASER_BEAM.get(), LaserBeamRenderer::new);
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