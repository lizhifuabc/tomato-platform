//package com.tomato.merchant.util;
//
///**
// * 检索串提取
// *
// * @author lizhifu
// * @date 2022/12/12
// */
//public class ExtractIndexUtil {
//    public static String extractIndex(String encryptedData) {
//        if (encryptedData == null || encryptedData.length() < 4) {
//            return null;
//        }
//        char sepInData = encryptedData.charAt(0);
//        if (encryptedData.charAt(encryptedData.length() - 2) != sepInData) {
//            return null;
//        }
//        String[] parts = String.split(encryptedData, sepInData);
//        if (sepInData == '$' || sepInData == '#') {
//            return parts[0];
//        } else {
//            return parts[1];
//        }
//    }
//}
