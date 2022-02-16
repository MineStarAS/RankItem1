package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§c아이템강화§f]"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("rankitem")?.setExecutor(CMD)
        Bukkit.getPluginManager().registerEvents(ReinforceClass, this)

        saveResource("config.yml", false)
    }

    override fun onDisable() {
    }
}