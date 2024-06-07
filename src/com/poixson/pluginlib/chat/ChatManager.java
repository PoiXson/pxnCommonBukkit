package com.poixson.pluginlib.chat;

import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.poixson.pluginlib.pxnPluginLib;
import com.poixson.pluginlib.chat.ChatMessage.ChatDelivery;
import com.poixson.tools.events.xListener;
import com.poixson.utils.BukkitUtils;


public class ChatManager implements xListener {

	protected final pxnPluginLib plugin;

	protected final String chat_format;
	protected final double local_distance;



	public ChatManager(final pxnPluginLib plugin,
			final String chat_format, final double local_distance) {
		this.plugin         = plugin;
		this.chat_format    = BukkitUtils.FormatColors(chat_format);
		this.local_distance = local_distance;
	}



	public void register() {
		xListener.super.register(this.plugin);
	}



	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		this.runChatMessage(new ChatMessage(this, event));
	}



	public void runChatMessage(final ChatMessage msg) {
		new BukkitRunnable() {
			final AtomicReference<ChatMessage> msg = new AtomicReference<ChatMessage>(null);
			public BukkitRunnable init(final ChatMessage msg) {
				this.msg.set(msg);
				return this;
			}
			@Override
			public void run() {
				final ChatMessage msg = this.msg.get();
				if (msg != null)
					msg.run();
			}
		}.init(msg)
		.runTask(this.plugin);
	}



	public double getLocalDistance() {
		return this.local_distance;
	}



	public boolean hasRadio(final Player player) {
//TODO
return true;
	}



	public String format(final Player player_from, final ChatDelivery deliver, final String msg) {
		String result = this.chat_format
			.replace("<PLAYER>", player_from.getName());
		// local chat
		if (ChatDelivery.DELIVER_LOCAL.equals(deliver)) {
			result = result
				.replace("<LOCAL>",  "")
				.replace("</LOCAL>", "");
		} else {
			final int tag_start = result.indexOf("<LOCAL>" );
			final int tag_end   = result.indexOf("</LOCAL>");
			if (tag_start >= 0 && tag_end > 0) {
				result =
					result.substring(0, tag_start) +
					result.substring(tag_end + 8);
			}
		}
		// radio chat
		if (ChatDelivery.DELIVER_RADIO.equals(deliver)) {
			result = result
				.replace("<RADIO>",  "")
				.replace("</RADIO>", "");
		} else {
			final int tag_start = result.indexOf("<RADIO>" );
			final int tag_end   = result.indexOf("</RADIO>");
			if (tag_start >= 0 && tag_end > 0) {
				result =
					result.substring(0, tag_start) +
					result.substring(tag_end + 8);
			}
		}
		return result + msg;
	}



}
