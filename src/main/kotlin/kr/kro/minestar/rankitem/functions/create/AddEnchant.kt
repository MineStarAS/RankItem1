package kr.kro.minestar.rankitem.functions.create

import kr.kro.minestar.rankitem.Main
import kr.kro.minestar.rankitem.Main.Companion.headItem
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.setDisplay
import kr.kro.minestar.utility.material.item
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class AddEnchant(override val player: Player, val item: ItemStack, val createRankItem: CreateRankItem) : GUI {
    override val pl = Main.pl
    override val gui = Bukkit.createInventory(null, 9 * 4, "인첸트 목록 1")
    val gui1 = Bukkit.createInventory(null, 9 * 4, "인첸트 목록 2")

    val white1 = headItem.getItemStack("White1")!!.setDisplay("§71번 목록으로")
    val white2 = headItem.getItemStack("White2")!!.setDisplay("§72번 목록으로")

    val level = listOf(
        headItem.getItemStack("Blue1")!!.setDisplay("§9인첸트 레벨 : 1"),
        headItem.getItemStack("Blue2")!!.setDisplay("§9인첸트 레벨 : 2"),
        headItem.getItemStack("Blue3")!!.setDisplay("§9인첸트 레벨 : 3"),
        headItem.getItemStack("Blue4")!!.setDisplay("§9인첸트 레벨 : 4"),
        headItem.getItemStack("Blue5")!!.setDisplay("§9인첸트 레벨 : 5"),
        headItem.getItemStack("Blue6")!!.setDisplay("§9인첸트 레벨 : 6"),
        headItem.getItemStack("Blue7")!!.setDisplay("§9인첸트 레벨 : 7"),
        headItem.getItemStack("Blue8")!!.setDisplay("§9인첸트 레벨 : 8"),
        headItem.getItemStack("Blue9")!!.setDisplay("§9인첸트 레벨 : 9"),
        headItem.getItemStack("Blue10")!!.setDisplay("§9인첸트 레벨 : 10"),
    )

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (enchant in Enchantment.values()) {
            val item = Material.ENCHANTED_BOOK.item()
            val slot: Slot? = when (enchant) {
                Enchantment.PROTECTION_ENVIRONMENTAL -> Slot(0, 1, item)
                Enchantment.PROTECTION_FIRE -> Slot(0, 2, item)
                Enchantment.PROTECTION_PROJECTILE -> Slot(0, 3, item)
                Enchantment.PROTECTION_EXPLOSIONS -> Slot(0, 4, item)
                Enchantment.THORNS -> Slot(0, 5, item)

                Enchantment.MENDING -> Slot(0, 7, item)

                Enchantment.OXYGEN -> Slot(1, 1, item)
                Enchantment.WATER_WORKER -> Slot(1, 2, item)

                Enchantment.PROTECTION_FALL -> Slot(1, 4, item)
                Enchantment.DEPTH_STRIDER -> Slot(1, 5, item)
                Enchantment.FROST_WALKER -> Slot(1, 6, item)
                Enchantment.SOUL_SPEED -> Slot(1, 7, item)

                Enchantment.DAMAGE_ALL -> Slot(2, 1, item)
                Enchantment.DAMAGE_UNDEAD -> Slot(2, 2, item)
                Enchantment.DAMAGE_ARTHROPODS -> Slot(2, 3, item)
                Enchantment.SWEEPING_EDGE -> Slot(2, 4, item)
                Enchantment.FIRE_ASPECT -> Slot(2, 5, item)
                Enchantment.LOOT_BONUS_MOBS -> Slot(2, 6, item)
                Enchantment.KNOCKBACK -> Slot(2, 7, item)
                else -> null
            }
            slot ?: continue
            slot.item.setDisplay("§e인첸트 부여")
            val meta = slot.item.itemMeta
            meta.addEnchant(enchant, 1, false)
            slot.item.itemMeta = meta
            gui.setItem(slot.get, slot.item)
        }
        gui.setItem(0, Material.DIAMOND_CHESTPLATE.item().setDisplay("§6방어구").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(6, Material.HEART_OF_THE_SEA.item().setDisplay("§6만능").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(9, Material.DIAMOND_HELMET.item().setDisplay("§6투구").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(12, Material.DIAMOND_BOOTS.item().setDisplay("§6부츠").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(18, Material.IRON_SWORD.item().setDisplay("§6검").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })

        gui.setItem(9 * 3 + 4, this.item)
    }

    fun displaying1() {
        gui1.clear()
        for (enchant in Enchantment.values()) {
            val item = Material.ENCHANTED_BOOK.item()
            val slot: Slot? = when (enchant) {
                Enchantment.LOOT_BONUS_BLOCKS -> Slot(0, 1, item)
                Enchantment.DIG_SPEED -> Slot(0, 2, item)
                Enchantment.DURABILITY -> Slot(0, 3, item)
                Enchantment.SILK_TOUCH -> Slot(0, 4, item)

                Enchantment.VANISHING_CURSE -> Slot(0, 6, item)
                Enchantment.BINDING_CURSE -> Slot(0, 7, item)

                Enchantment.ARROW_DAMAGE -> Slot(1, 1, item)
                Enchantment.ARROW_KNOCKBACK -> Slot(1, 2, item)
                Enchantment.ARROW_FIRE -> Slot(1, 3, item)
                Enchantment.ARROW_INFINITE -> Slot(1, 4, item)

                Enchantment.QUICK_CHARGE -> Slot(1, 6, item)
                Enchantment.PIERCING -> Slot(1, 7, item)
                Enchantment.MULTISHOT -> Slot(1, 8, item)

                Enchantment.LURE -> Slot(2, 1, item)
                Enchantment.LUCK -> Slot(2, 2, item)

                Enchantment.IMPALING -> Slot(2, 4, item)
                Enchantment.LOYALTY -> Slot(2, 5, item)
                Enchantment.RIPTIDE -> Slot(2, 6, item)
                Enchantment.CHANNELING -> Slot(2, 7, item)
                else -> null
            }
            slot ?: continue
            slot.item.setDisplay("§e인첸트 부여")
            val meta = slot.item.itemMeta
            meta.addEnchant(enchant, 1, false)
            slot.item.itemMeta = meta
            gui1.setItem(slot.get, slot.item)
        }
        gui1.setItem(0, Material.IRON_PICKAXE.item().setDisplay("§6도구").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui1.setItem(5, Material.WITHER_SKELETON_SKULL.item().setDisplay("§6저주").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui1.setItem(9, Material.BOW.item().setDisplay("§6활").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui1.setItem(14, Material.CROSSBOW.item().setDisplay("§6석궁").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui1.setItem(18, Material.FISHING_ROD.item().setDisplay("§6낚싯대").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui1.setItem(21, Material.TRIDENT.item().setDisplay("§6삼지창").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.inventory != gui && e.inventory != gui1) return
        if (e.whoClicked != player) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        val item = e.currentItem ?: return
        if (item.type != Material.ENCHANTED_BOOK) return
        val enchant = item.enchantments.keys.first()
        when (e.click) {
            ClickType.LEFT -> TODO()
            ClickType.SHIFT_LEFT -> TODO()
            ClickType.RIGHT -> TODO()
            ClickType.SHIFT_RIGHT -> TODO()
            ClickType.WINDOW_BORDER_LEFT -> TODO()
            ClickType.WINDOW_BORDER_RIGHT -> TODO()
            ClickType.MIDDLE -> TODO()
            ClickType.NUMBER_KEY -> TODO()
            ClickType.DOUBLE_CLICK -> TODO()
            ClickType.DROP -> TODO()
            ClickType.CONTROL_DROP -> TODO()
            ClickType.CREATIVE -> TODO()
            ClickType.SWAP_OFFHAND -> TODO()
            ClickType.UNKNOWN -> TODO()
        }
    }
}