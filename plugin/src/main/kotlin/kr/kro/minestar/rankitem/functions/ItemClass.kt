package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.item.cmData
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemClass {

    val reinforceTicket = Material.NAUTILUS_SHELL.item().cmData(100).display("§e[§f장비강화권§e]")

    val partsScrap = Material.NAUTILUS_SHELL.item().cmData(101).display("§7[§f장비 잔해§7]")

    fun rankStone(rank: Rank): ItemStack = Material.NAUTILUS_SHELL.item().cmData(rank.ordinal).display("$rank 스톤")

    fun head(id: Int) = HeadDatabaseAPI().getItemHead("$id") ?: Material.BARRIER.item().display("§c해당 ID의 머리가 없습니다")

    fun isEquipment(material: Material): Boolean {
        val list = listOf(
            "SWORD",
            "SHOVEL",
            "PICKAXE",
            "AXE",
            "HOE",

            "HELMET",
            "CHESTPLATE",
            "LEGGINGS",
            "BOOTS",

            "ELYTRA",
            "FLINT_AND_STEEL",
            "TRIDENT",
            "BOW",
            "CROSSBOW",
            "FISHING_ROD",
            "CARROT_ON_A_STICK",
            "WARPED_FUNGUS_ON_A_STICK",
            "SHEARS",
            "SHIELD",
        )
        if (material.maxStackSize != 1) return false
        for (s in list) if (material.name.contains(s)) return true
        return false
    }
}