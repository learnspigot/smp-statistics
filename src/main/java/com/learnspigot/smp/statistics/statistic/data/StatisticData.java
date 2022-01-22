package com.learnspigot.smp.statistics.statistic.data;

import com.learnspigot.smp.statistics.statistic.Statistic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record StatisticData(@NotNull Statistic statistic, @Nullable String player, @NotNull String value) {
}
