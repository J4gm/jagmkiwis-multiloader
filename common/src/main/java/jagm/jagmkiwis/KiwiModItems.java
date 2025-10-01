package jagm.jagmkiwis;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;

import java.util.HashMap;
import java.util.function.Function;

public class KiwiModItems {

    public static final HashMap<String, Item> ITEMS_COMMON = new HashMap<>();

    public static final Item KIWI_EGG = createItem(
            "kiwi_egg",
            KiwiEggItem::new,
            new Item.Properties().stacksTo(16)
    );
    public static final Item KIWI_FRUIT = createItem(
            "kiwi_fruit",
            Item::new,
            new Item.Properties().food(Foods.APPLE)
    );
    public static final Item KIWI_SPAWN_EGG = createItem(
            "kiwi_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().component(DataComponents.ENTITY_DATA, TypedEntityData.of(KiwiModEntities.KIWI, new CompoundTag()))
    );
    public static final Item PAVLOVA = createItem(
            "pavlova",
            Item::new,
            new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.6F).build())
    );

    private static Item createItem (String name, Function<Item.Properties, Item> factory, Item.Properties props){
        Item item = factory.apply(props.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, name))));
        ITEMS_COMMON.put(name, item);
        return item;
    }

}
