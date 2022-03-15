package kr.kro.minestar.rankitem.functions.gui

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.rankitem.functions.RankClass
import kr.kro.minestar.rankitem.functions.ReinforceClass
import kr.kro.minestar.utility.event.disable
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.amount
import kr.kro.minestar.utility.number.addComma
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryCloseEvent

class TradePartsScrap(override val player: Player) : GUI {
    override val pl = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 3, "장비 분해")

    init {
        openGUI()
        "$prefix 랭크 아이템을 넣고 닫으면 §e장비 잔해§f를 얻습니다.".toPlayer(player)
    }

    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        disable()
        var amount = 0
        for (item in gui) {
            item ?: continue
            if (!RankClass.isRankStone(item)) continue
            val rank = RankClass.getRank(item) ?: continue
            val reinforce = ReinforceClass.getReinforceLevel(item) ?: 0
            amount += rank.partsScrap + reinforce
            item.amount = 0
        }
        for (item in gui) {
            item ?: continue
            val rank = RankClass.getRank(item)
            if (rank == null) {
                player.inventory.addItem(item)
                continue
            }
        }
        player.inventory.addItem(ItemClass.partsScrap.clone().amount(amount))
        "$prefix §7장비 잔해§f를 ${amount.addComma()}§f개 얻었습니다.".toPlayer(player)
    }
}