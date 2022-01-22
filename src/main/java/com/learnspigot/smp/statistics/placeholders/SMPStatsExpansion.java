package com.learnspigot.smp.statistics.placeholders;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class SMPStatsExpansion extends PlaceholderExpansion {

	ListMultimap<Statistic, Material> ignoredMaterials = ArrayListMultimap.create();
	ListMultimap<Statistic, EntityType> ignoredEntities = ArrayListMultimap.create();

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

		String formattedPlaceholder;
		Statistic convertedStat;

		if (placeholder.contains("_player")) {
			formattedPlaceholder = StringUtils.substringBefore(placeholder, "_player");

			switch (formattedPlaceholder.toUpperCase()) {
				case "mine_block": {
					return calculateTop(Statistic.MINE_BLOCK).getKey().getName();
				}
				case "use_item": {
					return calculateTop(Statistic.USE_ITEM).getKey().getName();
				}
				case "break_item": {
					return calculateTop(Statistic.BREAK_ITEM).getKey().getName();
				}
				case "craft_item": {
					return calculateTop(Statistic.CRAFT_ITEM).getKey().getName();
				}
			}

			convertedStat = convertToStatistic(formattedPlaceholder);

			if (convertedStat == null) return "NULL";

			return calculateTop(convertedStat).getKey().getName();
		}

		if (placeholder.contains("_amt")) {
			formattedPlaceholder = StringUtils.substringBefore(placeholder, "_amt");

			switch (formattedPlaceholder.toUpperCase()) {
				case "mine_block": {
					return calculateTop(Statistic.MINE_BLOCK).getValue().toString();
				}
				case "use_item": {
					return calculateTop(Statistic.USE_ITEM).getValue().toString();
				}
				case "break_item": {
					return calculateTop(Statistic.BREAK_ITEM).getValue().toString();
				}
				case "craft_item": {
					return calculateTop(Statistic.CRAFT_ITEM).getValue().toString();
				}
			}

			convertedStat = convertToStatistic(formattedPlaceholder);

			if (convertedStat == null) return "NULL";

			return calculateTop(convertedStat).getValue().toString();
		}

		return "NULL PLACEHOLDER";

	}

	private Map.Entry<OfflinePlayer, Long> calculateTop(Statistic statistic) {

		Map<OfflinePlayer, Long> map = new HashMap<>();
		final AtomicLong totalAmt = new AtomicLong();

		switch (statistic.getType()) {
			case BLOCK:
			case ITEM: {
				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					for (Material mat : Material.values()) {

						if (ignoredMaterials.get(statistic).contains(mat)) continue;

						try {
							totalAmt.addAndGet(p.getStatistic(statistic, mat));
						} catch (IllegalArgumentException e) {
							ignoredMaterials.put(statistic, mat);
						}
					}

					map.put(p, totalAmt.get());
				}

				Map.Entry<OfflinePlayer, Long> maxEntry = null;

				for (Map.Entry<OfflinePlayer, Long> entry : map.entrySet()) {
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
						maxEntry = entry;
					}
				}
				return maxEntry;
			}
			case ENTITY: {
				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					for (EntityType entity : EntityType.values()) {

						if (ignoredEntities.get(statistic).contains(entity)) continue;

						try {
							totalAmt.addAndGet(p.getStatistic(statistic, entity));
						} catch (IllegalArgumentException e) {
							ignoredEntities.put(statistic, entity);
						}
					}

					map.put(p, totalAmt.get());
				}

				Map.Entry<OfflinePlayer, Long> maxEntry = null;

				for (Map.Entry<OfflinePlayer, Long> entry : map.entrySet()) {
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
						maxEntry = entry;
					}
				}
			}

			case UNTYPED: {
				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					map.put(p, (long) p.getStatistic(statistic));
				}

				Map.Entry<OfflinePlayer, Long> maxEntry = null;
				for (Map.Entry<OfflinePlayer, Long> entry : map.entrySet()) {
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
						maxEntry = entry;
					}
				}

				return maxEntry;
			}
		}

		return null;
	}

	private Statistic convertToStatistic(String statString) {
		final Optional<Statistic> convertedStat = Enums.getIfPresent(Statistic.class, statString.toUpperCase());

		if (!convertedStat.isPresent()) return null;

		return convertedStat.get();
	}

	public enum StatsType {
		NORMAL,
		MATERIAL,
		ENTITY;
	}

}
