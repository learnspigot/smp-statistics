package com.learnspigot.smp.statistics.expansion;

import com.learnspigot.smp.statistics.statistic.Statistic;
import com.learnspigot.smp.statistics.statistic.StatisticHandler;
import com.learnspigot.smp.statistics.statistic.data.StatisticData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class StatisticsExpansion extends PlaceholderExpansion {
    private final StatisticHandler statisticHandler;

    public StatisticsExpansion(final @NotNull StatisticHandler statisticHandler) {
        this.statisticHandler = statisticHandler;
    }

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
        return "2.0.0";
    }

    @Override
    public @Nullable String onRequest(final @NotNull OfflinePlayer player, final @NotNull String placeholder) {
        boolean b = placeholder.endsWith("_player");
        int i = b ? placeholder.length() - 7 : placeholder.length() - 4;

        Optional<Statistic> statistic = Statistic.fromString(placeholder.substring(0, i));
        if (statistic.isPresent()) {
            StatisticData statisticData = statisticHandler.getTop(statistic.get());
            return b ? statisticData.player() : statisticData.value();
        }

        StatisticData statisticData = statisticHandler.getTop(Statistic.OTHER
                .bukkitStatistic(placeholder.substring(0, i)));
        return b ? statisticData.player() : statisticData.value();
    }
}
