package kr.kro.minestar.rankitem

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9ItemReinForce§f]"
        lateinit var headItem: YamlConfiguration
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("rankitem")?.setExecutor(CMD)

        saveResource("HEAD_ITEM.yml", true)
        val headFile = File(dataFolder, "HEAD_ITEM.yml")
        headItem = YamlConfiguration.loadConfiguration(headFile)
        headFile.delete()

        saveResource("config.yml", false)
    }

    override fun onDisable() {
    }
}