package com.ebicep.horseopacity

import com.ebicep.horseopacity.config.Config
import com.ebicep.horseopacity.config.ConfigScreen
import dev.architectury.event.events.client.ClientTickEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "horseopacity"

object HorseOpacity {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        Config.load()

        ClientTickEvent.CLIENT_POST.register {
            ConfigScreen.handleOpenScreen()
        }
    }
}