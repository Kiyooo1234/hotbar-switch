# Swift Hotbar (Fabric, Minecraft 1.21.11, client-side)

When you change hotbar slots, the item in your hand lowers and raises again. Vanilla takes about
6 ticks for that. Swift Hotbar makes that animation faster (2x by default, up to 2.5x = about 1 tick).

It is **visual only**: the slot change itself, attack cooldown and anything the server checks are
not touched.

## Settings
Install **Mod Menu**, then Mods > Swift Hotbar > settings button:
- **Swift Hotbar: ON/OFF**
- **Switch animation speed:** 1.0x (vanilla), 1.5x, 2.0x, 2.5x

Without Mod Menu, edit `config/swifthotbar.json`:

    { "enabled": true, "speed": 2.0 }

## Get the jar
Same as Quick Use:
- **Online:** upload this folder to a GitHub repository (create `.github/workflows/build.yml` on GitHub
  if the hidden folder was skipped), then open Actions > latest run > Artifacts > swifthotbar-jar.
- **On your PC:** install JDK 21, run `gradlew build`, take `build/libs/swifthotbar-1.0.0.jar`.

Put the jar in `.minecraft/mods` with Fabric API for 1.21.11.

## If the build complains about a version
Open https://fabricmc.net/develop, pick 1.21.11 and copy the values into `gradle.properties`.
