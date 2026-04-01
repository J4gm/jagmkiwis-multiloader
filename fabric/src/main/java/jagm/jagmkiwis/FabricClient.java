package jagm.jagmkiwis;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
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
        ModelLayerRegistry.registerModelLayer(KiwiModel.KIWI_LAYER, KiwiModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(KiwiModel.BABY_KIWI_LAYER, () -> KiwiModel.createBodyLayer().apply(KiwiModel.BABY_TRANSFORMER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
            content.insertAfter(Items.MELON_SLICE, KiwiModItems.KIWI_FRUIT);
            content.insertAfter(Items.PUMPKIN_PIE, KiwiModItems.PAVLOVA);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.insertAfter(Items.LLAMA_SPAWN_EGG, KiwiModItems.KIWI_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(content -> content.insertAfter(Items.BLUE_EGG, KiwiModItems.KIWI_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(content -> content.insertAfter(Items.BLUE_EGG, KiwiModItems.KIWI_EGG));
    }

}
