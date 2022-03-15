package kr.kro.minestar.rankitem.enums

enum class Rank(private val color: String, val reinforceLimit: Int, val partsScrap: Int) {
    SSS("§4", 20, 10),
    SS("§5", 17, 9),
    S("§6", 15, 8),
    AA("§9", 12, 7),
    A("§c", 10, 6),
    B("§b", 9, 5),
    C("§a", 8, 4),
    D("§e", 7, 3),
    E("§7", 6, 2),
    F("§8", 5, 1),
    ;

    override fun toString(): String = "§f$color$name"
}