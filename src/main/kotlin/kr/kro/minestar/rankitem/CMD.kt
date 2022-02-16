package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.functions.dust.TradeRankStone
import kr.kro.minestar.rankitem.functions.reainforce.ReinforceClass
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private enum class Arg { create, trade, reinforce }

    override fun onCommand(player: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (player !is Player) return false
        if (!player.isOp) return false
        if (args.isEmpty()) return false
        when (args.first()) {
            Arg.create.name -> CreateRankItemClass.newClass(player, player.inventory.itemInMainHand)
            Arg.trade.name -> TradeRankStone(player)
            Arg.reinforce.name -> ReinforceClass.newClass(player, player.inventory.itemInMainHand)
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

