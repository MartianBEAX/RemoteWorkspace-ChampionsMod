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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import net.mcreator.championsmod.item.MjolnirShardItem;
import net.mcreator.championsmod.item.BrokenMjolnirProjectileItem;
import net.mcreator.championsmod.item.BrokenMjolnirItem;
import net.mcreator.championsmod.ChampionsModMod;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;
import java.util.Map;

public class BrokenMjolnirRightclickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency world for procedure BrokenMjolnirRightclick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency entity for procedure BrokenMjolnirRightclick!");
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
							if (itemstackiterator.getItem() == BrokenMjolnirItem.block) {
								if (entity instanceof PlayerEntity)
									((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 45);
								itemstackiterator.getOrCreateTag().putDouble("Cooldown", 45);
							}
						}
					}
				}
				if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
						.getItem() == BrokenMjolnirItem.block) {
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.OFF_HAND, true);
					}
				} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
						.getItem() == BrokenMjolnirItem.block) {
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
					}
				}
				if (entity instanceof LivingEntity) {
					LivingEntity _ent = (LivingEntity) entity;
					if (!_ent.world.isRemote()) {
						BrokenMjolnirProjectileItem.shoot(_ent.world, _ent, new Random(), (float) 1.25, 8, 5);
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
							ItemStack _stktoremove = new ItemStack(BrokenMjolnirItem.block);
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
							(int) 100, 0.4, 0.4, 0.4, 0.25);
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
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)), false);
							}
						} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
								.getItem() == Blocks.AIR.asItem()) {
							if (entity instanceof LivingEntity) {
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)), false);
							}
						} else {
							if (entity instanceof PlayerEntity) {
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjonir_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.5, 1)), false);
							}
						}
						{
							AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
									.ifPresent(capability -> _iitemhandlerref.set(capability));
							if (_iitemhandlerref.get() != null) {
								for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
									ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
									if (itemstackiterator.getItem() == BrokenMjolnirItem.block) {
										if (entity instanceof PlayerEntity)
											((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 45);
										itemstackiterator.getOrCreateTag().putDouble("Cooldown", 45);
									}
								}
							}
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 75);
			}
		} else if (entity.isSneaking()) {
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
							if (itemstackiterator.getItem() == BrokenMjolnirItem.block) {
								if (entity instanceof PlayerEntity)
									((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 30);
								itemstackiterator.getOrCreateTag().putDouble("Cooldown", 30);
							}
						}
					}
				}
				if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
						.getItem() == BrokenMjolnirItem.block) {
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
					}
				} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
						.getItem() == BrokenMjolnirItem.block) {
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.OFF_HAND, true);
					}
				}
				if (world instanceof World && !world.isRemote()) {
					((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("champions_mod:mjolnir_shard_throw")),
							SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
				} else {
					((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("champions_mod:mjolnir_shard_throw")),
							SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
				}
				for (int index0 = 0; index0 < (int) (7); index0++) {
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
							if (entity instanceof LivingEntity) {
								LivingEntity _ent = (LivingEntity) entity;
								if (!_ent.world.isRemote()) {
									MjolnirShardItem.shoot(_ent.world, _ent, new Random(), (float) MathHelper.nextDouble(new Random(), 1, 1.25), 3,
											1);
								}
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, (int) (MathHelper.nextDouble(new Random(), 0, 5)));
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
							ItemStack _stktoremove = new ItemStack(BrokenMjolnirItem.block);
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
							(int) 125, 0.5, 0.5, 0.5, 0.25);
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
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
							}
						} else if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
								.getItem() == Blocks.AIR.asItem()) {
							if (entity instanceof LivingEntity) {
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
								if (entity instanceof ServerPlayerEntity)
									((ServerPlayerEntity) entity).inventory.markDirty();
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)), false);
							}
						} else {
							if (entity instanceof PlayerEntity) {
								ItemStack _setstack = new ItemStack(BrokenMjolnirItem.block);
								_setstack.setCount((int) 1);
								ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
							}
							if (world instanceof World && !world.isRemote()) {
								((World) world).playSound(null, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
										SoundCategory.MASTER, (float) 1.5, (float) (MathHelper.nextDouble(new Random(), 0.75, 1)));
							} else {
								((World) world).playSound((entity.getPosX()), (entity.getPosY()), (entity.getPosZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("champions_mod:mjolnir_shards_return")),
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
									if (itemstackiterator.getItem() == BrokenMjolnirItem.block) {
										if (entity instanceof PlayerEntity)
											((PlayerEntity) entity).getCooldownTracker().setCooldown(itemstackiterator.getItem(), (int) 30);
										itemstackiterator.getOrCreateTag().putDouble("Cooldown", 30);
									}
								}
							}
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 75);
			}
		}
	}
}
