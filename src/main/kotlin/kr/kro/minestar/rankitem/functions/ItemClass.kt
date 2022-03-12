package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.item.cmData
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.unColor
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemClass {

    fun rankStone(rank: Rank): ItemStack = Material.NAUTILUS_SHELL.item().cmData(rank.ordinal).display("$rank 스톤")

    fun isRankStone(item: ItemStack): Boolean {
        if (item.type != Material.GLOWSTONE_DUST) return false
        if (!item.itemMeta.hasDisplayName()) return false
        if (!item.itemMeta.hasCustomModelData()) return false
        if (!item.itemMeta.displayName.contains("스톤")) return false
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
        if (isRankStone(item)) {
            for (rank in Rank.values()) if (item.display().unColor().contains(rank.name)) return rank
            return null
        }
        if (!isRankItem(item)) return null
        val rankLore = item.lore?.last() ?: return null
        if (!rankLore.contains("랭크")) return null
        var rank: Rank? = null
        for (r in Rank.values()) if (rankLore.contains(r.name)) {
            rank = r
            break
        }
        return rank
    }

    fun nextRank(rank: Rank): Rank? {
        val int = rank.ordinal - 1
        if (int < 0) return null
       return Rank.values()[int]
    }

    fun head(id: Int) = HeadDatabaseAPI().getItemHead("$id") ?: Material.BARRIER.item().display("§c해당 ID의 머리가 없습니다")
}