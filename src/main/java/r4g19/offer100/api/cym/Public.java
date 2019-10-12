package r4g19.offer100.api.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cym.BootstrapTableColumn;
import r4g19.offer100.properties.cym.Flags;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static r4g19.offer100.utils.cym.ReflectUtils.getTable;

@RestController
@RequestMapping("api/public")
@APIEntrance(name = "public")
public class Public extends APIBase {

    @RestController
    @RequestMapping("api/public/lang")
    @PropertySource("classpath:properties/flags.properties")
    public static class Lang extends APIBase {

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
                } catch (NoSuchMessageException ignored) {
                    col.setTitle(col.getField());
                }
                try {
                    Flags.Field colFlag = flags.getTableField(getTable(tableName), col.getField());
                    col.setVisible(colFlag.visibility);
                } catch (Exception e) {
                    col.setVisible(true);
                }
                list.add(col);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
}
