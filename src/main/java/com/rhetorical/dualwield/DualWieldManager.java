package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DualWieldManager implements Listener {

    private static DualWieldManager instance;

    /** Instantiate the instance and register events */
    private DualWieldManager() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    /** Gets the singleton insgance of the DualWieldManager.
	 * @return The instance of the DualWieldManager.
	 * */
    static DualWieldManager getInstance() {
        return instance;
    }

    /**
	 * Creates a new DualWieldManager if it does not exist.
	 * @throws DualWieldManagerAlreadyExistsException Is thrown if the DualWieldManager already exists.
	 * */
    static void create() throws DualWieldManagerAlreadyExistsException {
        if (instance == null)
            instance = new DualWieldManager();
        else
            throw new DualWieldManagerAlreadyExistsException("The DualWieldManager already exists, but a new was was attempted to be created!");
    }

    /**
	 * Destroys the singleton instance.
	 * */
    static void destroy() {
        instance = null;
    }


    /**
	 * Performs a swing for the player.
	 *
     * @param from The player who swung their offhand.
     * @return If the player hit an entity.
     * */
    private boolean performSwing(Player from) {

        ItemStack heldItem = from.getInventory().getItemInOffHand();

        if (Main.requirePermission) {
            if (!from.hasPermission("rdw.use." + heldItem.getType().toString().toUpperCase()) && !from.isOp() && !from.hasPermission("rdw.use.*"))
                return false;
        }

        PacketHandler.playOffhandSwingAnimation(from);

        LivingEntity e = PlayerUtility.getTargetEntity(from, ItemStats.getSwingDistance(heldItem.getType()));

        if (e != null) {
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(from, e, EntityDamageEvent.DamageCause.CUSTOM, ItemStats.getAttackDamage(heldItem.getType()));
            Bukkit.getServer().getPluginManager().callEvent(event);
            return true;
        }

        return false;
    }

    /* Event Handlers */

	@EventHandler
	void onRightClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (!Main.allowedMaterials.contains(e.getPlayer().getInventory().getItemInOffHand().getType()))
			return;

		if (!Main.disallowedMaterials.contains(e.getPlayer().getInventory().getItemInMainHand().getType())) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK
					&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null
					&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof BlockDataMeta)
				return;

			Material mainhand = e.getPlayer().getInventory().getItemInMainHand().getType();
			if (e.getAction() == Action.RIGHT_CLICK_AIR
					&& (mainhand == Material.POTION
						|| mainhand == Material.LINGERING_POTION
					|| mainhand == Material.SPLASH_POTION
					|| mainhand == Material.SHIELD
					|| mainhand == Material.BOW
					|| mainhand == Material.CROSSBOW
					|| PlayerUtility.isFood(mainhand))) {
				return;
			}

			performSwing(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerHitEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
			if (!(e.getEntity() instanceof LivingEntity))
				return;

			if (e.isCancelled())
				return;

			Player p = (Player) e.getDamager();

			ItemStack hit = p.getInventory().getItemInOffHand();

			LivingEntity le = (LivingEntity) e.getEntity();

			if (le instanceof Player) {
				Player damaged = (Player) le;
				if (damaged.getGameMode() != GameMode.SURVIVAL && damaged.getGameMode() != GameMode.ADVENTURE) {
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1.0f, 1.0f);
					return;
				}
			}

			double damage = PlayerUtility.getDamage(hit, p, le, e.getDamage());
			le.damage(damage, e.getDamager());

			if (damage != 0d) {
				le.setVelocity(e.getDamager().getLocation().getDirection().setY(0.3d).multiply(0.5d));
			}

			/* Enchantment stuff */

			if (!(e.getEntity() instanceof Player))
				return;

			Player victim = (Player) e.getEntity();

			List<ItemStack> armor = new ArrayList<ItemStack>();

			if (victim.getInventory().getBoots() != null) {
				armor.add(victim.getInventory().getBoots());
			}

			if (victim.getInventory().getLeggings() != null) {
				armor.add(victim.getInventory().getLeggings());
			}

			if (victim.getInventory().getChestplate() != null) {
				armor.add(victim.getInventory().getChestplate());
			}

			if (victim.getInventory().getHelmet() != null) {
				armor.add(victim.getInventory().getHelmet());
			}

			double thornsDamage = 0d;

			for(ItemStack a : armor) {
				Map<Enchantment, Integer> enchantments = a.getEnchantments();

				if (enchantments.isEmpty())
					continue;


				if (enchantments.containsKey(Enchantment.THORNS)) {
					int level = enchantments.get(Enchantment.THORNS);

					double rand = Math.random();

					if (level * .15 < rand) {
						thornsDamage +=  Math.floor((Math.random() * 3) + 1);
					}
				}
			}

			if (thornsDamage != 0) {
				if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
					p.damage(thornsDamage);
					p.getWorld().playSound(victim.getLocation(), Sound.ENCHANT_THORNS_HIT, 1, 1);
				}
			}

			e.setCancelled(true);
		}
	}

}