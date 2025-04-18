package jagm.jagmkiwis;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class KiwiEggEntity extends ThrowableItemProjectile {

    public KiwiEggEntity(EntityType<? extends KiwiEggEntity> kiwiEgg, Level level){
        super(kiwiEgg, level);
    }

    public KiwiEggEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(KiwiModEntities.KIWI_EGG.get(), owner, level, stack);
    }

    public KiwiEggEntity(Level level, double x, double y, double z, ItemStack item) {
        super(KiwiModEntities.KIWI_EGG.get(), x, y, z, level, item);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if(this.level() instanceof ServerLevel serverLevel) {
            result.getEntity().hurtServer(serverLevel, this.damageSources().thrown(this, this.getOwner()), 0.0F);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }
                for(int j = 0; j < i; ++j) {
                    KiwiEntity kiwi = KiwiModEntities.KIWI.get().create(this.level(), EntitySpawnReason.TRIGGERED);
                    if (kiwi != null) {
                        kiwi.setAge(-24000);
                        kiwi.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        if (!kiwi.fudgePositionAfterSizeChange(EntityDimensions.fixed(0.0F, 0.0F))) {
                            break;
                        }
                        this.level().addFreshEntity(kiwi);
                    }
                }
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return KiwiModItems.KIWI_EGG.get();
    }

}
