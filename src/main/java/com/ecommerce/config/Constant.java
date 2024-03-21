package com.ecommerce.config;

import lombok.Getter;

public class Constant {
    @Getter
    public enum UserRole {
        ADMIN((byte) 1),
        CUSTOMER((byte) 2);

        private final byte role;

        UserRole(byte role) {
            this.role = role;
        }

    }

    @Getter
    public enum RegexPattern {
        EMAIL("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"),
        PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

        private final String pattern;

        RegexPattern(String pattern) {
            this.pattern = pattern;
        }

    }

    @Getter
    public enum UserQuery {
        FIND_ALL("{CALL proc_find_all_user()}"),
        FIND_ONE(""),
        REGISTER("{CALL proc_register(?,?,?,?)}"),
        LOGIN(""),
        GET_EMAIL("{CALL proc_get_email(?)}"),
        GET_BY_EMAIL("{CALL proc_get_by_email(?)}"),
        UPDATE(""),
        BLOCK(""),
        ;

        private final String query;

        UserQuery(String query) {
            this.query = query;
        }

    }

    @Getter
    public enum ProductQuery {
        FIND_ALL("{CALL proc_get_all_product()}"),
        FIND_ONE("{CALL proc_get_by_product_id(?)}"),
        FIND_BY_CATEGORY_ID("{CALL proc_get_product_by_category(?)}"),
        GET_IMAGE_BY_PRODUCT_ID("{CALL proc_get_image_by_product_id(?)}"),
        CREATE("{CALL proc_create_product(?,?,?,?,?,?)}"),
        CREATE_IMG("{CALL proc_creaete_img(?,?)}"),
        UPDATE("{CALL proc_update_product(?,?,?,?,?,?)}"),
        DELETE("{CALL proc_change_is_delete(?)}"),
        DELETE_IMG("{CALL proc_delete_product_img(?,?)}"),
        DELETE_ALL_IMG_BY_PRODUCT_ID("{CALL delete_img_by_product_id(?)}")
        ;

        private final String query;

        ProductQuery(String query) {
            this.query = query;
        }

    }

    @Getter
    public enum CategoryQuery {
        FIND_ALL("{CALL proc_get_all_category()}"),
        FIND_ONE("{CALL proc_get_by_category_id(?)}"),
        FIND_BY_PRODUCT_ID(""),
        GET_CATEGORY_HIERARCHY("{CALL proc_get_category_hierarchy()}"),
        CREATE("{CALL proc_create_category(?,?,?,?)}"),
        CREATE_NO_PARENT("{CALL proc_create_category_no_parent_id(?,?,?)}"),
        UPDATE("{CALL proc_update_category(?,?,?,?,?)}"),
        UPDATE_NO_PARENT("{CALL proc_update_category_no_parent(?,?,?,?)}"),
        DELETE("{CALL proc_change_is_delete_category(?)}")
        ;

        private final String query;

        CategoryQuery(String query) {
            this.query = query;
        }

    }

    @Getter
    public enum CartQuery {
        GET_CART_BY_USER_ID("{CALL proc_get_cart_by_uid(?)}"),
        GET_LIST_CART_ITEM("{CALL proc_get_list_cart_item(?)}"),
        CREATE("{CALL proc_create_cart(?)}"),
        ADD_TO_CART("{CALL proc_add_cart_item(?,?,?,?)}"),
        UPDATE_CART_ITEM("{CALL proc_update_cart_item(?,?,?,?)}"),
        DELETE_CART_ITEM("{CALL delete_cart_item(?)}"),
        DELETE_ALL_CART_ITEM("{CALL delete_all_cart_item(?)}"),
        CHECK_PRODUCT_EXISTS("{CALL proc_check_product_exists(?,?,?)}"),
        CHECK_OUT("{CALL proc_check_out(?)}");

        private final String query;

        CartQuery(String query) {
            this.query = query;
        }
    }
}
