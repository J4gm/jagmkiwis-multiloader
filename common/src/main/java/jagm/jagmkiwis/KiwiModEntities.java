package jagm.jagmkiwis;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;

public class KiwiModEntities {

    public static final String KIWI_NAME = "kiwi";
    public static final String LASER_BEAM_NAME = "laser_beam";

    public static final Supplier<EntityType<KiwiEntity>> KIWI = Suppliers.memoize(() -> EntityType.Builder
            .of(KiwiEntity::new, MobCategory.CREATURE)
            .clientTrackingRange(8)
            .sized(0.5F, 0.5F)
            .build(KIWI_NAME)
    );
    public static final Supplier<EntityType<LaserBeamEntity>> LASER_BEAM = Suppliers.memoize(() -> EntityType.Builder
            .of((EntityType.EntityFactory<LaserBeamEntity>)LaserBeamEntity::new, MobCategory.MISC)
            .updateInterval(20)
            .sized(0.5F, 0.5F)
            .build(LASER_BEAM_NAME)
    );

    public static void addCatGoal(Cat cat, GoalSelector targetSelector){
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(cat, KiwiEntity.class, true));
    }

}
