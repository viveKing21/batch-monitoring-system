package com.watchman.utils;

import java.util.UUID;

public class UidGen {
    static public String generate(){
        return UUID.randomUUID().toString();
    }
}
