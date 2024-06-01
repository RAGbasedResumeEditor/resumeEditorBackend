package com.team2.resumeeditorproject.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Login {

    DEFAULT("DEFAULT"), // LOCAL
    SOCIAL("SOCIAL"); //OAuth

    private final String value;
}
