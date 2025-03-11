package ru.sema1ary.spawn.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.sema1ary.spawn.service.SpawnService;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

@RequiredArgsConstructor
@Command(name = "spawn")
public class SpawnCommand {
    private final MiniMessage miniMessage;
    private final SpawnService spawnService;
    private final ConfigService configService;

    @Async
    @Execute
    void execute(@Context Player sender) {
        spawnService.teleportToSpawn(sender);
        sender.sendMessage(miniMessage.deserialize(configService.get("spawn-command-message")));
    }

    @Async
    @Execute
    @Permission("spawn.other")
    void execute(@Context Player sender, @Arg("цель") Player target) {
        spawnService.teleportToSpawn(target);
        sender.sendMessage(miniMessage.deserialize(configService.get("spawn-command-other-sender")));
        target.sendMessage(miniMessage.deserialize(configService.get("spawn-command-other-target")));
    }

    @Async
    @Execute(name = "reload")
    @Permission("spawn.reload")
    void reload (@Context CommandSender sender) {
        configService.reload();
        sender.sendMessage(miniMessage.deserialize(configService.get("reload-message")));
    }

    @Async
    @Execute(name = "set")
    @Permission("spawn.set")
    void set(@Context Player sender) {
        spawnService.setSpawn(sender.getLocation());
        sender.sendMessage(miniMessage.deserialize(configService.get("spawn-set-message")));
    }
}
