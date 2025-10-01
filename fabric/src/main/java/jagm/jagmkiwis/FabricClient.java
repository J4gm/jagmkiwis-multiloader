package jagm.jagmkiwis;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRenderers.register(KiwiModEntities.KIWI, KiwiRenderer::new);
        EntityRenderers.register(KiwiModEntities.LASER_BEAM, LaserBeamRenderer::new);
        EntityRenderers.register(KiwiModEntities.KIWI_EGG, ThrownItemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(KiwiModel.KIWI_LAYER, KiwiModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(KiwiModel.BABY_KIWI_LAYER, () -> KiwiModel.createBodyLayer().apply(KiwiModel.BABY_TRANSFORMER));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
            content.addAfter(Items.MELON_SLICE, KiwiModItems.KIWI_FRUIT);
            content.addAfter(Items.PUMPKIN_PIE, KiwiModItems.PAVLOVA);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.addAfter(Items.LLAMA_SPAWN_EGG, KiwiModItems.KIWI_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> content.addAfter(Items.BLUE_EGG, KiwiModItems.KIWI_EGG));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(content -> content.addAfter(Items.BLUE_EGG, KiwiModItems.KIWI_EGG));
    }

}
