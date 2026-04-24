#!/bin/bash

echo "== Helix UI Fix Starting =="

# 1. Force correct client name (ONLY if file exists)
if [ -f src/game/java/net/minecraft/client/myclient/MyClient.java ]; then
  sed -i 's/MyClient/Helix Client/g' src/game/java/net/minecraft/client/myclient/MyClient.java
fi

# 2. Ensure ModuleManager has init method properly structured
FILE="src/game/java/net/minecraft/client/myclient/ModuleManager.java"

if [ -f "$FILE" ]; then
  echo "Checking ModuleManager..."

  # Just make sure init() exists (do NOT destroy formatting)
  grep -q "public static void init" "$FILE"
  if [ $? -eq 0 ]; then
    echo "init() found ✔"
  else
    echo "WARNING: init() not found"
  fi
fi

# 3. Ensure Right Shift keybind still exists
grep -R "KEY_RSHIFT" -n src/game/java/net/minecraft/client | head

echo "== Done =="
