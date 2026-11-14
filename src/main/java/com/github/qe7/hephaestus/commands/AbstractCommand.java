package com.github.qe7.hephaestus.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractCommand {
    protected final String name;
    protected final String usage;

    protected String description;

    public abstract void execute(String[] args);
}
