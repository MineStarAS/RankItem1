package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.material.item
import org.bukkit.Material
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

    fun isRankDust(item: ItemStack): Boolean {
        if (item.type != Material.GLOWSTONE_DUST) return false
        if (!item.itemMeta.hasDisplayName()) return false
        if (!item.itemMeta.hasCustomModelData()) return false
        if (item.itemMeta.displayName.contains("가루")) return false
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

    fun getRank(item: ItemStack): Rank? {
        val lore = item.lore ?: return null
        if (lore.isEmpty()) return null
        val rankLore = lore.last()
        if (!rankLore.contains("랭크")) return null
        var rank: Rank? = null
        for (r in Rank.values()) if (rankLore.contains(r.name)) {
            rank = r
            break
        }
        return rank
    }
}