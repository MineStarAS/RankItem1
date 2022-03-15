package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.utility.bool.BooleanScript
import kr.kro.minestar.utility.bool.addScript
import kr.kro.minestar.utility.inventory.howManyHasSameItem
import kr.kro.minestar.utility.item.*
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.number.percent
import kr.kro.minestar.utility.string.unColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object ReinforceClass {

    val limitLevel = mapOf(
        Pair(Enchantment.ARROW_FIRE, 1),
        Pair(Enchantment.ARROW_INFINITE, 1),
        Pair(Enchantment.MULTISHOT, 1),
        Pair(Enchantment.SILK_TOUCH, 1),
        Pair(Enchantment.LURE, 3),
        Pair(Enchantment.MENDING, 1),
        Pair(Enchantment.VANISHING_CURSE, 1),
        Pair(Enchantment.BINDING_CURSE, 1),
        Pair(Enchantment.CHANNELING, 1),
    )

    fun getReinforceLevel(item: ItemStack): Int? {
        if (!RankClass.isRankItem(item)) return null
        val display = item.display().unColor()
        val split = display.split('+')
        if (split.size < 2) return 0
        return split.last().toIntOrNull()
    }

    fun setReinforceLevel(item: ItemStack, level: Int): Boolean {
        if (RankClass.isRankStone(item)) return false
        if (level < 0) return false
        val reinforceLevel = getReinforceLevel(item) ?: return false
        if (reinforceLevel == 0) {
            item.addSuffix("§8+1")
            addEnchantLevel(item, 1)
            return true
        }
        item.display(item.display().replace("§8+$reinforceLevel", "§8+${level}"))
        addEnchantLevel(item, level - reinforceLevel)
        return true
    }

    fun addEnchantLevel(item: ItemStack, level: Int) {
        val meta = item.itemMeta
        val enchants = meta.enchants
        for (en in enchants.keys) if (!limitLevel.contains(en)) meta.addEnchant(en, enchants[en]!! + level, true)
        item.itemMeta = meta
    }

    fun canReinforce(item: ItemStack): BooleanScript {
        if (!RankClass.isRankItem(item)) return false.addScript("$prefix §c랭크 아이템이 아닙니다.")
        val rank = RankClass.getRank(item) ?: return false.addScript("$prefix §c랭크 아이템이 아닙니다.")
        val level = getReinforceLevel(item) ?: 0
        if (rank.reinforceLimit <= level) return false.addScript("$prefix §c강화할 수 있는 최대치에 도달한 아이템입니다.")
        return true.addScript()
    }

    fun reinforce(inventory: Inventory, item: ItemStack): BooleanScript {
        val can = canReinforce(item)
        if (!can.boolean) return can
        val level = getReinforceLevel(item) ?: 0
        if (inventory.howManyHasSameItem(ItemClass.reinforceTicket) < level + 1)
            return false.addScript("$prefix ${ItemClass.reinforceTicket.display()} §c이 §e${level + 1} §c개 필요합니다.")
        inventory.removeItem(ItemClass.reinforceTicket.clone().amount(level + 1))
        if (!reinforcePercent(level + 1).percent()) return false.addScript("$prefix §c강화에 실패하였습니다.")
        setReinforceLevel(item, level + 1)
        if (RankClass.getRank(item)!!.reinforceLimit <= level + 1)return true.addScript("$prefix §e최대 단계 §a까지 강화에 성공하였습니다!")
        return true.addScript("$prefix §a강화에 성공하였습니다!")
    }

    fun reinforcePercent(level: Int) = pl.config.getDouble(level.toString())

    fun percentItem(level: Int) = Material.ENCHANTED_BOOK.item()
        .display("§e강화 확률 §f: §7${reinforcePercent(level) * 100} %")
        .addLore("§a필요한 장비강화권 §f: §f${level} §a개")
        .flagAll()

}