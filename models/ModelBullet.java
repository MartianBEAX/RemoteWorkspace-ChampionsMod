// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public static class ModelBullet extends EntityModel<Entity> {
	private final ModelRenderer Bullet;

	public ModelBullet() {
		textureWidth = 32;
		textureHeight = 32;

		Bullet = new ModelRenderer(this);
		Bullet.setRotationPoint(0.0F, 20.6F, 0.38F);
		setRotationAngle(Bullet, -1.5708F, 0.0F, 0.0F);
		Bullet.setTextureOffset(11, 0).addBox(-1.0F, -1.0F, -2.62F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		Bullet.setTextureOffset(8, 12).addBox(-0.5F, -0.5F, -3.02F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Bullet.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -2.12F, 3.0F, 3.0F, 4.0F, 0.0F, false);
		Bullet.setTextureOffset(9, 8).addBox(-1.0F, -1.0F, 1.38F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		Bullet.setTextureOffset(0, 8).addBox(-1.5F, -1.5F, 2.38F, 3.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		Bullet.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
	}
}