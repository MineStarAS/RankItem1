package kr.kro.minestar.rankitem.functions

import kr.kro.minestar.rankitem.Main.Companion.pl
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.utility.bool.BooleanScript
import kr.kro.minestar.utility.bool.addScript
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.toServer
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import java.io.File

object RankItemClass {

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
                .addLore(" ")
                .addLore("§f§7랭크 : ${Rank.F}")
        )
        return true.addScript("$prefix §a정상적으로 아이템이 등록되었습니다!")
    }
}