package r4g19.offer100.properties.cym;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.properties.cym.mapping.UserType;

@Configuration
@PropertySource("classpath:properties/visibility.properties")
public class Flags extends ComponentBase {

    public static final char TRUE = 'T';
    public static final char FALSE = 'F';
    @Autowired
    private Environment env; //用来获取properties里的内容

    /**
     * 将T/F转为bool
     *
     * @param c char字符
     * @return 对应的boolean值
     */
    public static boolean c2b(char c) {
        return c == TRUE;
    }

    /**
     * 获取表中字段对应的FieldFlags
     *
     * @param jTable    表
     * @param fieldName 字段
     * @return Field Flags
     */
    public Visibility getTableField(Table jTable, String fieldName) {
        String flagStr = env.getProperty(String.format("%s.%s", jTable.getName(), fieldName));
        if (flagStr == null) {
            logger.error(String.format("field not exist: field.%s.%s", jTable, fieldName));
            return new Visibility(true, false, false, false);
        }
        logger.trace(String.format("field.%s.%s", jTable, fieldName));
        Visibility visibility = new Visibility();
        visibility.setLocalhost(c2b(flagStr.charAt(0)));
        visibility.setPersonal(c2b(flagStr.charAt(1)));
        visibility.setEntrepreneurial(c2b(flagStr.charAt(2)));
        visibility.setAnonymous(c2b(flagStr.charAt(3)));
        logger.trace(visibility.toString());
        return visibility;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Visibility {
        public boolean localhost;
        public boolean personal;
        public boolean entrepreneurial;
        public boolean anonymous;

        public boolean getByUserType(UserType userType) {
            switch (userType) {
                case Anonymous:
                    return anonymous;
                case Localhost:
                    return localhost;
                case Entrepreneurial:
                    return entrepreneurial;
                case Personal:
                    return personal;
            }
            return false;
        }
    }

}
