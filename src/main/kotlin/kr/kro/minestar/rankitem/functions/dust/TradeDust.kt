package kr.kro.minestar.rankitem.functions.dust

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.amount
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryCloseEvent
import kotlin.random.Random

class TradeDust(override val player: Player) : GUI {
    override val pl = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 4, "랭크가루 교환")

    init {
        openGUI()
    }

    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        HandlerList.unregisterAll(this)
        val map = hashMapOf(
            Pair(Rank.SSS, 0),
            Pair(Rank.SS, 0),
            Pair(Rank.S, 0),
            Pair(Rank.AA, 0),
            Pair(Rank.A, 0),
            Pair(Rank.B, 0),
            Pair(Rank.C, 0),
            Pair(Rank.D, 0),
            Pair(Rank.E, 0),
            Pair(Rank.F, 0),
        )
        for (item in gui) {
            item ?: continue
            val rank = ItemClass.getRank(item)
            if (rank == null) {
                player.inventory.addItem(item)
                continue
            }
            var amount = 0
            for (int in 1..item.amount) {
                val a = 3 + Random.nextInt(3)
                player.inventory.addItem(ItemClass.rankDust(rank).amount(a))
                amount += a
            }
            map[rank] = map[rank]!! + amount
        }
        for (pair in map) {
            if (pair.value == 0) continue
            val dust = ItemClass.rankDust(pair.key)
            "$prefix ${dust.display()} §f를 §e${pair.value} §f개 얻었습니다.".toPlayer(player)
        }
    }
}