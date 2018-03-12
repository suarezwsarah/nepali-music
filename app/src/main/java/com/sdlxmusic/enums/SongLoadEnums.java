package com.sdlxmusic.enums;

public enum  SongLoadEnums {

    SUCCESS("1"), FAIL("0");

    private final String itemCd;

    SongLoadEnums(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getItemCd() {
        return itemCd;
    }
}
