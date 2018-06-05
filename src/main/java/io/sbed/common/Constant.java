package io.sbed.common;

/**
 * @author heguoliang
 * @Description: TODO(常量)
 * @date 2017-6-23 15:07
 */
public class Constant {

    /**
     * 登录错误次数
     */
    public  static final String LOGIN_ERROR_TIMES= "login_error_times";

    /**
     * 超级管理员ID
     */
	public static final int SUPER_ADMIN = 1;

    /**
     * utf-8编码
     */
	public static final String ENCODING_UTF_8="UTF-8";

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 用户 状态
     */
    public enum UserStatus {
        /**
         * 禁用
         */
        DISABLE(0),
        /**
         * 正常
         */
        NORMAL(1);

        private int value;

        UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Taxonomy类型
     */
    public enum TaxonomyType {
        /**
         * 分类
         */
        CATEGORY(0),
        /**
         * 专题
         */
        FEATURE(1),
        /**
         * 标签
         */
        TAG(2);

        private int value;

        TaxonomyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Content状态
     */
    public enum ContentStatus {
        /**
         * 草稿
         */
        DRAFT(0),
        /**
         * 正常
         */
        NORMAL(1),
        /**
         * 删除
         */
        DELETE(2);

        private int value;

        ContentStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
