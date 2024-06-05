package com.ebicep.horseopacity.events.forge

import com.ebicep.horseopacity.config.ConfigScreen
import com.mojang.brigadier.Command
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent


object ClientCommandRegistration {

    fun registerCommands(event: RegisterClientCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("horseopacity")
                .executes {
                    ConfigScreen.open = true
                    Command.SINGLE_SUCCESS
                }
        )
    }

}