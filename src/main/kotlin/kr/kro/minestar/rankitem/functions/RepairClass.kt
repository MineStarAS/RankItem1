package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.utility.bool.BooleanScript
import kr.kro.minestar.utility.bool.addScript
import kr.kro.minestar.utility.inventory.howManyHasSameItem
import kr.kro.minestar.utility.item.amount
import kr.kro.minestar.utility.item.cmData
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.item.flagAll
import kr.kro.minestar.utility.material.item
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

object RepairClass {

    fun repair(inventory: Inventory, item: ItemStack): BooleanScript {
        val booleanScript = canRepair(item)
        if (!booleanScript.boolean) return booleanScript
        val need = needPartsScrap(item)
        if (inventory.howManyHasSameItem(ItemClass.partsScrap) < need)
            return false.addScript("$prefix ${ItemClass.partsScrap.display()} §c이 §e$need §c개 필요합니다.")
        inventory.removeItem(ItemClass.partsScrap.clone().amount(need))
        val meta = item.itemMeta as Damageable
        meta.damage = 0
        item.itemMeta = meta
        return true.addScript("$prefix ${item.display()} §a을/를 수리하였습니다.")
    }

    fun canRepair(item: ItemStack): BooleanScript {
        if (!RankClass.isRankItem(item)) return false.addScript("$prefix §c랭크 아이템이 아닙니다.")
        if ((item.itemMeta as Damageable).damage == 0) return false.addScript("$prefix §c손상되지 않은 아이템입니다.")
        return true.addScript()
    }

    fun needPartsScrap(item: ItemStack): Int {
        val level = ReinforceClass.getReinforceLevel(item) ?: 0
        if (level == 0) return 1
        return level / 4
    }

    fun needPartsScrapItem(item: ItemStack) = Material.NAUTILUS_SHELL.item().cmData(101)
        .display("§a필요한 잔해 §f: §f${needPartsScrap(item)} §a개")
        .flagAll()
}