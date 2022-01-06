package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.enums.Rank
import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.rankitem.functions.create.CreateRankItemClass
import kr.kro.minestar.rankitem.functions.dust.TradeDust
import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass
import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.utility.string.toServer
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private enum class Arg { create, trade, cmd3 }

    override fun onCommand(player: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (player !is Player) return false
        if (args.isEmpty()) "rankitem".toPlayer(player).also { return false }
        when (args.first()) {
            "test" -> {
                ReinforceClass.newClass(player, player.inventory.itemInMainHand)
            }
            Arg.create.name -> CreateRankItemClass.newClass(player, player.inventory.itemInMainHand)
            Arg.trade.name -> TradeDust(player)
            Arg.cmd3.name -> {}
        }
        return false
    }

    override fun onTabComplete(p: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        val arg = mutableListOf<String>()
        if (args.size == 1) {
            for (v in enumValues<Arg>()) arg.add(v.name)
            for (s in arg) if (s.contains(args.last())) list.add(s)
        }
        if (args.size > 1) when (args.first()) {

        }
        return list
    }
}

