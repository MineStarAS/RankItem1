package kr.kro.minestar.rankitem.functions.reainforce

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.rankitem.functions.ItemClass.isRankItem
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.item.flagAll
import kr.kro.minestar.utility.material.item
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ReinforceClass {
    fun newClass(player: Player, item: ItemStack) {
        if (!isRankItem(item)) return
        if (item.amount != 1) return
        Reinforce(player, item)
    }

    fun rankPercent(rank: Rank) = pl.config.getDouble(rank.name)

    fun rankUp(item: ItemStack): Boolean {
        if (ItemClass.isRankDust(item)) return false
        val currentRank = ItemClass.getRank(item) ?: return false
        val rank = ItemClass.nextRank(currentRank) ?: return false
        val meta = item.itemMeta
        val lore = meta.lore!!
        lore.removeAt(lore.lastIndex)
        lore.add("§f§7랭크 : $rank")
        meta.lore = lore
        val enchants = meta.enchants
        for (en in enchants.keys) meta.addEnchant(en, enchants[en]!! + 1, true)
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
}