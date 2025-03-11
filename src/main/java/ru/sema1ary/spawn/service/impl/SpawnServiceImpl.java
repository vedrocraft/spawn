package ru.sema1ary.spawn.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.sema1ary.spawn.dao.SpawnDao;
import ru.sema1ary.spawn.model.SpawnModel;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.vedrocraftapi.serialization.LocationSerializer;

import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class SpawnServiceImpl implements SpawnService {
    private final SpawnDao spawnDao;

    @Override
    public SpawnModel save(@NonNull SpawnModel spawn) {
        try {
            return spawnDao.save(spawn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SpawnModel> get() {
        try {
            return spawnDao.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSpawn(Location location) {
        SpawnModel spawn;
        Optional<SpawnModel> optionalHub = get();
        if(optionalHub.isEmpty()) {
            spawn = SpawnModel.builder()
                    .location(LocationSerializer.serialize(location))
                    .build();
        } else  {
            spawn = optionalHub.get();
            spawn.setLocation(LocationSerializer.serialize(location));
        }

        save(spawn);
    }

    @Override
    public void teleportToSpawn(@NonNull Player player) {
        Optional<SpawnModel> optionalSpawn = get();

        if(optionalSpawn.isEmpty()) {
            return;
        }

        player.teleportAsync(LocationSerializer.deserialize(optionalSpawn.get().getLocation()));
    }
}
