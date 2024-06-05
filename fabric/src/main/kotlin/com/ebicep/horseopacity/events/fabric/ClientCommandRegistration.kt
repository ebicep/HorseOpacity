package com.ebicep.horseopacity.events.fabric

import com.ebicep.horseopacity.config.ConfigScreen
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

object ClientCommandRegistration {

    fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource?>, _: CommandBuildContext? ->
            dispatcher.register(
                ClientCommandManager.literal("horseopacity")
                    .executes {
                        ConfigScreen.open = true
                        Command.SINGLE_SUCCESS
                    }
            )
        })
    }

}