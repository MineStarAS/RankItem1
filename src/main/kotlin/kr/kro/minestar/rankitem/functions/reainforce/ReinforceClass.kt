package kr.kro.minestar.rankitem.functions.reainforce

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.utility.number.percent
import org.bukkit.inventory.ItemStack

object ReinforceClass {
    fun newClass() {

    }

    fun rankPercent(rank: Rank) = pl.config.getDouble(rank.name).percent()

    fun rankUp(item: ItemStack): Boolean {
        if (!ItemClass.isRankItem(item)) return false

        return false
    }
}