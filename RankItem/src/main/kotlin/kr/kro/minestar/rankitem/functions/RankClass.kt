package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.bool.BooleanScript
import kr.kro.minestar.utility.bool.addScript
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.unColor
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File

object RankClass {

    val file = File(pl.dataFolder, "RankItemList.yml")

    val rankItemList = mutableListOf<ItemStack>()

    fun dataLoad() {
        rankItemList.clear()
        val data = YamlConfiguration.loadConfiguration(file)
        for (key in data.getKeys(false)) rankItemList.add(data.getItemStack(key) ?: continue)
    }

    fun dataSave() {
        val data = YamlConfiguration()
        for ((int, item) in rankItemList.withIndex()) data["$int"] = item
        data.save(file)
    }

    fun createRankItem(item: ItemStack): BooleanScript {
        if (item.type == Material.AIR) return false.addScript("$prefix §c등록할 아이템 종류를 손에 들어야 합니다.")
        if (item.maxStackSize != 1) return false.addScript("$prefix §c장비 아이템이어야 합니다.")

        rankItemList.add(
            item.type.item()
                .display(item.type.name)
                .addLore(" ")
                .addLore("§f§7랭크 : ${Rank.F}")
        )
        return true.addScript("$prefix §a정상적으로 아이템이 등록되었습니다!")
    }

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

    fun setRank(item: ItemStack, rank: Rank): Boolean {
        if (isRankStone(item)) return false
        val meta = item.itemMeta
        val lore = meta.lore!!
        lore.removeAt(lore.lastIndex)
        lore.add("§f§7랭크 : $rank")
        meta.lore = lore
        item.itemMeta = meta
        return true
    }
}