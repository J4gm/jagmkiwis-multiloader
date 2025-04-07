package jagm.jagmkiwis;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(KiwiModEntities.KIWI.get(), KiwiRenderer::new);
        EntityRendererRegistry.register(KiwiModEntities.LASER_BEAM.get(), LaserBeamRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(KiwiModel.KIWI_LAYER, KiwiModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(KiwiModel.BABY_KIWI_LAYER, () -> KiwiModel.createBodyLayer().apply(KiwiModel.BABY_TRANSFORMER));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
            content.addAfter(Items.MELON_SLICE, KiwiModItems.KIWI_FRUIT.get());
            content.addAfter(Items.PUMPKIN_PIE, KiwiModItems.PAVLOVA.get());
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> {
            content.addAfter(Items.LLAMA_SPAWN_EGG, KiwiModItems.KIWI_SPAWN_EGG.get());
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            content.addAfter(Items.EGG, KiwiModItems.KIWI_EGG.get());
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(content -> {
            content.addAfter(Items.EGG, KiwiModItems.KIWI_EGG.get());
        });
    }

}
