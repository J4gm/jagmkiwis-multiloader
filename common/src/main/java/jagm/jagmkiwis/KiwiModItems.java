package jagm.jagmkiwis;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.HashMap;
import java.util.function.Function;

public class KiwiModItems {

    public static final HashMap<String, Supplier<Item>> ITEMS_COMMON = new HashMap<>();

    public static final Supplier<Item> KIWI_EGG = createItemSupplier(
            "kiwi_egg",
            KiwiEggItem::new,
            new Item.Properties().stacksTo(16)
    );
    public static final Supplier<Item> KIWI_FRUIT = createItemSupplier(
            "kiwi_fruit",
            Item::new,
            new Item.Properties().food(Foods.APPLE)
    );
    public static final Supplier<Item> KIWI_SPAWN_EGG = createItemSupplier(
            "kiwi_spawn_egg",
            props -> new SpawnEggItem(KiwiModEntities.KIWI.get(), 0x97784A, 0xBEE000, props),
            new Item.Properties()
    );
    public static final Supplier<Item> PAVLOVA = createItemSupplier(
            "pavlova",
            Item::new,
            new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.6F).build())
    );

    private static Supplier<Item> createItemSupplier (String name, Function<Item.Properties, Item> factory, Item.Properties props){
        Supplier<Item> itemSupplier = Suppliers.memoize(() -> factory.apply(props));
        ITEMS_COMMON.put(name, itemSupplier);
        return itemSupplier;
    }

}
