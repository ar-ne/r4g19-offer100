package r4g19.offer100.model.cym;

import lombok.Data;

/**
 * 列属性
 */
@Data
public class BootstrapTableColumn {
    String field;
    String title;
    boolean sortable;
    boolean visible;
    boolean checkbox;
}