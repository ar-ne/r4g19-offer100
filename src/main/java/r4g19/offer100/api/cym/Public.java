package r4g19.offer100.api.cym;

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
import r4g19.offer100.model.cym.BootStrapTableColumn;
import r4g19.offer100.properties.cym.Flags;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/public")
public class Public extends APIBase {

    @RestController
    @RequestMapping("api/public/lang")
    @PropertySource("classpath:properties/flags.properties")
    public class Lang extends APIBase {

        private final MessageSource messageSource; //在代码里获取Messages
        private final Flags flags;

        @Autowired
        public Lang(MessageSource messageSource, Flags flags) {
            this.messageSource = messageSource;
            this.flags = flags;
        }

        @PostMapping("bs-table/{tableName}")
        public HttpEntity<List<BootStrapTableColumn>> bs_table(BootStrapTableColumn[] cols, @PathVariable String tableName) {
            List<BootStrapTableColumn> list = new LinkedList<>();
            for (BootStrapTableColumn col : cols) {
                try {
                    col.setTitle(messageSource.getMessage(tableName + "." + col.getField(), null, Locale.getDefault()));
                    Flags.Field colFlag = flags.getFieldFlag(tableName, col.getField());
                    col.setVisible(colFlag.visibility);
                    list.add(col);
                } catch (NoSuchMessageException ignored) {
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
}
