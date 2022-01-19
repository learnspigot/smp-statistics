package com.bungoh.smpstatistics.placeholders;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang3.StringUtils;
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

        String formattedPlaceholder = null;

        if (placeholder.contains("_player")) {
            formattedPlaceholder = StringUtils.substringBefore(placeholder, "_player");
            return getTopPlayer(convertToStatistic(formattedPlaceholder)).getKey().getName();
        }

        if (placeholder.contains("_amt")) {
            formattedPlaceholder = StringUtils.substringBefore(placeholder, "_amt");
            return getTopPlayer(convertToStatistic(formattedPlaceholder)).getValue().toString();
        }

        if (convertToStatistic(formattedPlaceholder) == null) return "NULL PLACEHOLDER";

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

    private Statistic convertToStatistic(String statString) {
        final Optional<Statistic> convertedStat = Enums.getIfPresent(Statistic.class, statString.toUpperCase());

        if(!convertedStat.isPresent()) return null;

        return convertedStat.get();
    }

}
