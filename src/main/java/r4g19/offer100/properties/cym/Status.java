package r4g19.offer100.properties.cym;

import org.springframework.http.HttpStatus;

public class Status {
    public static final int normal = 200;
    public static final int replace = 201;
    public static final int eval = 203;
    public static final int showSuccess = 205;
    public static final int showSuccessMsg = 202;
    public static final int showFailMsg = 406;
    public static final int showFail = 407;

    public static HttpStatus toHttpStatus(int CODE) {
        return HttpStatus.valueOf(CODE);
    }
}
