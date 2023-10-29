package com.Estructura.API.service;
import com.Estructura.API.requests.cart.PaymentDetailsRequest;
import com.Estructura.API.responses.cart.PaymentResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
public class PaymentServiceImpl implements PaymentService{

    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PaymentResponse getPaymentDetails(
        PaymentDetailsRequest paymentDetailsRequest) {
        String merchantId = "1223006";
        String currency = "LKR";
        String orderID        = "12345";
        DecimalFormat df       = new DecimalFormat("0.00");
        String amountFormatted = df.format(paymentDetailsRequest.getAmount());
        String merchantSecret = "MzM4MDA5MjU0NTE4OTQyMTY4MzY4NTM4MDA4NTIzNjAyNDAwODE1";

        // Calculate the hash
        String hash    = getMd5(merchantId+ orderID + amountFormatted + currency + getMd5(merchantSecret));

        // Create a PaymentResponse object for the API response
        PaymentResponse response = new PaymentResponse();
        response.setUser_id("your_user_id");
        response.setFirst_name("your_user_name");
        response.setLast_name("Kumara");
        response.setEmail("your_user_email");
        response.setPhone("0777123456");
        response.setAddress("No.1, Galle Road");
        response.setCity("Colombo");
        response.setUserid("your_user_id");
        response.setAmount(paymentDetailsRequest.getAmount());
        response.setMerchant_id(merchantId);
        response.setOrder_id(orderID);
        response.setCurrency(currency);
        response.setHash(hash);

        return response;
    }
}
