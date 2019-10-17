package r4g19.offer100.service.zhj;

import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Table;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.jooq.tables.daos.CollectionDao;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.daos.SubmissionDao;
import r4g19.offer100.jooq.tables.pojos.*;
import r4g19.offer100.properties.cym.mapping.SubmissionStatus;
import r4g19.offer100.service.ServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看已申请的职位
 * 接受面试通知
 * 管理收藏的职位
 * 显示职位列表
 * 搜索职位
 * 显示职位详情
 */
@Service
public class JobService extends ServiceBase {
    /*操作类型 增删改查*/
    public static final int ADD = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int SEARCH = 3;

    private final int waiting = 0;//申请等待中
    private final int fail = 1;//申请被拒绝
    private final int success = 2;//申请通过，可以面试




    /**
     * 查看已申请的职位
     */
    public List<Hiring> findApplyedJob(String resUsername){
        SubmissionDao submissionDao = new SubmissionDao(dsl.configuration());
        HiringDao hiringDao = new HiringDao(dsl.configuration());
        List<Submission> list = submissionDao.fetchByResUsername(resUsername);
        List<Hiring> hirs = new ArrayList<>();
        for (Submission sub : list){
            List<Hiring> tmp = hiringDao.fetchById(sub.getHirId());
            Hiring hiring = tmp.get(0);
            hirs.add(hiring);
        }
        return hirs;
    }

    /**
     * 查看通知面试的申请
     */
    public List<Hiring> findApplySuccessJob(String resUsername){
        SubmissionDao submissionDao = new SubmissionDao(dsl.configuration());
        HiringDao hiringDao = new HiringDao(dsl.configuration());
        List<Submission> list = submissionDao.fetchByResUsername(resUsername);
        List<Hiring> hirs = new ArrayList<>();
        for (Submission sub : list){
            if (sub.getStatus() != SubmissionStatus.APPROVE) continue;
            List<Hiring> tmp = hiringDao.fetchById(sub.getHirId());
            Hiring hiring = tmp.get(0);
            hirs.add(hiring);
        }
        return hirs;
    }

    /**
     * 查看申请失败的申请
     */
    public List<Hiring> findApplyFailJob(String resUsername){
        SubmissionDao submissionDao = new SubmissionDao(dsl.configuration());
        HiringDao hiringDao = new HiringDao(dsl.configuration());
        List<Submission> list = submissionDao.fetchByResUsername(resUsername);
        List<Hiring> hirs = new ArrayList<>();
        for (Submission sub : list){
            if (sub.getStatus() != SubmissionStatus.REJECT) continue;
            List<Hiring> tmp = hiringDao.fetchById(sub.getHirId());
            Hiring hiring = tmp.get(0);
            hirs.add(hiring);
        }
        return hirs;
    }

    /**
     * 管理收藏的职位
     */
    public List<Collection> manageCollectionJob(Collection collection, int op){
        CollectionDao collectionDao = new CollectionDao(dsl.configuration());
        switch (op){
            case ADD:
                collectionDao.insert(collection);
                break;

            case DELETE:
                collectionDao.delete(collection);
                break;

            case UPDATE:
                collectionDao.update(collection);
                break;

            case SEARCH:
                String s = collection.getPerUsername();
                List<Collection> list;
                list = collectionDao.fetchByPerUsername(s);
//                long hirId = collection.getHirId();
//                List<Collection> list_c = new ArrayList<>();
//                for (Collection c : list){
//                    if (c.getHirId() != hirId) continue;
//                    list_c.add(c);
//                }
//                return list_c;
                return list;
        }
        return null;
    }

    /**
     * 搜索职位
     */
    public List<Hiring> searchJob(Hiring condition){
        HiringDao hiringDao = new HiringDao(dsl.configuration());
        List<Hiring> list = hiringDao.findAll();
        //字符串为""的记得要处理为null
        if (condition.getUsername() != null){
            System.out.println("iiiiiiiiiiiiiiii");
            //list = hiringDao.fetchByUsername(condition.getUsername());
            Result r = dsl.select().from(Tables.HIRING).where(Tables.HIRING.USERNAME.like("s")).fetch();//返回rusult类型
            list = new ArrayList<>();
            for (Object o : r.toArray()){
                list.add((Hiring)o);
            }
        }
        return list;
    }

    public void test(){
        SubmissionDao submissionDao = new SubmissionDao(dsl.configuration());
        //submissionDao.
    }
}
