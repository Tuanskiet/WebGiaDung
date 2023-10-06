package com.poly.WebGiaDung.utils;

public class MessageUtils {
    public enum Product{
        ADD_SUCCESS("Thêm thành công!"),
        ADD_FAILED("Thêm thất bại!"),
        ERROR_FOREIGN_KEY("Đối tượng chứa dữ liệu, không thể xóa!"),
        PROD_ALREADY_EXIST("Tên sản phẩm đã tồn tại!");
        private String value;
        Product(String val){
            this.value = val;
        }
        public String getVal(){
            return this.value;
        }
    }

    public enum Account{
        SUBJECT_MAIL_FORGOT_PASSWORD("Xác nhận đổi mật khẩu!");
        private String value;
        Account(String val){
            this.value = val;
        }
        public String getVal(){
            return this.value;
        }
    }

}