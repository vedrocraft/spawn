package ru.sema1ary.spawn.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

@RequiredArgsConstructor
public class DeathListener implements Listener {
    private final SpawnService spawnService;
    private final ConfigService configService;

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        if((boolean) configService.get("teleport-when-player-death")) {
            spawnService.teleportToSpawn(event.getPlayer());
        }
    }
}
