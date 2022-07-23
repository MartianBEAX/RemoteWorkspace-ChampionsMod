package net.mcreator.championsmod.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.particles.ParticleTypes;

import net.mcreator.championsmod.ChampionsModMod;

import java.util.Map;

public class StormBreakerProjectileHitsProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency world for procedure StormBreakerProjectileHits!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency x for procedure StormBreakerProjectileHits!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency y for procedure StormBreakerProjectileHits!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				ChampionsModMod.LOGGER.warn("Failed to load dependency z for procedure StormBreakerProjectileHits!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).spawnParticle(ParticleTypes.ENCHANT, x, y, z, (int) 200, 1, 1, 1, 0.1);
		}
	}
}
