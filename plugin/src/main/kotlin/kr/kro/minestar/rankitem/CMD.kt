package kr.kro.minestar.rankitem

import kr.kro.minestar.rankitem.functions.ItemClass
import kr.kro.minestar.rankitem.functions.RankClass
import kr.kro.minestar.rankitem.functions.gui.RankItemListGUI
import kr.kro.minestar.rankitem.functions.gui.Reinforce
import kr.kro.minestar.rankitem.functions.gui.Repair
import kr.kro.minestar.rankitem.functions.gui.TradePartsScrap
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private enum class Arg { create, list, trade, reinforce, repair }

    override fun onCommand(player: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (player !is Player) return false
        if (!player.isOp) return false
        if (args.isEmpty()) return false
        when (args.first()) {
            Arg.create.name -> RankClass.createRankItem(player.inventory.itemInMainHand).script.toPlayer(player)
            Arg.list.name -> RankItemListGUI(player)
            Arg.trade.name -> TradePartsScrap(player)
            Arg.reinforce.name -> Reinforce(player)
            Arg.repair.name -> Repair(player)
            "test1" -> player.inventory.addItem(ItemClass.reinforceTicket)
            "test2" -> player.inventory.addItem(ItemClass.partsScrap)
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

