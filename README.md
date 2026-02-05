# Light (Prototype) 🌌

A mysterious Android game prototype inspired by the time I watched *The OA*.  
This project is **not finished** yet, but the core idea is a secret prize‑hunt game hidden behind a surreal intro experience.

![Loader Preview](app/src/main/res/raw/loader.gif)

## Story & Concept 🕯️
The app opens like a hidden door: you enter, see an empty space, and the world slowly reveals hints.  
The original plan was a **3D camera‑based experience** where the player finds a specific location/position in real space to unlock clues and guess the secret character. The prize element was meant to be part of that mystery.

## Current State ✨
This is an early prototype. The existing flow focuses on atmosphere and onboarding:
- Full‑screen immersive intro
- Animated GIF scenes (loader, earth status, explosion)
- Background audio during intro
- Network check with status feedback
- Google Sign‑In entry point
- Basic anti‑tamper check for known hacking tools

## What This Game Can Do ✅

| Capability | Status | Notes |
| --- | --- | --- |
| Intro animation sequence | ✅ Done | Loader GIF + title fade in/out |
| Background music | ✅ Done | Plays during intro |
| Network awareness | ✅ Done | Shows red/white earth based on connectivity |
| Start button flow | ✅ Done | Triggers explosion animation |
| Google Sign‑In | ✅ Done | Token request wired to backend endpoint |
| Full‑screen immersive UI | ✅ Done | Landscape + immersive mode |
| Anti‑tamper detection | ✅ Done | Blocks known cracking tools |
| 3D camera exploration | ⏳ Planned | Real‑space position detection |
| Secret character puzzle | ⏳ Planned | Clue‑based guessing |
| Prize system | ⏳ Planned | Reward logic still undefined |

## Tech & Stack 🧩
- Android (Java)
- Glide for GIFs
- Google Sign‑In
- Volley for networking
- Custom TypeWriter effect

## Build & Run 🛠️
1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run on a device (landscape preferred).

## Notes 📌
This repository is a creative snapshot of the idea. Contributions and feedback are welcome, especially on the **3D camera gameplay** and **mystery puzzle design**.

