package kr.kro.minestar.rankitem.functions.gui.edit

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.prefix
import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.rankitem.functions.RankClass
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

class EditRankItem(override val player: Player, val item: ItemStack) : GUI {
    private enum class Button {
        ADD_PREFIX, SET_DISPLAY, ADD_SUFFIX, ADD_LORE, REMOVE_LORE,
        EDIT_ENCHANT, EDIT_FLAG, EDIT_UNBREAKABLE, SET_RANK
    }

    private enum class Typing { NULL, DISPLAY, PREFIX, SUFFIX, LORE }

    override val pl = Main.pl
    override val gui: Inventory = Bukkit.createInventory(null, 9 * 2, "랭크 아이템 생성")

    private var typing = Typing.NULL

    private val slots = mapOf(
        Pair(Slot(0, 0, Material.NAME_TAG.item().display("§e접두사 추가")), Button.ADD_PREFIX),
        Pair(Slot(0, 1, Material.WRITABLE_BOOK.item().display("§e이름 설정")), Button.SET_DISPLAY),
        Pair(Slot(0, 2, Material.NAME_TAG.item().display("§e접미사 추가")), Button.ADD_SUFFIX),

        Pair(Slot(1, 0, Material.MAP.item().display("§e로어 추가")), Button.ADD_LORE),
        Pair(Slot(1, 1, Material.PAPER.item().display("§e로어 제거")), Button.REMOVE_LORE),

        Pair(Slot(0, 4, Material.ENCHANTED_BOOK.item().display("§e인첸트 추가")), Button.EDIT_ENCHANT),
        Pair(Slot(0, 6, Material.DIAMOND.item().display("§e부서짐 설정")), Button.EDIT_UNBREAKABLE),
        Pair(Slot(0, 7, Material.STRUCTURE_VOID.item().display("§e속성 숨김 설정")), Button.EDIT_FLAG),
        Pair(Slot(0, 8, ItemClass.rankStone(Rank.SSS).display("§e랭크 설정")), Button.SET_RANK),
    )

    init {
        if (player.isOp) if (item.type != Material.AIR) openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (slot in slots.keys) gui.setItem(slot.get, slot.item)
        gui.setItem(9 + 4, item)
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return

        var slot: Slot? = null
        for (s in slots.keys) if (s.get == e.slot) {
            slot = s
            break
        }
        slot ?: return
        val button = slots[slot] ?: return
        when (button) {
            Button.ADD_PREFIX -> {
                typing = Typing.PREFIX
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            Button.SET_DISPLAY -> {
                typing = Typing.DISPLAY
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 입력할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            Button.ADD_SUFFIX -> {
                typing = Typing.SUFFIX
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            Button.ADD_LORE -> {
                typing = Typing.LORE
                player.closeInventory()
                Bukkit.getPluginManager().registerEvents(this, pl)
                "$prefix §a채팅으로 추가할 수 있습니다.".toPlayer(player)
                "§7(취소는 \"취소\" 입력)".toPlayer(player)
            }
            Button.REMOVE_LORE -> {
                val lore = item.lore!!
                if (lore.size <= 2) return
                lore.removeAt(lore.lastIndex - 2)
                if (lore.size == 3) lore.removeAt(lore.lastIndex - 2)
                item.lore = lore
                displaying()
            }
            Button.EDIT_ENCHANT -> AddEnchant(player, item, this)
            Button.EDIT_FLAG -> {
                if (!item.itemFlags.contains(ItemFlag.HIDE_ATTRIBUTES)) item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                else item.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                displaying()
            }
            Button.EDIT_UNBREAKABLE -> {
                val meta = item.itemMeta
                meta.isUnbreakable = !meta.isUnbreakable
                item.itemMeta = meta
                displaying()
            }
            Button.SET_RANK -> SetRank(player, this)
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
                val rank = RankClass.getRank(item)
                val lore = item.lore!!
                lore.removeAt(lore.lastIndex)
                lore.removeAt(lore.lastIndex)
                if (lore.size == 0) lore.add(" ")
                lore.add("§f$message")
                lore.add(" ")
                lore.add("§f§7랭크 : $rank")
                item.lore = lore
            }
            Typing.NULL -> return
        }
        e.isCancelled = true
        typing = Typing.NULL
        HandlerList.unregisterAll(this)
        openGUI()
    }
}