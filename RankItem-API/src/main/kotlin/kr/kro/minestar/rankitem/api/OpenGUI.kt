package kr.kro.minestar.rankitem.api

import kr.kro.minestar.rankitem.functions.gui.Reinforce
import kr.kro.minestar.rankitem.functions.gui.Repair
import kr.kro.minestar.rankitem.functions.gui.TradePartsScrap
import org.bukkit.entity.Player

object RankItemOpenGUI {
    fun reinforce(player: Player) {
        Reinforce(player)
    }
    fun repair(player: Player) {
        Repair(player)
    }
    fun tradePartsScrap(player: Player) {
        TradePartsScrap(player)
    }
}