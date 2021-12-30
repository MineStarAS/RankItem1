package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.data.RankItem
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.string.toServer
import kr.kro.minestar.utility.unit.setFalse
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ReinforceClass {

    fun reinforceRankItem(player: Player, item: ItemStack) {

    }

    fun toRankItem(item: ItemStack): RankItem? {
        val lore = item.lore ?: return null
        if (lore.isEmpty()) return null
        val rankLore = lore.first()
        if (!rankLore.contains("랭크")) return null
        var rank: Rank? = null
        for (r in Rank.values()) if (rankLore.contains(r.toString())) {
            rank = r
            break
        }
        rank ?: return null
        return RankItem(item, rank)
    }


}