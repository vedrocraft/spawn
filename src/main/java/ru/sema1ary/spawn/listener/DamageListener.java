package ru.sema1ary.spawn.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.sema1ary.spawn.service.SpawnService;
import ru.vidoskim.bukkit.service.ConfigService;

@RequiredArgsConstructor
public class DamageListener implements Listener {
    private final SpawnService spawnService;
    private final ConfigService configService;

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if(!(boolean) configService.get("teleport-when-player-fall-in-void")) {
            return;
        }

        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            spawnService.teleportToSpawn(player);
        }
    }
}
