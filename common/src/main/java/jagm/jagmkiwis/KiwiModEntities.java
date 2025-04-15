package jagm.jagmkiwis;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.List;

public class KiwiModEntities {

    public static final String KIWI_NAME = "kiwi";
    public static final String LASER_BEAM_NAME = "laser_beam";

    public static final float KIWI_JOCKEY_CHANCE = 0.05F;

    public static final Supplier<EntityType<KiwiEntity>> KIWI = Suppliers.memoize(() -> EntityType.Builder
            .of(KiwiEntity::new, MobCategory.CREATURE)
            .clientTrackingRange(8)
            .sized(0.5F, 0.5F)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, KIWI_NAME)))
    );
    public static final Supplier<EntityType<LaserBeamEntity>> LASER_BEAM = Suppliers.memoize(() -> EntityType.Builder
            .of((EntityType.EntityFactory<LaserBeamEntity>)LaserBeamEntity::new, MobCategory.MISC)
            .noLootTable()
            .updateInterval(20)
            .sized(0.5F, 0.5F)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, LASER_BEAM_NAME)))
    );

    public static void addCatGoal(Cat cat, GoalSelector targetSelector){
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(cat, KiwiEntity.class, true));
    }

    public static SpawnGroupData finalizeZombieSpawn(Zombie zombie, ServerLevelAccessor level, SpawnGroupData spawnGroupData, DifficultyInstance difficulty){
        RandomSource random = level.getRandom();
        if(spawnGroupData == null){
            spawnGroupData = new Zombie.ZombieGroupData(Zombie.getSpawnAsBabyOdds(random), true);
        }
        if(spawnGroupData instanceof Zombie.ZombieGroupData zombieGroupData){
            if(zombieGroupData.isBaby && zombieGroupData.canSpawnJockey){
                if (random.nextFloat() < KIWI_JOCKEY_CHANCE) {
                    List<KiwiEntity> list = level.getEntitiesOfClass(KiwiEntity.class, zombie.getBoundingBox().inflate(5.0F, 3.0F, 5.0F), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
                    if (!list.isEmpty()) {
                        KiwiEntity kiwi = list.getFirst();
                        kiwi.isKiwiJockey = true;
                        zombie.startRiding(kiwi);
                        return new Zombie.ZombieGroupData(true, false);
                    }
                } else if (random.nextFloat() < KIWI_JOCKEY_CHANCE) {
                    KiwiEntity kiwi = KIWI.get().create(zombie.level(), EntitySpawnReason.JOCKEY);
                    if (kiwi != null) {
                        kiwi.snapTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), 0.0F);
                        kiwi.finalizeSpawn(level, difficulty, EntitySpawnReason.JOCKEY, null);
                        kiwi.isKiwiJockey = true;
                        zombie.startRiding(kiwi);
                        level.addFreshEntity(kiwi);
                        return new Zombie.ZombieGroupData(true, false);
                    }
                }
            }
        }
        return spawnGroupData;
    }

}
