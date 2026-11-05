package dev.janrhey.swifthotbar.mixin;

import dev.janrhey.swifthotbar.SwiftHotbarConfig;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * When you switch hotbar slots, the held item lowers and raises again. Vanilla moves that
 * animation by at most 0.4 per tick (about 3 ticks down + 3 ticks up). We scale that step
 * so the animation finishes sooner. This only changes what you SEE: the slot change itself,
 * attack cooldown and everything the server checks are not touched.
 */
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
	@ModifyConstant(
			method = "tick",
			constant = {
					@Constant(floatValue = 0.4F),
					@Constant(floatValue = -0.4F)
			}
	)
	private float swifthotbar$speedUpEquipAnimation(float original) {
		if (!SwiftHotbarConfig.isEnabled()) {
			return original;
		}
		return original * SwiftHotbarConfig.speed();
	}
}
