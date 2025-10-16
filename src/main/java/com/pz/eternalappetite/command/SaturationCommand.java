package com.pz.eternalappetite.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SaturationCommand implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        float saturation = source.getPlayer().getFoodData().getSaturationLevel();
        source.sendSystemMessage(Component.translatable(
                "commands.saturation.current",
                String.format("%.2f", saturation)
        ));
        return 0;
    }
}
