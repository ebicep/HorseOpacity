package com.ebicep.horseopacity.forge

import com.ebicep.horseopacity.HorseOpacity
import com.ebicep.horseopacity.MOD_ID
import com.ebicep.horseopacity.config.ConfigScreen
import com.ebicep.horseopacity.events.forge.ClientCommandRegistration
import dev.architectury.platform.forge.EventBuses
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(MOD_ID)
object HorseOpacityForge {

    init {
        EventBuses.registerModEventBus(MOD_ID, MOD_BUS)
        HorseOpacity.init()
        MinecraftForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory::class.java) {
            ConfigScreenHandler.ConfigScreenFactory { _: Minecraft, parent: Screen ->
                ConfigScreen.getConfigScreen(parent)
            }
        }
    }

}
