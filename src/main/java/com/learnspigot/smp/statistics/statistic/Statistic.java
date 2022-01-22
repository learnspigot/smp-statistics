package com.learnspigot.smp.statistics.statistic;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public enum Statistic {
    KILLS(org.bukkit.Statistic.PLAYER_KILLS),
    DEATHS(org.bukkit.Statistic.DEATHS),
    DISTANCE_TRAVELLED(org.bukkit.Statistic.WALK_ONE_CM),
    RAIDS_WON(org.bukkit.Statistic.RAID_WIN),
    TIME_PLAYED(org.bukkit.Statistic.PLAY_ONE_MINUTE),
    OTHER(null);

    private final org.bukkit.Statistic bukkitStatistic;

    Statistic(final @NotNull org.bukkit.Statistic bukkitStatistic) {
        this.bukkitStatistic = bukkitStatistic;
    }

    public @NotNull org.bukkit.Statistic bukkitStatistic() {
        return bukkitStatistic;
    }

    public @NotNull String convert(final int value) {
        switch (this) {
            case TIME_PLAYED -> {
                return ((value / 20) / 3600) + " hours";
            }
            case DISTANCE_TRAVELLED -> {
                return (value / 100) + " blocks";
            }
            default -> {
                return String.valueOf(value);
            }
        }
    }

    public static @NotNull Optional<Statistic> fromString(final @NotNull String string) {
        return Optional.of(valueOf(string.toUpperCase()));
    }
}
