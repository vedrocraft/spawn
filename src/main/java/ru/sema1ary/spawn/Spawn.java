package ru.sema1ary.spawn;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.spawn.command.SpawnCommand;
import ru.sema1ary.spawn.listener.DamageListener;
import ru.sema1ary.spawn.listener.DeathListener;
import ru.sema1ary.spawn.listener.JoinListener;
import ru.sema1ary.spawn.model.SpawnModel;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.spawn.service.impl.SpawnServiceImpl;
import ru.sema1ary.vedrocraftapi.BaseCommons;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.ormlite.DatabaseUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public final class Spawn extends JavaPlugin implements BaseCommons {

    @Override
    public void onEnable() {
        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        DatabaseUtil.initConnectionSource(this, SpawnModel.class);

        ServiceManager.registerService(SpawnService.class, new SpawnServiceImpl(
                getDao(SpawnModel.class)));

        getServer().getPluginManager().registerEvents(new JoinListener(
                getService(SpawnService.class),
                getService(ConfigService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new DamageListener(
                getService(SpawnService.class),
                getService(ConfigService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new DeathListener(
                getService(SpawnService.class),
                getService(ConfigService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new SpawnCommand(
                        getService(SpawnService.class),
                        getService(ConfigService.class)))
                .build();
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true);
    }
}
