package com.bungoh.smpstatistics.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SMPStatsExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "smpstats";
    }

    @Override
    public @NotNull String getAuthor() {
        return "LearnSpigot";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String placeholder) {
        if (placeholder.equalsIgnoreCase("kills_player")) {
            return getTopPlayer(Statistic.PLAYER_KILLS).getKey().getName();
        }

        if (placeholder.equalsIgnoreCase("deaths_player")) {
            return getTopPlayer(Statistic.DEATHS).getKey().getName();
        }

        if (placeholder.equalsIgnoreCase("kills_amt")) {
            return getTopPlayer(Statistic.PLAYER_KILLS).getValue().toString();
        }

        if (placeholder.equalsIgnoreCase("deaths_amt")) {
            return getTopPlayer(Statistic.DEATHS).getValue().toString();
        }

        return null;
    }

    private Map.Entry<OfflinePlayer, Integer> getTopPlayer(Statistic statistic) {
        Map<OfflinePlayer, Integer> map = new HashMap<>();
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            map.put(p, p.getStatistic(statistic));
        }

        Map.Entry<OfflinePlayer, Integer> maxEntry = null;
        for (Map.Entry<OfflinePlayer, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }

}
