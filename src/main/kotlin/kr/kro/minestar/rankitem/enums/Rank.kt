package kr.kro.minestar.rankitem.enums

enum class Rank(private val color: String) {
    SSS("§4"),
    SS("§5"),
    S("§6"),
    AA("§9"),
    A("§c"),
    B("§b"),
    C("§a"),
    D("§e"),
    E("§7"),
    F("§8"),
    ;

    override fun toString(): String = "§f$color$name"
}