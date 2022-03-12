package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass.isRankItem
import kr.kro.minestar.rankitem.functions.gui.reainforce.Reinforce
import kr.kro.minestar.rankitem.functions.gui.stone.TradeRankStone
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.item.flagAll
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object ReinforceClass : Listener {

    val limitLevel = mapOf(
        Pair(Enchantment.ARROW_FIRE, 1),
        Pair(Enchantment.ARROW_INFINITE, 1),
        Pair(Enchantment.MULTISHOT, 1),
        Pair(Enchantment.SILK_TOUCH, 1),
        Pair(Enchantment.LURE, 3),
        Pair(Enchantment.MENDING, 1),
        Pair(Enchantment.VANISHING_CURSE, 1),
        Pair(Enchantment.BINDING_CURSE, 1),
        Pair(Enchantment.CHANNELING, 1),
    )

    fun rankPercent(rank: Rank) = pl.config.getDouble(rank.name)

    fun rankUp(item: ItemStack): Boolean {
        if (ItemClass.isRankStone(item)) return false
        val currentRank = ItemClass.getRank(item) ?: return false
        val rank = ItemClass.nextRank(currentRank) ?: return false
        val meta = item.itemMeta
        val lore = meta.lore!!
        lore.removeAt(lore.lastIndex)
        lore.add("§f§7랭크 : $rank")
        meta.lore = lore
        val enchants = meta.enchants
        for (en in enchants.keys) if (!limitLevel.contains(en)) meta.addEnchant(en, enchants[en]!! + 1, true)
        item.itemMeta = meta
        return true
    }

    fun rankSet(item: ItemStack, rank : Rank) :Boolean {
        if (ItemClass.isRankStone(item)) return false
        val currentRank = ItemClass.getRank(item) ?: return false
        val meta = item.itemMeta
        val lore = meta.lore!!
        lore.removeAt(lore.lastIndex)
        lore.add("§f§7랭크 : $rank")
        meta.lore = lore
        item.itemMeta = meta
        return true
    }

    fun rankPercentItem(rank: Rank): ItemStack {
        val item = Material.ENCHANTED_BOOK.item().display("§e강화 확률").flagAll()
        item.addLore(" ")
        item.addLore("§7다음 강화 랭크 : $rank")
        item.addLore("§7확률 : ${rankPercent(rank) * 100} %")
        return item
    }

    @EventHandler
    fun blockClick(e: PlayerInteractEvent) {
        e.clickedBlock ?: return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        val anvil = listOf(Material.ANVIL,Material.CHIPPED_ANVIL,Material.DAMAGED_ANVIL)
        if (anvil.contains(e.clickedBlock!!.type)) {
            e.isCancelled = true
            val item = e.item ?: return "$prefix 강화 할 아이템을 손에 들고 클릭하세요!".toPlayer(e.player)
            if (!isRankItem(item)) return "$prefix §c해당 아이템은 강화 할 수 없습니다.".toPlayer(e.player)
            Reinforce(e.player, item)
            return
        }
        if (e.clickedBlock!!.type == Material.STONECUTTER) {
            e.isCancelled = true
            TradeRankStone(e.player)
        }
    }
}