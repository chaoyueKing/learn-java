package org.chaoyue.test.export.excl;

import java.util.LinkedHashMap;
import java.util.Map;

public class BudgetEnums {
    public enum BudgetExcelEnums {


        PRODUCT_CODE("产品编号", "productCode"),
        PRODUCT_NAME("产品名称", "productName"),
        PRODUCT_BRAND("品牌", "brand"),
        PRODUCT_PARAMETER("规格", "productParameter"),
        PRODUCT_MEMO("产品备注", "productMemo"),
        PRODUCT_UNITS("单位", "units"),
        PRODUCT_NUM("数量", "num"),
        COST_PRICE("成本价（元）", "costPrice"),
        TOTAL_PRICE("成本总价（元）", "totalCostPrice"),
        PRICE("销售价（元）", "salePrice"),
        SALES_PRICE("销售总价（元）", "totalSalePrice"),
        GROSS_RATE("毛利率（%）", "grossRate"),
        LOSS_RATE("损耗率（%）", "lossRate"),
        MEMO("备注", "memo"),;

        private static final Map<String, String> MAP;

        static {
            MAP = new LinkedHashMap<>();
            for (BudgetExcelEnums e : BudgetExcelEnums.values()) {
                MAP.put(e.getValue(), e.getText());
            }
        }

        BudgetExcelEnums(String value, String text) {
            this.value = value;
            this.text = text;
        }

        private String value;
        private String text;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static String getDesc(String code) {
            for (BudgetExcelEnums item : values()) {
                if (item.getValue().equals(code)) {
                    return item.getText();
                }
            }
            return null;
        }

        public static Map<String, String> getMap() {
            return MAP;
        }


    }
}
