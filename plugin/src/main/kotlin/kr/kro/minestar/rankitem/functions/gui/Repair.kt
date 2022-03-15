package kr.kro.minestar.rankitem.functions.gui

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.functions.ItemClass.head
import kr.kro.minestar.rankitem.functions.RepairClass
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Repair(override val player: Player) : GUI {
    override val pl: JavaPlugin = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 1, "[장비 수리]")

    var item: ItemStack? = null

    val description = head(9236).display("§7수리할 아이템을 클릭하세요!")
    val repairButton = Material.ANVIL.item().display("§e수리하기")

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in 0 until gui.size) gui.setItem(slot, Material.BLACK_STAINED_GLASS_PANE.item().display(" "))
        if (item != null) if (!RepairClass.canRepair(item!!).boolean) item = null
        if (item == null) {
            gui.setItem(4, description)
            return
        }
        gui.setItem(2, RepairClass.needPartsScrapItem(item!!))
        gui.setItem(6, repairButton)
        gui.setItem(4, item!!.clone())
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.inventory != gui) return
        if (e.whoClicked != player) return
        e.isCancelled = true
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return
        if (e.clickedInventory == e.view.bottomInventory) {
            val booleanScript = RepairClass.canRepair(clickItem)
            if (!booleanScript.boolean) return booleanScript.script.toPlayer(player)
            item = clickItem
            displaying()
            return
        }
        if (e.clickedInventory == e.view.topInventory) {
            if (item == null) {
                displaying()
                return
            }
            if (clickItem != repairButton) return
            if (!RepairClass.canRepair(item!!).boolean) {
                item = null
                displaying()
                return
            }
            val booleanScript = RepairClass.repair(player.inventory, item!!)
            if (booleanScript.boolean) {
                displaying()
                player.playSound(player, Sound.BLOCK_ANVIL_USE, SoundCategory.AMBIENT, 1.0F, 1.0F)
            } else player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.AMBIENT, 1.0F, 1.0F)
            booleanScript.script.toPlayer(player)
        }
    }
}