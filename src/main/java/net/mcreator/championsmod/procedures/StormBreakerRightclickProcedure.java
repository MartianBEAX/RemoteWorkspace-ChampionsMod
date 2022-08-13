package net.mcreator.championsmod.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import net.mcreator.championsmod.item.StormBreakerProjectileItem;
import net.mcreator.championsmod.item.StormBreakerItem;
import net.mcreator.championsmod.ChampionsModMod;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;
import java.util.Map;

public class StormBreakerRightclickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency world for procedure StormBreakerRightclick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency entity for procedure StormBreakerRightclick!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (!entity.isSneaking()) {
			if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getOrCreateTag()
					.getDouble("Cooldown") <= 0
					|| ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getOrCreateTag()
							.getDouble("Cooldown") <= 0) {
				{
					AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
							.ifPresent(capability -> _iitemhandlerref.set(capability));
					if (_iitemhandlerref.get() != null) {
						for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
							ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
							if (itemstackiterator.getItem() == StormBreakerItem.block) {
								if (entity instanceof PlayerEntity)
									((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 100);
								itemstackiterator.getOrCreateTag().putDouble("Cooldown", 100);
							}
							if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
									.getItem() == StormBreakerItem.block) {
								if (entity instanceof LivingEntity) {
									((LivingEntity) entity).swing(Hand.OFF_HAND, true);
								}
							} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
									.getItem() == StormBreakerItem.block) {
								if (entity instanceof LivingEntity) {
									((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
								}
							}
						}
					}
				}
				{
					Entity _shootFrom = entity;
					World projectileLevel = _shootFrom.world;
					if (!projectileLevel.isRemote()) {
						ProjectileEntity _entityToSpawn = new Object() {
							public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
								AbstractArrowEntity entityToSpawn = new StormBreakerProjectileItem.ArrowCustomEntity(StormBreakerProjectileItem.arrow,
										world);
								entityToSpawn.setShooter(shooter);
								entityToSpawn.setDamage(damage);
								entityToSpawn.setKnockbackStrength(knockback);
								entityToSpawn.setSilent(true);

								return entityToSpawn;
							}
						}.getArrow(projectileLevel, entity, 10, 5);
						_entityToSpawn.setPosition(_shootFrom.getPosX(), _shootFrom.getPosYEye() - 0.1, _shootFrom.getPosZ());
						_entityToSpawn.shoot(_shootFrom.getLookVec().x, _shootFrom.getLookVec().y, _shootFrom.getLookVec().z, (float) 1.5, 0);
						projectileLevel.addEntity(_entityToSpawn);
					}
				}
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
						if (entity instanceof PlayerEntity) {
							ItemStack _stktoremove = new ItemStack(StormBreakerItem.block);
							((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
									((PlayerEntity) entity).container.func_234641_j_());
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 3);
				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticle(ParticleTypes.ENCHANT,
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2.5, entity.getLook(1f).y * 2.5, entity.getLook(1f).z * 2.5),
									RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2.5, entity.getLook(1f).y * 2.5, entity.getLook(1f).z * 2.5),
									RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
							(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
									entity.getEyePosition(1f).add(entity.getLook(1f).x * 2.5, entity.getLook(1f).y * 2.5, entity.getLook(1f).z * 2.5),
									RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ()),
							(int) 125, 0.5, 0.5, 0.5, 0.1);
				}
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
								.getItem() == Blocks.AIR.asItem()) {
							if (entity instanceof LivingEntity) {
								ItemStack _setstack = new ItemStack(StormBreakerItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
							}
						} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
								.getItem() == Blocks.AIR.asItem()) {
							if (entity instanceof LivingEntity) {
								ItemStack _setstack = new ItemStack(StormBreakerItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
							}
						} else {
							if (entity instanceof PlayerEntity) {
								ItemStack _setstack = new ItemStack(StormBreakerItem.block);
								_setstack.setCount((int) 1);
								ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:storm_breaker_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
							}
						}
						{
							AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
									.ifPresent(capability -> _iitemhandlerref.set(capability));
							if (_iitemhandlerref.get() != null) {
								for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
									ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
									if (itemstackiterator.getItem() == StormBreakerItem.block) {
										if (entity instanceof PlayerEntity)
											((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 100);
										itemstackiterator.getOrCreateTag().putDouble("Cooldown", 100);
									}
									if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
											.getItem() == StormBreakerItem.block) {
										if (entity instanceof LivingEntity) {
											((LivingEntity) entity).swing(Hand.OFF_HAND, true);
										}
									} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
											.getItem() == StormBreakerItem.block) {
										if (entity instanceof LivingEntity) {
											((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
										}
									}
								}
							}
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 125);
			}
		}
	}
}
