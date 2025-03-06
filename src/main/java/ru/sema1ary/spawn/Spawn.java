package ru.sema1ary.spawn;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ormlite.ConnectionSourceUtil;
import ru.sema1ary.spawn.listener.DamageListener;
import ru.sema1ary.spawn.listener.JoinListener;
import ru.sema1ary.spawn.model.SpawnModel;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.spawn.service.impl.SpawnServiceImpl;
import ru.vidoskim.bukkit.service.ConfigService;
import ru.vidoskim.bukkit.service.impl.ConfigServiceImpl;
import ru.vidoskim.bukkit.util.LiteCommandUtil;
import service.ServiceManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Spawn extends JavaPlugin {
    private JdbcPooledConnectionSource connectionSource;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        initConnectionSource();

        ServiceManager.registerService(SpawnService.class, new SpawnServiceImpl(
                getDao(SpawnModel.class)));

        getServer().getPluginManager().registerEvents(new JoinListener(ServiceManager.getService(SpawnService.class),
                ServiceManager.getService(ConfigService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new DamageListener(ServiceManager.getService(SpawnService.class),
                ServiceManager.getService(ConfigService.class)
        ), this);

        new LiteCommandUtil().create(
                ServiceManager.getService(ConfigService.class).get("litecommands-prefix"),
                ServiceManager.getService(ConfigService.class).get("litecommands-invalid-usage"),
                ServiceManager.getService(ConfigService.class).get("litecommands-player-only"),
                ServiceManager.getService(ConfigService.class).get("litecommands-player-not-found")
        );
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true, connectionSource);
    }

    @SneakyThrows
    private void initConnectionSource() {
        Path databaseFilePath = Paths.get("plugins/chat-room/database.sqlite");
        if(!Files.exists(databaseFilePath) && !databaseFilePath.toFile().createNewFile()) {
            return;
        }

        connectionSource = ConnectionSourceUtil.connectNoSQLDatabase("sqlite",
                databaseFilePath.toString(), SpawnModel.class);

    }

    @SuppressWarnings("all")
    private <D extends Dao<T, ?>, T> D getDao(Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }

}
