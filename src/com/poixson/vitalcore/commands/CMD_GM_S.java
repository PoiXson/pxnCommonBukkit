package com.poixson.vitalcore.commands;

import static com.poixson.vitalcore.VitalCorePlugin.CHAT_PREFIX;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poixson.tools.commands.pxnCommandRoot;
import com.poixson.vitalcore.VitalCorePlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;


// /gms
public class CMD_GM_S extends pxnCommandRoot {



	public CMD_GM_S(final VitalCorePlugin plugin) {
		super(
			plugin,
			"pxn", // namespace
			"Change game mode to Survival.", // desc
			null, // usage
			"pxn.cmd.gm.s", // perm
			// labels
			"gms",
			"gm-s",
			"gmsurvival",
			"gm-survival"
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		final int num_args = args.length;
		// self
		if (num_args == 0) {
			if (player == null) {
				sender.sendMessage("Cannot change game mode for console");
				return true;
			}
			if (!sender.hasPermission("pxn.cmd.gm.s"))
				return false;
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(Component.textOfChildren(
				Component.text("Game mode: "               ).color(NamedTextColor.AQUA),
				Component.text(GameMode.SURVIVAL.toString()).color(NamedTextColor.GOLD)
			));
			return true;
		// other players
		} else {
			if (!sender.hasPermission("pxn.cmd.gm.s.other"))
				return false;
			int count = 0;
			LOOP_ARGS:
			for (final String arg : args) {
				final Player p = Bukkit.getPlayer(arg);
				if (p == null) {
					sender.sendMessage(CHAT_PREFIX.append(Component.text(
						"Player not found: "+arg).color(NamedTextColor.RED)));
					continue LOOP_ARGS;
				}
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(Component.textOfChildren(
					Component.text("Game mode: "               ).color(NamedTextColor.AQUA),
					Component.text(GameMode.SURVIVAL.toString()).color(NamedTextColor.GOLD)
				));
				count++;
			}
			if (count > 0) {
				sender.sendMessage(CHAT_PREFIX.append(Component.text(String.format(
					"Set game mode to %s for %d player%s",
					GameMode.SURVIVAL.toString(),
					Integer.valueOf(count),
					(count == 1 ? "" : "s")
				)).color(NamedTextColor.AQUA)));
				return true;
			}
		}
		return false;
	}



	@Override
	public List<String> onTabComplete(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission("pxn.cmd.gm.s.other"))
			return null;
		return this.onTabComplete_Players(args);
	}



}
