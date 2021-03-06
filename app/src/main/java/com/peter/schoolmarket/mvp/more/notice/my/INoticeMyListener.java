package com.peter.schoolmarket.mvp.more.notice.my;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/22.
 */

public interface INoticeMyListener {
    void onNoticeReqComplete(Result<List<Notice>> result);
    void onDeleteReqComplete(Result<String> result);
}
