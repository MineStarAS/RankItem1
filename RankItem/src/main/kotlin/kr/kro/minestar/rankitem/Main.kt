package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.functions.RankClass
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

        saveResource("config.yml", false)
        RankClass.dataLoad()
    }

    override fun onDisable() {
        RankClass.dataSave()
    }
}