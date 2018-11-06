package com.fjnu.trade.model;

/**
 * Created by LCC on 2018/5/10.
 */
public enum Country {

    /** 菲律宾 */
    PHILIPPINES("菲律宾", "ph"),
    /** 泰国 */
    THAILAND("泰国", "th"),
    /** 印尼 */
    INDONESIA("印尼", "id"),
    /** 马来西亚 */
    MALAYSIA("马来西亚", "my"),
    /** 新加坡 */
    SINGAPORE("新加坡", "sg"),
    /** 越南 */
    VIETNAM("越南", "vn");

    private String name;
    private String code;

    Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
