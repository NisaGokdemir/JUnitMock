package com.gokdemir.unitmockdemo.config;

public class RestApis {
    public static final String DEVELOPER ="/dev";
    public static final String TEST = "/test";
    public static final String RELEASE = "/prod";
    public static final String VERSIONS = "/v1";

    public static final String PAYMENT = DEVELOPER+VERSIONS+"/payment";
    public static final String CREATE_PAYMENT = "/create-payment";
    public static final String UPDATE_PAYMENT = "/update-payment/{id}";
    public static final String DELETE_PAYMENT = "/delete-payment/{id}";
    public static final String LIST_PAYMENT = "/list-payment/{id}";
    public static final String LIST_PAYMENT_ALL = "/list-payment";
    public static final String LIST_PAYMENT_PAGEABLE = "/list-payment-pageable";

    public static final String PAYMENT_METHOD = DEVELOPER+VERSIONS+"/paymentMethod";
    public static final String CREATE_PAYMENT_METHOD = "/create-payment-method";
    public static final String UPDATE_PAYMENT_METHOD = "/update-payment-method/{id}";
    public static final String DELETE_PAYMENT_METHOD = "/delete-payment-method/{id}";
    public static final String LIST_PAYMENT_METHOD = "/list-payment-method/{id}";
    public static final String LIST_PAYMENT_METHOD_ALL = "/list-payment-method";
    public static final String LIST_PAYMENT_METHOD_PAGEABLE = "/list-payment-method-pageable";


}
