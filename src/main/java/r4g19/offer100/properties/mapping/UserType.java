package r4g19.offer100.properties.mapping;

import org.springframework.security.core.GrantedAuthority;

/**
 * 用户类型的枚举类型和转换器
 */
public enum UserType {
    PERSONAL,ENTREPRENEURIAL;

    public static final String prefix = "ROLE_";

    /**
     * 获取用户的权限信息
     *
     * @return 权限信息
     */
    public Authority getAuthority() {
        return new Authority(this);
    }

    /**
     * 用户类型转换器
     */
    public static class Converter implements org.jooq.Converter<Integer, UserType> {

        @Override
        public UserType from(Integer databaseObject) {
            switch (databaseObject) {
                case 0:
                    return PERSONAL;
                case 1:
                    return ENTREPRENEURIAL;
            }
            throw new RuntimeException("DBO not a valid 'UserType'");
        }

        @Override
        public Integer to(UserType userObject) {
            switch (userObject) {
                case PERSONAL:
                    return 0;
                case ENTREPRENEURIAL:
                    return 1;
            }
            throw new RuntimeException("UO not a valid 'UserType'");
        }

        @Override
        public Class<Integer> fromType() {
            return Integer.class;
        }

        @Override
        public Class<UserType> toType() {
            return UserType.class;
        }
    }

    /**
     * 用户的权限信息
     */
    public static class Authority implements GrantedAuthority {
        private UserType userType;

        Authority(UserType userType) {
            this.userType = userType;
        }

        @Override
        public String getAuthority() {
            return prefix + userType.name();
        }

        public UserType getUserType() {
            return userType;
        }
    }
}