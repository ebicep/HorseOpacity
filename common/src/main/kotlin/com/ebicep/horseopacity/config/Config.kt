package com.ebicep.horseopacity.config

import com.ebicep.horseopacity.HorseOpacity
import com.ebicep.horseopacity.MOD_ID
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.awt.Color
import java.io.File

private val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
}
val configDirectoryPath: String
    get() = ConfigDirectory.getConfigDirectory().toString() + "\\horseopacity"

object Config {

    var values = ConfigVariables()

    fun save() {
        val configDirectory = File(configDirectoryPath)
        if (!configDirectory.exists()) {
            configDirectory.mkdir()
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        configFile.writeText(json.encodeToString(ConfigVariables.serializer(), values))
    }

    fun load() {
        HorseOpacity.LOGGER.info("Config Directory: ${ConfigDirectory.getConfigDirectory().toAbsolutePath().normalize()}")
        val configDirectory = File(configDirectoryPath)
        if (!configDirectory.exists()) {
            return
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        if (configFile.exists()) {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
            values = json.decodeFromString(ConfigVariables.serializer(), configFile.readText())
            loadValues()
        }
    }

    private fun loadValues() {

    }

}

@Serializable
data class ConfigVariables(
    var enabled: Boolean = true,
    var opacity: Float = 1f,
)