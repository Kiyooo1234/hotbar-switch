package dev.janrhey.swifthotbar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/** Simple settings screen (opened from Mod Menu): on/off button and speed button. */
public class SwiftHotbarConfigScreen extends Screen {
	private static final float[] SPEEDS = {1.0F, 1.5F, 2.0F, 2.5F};

	private final Screen parent;

	public SwiftHotbarConfigScreen(Screen parent) {
		super(Component.literal("Swift Hotbar"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int cy = this.height / 2;

		this.addRenderableWidget(Button.builder(toggleText(), button -> {
			SwiftHotbarConfig.setEnabled(!SwiftHotbarConfig.isEnabled());
			button.setMessage(toggleText());
		}).bounds(cx - 100, cy - 24, 200, 20).build());

		this.addRenderableWidget(Button.builder(speedText(), button -> {
			SwiftHotbarConfig.setSpeed(nextSpeed(SwiftHotbarConfig.speed()));
			button.setMessage(speedText());
		}).bounds(cx - 100, cy, 200, 20).build());

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
				.bounds(cx - 100, cy + 36, 200, 20).build());
	}

	private static float nextSpeed(float current) {
		for (float s : SPEEDS) {
			if (s > current + 0.01F) {
				return s;
			}
		}
		return SPEEDS[0];
	}

	private static Component toggleText() {
		return Component.literal("Swift Hotbar: " + (SwiftHotbarConfig.isEnabled() ? "ON" : "OFF"));
	}

	private static Component speedText() {
		return Component.literal("Switch animation speed: " + SwiftHotbarConfig.speed() + "x (vanilla 1.0x)");
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
	}

	@Override
	public void onClose() {
		SwiftHotbarConfig.save();
		this.minecraft.setScreen(this.parent);
	}
}
