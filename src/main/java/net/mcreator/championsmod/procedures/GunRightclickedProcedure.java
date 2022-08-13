package net.mcreator.championsmod.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.Hand;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.championsmod.item.GunItem;
import net.mcreator.championsmod.item.BulletItem;
import net.mcreator.championsmod.ChampionsModMod;

import java.util.Map;

public class GunRightclickedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency world for procedure GunRightclicked!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency entity for procedure GunRightclicked!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() == GunItem.block
				&& ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == GunItem.block) {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).swing(Hand.OFF_HAND, true);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE,
						(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
								RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
						(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
								RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
						(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
								RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ()),
						(int) 5, 0.1, 0.1, 0.1, 0);
			}
			{
				Entity _shootFrom = entity;
				World projectileLevel = _shootFrom.world;
				if (!projectileLevel.isRemote()) {
					ProjectileEntity _entityToSpawn = new Object() {
						public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
							AbstractArrowEntity entityToSpawn = new BulletItem.ArrowCustomEntity(BulletItem.arrow, world);
							entityToSpawn.setShooter(shooter);
							entityToSpawn.setDamage(damage);
							entityToSpawn.setKnockbackStrength(knockback);
							entityToSpawn.setSilent(true);

							return entityToSpawn;
						}
					}.getArrow(projectileLevel, entity, 5, 1);
					_entityToSpawn.setPosition(_shootFrom.getPosX(), _shootFrom.getPosYEye() - 0.1, _shootFrom.getPosZ());
					_entityToSpawn.shoot(_shootFrom.getLookVec().x, _shootFrom.getLookVec().y, _shootFrom.getLookVec().z, (float) 1.75, 0);
					projectileLevel.addEntity(_entityToSpawn);
				}
			}
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).getCooldownTracker().setCooldown(
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem(), (int) 40);
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).getCooldownTracker().setCooldown(
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem(), (int) 40);
			new Object() {
				private int ticks = 0;
				private float waitTicks;
				private IWorld world;

				public void start(IWorld world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}

				private void run() {
					if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
							.getItem() == GunItem.block) {
						if (entity instanceof LivingEntity) {
							((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
						}
						if (world instanceof ServerWorld) {
							((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE,
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2,
													entity.getLook(1f).z * 2),
											RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2,
													entity.getLook(1f).z * 2),
											RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2,
													entity.getLook(1f).z * 2),
											RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ()),
									(int) 5, 0.1, 0.1, 0.1, 0);
						}
						{
							Entity _shootFrom = entity;
							World projectileLevel = _shootFrom.world;
							if (!projectileLevel.isRemote()) {
								ProjectileEntity _entityToSpawn = new Object() {
									public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
										AbstractArrowEntity entityToSpawn = new BulletItem.ArrowCustomEntity(BulletItem.arrow, world);
										entityToSpawn.setShooter(shooter);
										entityToSpawn.setDamage(damage);
										entityToSpawn.setKnockbackStrength(knockback);
										entityToSpawn.setSilent(true);

										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, 5, 1);
								_entityToSpawn.setPosition(_shootFrom.getPosX(), _shootFrom.getPosYEye() - 0.1, _shootFrom.getPosZ());
								_entityToSpawn.shoot(_shootFrom.getLookVec().x, _shootFrom.getLookVec().y, _shootFrom.getLookVec().z, (float) 1.75,
										0);
								projectileLevel.addEntity(_entityToSpawn);
							}
						}
					}
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 5);
		} else {
			if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() == GunItem.block) {
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).swing(Hand.OFF_HAND, true);
				}
				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE,
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ()),
							(int) 5, 0.1, 0.1, 0.1, 0);
				}
				{
					Entity _shootFrom = entity;
					World projectileLevel = _shootFrom.world;
					if (!projectileLevel.isRemote()) {
						ProjectileEntity _entityToSpawn = new Object() {
							public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
								AbstractArrowEntity entityToSpawn = new BulletItem.ArrowCustomEntity(BulletItem.arrow, world);
								entityToSpawn.setShooter(shooter);
								entityToSpawn.setDamage(damage);
								entityToSpawn.setKnockbackStrength(knockback);
								entityToSpawn.setSilent(true);

								return entityToSpawn;
							}
						}.getArrow(projectileLevel, entity, 5, 1);
						_entityToSpawn.setPosition(_shootFrom.getPosX(), _shootFrom.getPosYEye() - 0.1, _shootFrom.getPosZ());
						_entityToSpawn.shoot(_shootFrom.getLookVec().x, _shootFrom.getLookVec().y, _shootFrom.getLookVec().z, (float) 1.75, 0);
						projectileLevel.addEntity(_entityToSpawn);
					}
				}
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldownTracker().setCooldown(
							((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem(), (int) 30);
			} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
					.getItem() == GunItem.block) {
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
				}
				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE,
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2, entity.getLook(1f).y * 2, entity.getLook(1f).z * 2),
									RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ()),
							(int) 5, 0.1, 0.1, 0.1, 0);
				}
				{
					Entity _shootFrom = entity;
					World projectileLevel = _shootFrom.world;
					if (!projectileLevel.isRemote()) {
						ProjectileEntity _entityToSpawn = new Object() {
							public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
								AbstractArrowEntity entityToSpawn = new BulletItem.ArrowCustomEntity(BulletItem.arrow, world);
								entityToSpawn.setShooter(shooter);
								entityToSpawn.setDamage(damage);
								entityToSpawn.setKnockbackStrength(knockback);
								entityToSpawn.setSilent(true);

								return entityToSpawn;
							}
						}.getArrow(projectileLevel, entity, 5, 1);
						_entityToSpawn.setPosition(_shootFrom.getPosX(), _shootFrom.getPosYEye() - 0.1, _shootFrom.getPosZ());
						_entityToSpawn.shoot(_shootFrom.getLookVec().x, _shootFrom.getLookVec().y, _shootFrom.getLookVec().z, (float) 1.75, 0);
						projectileLevel.addEntity(_entityToSpawn);
					}
				}
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldownTracker().setCooldown(
							((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem(), (int) 30);
			}
		}
	}
}
