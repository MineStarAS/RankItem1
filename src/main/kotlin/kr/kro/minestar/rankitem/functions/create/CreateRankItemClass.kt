package kr.kro.minestar.rankitem.functions.create

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object CreateRankItemClass {
    fun newClass(player: Player, item: ItemStack?): Boolean {
        item ?: return false
        if (!player.isOp) return false
        if (item.type == Material.AIR) return false
        CreateRankItem(player, item)
        return true
    }
}