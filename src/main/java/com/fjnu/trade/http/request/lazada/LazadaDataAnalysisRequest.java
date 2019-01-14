package com.fjnu.trade.http.request.lazada;

import com.fjnu.trade.view.modules.summary.LazadaShopMonthlySummaryJPanel;
import javafx.scene.control.Cell;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LCC on 2018/8/17.
 */
public interface LazadaDataAnalysisRequest {

    /**
     * {@link LazadaShopMonthlySummaryJPanel#openGenerateExcelUrl()}中的导出按钮根据该函数注解获取url
     *
     * @param shopId
     * @param beginTime
     * @param endTime
     * @return
     */
    @GET("lazada/data-analysis/generate-excel")
    Cell<String> generateExcel(@Query("shopId") String shopId,
                               @Query("beginTime") String beginTime,
                               @Query("endTime") String endTime);
}
