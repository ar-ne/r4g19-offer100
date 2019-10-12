package r4g19.offer100.properties.cym.mapping;

public enum SubmissionStatus {
    NONE, APPROVE, REJECT;

    public static class Converter implements org.jooq.Converter<Integer, SubmissionStatus> {
        @Override
        public SubmissionStatus from(Integer databaseObject) {
            switch (databaseObject) {
                case 1:
                    return APPROVE;
                case 2:
                    return REJECT;
                default:
                    return NONE;
            }
        }

        @Override
        public Integer to(SubmissionStatus userObject) {
            switch (userObject) {
                case NONE:
                    return 0;
                case APPROVE:
                    return 1;
                case REJECT:
                    return 2;
            }
            throw new RuntimeException("UO is not a valid SubmissionStatus:" + userObject);
        }

        @Override
        public Class<Integer> fromType() {
            return Integer.class;
        }

        @Override
        public Class<SubmissionStatus> toType() {
            return SubmissionStatus.class;
        }
    }
}
