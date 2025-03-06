package ru.sema1ary.spawn.dao;

import com.j256.ormlite.dao.Dao;
import lombok.NonNull;
import ru.sema1ary.spawn.model.SpawnModel;

import java.sql.SQLException;
import java.util.Optional;

public interface SpawnDao extends Dao<SpawnModel, Long> {
    SpawnModel save(@NonNull SpawnModel spawn) throws SQLException;

    Optional<SpawnModel> get() throws SQLException;
}
