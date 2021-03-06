package io.github.mats391.abandonpet;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AbandonPet extends JavaPlugin implements Listener
{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCatTame( final EntityTameEvent event ) {
		if ( event.isCancelled() )
			return;

		if ( event.getEntityType().equals( EntityType.OCELOT ) )
		{
			final Ocelot cat = (Ocelot) event.getEntity();

			if ( !cat.getCatType().equals( Type.WILD_OCELOT ) )
				event.setCancelled( true );

			cat.setOwner( event.getOwner() );
		}
	}

	@Override
	public void onEnable() {
		final PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents( this, this );
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPetBreed( final CreatureSpawnEvent event ) {
		if ( event.getEntity() instanceof Tameable && event.getSpawnReason().equals( SpawnReason.BREEDING ) )
			( (Tameable) event.getEntity() ).setTamed( false );
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPetHit( final PlayerInteractEntityEvent event ) {
		if ( event.getRightClicked() instanceof Tameable && event.getPlayer().getItemInHand().getType().equals( Material.STICK ) )
			if ( ( (Tameable) event.getRightClicked() ).getOwner().equals( event.getPlayer() ) )
				( (Tameable) event.getRightClicked() ).setTamed( false );

	}

}
