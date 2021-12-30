package kr.kro.minestar.rankitem.data

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.item.display
import org.bukkit.inventory.ItemStack

class RankItem(val item: ItemStack,val rank: Rank) {
    val display = item.display()
}