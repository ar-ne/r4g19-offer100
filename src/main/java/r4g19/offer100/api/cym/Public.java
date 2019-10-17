package r4g19.offer100.api.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.core.Authentication;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cym.BootstrapTableColumn;
import r4g19.offer100.properties.cym.Flags;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static r4g19.offer100.utils.cym.ReflectUtils.getTable;

@API("public")
public class Public extends APIBase {

    @API("public/lang")
    public static class Lang extends APIBase {

        private final MessageSource messageSource; //在代码里获取Messages
        private final Flags flags;

        @Autowired
        public Lang(MessageSource messageSource, Flags flags) {
            this.messageSource = messageSource;
            this.flags = flags;
        }

        @POST
        @Path("bs-table/{tableName}")
        public List<BootstrapTableColumn> bs_table(BootstrapTableColumn[] cols, @PathParam("tableName") String tableName, @Context SecurityContext context, @Context HttpServletRequest request) {
            List<BootstrapTableColumn> list = new LinkedList<>();
            for (BootstrapTableColumn col : cols) {
                try {
                    col.setTitle(messageSource.getMessage(tableName.toUpperCase() + "." + col.getField(), null, Locale.getDefault()));
                } catch (NoSuchMessageException ignored) {
                    logger.error("Can not get field from messages for {}.{}, which will be set to field name.", tableName, col.getField());
                    col.setTitle(col.getField());
                }
                try {
                    Flags.Visibility visibility = flags.getTableField(getTable(tableName), col.getField());
                    Authentication authentication = getAuthentication(context);
                    if (authentication != null && authentication.isAuthenticated()) {
                        col.setVisible(visibility.getByUserType(getUserType(authentication)));
                    } else col.setVisible(visibility.getByUserType(getUserType(request)));
                } catch (Exception e) {
                    logger.error("Can not get Visibility flags from file for {}.{}, which will be set to true.", tableName, col.getField());
                    col.setVisible(true);
                }
                list.add(col);
            }
            return list;
        }
    }
}
