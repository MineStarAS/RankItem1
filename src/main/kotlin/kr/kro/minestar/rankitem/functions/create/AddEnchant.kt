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
    override val gui = Bukkit.createInventory(null, 9 * 4, "인첸트 목록")

    val backItem = headItem.getItemStack("RedArrowLeft")!!.setDisplay("§c뒤로 가기")

    val white = listOf(
        headItem.getItemStack("White1")!!.setDisplay("§71번 목록으로"),
        headItem.getItemStack("White2")!!.setDisplay("§72번 목록으로"),
    )

    val levelItem = listOf(
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

    var level = 0

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (enchant in Enchantment.values()) {
            val book = Material.ENCHANTED_BOOK.item()
            val slot: Slot? = when (enchant) {
                Enchantment.PROTECTION_ENVIRONMENTAL -> Slot(0, 1, book)
                Enchantment.PROTECTION_FIRE -> Slot(0, 2, book)
                Enchantment.PROTECTION_PROJECTILE -> Slot(0, 3, book)
                Enchantment.PROTECTION_EXPLOSIONS -> Slot(0, 4, book)
                Enchantment.THORNS -> Slot(0, 5, book)

                Enchantment.MENDING -> Slot(0, 7, book)

                Enchantment.OXYGEN -> Slot(1, 1, book)
                Enchantment.WATER_WORKER -> Slot(1, 2, book)

                Enchantment.PROTECTION_FALL -> Slot(1, 4, book)
                Enchantment.DEPTH_STRIDER -> Slot(1, 5, book)
                Enchantment.FROST_WALKER -> Slot(1, 6, book)
                Enchantment.SOUL_SPEED -> Slot(1, 7, book)

                Enchantment.DAMAGE_ALL -> Slot(2, 1, book)
                Enchantment.DAMAGE_UNDEAD -> Slot(2, 2, book)
                Enchantment.DAMAGE_ARTHROPODS -> Slot(2, 3, book)
                Enchantment.SWEEPING_EDGE -> Slot(2, 4, book)
                Enchantment.FIRE_ASPECT -> Slot(2, 5, book)
                Enchantment.LOOT_BONUS_MOBS -> Slot(2, 6, book)
                Enchantment.KNOCKBACK -> Slot(2, 7, book)
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

        gui.setItem(9 * 3, backItem)
        gui.setItem(9 * 3 + 4, item)
        gui.setItem(9 * 3 + 7, levelItem[level])
        gui.setItem(9 * 3 + 8, white[1])
    }

    fun displaying1() {
        gui.clear()
        for (enchant in Enchantment.values()) {
            val book = Material.ENCHANTED_BOOK.item()
            val slot: Slot? = when (enchant) {
                Enchantment.LOOT_BONUS_BLOCKS -> Slot(0, 1, book)
                Enchantment.DIG_SPEED -> Slot(0, 2, book)
                Enchantment.DURABILITY -> Slot(0, 3, book)
                Enchantment.SILK_TOUCH -> Slot(0, 4, book)

                Enchantment.VANISHING_CURSE -> Slot(0, 6, book)
                Enchantment.BINDING_CURSE -> Slot(0, 7, book)

                Enchantment.ARROW_DAMAGE -> Slot(1, 1, book)
                Enchantment.ARROW_KNOCKBACK -> Slot(1, 2, book)
                Enchantment.ARROW_FIRE -> Slot(1, 3, book)
                Enchantment.ARROW_INFINITE -> Slot(1, 4, book)

                Enchantment.QUICK_CHARGE -> Slot(1, 6, book)
                Enchantment.PIERCING -> Slot(1, 7, book)
                Enchantment.MULTISHOT -> Slot(1, 8, book)

                Enchantment.LURE -> Slot(2, 1, book)
                Enchantment.LUCK -> Slot(2, 2, book)

                Enchantment.IMPALING -> Slot(2, 4, book)
                Enchantment.LOYALTY -> Slot(2, 5, book)
                Enchantment.RIPTIDE -> Slot(2, 6, book)
                Enchantment.CHANNELING -> Slot(2, 7, book)
                else -> null
            }
            slot ?: continue
            slot.item.setDisplay("§e인첸트 부여")
            val meta = slot.item.itemMeta
            meta.addEnchant(enchant, 1, false)
            slot.item.itemMeta = meta
            gui.setItem(slot.get, slot.item)
        }
        gui.setItem(0, Material.IRON_PICKAXE.item().setDisplay("§6도구").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(5, Material.WITHER_SKELETON_SKULL.item().setDisplay("§6저주").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(9, Material.BOW.item().setDisplay("§6활").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(14, Material.CROSSBOW.item().setDisplay("§6석궁").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(18, Material.FISHING_ROD.item().setDisplay("§6낚싯대").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })
        gui.setItem(21, Material.TRIDENT.item().setDisplay("§6삼지창").also { it.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) })

        gui.setItem(9 * 3, backItem)
        gui.setItem(9 * 3 + 4, item)
        gui.setItem(9 * 3 + 7, levelItem[level])
        gui.setItem(9 * 3 + 8, white[0])
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.inventory != gui) return
        if (e.whoClicked != player) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val clickItem = e.currentItem ?: return
        if (clickItem.type == Material.ENCHANTED_BOOK) {
            val enchant = clickItem.enchantments.keys.first()
            if (item.enchantments.contains(enchant)) item.itemMeta = item.itemMeta.also { it.removeEnchant(enchant) }
            else item.itemMeta = item.itemMeta.also { it.addEnchant(enchant, level + 1, true) }
            gui.setItem(9 * 3 + 4, item)
            return
        }
        when (clickItem) {
            backItem -> createRankItem.openGUI()
            white[0] -> displaying()
            white[1] -> displaying1()
            levelItem[0],
            levelItem[1],
            levelItem[2],
            levelItem[3],
            levelItem[4],
            levelItem[5],
            levelItem[6],
            levelItem[7],
            levelItem[8] -> gui.setItem(9 * 3 + 7, levelItem[++level])
            levelItem[9] -> {
                level = 0
                gui.setItem(9 * 3 + 7, levelItem[level])
            }
            item -> player.inventory.addItem(item)
        }
    }
}