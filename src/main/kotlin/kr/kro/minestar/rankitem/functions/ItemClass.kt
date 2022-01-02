package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.create.CreateRankItem
import kr.kro.minestar.utility.material.item
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ItemClass {
    fun rankDust(rank: Rank): ItemStack {
        val item = Material.GLOWSTONE_DUST.item()
        val meta = item.itemMeta
        meta.setCustomModelData(rank.ordinal)
        meta.setDisplayName("$rank 가루")
        item.itemMeta = meta
        return item
    }

    fun createRankItem(player: Player, item: ItemStack?): Boolean {
        item ?: return false
        if (!player.isOp) return false
        if (item.type == Material.AIR) return false
        CreateRankItem(player, item)
        return true
    }

    fun isRankItem(item: ItemStack): Boolean {
        val lore = item.lore ?: return false
        if (lore.isEmpty()) return false
        val rankLore = lore.last()
        if (!rankLore.contains("랭크")) return false
        var rank: Rank? = null
        for (r in Rank.values()) if (rankLore.contains(r.name)) {
            rank = r
            break
        }
        rank ?: return false
        return true
    }
}