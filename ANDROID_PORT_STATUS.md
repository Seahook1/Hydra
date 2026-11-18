# Android port status

This branch tracks Android support work for Hydra without changing the existing Windows build.

## Current status

- [x] Create an isolated `android-apk-support` branch.
- [x] Audit the current project configuration and UI entry points.
- [ ] Confirm the exact Android Among Us package/version and Unity/IL2CPP build.
- [ ] Confirm that a supported Android mod-loader path exists for this game version.
- [ ] Add an Android-specific plugin project and ARM64 build configuration.
- [ ] Add touch-first menu controls and Android-safe input handling.
- [ ] Add automated build checks and publish test artifacts.
- [ ] Validate on a real Android device before calling the port supported.

## Findings from the current code

The existing project targets `.NET 6` and references `AmongUs.GameLibs.Steam`, `BepInEx.IL2CPP.MSBuild`, and `BepInEx.Unity.IL2CPP`. The plugin also declares the Windows process name `Among Us.exe`.

The menu currently opens from a keyboard event (Insert), uses keyboard navigation, and drags with mouse events. A mobile build therefore needs a separate input/UI path rather than simply changing the target framework.

## Android architecture decision

Do not reuse the Windows Steam game libraries or copy the Windows plugin binary into an APK. Android IL2CPP needs Android-specific game metadata and native ARM64-compatible loader/runtime components. The Android loader must be verified against the exact Among Us build before any APK packaging is attempted.

The first Android milestone should be a minimal, harmless plugin that loads and displays a touch-accessible test panel. Only after that passes on-device should feature modules be ported one at a time.

## APK packaging

The repository should build Hydra's own Android components and publish their artifacts. It should not commit or redistribute Innersloth's original game APK, game assets, or signing keys. Any local packaging workflow must operate on a game copy supplied by the device owner and preserve the original install as a rollback option.

## Required compatibility information

Before implementing the Android loader integration, record:

- Android Among Us package name
- Exact game version/build number
- Whether the APK contains `libil2cpp.so`
- Device ABI (expected: `arm64-v8a`)

No Android APK is considered supported until the loader, plugin startup, touch UI, and a clean game launch have all been tested on-device.
