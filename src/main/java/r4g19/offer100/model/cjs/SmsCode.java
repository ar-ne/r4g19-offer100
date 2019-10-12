package r4g19.offer100.model.cjs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCode {
    private String code;
    private Timestamp time;

    public String getJSON() {
        return String.format("{\"code\":\"%s\"}", code);
    }

    public SmsCode(String code) {
        this.code = code;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public static SmsCode generate() {
        return new SmsCode(String.valueOf(((int) (Math.random() * 1000000))), new Timestamp(System.currentTimeMillis()));
    }

    public boolean equals(SmsCode smsCode) {
        if (Math.abs(this.time.getTime() - smsCode.time.getTime()) < 300000)
            return this.getCode().equals(smsCode.getCode());
        return false;
    }

    public boolean equals(String smsCode) {
        return this.equals(new SmsCode(smsCode));
    }

    public int hashCode(){
        return 0;
    }
}
