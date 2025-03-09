package ru.sema1ary.spawn.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.sema1ary.spawn.service.SpawnService;
import ru.vidoskim.bukkit.service.ConfigService;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final SpawnService spawnService;
    private final ConfigService configService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if((boolean) configService.get("teleport-on-first-join")
                && !player.hasPlayedBefore()) {
            spawnService.teleportToSpawn(player);
            return;
        }

        if(configService.get("teleport-on-join")) {
            spawnService.teleportToSpawn(player);
        }
    }
}
