package com.peter.schoolmarket.mvp.sort.trades;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;

import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

public interface ITradeTagDetailListener {
    void onComplete(Result<List<Trade>> result);
    void onSearchReqComplete(Result<List<Trade>> result);//从服务器中得到数据后的操作
}
