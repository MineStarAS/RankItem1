package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.functions.RankItemClass
import kr.kro.minestar.rankitem.functions.gui.RankItemListGUI
import kr.kro.minestar.rankitem.functions.gui.reainforce.Reinforce
import kr.kro.minestar.rankitem.functions.gui.stone.TradeRankStone
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private enum class Arg { create, list, trade, reinforce }

    override fun onCommand(player: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (player !is Player) return false
        if (!player.isOp) return false
        if (args.isEmpty()) return false
        when (args.first()) {
            Arg.create.name -> RankItemClass.createRankItem(player.inventory.itemInMainHand).script.toPlayer(player)
            Arg.list.name -> RankItemListGUI(player)
            Arg.trade.name -> TradeRankStone(player)
            Arg.reinforce.name -> Reinforce(player, player.inventory.itemInMainHand)
        }
        return false
    }

    override fun onTabComplete(p: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        if (args.size == 1) {
            for (s in Arg.values()) if (s.name.contains(args.last())) list.add(s.name)
        }
        if (args.size > 1) when (args.first()) {
        }
        return list
    }
}

