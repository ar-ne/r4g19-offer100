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
    private  String phone;

    public String getJSON() {
        return String.format("{\"code\":\"%s\"}", code);
    }

    public SmsCode(String code,String phone) {
        this.code = code;
        this.phone = phone;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public static SmsCode generate(String phone) {
        return new SmsCode(String.valueOf(((int) (Math.random() * 1000000))), new Timestamp(System.currentTimeMillis()),phone);
    }

    public boolean equals(SmsCode smsCode) {
        if(!this.phone.equals(smsCode.getPhone())) return false;
        if (Math.abs(this.time.getTime() - smsCode.time.getTime()) < 300000)
            return this.getCode().equals(smsCode.getCode());
        return false;
    }

    public boolean equals(String smsCode,String  phone) {
        return this.equals(new SmsCode(smsCode,phone));
    }

    public int hashCode(){
        return 0;
    }
}
