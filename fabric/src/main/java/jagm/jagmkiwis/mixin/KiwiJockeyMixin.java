package jagm.jagmkiwis.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import jagm.jagmkiwis.KiwiModEntities;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class KiwiJockeyMixin {

    @Inject(at = @At("HEAD"), method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;")
    private void finalizeSpawnInjector(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> info, @Local LocalRef<SpawnGroupData> spawnGroupDataLocalRef){
        spawnGroupDataLocalRef.set(KiwiModEntities.finalizeZombieSpawn((Zombie)(Object)this, level, spawnGroupData, difficulty));
    }

}
