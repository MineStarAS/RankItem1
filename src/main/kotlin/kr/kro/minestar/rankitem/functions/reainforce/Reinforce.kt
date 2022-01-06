package kr.kro.minestar.rankitem.functions.reainforce

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass.getRank
import kr.kro.minestar.rankitem.functions.ItemClass.nextRank
import kr.kro.minestar.rankitem.functions.ItemClass.rankDust
import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass.rankPercent
import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass.rankPercentItem
import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass.rankUp
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.inventory.howManyHasSameItem
import kr.kro.minestar.utility.item.amount
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.item.flagAll
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.number.percent
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Reinforce(override val player: Player, val item: ItemStack) : GUI {
    override val pl: JavaPlugin = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 1, "랭크 업")

    val reinforceButton = Material.ANVIL.item().display("§a강화하기")

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        val rank = nextRank(getRank(item)!!)
        val rankPercentItem = if (rank != null) rankPercentItem(rank)
        else Material.ENCHANTED_BOOK.item().display("§c더 이상 강화를 할 수 없습니다").flagAll()
        gui.setItem(2, rankPercentItem)
        gui.setItem(6, reinforceButton)
        gui.setItem(4, item.clone())
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.inventory != gui) return
        if (e.whoClicked != player) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return
        if (clickItem != reinforceButton) return

        val rank = getRank(item) ?: return
        val rankDust = rankDust(rank)

        if (rank == Rank.SSS) return "$prefix §c더 이상 강화를 할 수 없습니다.".toPlayer(player)

        if (player.inventory.howManyHasSameItem(rankDust) < 10) return "$prefix ${rankDust.display()} §c10 개가 필요합니다.".toPlayer(player)
        player.inventory.removeItem(rankDust.amount(10))

        if (rankPercent(nextRank(rank)!!).percent()) {
            rankUp(item)
            displaying()
            return "$prefix §a강화에 성공하였습니다!".toPlayer(player)
        }
        "$prefix §c강화에 실패하였습니다.".toPlayer(player)
    }
}