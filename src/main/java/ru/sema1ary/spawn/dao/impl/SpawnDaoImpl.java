package ru.sema1ary.spawn.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import lombok.NonNull;
import ru.sema1ary.spawn.dao.SpawnDao;
import ru.sema1ary.spawn.model.SpawnModel;

import java.sql.SQLException;
import java.util.Optional;

public class SpawnDaoImpl extends BaseDaoImpl<SpawnModel, Long> implements SpawnDao {
    public SpawnDaoImpl(ConnectionSource connectionSource, Class<SpawnModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public SpawnModel save(@NonNull SpawnModel spawn) throws SQLException {
        createOrUpdate(spawn);
        return spawn;
    }

    @Override
    public Optional<SpawnModel> get() throws SQLException {
        SpawnModel result = queryForId(1L);
        return Optional.ofNullable(result);
    }
}
