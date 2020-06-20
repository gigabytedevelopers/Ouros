package candybar.lib.items;

/*
 * CandyBar - Material Dashboard
 *
 * Copyright (c) 2014-2016 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class InAppBilling {

    private final String mProductId;
    private String mProductName;
    private String mPrice;
    private int mProductCount;

    public static final int DONATE = 0;
    public static final int PREMIUM_REQUEST = 1;

    public InAppBilling(String productId) {
        mProductId = productId;
    }

    public InAppBilling(String price, String productId, String productName) {
        mPrice = price;
        mProductId = productId;
        mProductName = productName;
    }

    public InAppBilling(String price, String productId, String productName, int productCount) {
        mPrice = price;
        mProductId = productId;
        mProductName = productName;
        mProductCount = productCount;
    }

    public InAppBilling(String productId, int productCount) {
        mProductId = productId;
        mProductCount = productCount;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getProductId() {
        return mProductId;
    }

    public String getProductName() {
        return mProductName;
    }

    public int getProductCount() {
        return mProductCount;
    }

}
