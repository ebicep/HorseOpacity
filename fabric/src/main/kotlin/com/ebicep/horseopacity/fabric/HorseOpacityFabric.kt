package com.ebicep.horseopacity.fabric

import com.ebicep.horseopacity.HorseOpacity
import com.ebicep.horseopacity.events.fabric.ClientCommandRegistration
import net.fabricmc.api.ModInitializer


object HorseOpacityFabric : ModInitializer {

    override fun onInitialize() {
        HorseOpacity.init()
        ClientCommandRegistration.registerCommands()
    }

}
