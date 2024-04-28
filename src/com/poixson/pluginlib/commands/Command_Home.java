package com.poixson.pluginlib.commands;

import org.bukkit.command.CommandSender;

import com.poixson.pluginlib.pxnPluginLib;
import com.poixson.tools.commands.pxnCommandRoot;


public class Command_Home extends pxnCommandRoot {



	public Command_Home(final pxnPluginLib plugin) {
		super(
			plugin,
			"Teleport to a saved home location.", // desc
			null, // usage
			"pxn.cmd.home", // perm
			"home"
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
System.out.println("COMMAND:"); for (final String arg : args) System.out.println("  "+arg);
return false;
	}



}