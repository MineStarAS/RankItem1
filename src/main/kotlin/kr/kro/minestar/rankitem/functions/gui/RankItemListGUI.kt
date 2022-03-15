package kr.kro.minestar.rankitem.functions.gui

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.functions.ItemClass.head
import kr.kro.minestar.rankitem.functions.RankClass
import kr.kro.minestar.rankitem.functions.gui.edit.EditRankItem
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.display
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class RankItemListGUI(override val player: Player) : GUI {
    override val pl = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 6, "[RankItemList]")

    var page = 0

    val button = listOf(
        Slot(5, 0, head(8895).display("§9[§f이전 페이지§9]")),
        Slot(5, 8, head(8893).display("§9[§f다음 페이지§9]")),
    )

    val pageItem = Slot(5, 4, head(11504).display("§7[페이지 : §f$page§7]"))

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in button) gui.setItem(slot.get, slot.item)
        gui.setItem(pageItem.get, pageItem.item.clone().also { item ->
            if (64 <= page) item.amount = 1
            else item.amount = page + 1
        })

        for (slot in (page * 45) until ((page + 1) * 45)) {
            if (RankClass.rankItemList.lastIndex < slot) break
            gui.addItem(RankClass.rankItemList[slot])
        }
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        val clickItem = e.currentItem ?: return

        if (e.slot < 0) return
        if (e.slot in 9 * 5 until 9 * 6) {
            if (e.click != ClickType.LEFT) return
            when (clickItem) {
                button[0].item -> {
                    if (page == 0) return
                    --page
                    displaying()
                }
                button[1].item -> {
                    if (RankClass.rankItemList.lastIndex < page * 45) return
                    --page
                    displaying()
                }
            }
            return
        }
        val index = itemIndex(e.slot)
        val rankItem = rankItem(index) ?: return
        when (e.click) {
            ClickType.LEFT -> EditRankItem(player, rankItem)
            ClickType.SHIFT_LEFT -> player.inventory.addItem(rankItem)
            ClickType.DROP -> {
                RankClass.rankItemList.removeAt(index)
                displaying()
            }
            else -> return
        }
    }

    fun itemIndex(slot: Int) = page * 45 + slot

    fun rankItem(index: Int): ItemStack? {
        if (index !in 0..RankClass.rankItemList.lastIndex) return null
        return RankClass.rankItemList[index]
    }
}