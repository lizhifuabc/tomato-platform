package com.tomato.order;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ShardCfg
 *
 * @author lizhifu
 * @since 2023/1/2
 */
@Data
@AllArgsConstructor
public class ShardCfg {
    private int dbIdx;
    private int tblIdx;
}
