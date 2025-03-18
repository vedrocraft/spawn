package ru.sema1ary.spawn.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final SpawnService spawnService;
    private final ConfigService configService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(configService.get("clear-inventory-on-join")) {
            player.getInventory().clear();
        }

        if((boolean) configService.get("teleport-on-first-join") && !player.hasPlayedBefore()
                || (boolean) configService.get("teleport-on-join")) {
            spawnService.teleportToSpawn(player);
        }
    }
}
