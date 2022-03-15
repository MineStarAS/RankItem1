package kr.kro.minestar.rankitem.functions.gui

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.functions.ItemClass.head
import kr.kro.minestar.rankitem.functions.ReinforceClass
import kr.kro.minestar.rankitem.functions.ReinforceClass.percentItem
import kr.kro.minestar.rankitem.functions.ReinforceClass.reinforce
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

class Reinforce(override val player: Player) : GUI {
    override val pl: JavaPlugin = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 1, "[장비 강화]")

    var item: ItemStack? = null

    val description = head(9236).display("§7강화할 아이템을 클릭하세요!")
    val reinforceButton = Material.ANVIL.item().display("§a강화하기")

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in 0 until gui.size) gui.setItem(slot, Material.BLACK_STAINED_GLASS_PANE.item().display(" "))
        if (item != null) if (!ReinforceClass.canReinforce(item!!).boolean) item = null
        if (item == null) {
            gui.setItem(4, description)
            return
        }
        val level = ReinforceClass.getReinforceLevel(item!!) ?: 0
        gui.setItem(2, percentItem(level + 1))
        gui.setItem(6, reinforceButton)
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
            val booleanScript = ReinforceClass.canReinforce(clickItem)
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
            if (clickItem != reinforceButton) return
            if (!ReinforceClass.canReinforce(item!!).boolean) {
                item = null
                displaying()
                return
            }
            val booleanScript = reinforce(player.inventory, item!!)
            if (booleanScript.boolean) {
                displaying()
                player.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.AMBIENT, 1.0F, 1.0F)
            } else player.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.AMBIENT, 0.5F, 1.0F)
            booleanScript.script.toPlayer(player)
        }
    }
}