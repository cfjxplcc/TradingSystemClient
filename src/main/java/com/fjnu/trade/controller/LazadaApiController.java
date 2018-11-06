package com.fjnu.trade.controller;

/**
 * Created by LCC on 2017/11/2.
 */
@Deprecated
public class LazadaApiController {

    private static final boolean DEBUG = true;

 /*   private GenerateExcelCallback callback;

    private LazadaApiController() {
    }

    public static final LazadaApiController getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final LazadaApiController instance = new LazadaApiController();
    }

    *//**
     * 获取ApiKeyMaps的keySet
     *
     * @return
     *//*
    public Set<String> getApiKeyMapsNames() {
        return new TreeSet<String>(api_key_maps.keySet());
    }

    public void generateExcel(String apiKeyMapsName, String orderStatus, String beginTime, String endTime, String excelFilePath, GenerateExcelCallback callback) {
        if (callback != null) {
            this.callback = callback;
            callback.onStart();
        }
        LazadaApiManager apiManager = LazadaApiManager.getInstance();

        apiManager.initClient(api_key_maps.get(apiKeyMapsName));

        OutputStream outputStream = null;
        WritableWorkbook writableWorkbook = null;

        if (callback != null) {
            callback.onProcessing(0, "开始获取订单数据");
        }
        try {
            String resultStr = "";
            OrdersFilter.Status status = null;
            try {
                status = OrdersFilter.Status.valueOf(orderStatus);
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
            List<Order> orders = apiManager.getOrders(status, beginTime, endTime);
            if (orders.size() > 0) {
                if (callback != null) {
                    callback.onProcessing(0, "获取到" + orders.size() + "订单数据");
                }
                //如果有数据先生成excel表格
                outputStream = new FileOutputStream(excelFilePath);
                writableWorkbook = Workbook.createWorkbook(outputStream);
                WritableSheet sheet = writableWorkbook.createSheet("订单信息", 0);

                //生成title信息
                Label label;
                int excelLabelTitlesLength = ExcelDataBean.EXCEL_LABEL_TITLES.length;

                CellView cv = new CellView(); //定义一个列显示样式
                WritableCellFormat writableCellFormat = new WritableCellFormat();
//                WritableCellFormat writableCellFormat = new WritableCellFormat(NumberFormats.TEXT);
                writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
                writableCellFormat.setAlignment(Alignment.LEFT);
                cv.setFormat(writableCellFormat);
                cv.setSize(10 * 600);

                for (int i = 0; i < excelLabelTitlesLength; i++) {
                    label = new Label(i, 0, ExcelDataBean.EXCEL_LABEL_TITLES[i]);
                    sheet.addCell(label);
                    sheet.setColumnView(i, cv);
                }

                cv.setAutosize(true);
                sheet.setColumnView(8, cv);

                if (callback != null) {
                    callback.onProcessing(0, "正在获取订单详情...");
                }
                for (Order order : orders) {
                    List<OrderItem> orderItems = apiManager.getOrderItems(order.getOrderId());
                    ExcelDataBean excelDataBean = new ExcelDataBean(order, orderItems);
                    //填充数据
                    fillExcelData(writableWorkbook, sheet, excelDataBean);
                }

                writableWorkbook.write();
                resultStr = "数据生成成功";
            } else {
                resultStr = "无订单数据";
            }

            if (callback != null) {
                callback.onFinish(resultStr);
            }
        } catch (LazadaException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e);
            }
        } catch (WriteException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e);
            }
        } finally {
            if (writableWorkbook != null) {
                try {
                    System.out.println("writableWorkbook.close()");
                    writableWorkbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fillExcelData(WritableWorkbook writableWorkbook, WritableSheet sheet, ExcelDataBean dataBean) throws WriteException, IOException {
        if (writableWorkbook == null || sheet == null || dataBean == null) {
            System.out.println("error:fillExcelData() writableWorkbook == null || sheet == null || dataBean == null");
            return;
        }

        int startRowIndex = sheet.getRows();
        int rowIndex = startRowIndex;
        if (DEBUG) {
            System.out.println("startRowIndex:" + startRowIndex);
        }
        WritableCell label;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDateStr = simpleDateFormat.format(new Date()).substring(0, 9);

        NumberFormat nf = new NumberFormat("#.00");
        WritableCellFormat wcfN = new WritableCellFormat(nf);
        wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);
        wcfN.setAlignment(Alignment.LEFT);

        for (OrderItem orderItem : dataBean.getOrderItems()) {
            //汇率
            float rate = -1;
            try {
                if (callback != null) {
                    callback.onProcessing(0, "开始获取汇率");
                }
                rate = ExchangeRateManager.getInstance().getExchangeRate(orderItem.getCurrency());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (callback != null) {
                callback.onProcessing(0, "开始填充excel表格(row:" + rowIndex + ")");
            }
            if (rowIndex == startRowIndex) {
                label = new Label(0, rowIndex, simpleDateFormat.format(dataBean.getCreateAt()));
                sheet.addCell(label);
                label = new Label(1, rowIndex, String.valueOf(dataBean.getOrderNumber()));
                sheet.addCell(label);
                label = new Label(2, rowIndex, Arrays.toString(dataBean.getStatuses()));
                sheet.addCell(label);
                label = new Number(3, rowIndex, dataBean.getPrice(), wcfN);
                sheet.addCell(label);
                label = new Number(4, rowIndex, rate);//汇率
                sheet.addCell(label);
                label = new Label(5, rowIndex, nowDateStr);//汇率日期
                sheet.addCell(label);
                if (rate == -1) {//汇率
                    label = new Number(6, rowIndex, 0, wcfN);
                } else {
                    BigDecimal b1 = new BigDecimal(dataBean.getPrice());
                    BigDecimal b2 = new BigDecimal(rate);
                    label = new Number(6, rowIndex, b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_DOWN).floatValue(), wcfN);
                }
                sheet.addCell(label);
            }

           *//* label = new Label(4, startRowIndex, dataBean.getCustomerFirstName());
            sheet.addCell(label);
            label = new Label(5, startRowIndex, dataBean.getCustomerLastName());
            sheet.addCell(label);
            label = new Label(6, startRowIndex, dataBean.getRemarks());
            sheet.addCell(label);
            label = new Label(7, startRowIndex, "收货地址信息：");
            sheet.addCell(label);
            Address address = dataBean.getAddressBilling();
            label = new Label(8, startRowIndex, address.getFirstName());
            sheet.addCell(label);
            label = new Label(9, startRowIndex, address.getLastName());
            sheet.addCell(label);
            label = new Label(10, startRowIndex, address.getPhone());
            sheet.addCell(label);
            label = new Label(11, startRowIndex, address.getPhone2());
            sheet.addCell(label);
            label = new Label(12, startRowIndex, address.getAddress1());
            sheet.addCell(label);
            label = new Label(13, startRowIndex, address.getAddress2());
            sheet.addCell(label);
            label = new Label(14, startRowIndex, address.getAddress3());
            sheet.addCell(label);
            label = new Label(15, startRowIndex, address.getAddress4());
            sheet.addCell(label);
            label = new Label(16, startRowIndex, address.getAddress5());
            sheet.addCell(label);
            label = new Label(17, startRowIndex, address.getCity());
            sheet.addCell(label);
            label = new Label(18, startRowIndex, address.getWard());
            sheet.addCell(label);
            label = new Label(19, startRowIndex, address.getRegion());
            sheet.addCell(label);
            label = new Label(20, startRowIndex, address.getPostCode());
            sheet.addCell(label);
            label = new Label(21, startRowIndex, address.getCountry());
            sheet.addCell(label);
            label = new Label(22, startRowIndex, "订单产品信息:");
            sheet.addCell(label);
            label = new Label(23, startRowIndex, String.valueOf(orderItem.getOrderItemId()));
            sheet.addCell(label);*//*
            label = new Label(7, rowIndex, orderItem.getCurrency());//Currency
            sheet.addCell(label);
            label = new Label(8, rowIndex, orderItem.getName());
            sheet.addCell(label);
            label = new Label(9, rowIndex, orderItem.getSku());
            sheet.addCell(label);
           *//* label = new Label(6, startRowIndex, String.valueOf(orderItem.getItemPrice()));
            sheet.addCell(label);
            label = new Label(7, startRowIndex, String.valueOf(orderItem.getPaidPrice()));
            sheet.addCell(label);*//*

            *//*label = new Label(29, startRowIndex, String.valueOf(orderItem.getWalletCredits()));
            sheet.addCell(label);
            label = new Label(30, startRowIndex, String.valueOf(orderItem.getTaxAmount()));
            sheet.addCell(label);
            label = new Label(31, startRowIndex, String.valueOf(orderItem.getShippingAmount()));
            sheet.addCell(label);
            label = new Label(32, startRowIndex, String.valueOf(orderItem.getShippingServiceCost()));
            sheet.addCell(label);*//*

            rowIndex++;
        }
        rowIndex--;
        if (rowIndex != startRowIndex) {
            sheet.mergeCells(0, startRowIndex, 0, rowIndex);
            sheet.mergeCells(1, startRowIndex, 1, rowIndex);
            sheet.mergeCells(2, startRowIndex, 2, rowIndex);
            sheet.mergeCells(3, startRowIndex, 3, rowIndex);
            sheet.mergeCells(4, startRowIndex, 4, rowIndex);
            sheet.mergeCells(5, startRowIndex, 5, rowIndex);
            sheet.mergeCells(6, startRowIndex, 6, rowIndex);
        }
    }
*/
}
