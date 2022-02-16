package kr.kro.minestar.rankitem.functions.create

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.*
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CreateRankItem(override val player: Player, val item: ItemStack) : GUI {
    private enum class Typing { NULL, DISPLAY, PREFIX, SUFFIX, LORE }

    override val pl = Main.pl
    override val gui: Inventory = Bukkit.createInventory(null, 9, "랭크 아이템 생성")

    private var typing = Typing.NULL

    val slots = listOf(
        Slot(0, 0, Material.WRITABLE_BOOK.item().display("§e이름 설정")),
        Slot(0, 1, Material.NAME_TAG.item().display("§e접두사 추가")),
        Slot(0, 2, Material.NAME_TAG.item().display("§e접미사 추가")),
        Slot(0, 6, Material.MAP.item().display("§e로어 추가")),
        Slot(0, 7, Material.PAPER.item().display("§e로어 제거")),
        Slot(0, 8, Material.ENCHANTED_BOOK.item().display("§e인첸트 추가")),
        Slot(0, 5, Material.STRUCTURE_VOID.item().display("§e속성 숨기기/보이기")),
        Slot(0, 3, ItemClass.rankStone(Rank.SSS).display("§e랭크 설정")),
    )

    init {
        if (player.isOp) if (item.type != Material.AIR) openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in slots) gui.setItem(slot.get, slot.item)
        if (!ItemClass.isRankItem(item)) {
            item.addLore(" ")
            item.addLore("§f§7랭크 : ${Rank.F}")
        }
        gui.setItem(4, item)
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return

        when (clickItem) {
            item -> player.inventory.addItem(clickItem.clone())
            slots[0].item -> {
                typing = Typing.DISPLAY
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 입력할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            slots[1].item -> {
                typing = Typing.PREFIX
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            slots[2].item -> {
                typing = Typing.SUFFIX
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            slots[3].item -> {
                typing = Typing.LORE
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            slots[4].item -> {
                val lore = item.lore!!
                if (lore.size <= 2) return
                lore.removeAt(lore.lastIndex - 2)
                if (lore.size == 3) lore.removeAt(lore.lastIndex - 2)
                item.lore = lore
                displaying()
            }
            slots[5].item -> AddEnchant(player, item, this)
            slots[6].item -> {
                if (!item.itemFlags.contains(ItemFlag.HIDE_ATTRIBUTES)) item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                else item.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                displaying()
            }
            slots[7].item -> SetRank(player, this)
        }
    }

    @EventHandler
    fun chat(e: PlayerChatEvent) {
        if (e.player != player) return
        if (typing == Typing.NULL) return
        val message = e.message.replace('&', '§').replace('_', ' ')
        if (message == "취소") return openGUI()
        when (typing) {
            Typing.DISPLAY -> item.display(message)
            Typing.PREFIX -> item.addPrefix(message)
            Typing.SUFFIX -> item.addSuffix(message)
            Typing.LORE -> {
                val rank = ItemClass.getRank(item)
                val lore = item.lore!!
                lore.removeAt(lore.lastIndex)
                lore.removeAt(lore.lastIndex)
                if (lore.size == 0) lore.add(" ")
                lore.add("§f$message")
                lore.add(" ")
                lore.add("§f§7랭크 : $rank")
                item.lore = lore
            }
        }
        e.isCancelled = true
        typing = Typing.NULL
        HandlerList.unregisterAll(this)
        openGUI()
    }
}