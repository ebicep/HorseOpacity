package com.ebicep.horseopacity.config.neoforge

import com.ebicep.horseopacity.config.Config
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry
import me.shedaniel.math.Color
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.util.function.Consumer

object ConfigScreenImpl {

    @JvmStatic
    fun getConfigScreen(previousScreen: Screen? = null): Screen {
        val builder: ConfigBuilder = ConfigBuilder.create()
            .setParentScreen(previousScreen)
            .setTitle(Component.translatable("horseOpacity.title"))
            .setSavingRunnable(Config::save)
            .transparentBackground()
        val entryBuilder: ConfigEntryBuilder = builder.entryBuilder()
        val general = builder.getOrCreateCategory(Component.translatable("horseOpacity.config.general.title"))
        general.addEntry(
            entryBuilder.booleanToggle("horseOpacity.config.general.enabled", Config.values.enabled)
            { Config.values.enabled = it }
        )
        general.addEntry(
            entryBuilder.percentSlider("horseOpacity.config.general.opacity", Config.values.opacity)
            { Config.values.opacity = it }
        )
        return builder.build()
    }

    private fun ConfigEntryBuilder.booleanToggle(translatable: String, variable: Boolean, saveConsumer: Consumer<Boolean>) =
        startBooleanToggle(Component.translatable(translatable), variable)
            .setDefaultValue(variable)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer(saveConsumer)
            .build()

    private fun ConfigEntryBuilder.percentSlider(
        translatable: String,
        variable: Float,
        saveConsumer: Consumer<Float>
    ):
            IntegerSliderEntry {
        val intValue = (variable * 100).toInt()
        return startIntSlider(Component.translatable(translatable), intValue, 0, 100)
            .setDefaultValue(intValue)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setTextGetter { Component.literal("$it%") }
            .setSaveConsumer { saveConsumer.accept(it / 100f) }
            .build()
    }


}
