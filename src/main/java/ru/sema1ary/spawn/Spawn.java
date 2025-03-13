package ru.sema1ary.spawn;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.SneakyThrows;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.spawn.command.SpawnCommand;
import ru.sema1ary.spawn.listener.DamageListener;
import ru.sema1ary.spawn.listener.DeathListener;
import ru.sema1ary.spawn.listener.JoinListener;
import ru.sema1ary.spawn.model.SpawnModel;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.spawn.service.impl.SpawnServiceImpl;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.ormlite.DaoFinder;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceGetter;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Spawn extends JavaPlugin implements DaoFinder, ServiceGetter {
    private JdbcPooledConnectionSource connectionSource;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        initConnectionSource();

        ServiceManager.registerService(SpawnService.class, new SpawnServiceImpl(
                getDao(connectionSource, SpawnModel.class)));

        getServer().getPluginManager().registerEvents(new JoinListener(ServiceManager.getService(SpawnService.class),
                ServiceManager.getService(ConfigService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new DamageListener(ServiceManager.getService(SpawnService.class),
                ServiceManager.getService(ConfigService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new DeathListener(ServiceManager.getService(SpawnService.class),
                ServiceManager.getService(ConfigService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new SpawnCommand(MiniMessage.miniMessage(), ServiceManager.getService(SpawnService.class),
                        ServiceManager.getService(ConfigService.class)))
                .build();
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true, connectionSource);
    }

    @SneakyThrows
    private void initConnectionSource() {
        Path databaseFilePath = Paths.get("plugins/spawn/database.sqlite");
        if(!Files.exists(databaseFilePath) && !databaseFilePath.toFile().createNewFile()) {
            return;
        }

        connectionSource = ConnectionSourceUtil.connectNoSQLDatabase(databaseFilePath.toString(), SpawnModel.class);

    }
}
