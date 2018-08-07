package xyz.ijusterik.mention;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.flowpowered.math.vector.Vector3d;

@Plugin(id = "mention", name = "Mention", version = "1.0", description = "Mention players in chat!")
public class Mention {

	@Listener
	public void onChatEvent(MessageChannelEvent.Chat event, @First Player usingPlayer) {
		Text playerChat = event.getMessage();
		String message = playerChat.toPlain();

		if (!(message.contains("§l@")) && message.contains("@")) {

			String[] segmentedMessage = message.split(" ");
			List<Player> mentionedPlayers = new ArrayList<Player>();

			for (String segment : segmentedMessage) {

				if (segment.startsWith("@")) {

					String calledPlayer = segment.substring(1);

					if (Sponge.getServer().getPlayer(calledPlayer).isPresent()) {
						mentionedPlayers.add(Sponge.getServer().getPlayer(calledPlayer).get());
					}
				}
			}

			for (Player p : mentionedPlayers) {
				Vector3d position = p.getLocation().getPosition();
				SoundType sound = SoundTypes.ENTITY_ARROW_HIT;
				double volume = 1;
				p.playSound(sound, position, volume);
				p.sendMessage(Text.of(usingPlayer.getName() + " mentioned you in chat! :)"));
			}
		}
		String formattedMessage = message.replace("@", "§l@");
		Text sendChat = Text.of(formattedMessage);
		event.setMessage(sendChat);
	}
}
