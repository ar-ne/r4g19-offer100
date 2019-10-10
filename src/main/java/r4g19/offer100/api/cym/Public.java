package r4g19.offer100.api.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cym.BootstrapTableColumn;
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
        public HttpEntity<List<BootstrapTableColumn>> bs_table(@RequestBody BootstrapTableColumn[] cols, @PathVariable String tableName) {
            List<BootstrapTableColumn> list = new LinkedList<>();
            for (BootstrapTableColumn col : cols) {
                try {
                    col.setTitle(messageSource.getMessage(tableName + "." + col.getField(), null, Locale.getDefault()));
                    Flags.Field colFlag = flags.getFieldFlag(tableName, col.getField());
                    col.setVisible(colFlag.visibility);
                } catch (NoSuchMessageException ignored) {
                    col.setVisible(true);
                    col.setTitle(col.getField());
                } finally {
                    list.add(col);
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
}
