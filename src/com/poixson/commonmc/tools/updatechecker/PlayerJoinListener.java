package com.poixson.commonmc.tools.updatechecker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.poixson.commonmc.pxnCommonPlugin;
import com.poixson.commonmc.tools.plugin.xListener;
import com.poixson.utils.Utils;


public class PlayerJoinListener extends xListener<pxnCommonPlugin> {

	protected final UpdateCheckManager manager;



	public PlayerJoinListener(final pxnCommonPlugin plugin, final UpdateCheckManager manager) {
		super(plugin);
		this.manager = manager;
	}



	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		if (this.manager.hasUpdate()) {
			final Player player = event.getPlayer();
			if (player.isOp() || player.hasPermission("pxncommon.updates")) {
				(new BukkitRunnable() {
					@Override
					public void run() {
						final UpdateCheckerTask[] updates = PlayerJoinListener.this.manager.getUpdatesToPlayers();
						String msg;
						for (final UpdateCheckerTask task : updates) {
							msg = task.getUpdateMessage();
							if (Utils.notEmpty(msg))
								player.sendMessage(msg);
						}
					}
				}).runTaskLater(this.plugin, 10L);
			}
		}
	}



}