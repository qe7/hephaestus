package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.StringTranslate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListSetting<T> extends AbstractSetting<Set<T>> {
    private final Map<String, T> lookup = new LinkedHashMap<>();
    private final Map<T, String> names = new LinkedHashMap<>();

    private ListSetting(String name, Collection<T> choices, Function<T, String> namer, Function<T, Integer> ider, Function<T, String> aliaser, Collection<T> defaults) {
        super(name, Collections.emptySet());

        for (T choice : choices) {
            final String raw = namer.apply(choice);
            final String key = raw == null ? "" : normalize(raw);
            final Integer id = ider.apply(choice);

            if (!key.isEmpty() && !lookup.containsKey(key)) {
                lookup.put(key, choice);
                names.put(choice, raw);
            } else if (id != null) {
                names.put(choice, String.valueOf(id));
            }
        }
        for (T choice : names.keySet()) {
            final Integer id = ider.apply(choice);
            if (id != null) lookup.putIfAbsent(String.valueOf(id), choice);
        }
        for (T choice : names.keySet()) {
            final String alias = aliaser.apply(choice);
            if (alias != null && !normalize(alias).isEmpty()) lookup.putIfAbsent(normalize(alias), choice);
        }

        setValue(new LinkedHashSet<>(defaults));
    }

    public static ListSetting<Block> blocks(String name, Block... defaults) {
        final List<Block> blocks = Arrays.stream(Block.blocksList).filter(block -> block != null).collect(Collectors.toList());
        return new ListSetting<>(name, blocks, block -> stripPrefix(block.getBlockName()), block -> block.blockID,
                block -> StringTranslate.getInstance().translateNamedKey(block.getBlockName()), Arrays.asList(defaults));
    }

    public static ListSetting<Item> items(String name, Item... defaults) {
        final List<Item> items = Arrays.stream(Item.itemsList).filter(item -> item != null).collect(Collectors.toList());
        return new ListSetting<>(name, items, item -> stripPrefix(item.getItemName()), item -> item.shiftedIndex,
                item -> StringTranslate.getInstance().translateNamedKey(item.getItemName()), Arrays.asList(defaults));
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static ListSetting<Class<? extends Entity>> entities(String name, Class<? extends Entity>... defaults) {
        final Map<Class<? extends Entity>, String> mapping = new LinkedHashMap<>();
        mapping.put(EntityPlayer.class, "Player");
        mapping.putAll((Map<Class<? extends Entity>, String>) EntityList.getClassToStringMapping());

        return new ListSetting<>(name, new ArrayList<>(mapping.keySet()), mapping::get, type -> null, type -> null, Arrays.asList(defaults));
    }

    public Collection<T> getChoices() {
        return Collections.unmodifiableSet(names.keySet());
    }

    public boolean contains(T choice) {
        return value.contains(choice);
    }

    public String nameOf(T choice) {
        return names.get(choice);
    }

    @Override
    public void setValue(Set<T> value) {
        for (T choice : value) {
            if (!names.containsKey(choice)) {
                throw new IllegalArgumentException(String.format("%s is not a valid choice for %s.", choice, getName()));
            }
        }
        super.setValue(Collections.unmodifiableSet(new LinkedHashSet<>(value)));
    }

    @Override
    public String format() {
        return value.isEmpty() ? "none" : value.stream().map(names::get).collect(Collectors.joining(","));
    }

    @Override
    protected Set<T> convert(String input) {
        if (input.equalsIgnoreCase("none")) return Collections.emptySet();

        final char first = input.charAt(0);
        final boolean add = first == '+';
        final boolean remove = first == '-';

        final Set<T> result = add || remove ? new LinkedHashSet<>(value) : new LinkedHashSet<>();
        final String list = add || remove ? input.substring(1) : input;

        boolean any = false;
        for (String part : list.split(",")) {
            if (part.trim().isEmpty()) continue;

            final T choice = lookup.get(normalize(part));
            if (choice == null) {
                throw new IllegalArgumentException(String.format("Unknown entry \"%s\" for %s.", part.trim(), getName()));
            }

            if (remove) result.remove(choice);
            else result.add(choice);
            any = true;
        }

        if (!any) throw new IllegalArgumentException(String.format("No entries given for %s.", getName()));
        return result;
    }

    private static String normalize(String name) {
        return name.trim().replace(" ", "").replace("_", "").toLowerCase(Locale.ROOT);
    }

    private static String stripPrefix(String name) {
        if (name == null) return null;
        final int dot = name.indexOf('.');
        return dot >= 0 ? name.substring(dot + 1) : name;
    }
}
