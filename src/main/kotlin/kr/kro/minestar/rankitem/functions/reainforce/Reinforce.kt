package kr.kro.minestar.rankitem.functions.reainforce

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.data.RankItem
import kr.kro.minestar.utility.gui.GUI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Reinforce(override val player: Player, rankItem: RankItem) : GUI {
    override val pl: JavaPlugin = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 3, "랭크 업")

}