package kr.kro.minestar.rankitem.functions.gui.edit

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.RankClass
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.unColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

class SetRank(override val player: Player, val backGUI: EditRankItem) : GUI {
    override val pl = Main.pl

    override val gui = Bukkit.createInventory(null, 9 * 2, "랭크 설정")

    val slots = listOf(
        Slot(0, 2, Material.GRAY_CONCRETE.item().display("${Rank.F}")),
        Slot(0, 3, Material.LIGHT_GRAY_CONCRETE.item().display("${Rank.E}")),
        Slot(0, 4, Material.YELLOW_CONCRETE.item().display("${Rank.D}")),
        Slot(0, 5, Material.LIME_CONCRETE.item().display("${Rank.C}")),
        Slot(0, 6, Material.LIGHT_BLUE_CONCRETE.item().display("${Rank.B}")),
        Slot(1, 2, Material.RED_TERRACOTTA.item().display("${Rank.A}")),
        Slot(1, 3, Material.BLUE_CONCRETE_POWDER.item().display("${Rank.AA}")),
        Slot(1, 4, Material.ORANGE_CONCRETE.item().display("${Rank.S}")),
        Slot(1, 5, Material.PURPLE_CONCRETE.item().display("${Rank.SS}")),
        Slot(1, 6, Material.RED_CONCRETE.item().display("${Rank.SSS}")),
    )

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in slots) gui.setItem(slot.get, slot.item)
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return

        val rank = try {
            Rank.valueOf(clickItem.display().unColor())
        } catch (e1: Exception) {
            return
        }
        RankClass.setRank(backGUI.item, rank)
        backGUI.openGUI()
        return
    }
}