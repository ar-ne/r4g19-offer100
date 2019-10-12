package r4g19.offer100.api.cym;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;

@RestController
@RequestMapping("api/user")
@APIEntrance(name = "user")
public class User extends APIBase {
}
