/**
 * 162012班 第四组
 * 智能售货机管理系统——XXX模块
 * FileName: AdminService
 * Author:   J.Y
 * Date:     2019/10/8 8:26
 * Description:
 */

package r4g19.offer100.service.yhr;

import org.jooq.Result;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.jooq.tables.daos.*;
import r4g19.offer100.jooq.tables.pojos.*;
import r4g19.offer100.jooq.tables.records.PersonalRecord;
import r4g19.offer100.service.ServiceBase;


/**
 * 功能描述: 
 * @author J.Y
 * @create 2019/10/8
 * @since 1.0.0
 */
@Service
public class AdminService extends ServiceBase {

    public void deleteUser(String username) {
        new LogDao(dsl.configuration()).deleteById();
        dsl.delete(Tables.COLLECTION).where(Tables.COLLECTION.PER_USERNAME.eq(username)).execute();
        dsl.delete(Tables.SUBMISSION).where(Tables.SUBMISSION.RES_USERNAME.eq(username)).execute();
        dsl.delete(Tables.SUBMISSION).where(Tables.SUBMISSION.HIR_USERNAME.eq(username)).execute();
        dsl.delete(Tables.HIRING).where(Tables.HIRING.USERNAME.eq(username)).execute();
        new EntrepreneurialDao(dsl.configuration()).deleteById(username);
        dsl.delete(Tables.RESUME).where(Tables.RESUME.USERNAME.eq(username)).execute();
        new PersonalDao(dsl.configuration()).deleteById(username);
        new LoginDao(dsl.configuration()).deleteById(username);

    }

    public void deleteNotice(int noticeId) {
        new NoticeDao(dsl.configuration()).deleteById(noticeId);
    }

    public void insertNotice(Notice notice) {
        new NoticeDao(dsl.configuration()).insert(notice);
    }

    public Login queryUser(String username) {
        Login login = new LoginDao(dsl.configuration()).fetchOneByUsername(username);
        return login;
    }

    public Personal queryPerson(String username) {
        Personal personal = new PersonalDao(dsl.configuration()).fetchOneByUsername(username);
        return personal;
    }

    public Entrepreneurial queryEntrepreneurial(String username) {
        Entrepreneurial entrepreneurial = new EntrepreneurialDao(dsl.configuration()).fetchOneByUsername(username);
        return entrepreneurial;
    }

    public Hiring queryHiring(Long id) {
        Hiring hiring = new HiringDao(dsl.configuration()).fetchOneById(id);
        return hiring;
    }

    public Notice queryNotice(Integer noticeId) {
        Notice notice = new NoticeDao(dsl.configuration()).fetchOneByNoticeId(noticeId);
        return notice;
    }

    public void updateUser(Login login) {
        new LoginDao(dsl.configuration()).update(login);
    }

    public void updatePersonal(Personal personal) {
        new PersonalDao(dsl.configuration()).update(personal);
    }

    public void updateEntrepreneurial(Entrepreneurial entrepreneurial) {
        new EntrepreneurialDao(dsl.configuration()).update(entrepreneurial);
    }

    public void updateHiring(Hiring hiring) {
        new HiringDao(dsl.configuration()).update(hiring);
    }

    public void updateNotice(Notice notice) {
        new NoticeDao(dsl.configuration()).update(notice);
    }

    public int first() {
        Result<PersonalRecord> personals = dsl.selectFrom(Tables.PERSONAL).where(Tables.PERSONAL.AGE.between(16, 20)).fetch();
        return personals.size();
    }

    public int sencond() {
        Result<PersonalRecord> personals = dsl.selectFrom(Tables.PERSONAL).where(Tables.PERSONAL.AGE.between(21, 30)).fetch();
        return personals.size();
    }

    public int third() {
        Result<PersonalRecord> personals = dsl.selectFrom(Tables.PERSONAL).where(Tables.PERSONAL.AGE.between(31, 40)).fetch();
        return personals.size();
    }

    public int fourth() {
        Result<PersonalRecord> personals = dsl.selectFrom(Tables.PERSONAL).where(Tables.PERSONAL.AGE.between(41, 50)).fetch();
        return personals.size();
    }

    public int fifth() {
        Result<PersonalRecord> personals = dsl.selectFrom(Tables.PERSONAL).where(Tables.PERSONAL.AGE.between(51, 60)).fetch();
        return personals.size();
    }

    public void deletehr(Long id) {
        new HiringDao(dsl.configuration()).deleteById(id);
    }

}

    