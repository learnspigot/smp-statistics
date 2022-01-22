package com.learnspigot.smp.statistics.statistic;

import com.learnspigot.smp.statistics.statistic.data.StatisticData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public final class StatisticHandler {
    public @NotNull StatisticData getTop(final @NotNull Statistic statistic) {
        if (statistic.bukkitStatistic() == null) {
            return new StatisticData(statistic, "null", "null");
        }

        OfflinePlayer o1 = Bukkit.getOfflinePlayers()[0];
        for (OfflinePlayer o2 : Bukkit.getOfflinePlayers()) {
            if (o1.getUniqueId() != o2.getUniqueId()) {
                o1 = compare(o1, o2, statistic);
            }
        }

        return new StatisticData(statistic, o1.getName(),
                statistic.convert(o1.getStatistic(statistic.bukkitStatistic())));
    }

    private @NotNull OfflinePlayer compare(final @NotNull OfflinePlayer o1, final @NotNull OfflinePlayer o2,
                                           final @NotNull Statistic statistic) {
        assert statistic.bukkitStatistic() != null;
        int a = o1.getStatistic(statistic.bukkitStatistic());
        int b = o2.getStatistic(statistic.bukkitStatistic());
        return a >= b ? o1 : o2;
    }
}
