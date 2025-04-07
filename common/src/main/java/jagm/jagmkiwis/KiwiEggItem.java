package jagm.jagmkiwis;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class KiwiEggItem extends EggItem {

	public KiwiEggItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (level instanceof ServerLevel serverlevel) {
			ThrownEgg thrownEgg = new ThrownEgg(level, player){

				@Override
				protected void onHit(HitResult result) {
					// Code from Projectile.onHit
					HitResult.Type hitresult$type = result.getType();
					if (hitresult$type == HitResult.Type.ENTITY) {
						EntityHitResult entityhitresult = (EntityHitResult)result;
						Entity entity = entityhitresult.getEntity();
						if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile projectile) {
							projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
						}

						this.onHitEntity(entityhitresult);
						this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
					} else if (hitresult$type == HitResult.Type.BLOCK) {
						BlockHitResult blockhitresult = (BlockHitResult)result;
						this.onHitBlock(blockhitresult);
						BlockPos blockpos = blockhitresult.getBlockPos();
						this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
					}
					// end
					if (!this.level().isClientSide) {
						if (this.random.nextInt(8) == 0) {
							int i = 1;
							if (this.random.nextInt(32) == 0) {
								i = 4;
							}
							for(int j = 0; j < i; ++j) {
								KiwiEntity kiwi = KiwiModEntities.KIWI.get().create(this.level());
								if (kiwi != null) {
									kiwi.setAge(-24000);
									kiwi.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
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
			};
			thrownEgg.setItem(itemstack);
			thrownEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.25F, 1.0F);
			level.addFreshEntity(thrownEgg);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemstack.consume(1, player);
		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());

	}

}
