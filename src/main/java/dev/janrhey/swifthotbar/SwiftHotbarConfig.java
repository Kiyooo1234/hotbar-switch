package dev.janrhey.swifthotbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tiny JSON config: config/swifthotbar.json
 *
 * enabled: master on/off switch (also in the Mod Menu settings screen).
 * speed:   multiplier for the hotbar switch animation. 1.0 = vanilla,
 *          2.0 = twice as fast, 2.5 = maximum (the animation finishes in one tick).
 */
public final class SwiftHotbarConfig {
	public static final float VANILLA_SPEED = 1.0F;
	public static final float MAX_SPEED = 2.5F;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("swifthotbar.json");

	/** Written to / read from the JSON file. */
	public boolean enabled = true;
	public float speed = 2.0F;

	private static SwiftHotbarConfig instance = new SwiftHotbarConfig();

	private SwiftHotbarConfig() {
	}

	public static boolean isEnabled() {
		return instance.enabled;
	}

	public static void setEnabled(boolean enabled) {
		instance.enabled = enabled;
	}

	public static float speed() {
		return instance.speed;
	}

	public static void setSpeed(float speed) {
		instance.speed = clamp(speed);
	}

	private static float clamp(float speed) {
		return Math.max(VANILLA_SPEED, Math.min(MAX_SPEED, speed));
	}

	public static void load() {
		try {
			if (Files.exists(FILE)) {
				SwiftHotbarConfig loaded = GSON.fromJson(Files.readString(FILE), SwiftHotbarConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			}
		} catch (Exception e) {
			SwiftHotbarClient.LOGGER.warn("Could not read {}, using defaults", FILE, e);
		}

		instance.speed = clamp(instance.speed);
		save();
	}

	public static void save() {
		try {
			Files.writeString(FILE, GSON.toJson(instance));
		} catch (IOException e) {
			SwiftHotbarClient.LOGGER.warn("Could not write {}", FILE, e);
		}
	}
}
