package r4g19.offer100.model.cym;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列属性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootStrapTableColumn {
    String field;
    String title;
    boolean sortable;
    boolean visible;
    boolean checkbox;
}