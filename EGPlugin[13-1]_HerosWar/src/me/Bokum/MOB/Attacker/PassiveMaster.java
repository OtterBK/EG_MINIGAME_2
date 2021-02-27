package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.avaje.ebean.enhance.asm.commons.GeneratorAdapter;

import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.CloudEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class PassiveMaster extends Ability{

	public Player passiver;
	public boolean skill3 = false;
	
	public PassiveMaster(String playername, Player p){
		super(playername, "�нú긶����");
		passiver = p;
		ItemStack item = new ItemStack(286, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�нú� ������ ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��66");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1.25");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		p.getInventory().setItem(0 ,item);
		
		item = new ItemStack(265, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c�ֽ�ų ��f]");
		item.setItemMeta(meta);
		p.getInventory().setItem(1 ,item);
		
		p.getInventory().setItem(2 ,null);
		p.getInventory().setItem(3 ,null);
	}
	
	public void description(){
		passiver.sendMessage("��6============= ��f[ ��b�нú� ������ ��f] - ��e������ ��7: ��cAttacker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d\n��713ĭ���� �ٶ󺸴� �÷��̾��� �ڷ� �̵�"
				+ "��f- ��a�нú�2(������ų) ��7: ��d��ø�� ���\n��7�������� �޾��� �� 25%Ȯ���� �ż�1���� 5��\n"
				+ "��f- ��2�нú�4(�ñر�)��f(��c���Ž� ��밡�ɡ�f) ��7: ��dũ��Ƽ��\n��7Ÿ�ݽ� 16%Ȯ���� ������ 1.5��.\n"
				+ "��f- ��2�нú�3��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ��\n��725%Ȯ���� ��� ���� ȸ��");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Player t = MobSystem.Getcorsurplayer(p, 13);
			if(t == null){
				p.sendMessage(Main.MS+"13ĭ���� �ٶ󺸴°��� �÷��̾ �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", 14, true);
			t.getWorld().playSound(t.getLocation(), Sound.BAT_TAKEOFF, 1.5f, 1.7f);
			p.getWorld().playSound(t.getLocation(), Sound.BAT_TAKEOFF, 1.5f, 1.7f);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0, 35, p.getLocation(), 20);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0, 35, t.getLocation(), 20);
			p.teleport(t.getLocation().subtract(t.getEyeLocation().getDirection()));
		}
	}
	
	public void Active(PlayerItemHeldEvent e){
		if(e.getNewSlot() == 1){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			PrimarySkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(passiver.getInventory().getHeldItemSlot() != 0) return;
		if(can_passive && MobSystem.Getrandom(0, 5) == 0){
			Player p = (Player) e.getDamager();
			p.sendMessage(Main.MS+"ũ��Ƽ��!");
			p.getWorld().playSound(p.getLocation(), Sound.FIRE_IGNITE, 1.5f, 0.2f);
			ParticleEffect.CRIT.display(0.1f, 0.1f, 0.1f, 0, 35, p.getLocation(), 20);
			e.setDamage(9);
		}else{
			e.setDamage(6);
		}
	}
	
	public void onHitDamaged(EntityDamageEvent e){
		Player p = (Player) e.getEntity();
		if(MobSystem.Getrandom(0, 2) == 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
		}
		if(can_passive && MobSystem.Getrandom(0, 3) == 0){
			e.setCancelled(true);
			p.sendMessage(Main.MS+"������ ȸ���߽��ϴ�!");
			p.getWorld().playSound(p.getLocation(), Sound.BAT_HURT, 1.5f, 0.7f);
			ParticleEffect.WATER_BUBBLE.display(0.1f, 0.1f, 0.1f, 0, 35, p.getLocation(), 20);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) ((e.getDamage()*1.25)));
	}
}




