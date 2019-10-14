package r4g19.offer100.properties.cym.mapping;

public enum VerifyType {
    NONE, PHONE, EMAIL, ALL;


    public static class Converter implements org.jooq.Converter<Integer, VerifyType> {

        @Override
        public VerifyType from(Integer databaseObject) {
            switch (databaseObject) {
                case 1:
                    return PHONE;
                case 2:
                    return EMAIL;
                case 3:
                    return ALL;
                default:
                    return NONE;
            }
        }

        @Override
        public Integer to(VerifyType userObject) {
            switch (userObject) {
                case ALL:
                    return 3;
                case EMAIL:
                    return 2;
                case PHONE:
                    return 1;
                case NONE:
                    return 0;
            }
            throw new RuntimeException("UO not a valid 'VerifyType'");
        }

        @Override
        public Class<Integer> fromType() {
            return Integer.class;
        }

        @Override
        public Class<VerifyType> toType() {
            return VerifyType.class;
        }
    }
}
