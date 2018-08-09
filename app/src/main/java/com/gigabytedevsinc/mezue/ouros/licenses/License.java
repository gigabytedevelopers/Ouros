package com.gigabytedevsinc.mezue.ouros.licenses;

import com.dm.material.dashboard.candybar.items.InAppBilling;

public class License {

    /*
     * License Checker
     * private static final boolean ENABLE_LICENSE_CHECKER = true; --> enabled
     * Change to private static final boolean ENABLE_LICENSE_CHECKER = false; if you want to disable it
     *
     * NOTE: If you disable license checker you need to remove LICENSE_CHECK permission inside AndroidManifest.xml
     */
    private static final boolean ENABLE_LICENSE_CHECKER = false;

    /*
     * NOTE: If license checker is disabled (above), just ignore this
     *
     * Generate 20 random bytes
     * For easy way, go to https://www.random.org/strings/
     * Set generate 20 random strings
     * Each string should be 2 character long
     * Check numeric digit (0-9)
     * Choose each string should be unique
     * Get string
     */
    private static final byte[] SALT = new byte[]{
            //Put generated random bytes below, separate with comma, ex: 14, 23, 58, 85, ...

    };

    /*
     * Your license key
     * If your app hasn't published at play store, publish it first as beta, get license key
     */
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtgLrKO2BXufWg76sy5gbBvGrZ9bW0z2qnbHBOablTLr0by189wCRnPqAZ+uGk8dua6OvLFXcV/NE6KpdwTTvKo1i80BPYfgVZvOgv8ewICylsDhQVyxxEpbRcJs3n6Jc0+hZAScU4O/SJHzpn+8Wii82kOvfjmFH7Hblp/mG6AlUB5KP+GiSiKx7zMJSjNKgLP6qXzY4QCo6+Lfsdf4w0Fd/xulBwYpFLU92zZtCs0EeBZVuQGbWAFFaK/xj4SjXT9lSRmKQml8xU/e650wmetlDPCDK/d06O7LiEY0olW9Y08I/FpeToo4wgkWj0LV09u5YIUI+hxwaj3CpnJtJawIDAQAB";

    /*
     * NOTE: Make sure your app name in project same as app name at play store listing
     * NOTE: Your InApp Purchase will works only after the apk published
     */

    /*
     * NOTE: If premium request disabled, just ignored this
     *
     * InApp product id for premium request
     * Product name displayed the same as product name displayed at play store
     * So make sure to name it properly, like include number of icons
     * Format: new InAppBilling("premium request product id", number of icons)
     */
    private static final InAppBilling[] PREMIUM_REQUEST_PRODUCTS = new InAppBilling[] {
            new InAppBilling("com.ouros.icons.first", 1),
            new InAppBilling("com.ouros.icons.second", 2),
            new InAppBilling("com.ouros.icons.third", 5),
            new InAppBilling("com.ouros.icons.fourth", 10)
    };

    /*
     * NOTE: If donation disabled, just ignored this
     *
     * InApp product id for donation
     * Product name displayed the same as product name displayed at play store
     * So make sure to name it properly
     * Format: new InAppBilling("donation product id")
     */
    private static final InAppBilling[] DONATION_PRODUCT = new InAppBilling[] {
            new InAppBilling("com.ouros.donation"),
            new InAppBilling("com.ouros.donation.two"),
            new InAppBilling("com.ouros.donation.three"),
            new InAppBilling("com.ouros.donation.four")
    };

    public static boolean isLicenseCheckerEnabled() {
        return ENABLE_LICENSE_CHECKER;
    }

    public static String getLicenseKey() {
        return LICENSE_KEY;
    }

    public static byte[] getRandomString() {
        return SALT;
    }

    public static String[] getPremiumRequestProductsId() {
        String[] productId = new String[PREMIUM_REQUEST_PRODUCTS.length];
        for (int i = 0; i < PREMIUM_REQUEST_PRODUCTS.length; i++) {
            productId[i] = PREMIUM_REQUEST_PRODUCTS[i].getProductId();
        }
        return productId;
    }

    public static int[] getPremiumRequestProductsCount() {
        int[] productCount = new int[PREMIUM_REQUEST_PRODUCTS.length];
        for (int i = 0; i < PREMIUM_REQUEST_PRODUCTS.length; i++) {
            productCount[i] = PREMIUM_REQUEST_PRODUCTS[i].getProductCount();
        }
        return productCount;
    }

    public static String[] getDonationProductsId() {
        String[] productId = new String[DONATION_PRODUCT.length];
        for (int i = 0 ; i < DONATION_PRODUCT.length; i++) {
            productId[i] = DONATION_PRODUCT[i].getProductId();
        }
        return productId;
    }

}
