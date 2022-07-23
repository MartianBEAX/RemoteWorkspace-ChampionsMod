// Made with Blockbench 4.3.0
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public static class ModelStormBreaker extends EntityModel<Entity> {
	private final ModelRenderer StormBreaker;
	private final ModelRenderer cube_r3_r1;
	private final ModelRenderer cube_r4_r1;
	private final ModelRenderer cube_r4_r2;
	private final ModelRenderer cube_r5_r1;

	public ModelStormBreaker() {
		textureWidth = 32;
		textureHeight = 32;

		StormBreaker = new ModelRenderer(this);
		StormBreaker.setRotationPoint(0.0444F, 9.9847F, -0.5567F);
		StormBreaker.setTextureOffset(5, 5).addBox(-1.0444F, -1.5847F, -0.4433F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		StormBreaker.setTextureOffset(0, 0).addBox(-0.5444F, -1.8847F, 0.0567F, 1.0F, 15.0F, 1.0F, 0.0F, false);
		StormBreaker.setTextureOffset(5, 15).addBox(-0.5444F, -1.1847F, 1.2567F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		StormBreaker.setTextureOffset(14, 4).addBox(-0.5444F, -1.6847F, 2.2567F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		StormBreaker.setTextureOffset(12, 12).addBox(-0.5444F, -1.6847F, 3.2567F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		cube_r3_r1 = new ModelRenderer(this);
		cube_r3_r1.setRotationPoint(0.0556F, -0.3899F, -3.112F);
		StormBreaker.addChild(cube_r3_r1);
		setRotationAngle(cube_r3_r1, 0.3927F, 0.0F, 0.0F);
		cube_r3_r1.setTextureOffset(24, 26).addBox(-0.5F, -0.85F, -1.025F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		cube_r4_r1 = new ModelRenderer(this);
		cube_r4_r1.setRotationPoint(0.0556F, -1.1473F, -2.5656F);
		StormBreaker.addChild(cube_r4_r1);
		setRotationAngle(cube_r4_r1, -0.3927F, 0.0F, 0.0F);
		cube_r4_r1.setTextureOffset(23, 25).addBox(-0.5F, -1.0F, -1.55F, 1.0F, 2.0F, 3.0F, 0.0F, false);

		cube_r4_r2 = new ModelRenderer(this);
		cube_r4_r2.setRotationPoint(0.0556F, -1.6974F, -2.0405F);
		StormBreaker.addChild(cube_r4_r2);
		setRotationAngle(cube_r4_r2, -0.3927F, 0.0F, 0.0F);
		cube_r4_r2.setTextureOffset(22, 26).addBox(-0.5F, -0.5F, -1.8F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r5_r1 = new ModelRenderer(this);
		cube_r5_r1.setRotationPoint(0.0556F, 0.0525F, -1.8386F);
		StormBreaker.addChild(cube_r5_r1);
		setRotationAngle(cube_r5_r1, 0.4363F, 0.0F, 0.0F);
		cube_r5_r1.setTextureOffset(22, 24).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		StormBreaker.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
	}
}