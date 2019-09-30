package r4g19.offer100.api.cym;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.properties.cym.Flags;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/public/i18n")
@PropertySource("classpath:flags.properties")
public class I18n extends APIBase {

    private final MessageSource messageSource; //在代码里获取Messages
    private final Flags flags;

    @Autowired
    public I18n(MessageSource messageSource, Flags flags) {
        this.messageSource = messageSource;
        this.flags = flags;
    }

    @PostMapping("/table/bs-table/{tableName}")
    public HttpEntity<List<BootStrapTableColumn>> bs_table(BootStrapTableColumn[] cols, @PathVariable String tableName) {
        List<BootStrapTableColumn> list = new LinkedList<>();
        for (BootStrapTableColumn col : cols) {
            try {
                col.setTitle(messageSource.getMessage(tableName + "." + col.field, null, Locale.getDefault()));
                Flags.Field colFlag = flags.getFieldFlag(tableName, col.field);
                col.setVisible(colFlag.visibility);
                list.add(col);
            } catch (NoSuchMessageException ignored) {
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 列属性
     */
    @Data
    static class BootStrapTableColumn {
        String field;
        String title;
        boolean sortable;
        boolean visible;
        boolean checkbox;
    }
}
