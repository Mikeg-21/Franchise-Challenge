package com.franchise.challenge.infraestructure.entrypoint.util;

public final class ApiConstants {
    private ApiConstants(){}

    public static final String API_V1                = "/api/v1";
    public static final String FRANCHISES            = API_V1 + "/franchises";

    public static final String PATH_VAR_ID           = "franchiseId";
    public static final String PATH_VAR_BRANCH       = "branchName";
    public static final String PATH_VAR_PRODUCT      = "productName";

    public static final String FRANCHISES_CREATE     = FRANCHISES;
    public static final String FRANCHISE_RENAME      = FRANCHISES + "/{" + PATH_VAR_ID + "}/rename";

    public static final String BRANCH_CREATE         = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches";
    public static final String BRANCH_RENAME         = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches/{" + PATH_VAR_BRANCH + "}/rename";

    public static final String PRODUCT_CREATE        = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches/{" + PATH_VAR_BRANCH + "}/products";
    public static final String PRODUCT_RENAME        = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches/{" + PATH_VAR_BRANCH + "}/products/{" + PATH_VAR_PRODUCT + "}/rename";
    public static final String PRODUCT_DELETE        = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches/{" + PATH_VAR_BRANCH + "}/products/{" + PATH_VAR_PRODUCT + "}";
    public static final String PRODUCT_STOCK_UPDATE  = FRANCHISES + "/{" + PATH_VAR_ID + "}/branches/{" + PATH_VAR_BRANCH + "}/products/{" + PATH_VAR_PRODUCT + "}/stock";

    public static final String MAX_STOCK_PER_BRANCH  = FRANCHISES + "/{" + PATH_VAR_ID + "}/max-stock";
}