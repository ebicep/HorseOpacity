package com.ebicep.horseopacity.neoforge

import com.ebicep.horseopacity.HorseOpacity
import com.ebicep.horseopacity.MOD_ID
import com.ebicep.horseopacity.config.ConfigScreen
import com.ebicep.horseopacity.events.neoforge.ClientCommandRegistration
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.ConfigScreenHandler
import net.neoforged.neoforge.common.NeoForge


@Mod(MOD_ID)
object HorseOpacityForge {

    init {
        HorseOpacity.init()
        NeoForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory::class.java) {
            ConfigScreenHandler.ConfigScreenFactory { _: Minecraft, parent: Screen ->
                ConfigScreen.getConfigScreen(parent)
            }
        }
    }

}
