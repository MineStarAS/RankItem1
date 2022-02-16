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

class TradeRankStone(override val player: Player) : GUI {
    override val pl = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 3, "랭크스톤 교환")

    init {
        openGUI()
        "$prefix 아이템을 넣고 닫으면 랭크스톤를 얻습니다."
    }

    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        HandlerList.unregisterAll(this)
        val hasRankStone = hashMapOf(
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
        val tradeRankStone = hashMapOf(
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
            if (!ItemClass.isRankStone(item)) continue
            val rank = ItemClass.getRank(item) ?: continue
            val stack = hasRankStone[rank] ?: 0
            hasRankStone[rank] = stack + item.amount
            item.amount = 0
        }
        for (pair in hasRankStone) {
            if (pair.key == Rank.SSS) {
                player.inventory.addItem(ItemClass.rankStone(Rank.SSS).amount(pair.value))
                continue
            }
            val nextRank = ItemClass.nextRank(pair.key)!!
            val amount = pair.value / 10
            val remainder = pair.value % 10
            player.inventory.addItem(ItemClass.rankStone(nextRank).amount(amount))
            player.inventory.addItem(ItemClass.rankStone(pair.key).amount(remainder))
            tradeRankStone[nextRank] = tradeRankStone[nextRank]!! + amount
        }
        for (item in gui) {
            item ?: continue
            var amount = 0
            val rank = ItemClass.getRank(item)
            if (rank == null) {
                player.inventory.addItem(item)
                continue
            }
            for (int in 1..item.amount) {
                val a = 3 + Random.nextInt(3)
                player.inventory.addItem(ItemClass.rankStone(rank).amount(a))
                amount += a
            }
            tradeRankStone[rank] = tradeRankStone[rank]!! + amount
        }
        for (pair in tradeRankStone) {
            if (pair.value == 0) continue
            val dust = ItemClass.rankStone(pair.key)
            "$prefix ${dust.display()} §f를 §e${pair.value} §f개 얻었습니다.".toPlayer(player)
        }
    }
}