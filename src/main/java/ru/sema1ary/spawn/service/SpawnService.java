package ru.sema1ary.spawn.service;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.sema1ary.spawn.model.SpawnModel;
import ru.sema1ary.vedrocraftapi.service.Service;

import java.util.Optional;

@SuppressWarnings("all")
public interface SpawnService extends Service {
    SpawnModel save(@NonNull SpawnModel spawn);

    Optional<SpawnModel> get();

    void setSpawn(Location location);

    void teleportToSpawn(@NonNull Player player);
}
