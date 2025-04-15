package jagm.jagmkiwis;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class LaserBeamEntity extends AbstractArrow {

    protected LaserBeamEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    protected LaserBeamEntity(Level level, LivingEntity shooter) {
        super(KiwiModEntities.LASER_BEAM.get(), level);
        this.setOwner(shooter);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        // Do not set to null or empty; this causes a crash when saving the entity.
        return new ItemStack(Items.ARROW);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockState blockstate = this.level().getBlockState(hitResult.getBlockPos());
        blockstate.onProjectileHit(this.level(), blockstate, hitResult, this);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        float f = (float) this.getDeltaMovement().length();
        double baseDamage = 4.0D;
        int i = Mth.ceil(Mth.clamp((double) f * baseDamage, 0.0D, Integer.MAX_VALUE));
        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int) Math.min(j + (long) i, 2147483647L);
        }
        Entity shooter = this.getOwner();
        DamageSource damagesource;
        if (shooter == null) {
            damagesource = this.damageSources().arrow(this, this);
        } else {
            damagesource = this.damageSources().arrow(this, shooter);
            if (shooter instanceof LivingEntity) {
                ((LivingEntity) shooter).setLastHurtMob(target);
            }
        }
        if(this.level() instanceof ServerLevel serverLevel){
            boolean flag = target.getType() == EntityType.ENDERMAN;
            if (target.hurtServer(serverLevel, damagesource, (float) i)) {
                if (flag) {
                    return;
                }
                if (target instanceof LivingEntity livingEntity) {
                    if(shooter instanceof LivingEntity) {
                        EnchantmentHelper.doPostAttackEffects(serverLevel, target, damagesource);
                    }
                    this.doPostHurtEffects(livingEntity);
                }
            }
        }

        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getDeltaMovement().length() < 1.0D) {
            if(this.level().isClientSide){
                this.makePoofParticles();
            }
            else {
                this.playSound(SoundEvents.FIRE_EXTINGUISH);
            }
            this.discard();
        }
    }

    private void makePoofParticles() {
        for(int i = 0; i < 10; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            double g = 10.0;
            this.level().addParticle(ParticleTypes.POOF, this.getRandomX(1.0) - d * g, this.getRandomY() - e * g, this.getRandomZ(1.0) - f * g, d, e, f);
        }
    }

}
