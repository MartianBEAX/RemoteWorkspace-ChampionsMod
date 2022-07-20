package net.mcreator.championsmod.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;

import net.mcreator.championsmod.ChampionsModMod;

import java.util.Map;

public class BrokenMjolnirEntityIsHitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency world for procedure BrokenMjolnirEntityIsHit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency entity for procedure BrokenMjolnirEntityIsHit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency sourceentity for procedure BrokenMjolnirEntityIsHit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (sourceentity instanceof PlayerEntity) {
			if (sourceentity.getMotion().getY() <= -0.1 && !sourceentity.isSprinting() && !sourceentity.isOnGround() && !sourceentity.isInWater()) {
				if (world instanceof ServerWorld) {
					LightningBoltEntity _ent = EntityType.LIGHTNING_BOLT.create((World) world);
					_ent.moveForced(Vector3d.copyCenteredHorizontally(new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ())));
					_ent.setEffectOnly(true);
					((World) world).addEntity(_ent);
				}
				entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) 2);
			}
		}
	}
}
