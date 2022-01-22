package com.learnspigot.smp.statistics;

import com.learnspigot.smp.statistics.expansion.StatisticsExpansion;
import com.learnspigot.smp.statistics.statistic.StatisticHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPStatistics extends JavaPlugin {
    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("Couldn't find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new StatisticsExpansion(new StatisticHandler()).register();
    }
}
