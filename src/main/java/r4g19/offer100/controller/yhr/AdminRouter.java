package r4g19.offer100.controller.yhr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.*;
import r4g19.offer100.properties.cym.Status;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.yhr.AdminService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class AdminRouter extends ControllerBase {
    @Autowired
    CommonCRUD commonCRUD;
    @Resource
    AdminService adminService;

    /***
     * 用户列表
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("userType", "admin");
        return "redirect:userList";
    }


    @GetMapping("/userEdit/{username}")
    public String userEdit(@PathVariable String username, Model model) {
        Login login = adminService.queryUser(username);
        Personal personal = adminService.queryPerson(username);
        model.addAttribute("login", login);
        model.addAttribute("userType", "admin");
        model.addAttribute("personal", personal);
        return "admin/user_edit";
    }

    @PostMapping("/userDoEdit")
    @ResponseBody
    public String userDoEdit(HttpServletResponse response, String username, String dN, String email, String tel, String age, String edu, String school) {
        Personal personal = adminService.queryPerson(username);
        Login login = adminService.queryUser(username);
        if (age == null || age.trim().equals("")) {
            response.setStatus(Status.showFailMsg);
            return "年龄不能为空！";
        } else {
            personal.setAge(Integer.parseInt(age));
            personal.setEdu(edu);
            personal.setSchool(school);
            login.setDisplayname(dN);
            login.setEmail(email);
            login.setTel(tel);
            adminService.updatePersonal(personal);
            adminService.updateUser(login);
            return showSuccessMsgAndVisit("修改成功", "/admin/userList", response);
        }
    }

    /***
     * 公司列表
     * @param model
     * @return
     */
    @GetMapping("/enterprise")
    public String enterpriseList(Model model) {
        model.addAttribute("userType", "admin");
        return "redirect:enterpriseList";
    }

    @GetMapping("/enterpriseEdit/{username}")
    public String enterpriseEdit(@PathVariable String username, Model model) {
        Entrepreneurial entrepreneurial = adminService.queryEntrepreneurial(username);
        model.addAttribute("userType", "admin");
        model.addAttribute("entrepreneurial", entrepreneurial);
        return "admin/enterprise_edit";
    }

    @PostMapping("/enterpriseDoEdit")
    @ResponseBody
    public String enterpriseDoEdit(HttpServletResponse response, String username, String address, String scale, String type, String field, String brief, String detail, String accuAddr) {
        Entrepreneurial entrepreneurial = adminService.queryEntrepreneurial(username);

        entrepreneurial.setAccuAddr(accuAddr);
        entrepreneurial.setBrief(brief);
        entrepreneurial.setDetail(detail);
        entrepreneurial.setScale(scale);
        entrepreneurial.setType(type);
        entrepreneurial.setField(field);
        entrepreneurial.setAddress(address);
        adminService.updateEntrepreneurial(entrepreneurial);
        return showSuccessMsgAndVisit("修改成功", "/admin/enterpriseList", response);

    }

    @GetMapping("/hr")
    public String hr(Model model) {
        model.addAttribute("userType", "admin");
        return "redirect:HRList";
    }

    @GetMapping("/hrEdit/{id}")
    public String hrEdit(Model model, @PathVariable Long id) {
        Hiring hiring = adminService.queryHiring(id);
        model.addAttribute("userType", "admin");
        model.addAttribute("hiring", hiring);
        return "admin/hiring_edit";
    }

    @PostMapping("/hiringDoEdit")
    @ResponseBody
    public String hiringDoEdit(HttpServletResponse response, Long id, String salary, String position, String eduRequirement, String workAddress, String interviewAddress, String detail) {
        Hiring hiring = adminService.queryHiring(id);
        if (hiring == null) {
            response.setStatus(Status.showFailMsg);
            return "标题与内容不能为空！";
        } else {
            hiring.setDetail(detail);
            hiring.setEduRequirement(eduRequirement);
            hiring.setInterviewAddress(interviewAddress);
            hiring.setSalary(salary);
            hiring.setWorkAddress(workAddress);
            hiring.setPosition(position);
            adminService.updateHiring(hiring);
            return showSuccessMsgAndVisit("修改成功", "/admin/HRList", response);
        }
    }

    @GetMapping("/notice")
    public String noticeList(Model model) {
        model.addAttribute("userType", "admin");
        return "redirect:noticeList";
    }

    @PostMapping("/noticeDoAdd")
    @ResponseBody
    public String noticeDoAdd(String title, String noticeMessage, HttpServletResponse response) throws ParseException {

        Notice notice = new Notice();
        if (title == null || noticeMessage == null || title.trim() == "" || noticeMessage.trim() == "") {
            response.setStatus(Status.showFailMsg);
            return "标题与内容不能为空！";
        } else {
            notice.setNoticeTitle(title);
            notice.setNoticeText(noticeMessage);
            Date date = new Date();//获得系统时间.
            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
            String nowTime = sdf.format(date);
            java.sql.Timestamp ctime = new java.sql.Timestamp(sdf.parse(nowTime).getTime());
            notice.setNoticeTime(ctime);
            adminService.insertNotice(notice);
            return showSuccessMsgAndVisit("添加成功", "/admin/noticeList", response);
        }
    }

    @GetMapping("/noticeEdit/{noticeId}")
    public String noticeEdit(@PathVariable Integer noticeId, Model model) {
        Notice notice = adminService.queryNotice(noticeId);
        model.addAttribute("userType", "admin");
        model.addAttribute("notice", notice);
        return "admin/notice_edit";
    }

    @PostMapping("/noticeDoEdit")
    @ResponseBody
    public String noticeDoEdit(HttpServletResponse response, Integer noticeId, String noticeTitle, String noticeText) {
        Notice notice = adminService.queryNotice(noticeId);
        if (noticeTitle == null || noticeText == null || noticeTitle.trim() == "" || noticeText.trim() == "") {
            response.setStatus(Status.showFailMsg);
            return "标题与内容不能为空！";
        } else {
            System.out.println(noticeId);
            notice.setNoticeTitle(noticeTitle);
            notice.setNoticeText(noticeText);
            Date date = new Date();//获得系统时间.
            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
            String nowTime = sdf.format(date);
            Timestamp ctime = null;
            try {
                ctime = new Timestamp(sdf.parse(nowTime).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            notice.setNoticeTime(ctime);
            adminService.updateNotice(notice);
            return showSuccessMsgAndVisit("修改成功", "/admin/noticeList", response);
        }

    }

    @GetMapping("/statistic")
    public String statistic(Model model) {
        int first = adminService.first();
        int second = adminService.sencond();
        int third = adminService.third();
        int fourth = adminService.fourth();
        int fifth = adminService.fifth();
        model.addAttribute("userType", "admin");
        model.addAttribute("first", first);
        model.addAttribute("second", second);
        model.addAttribute("third", third);
        model.addAttribute("fourth", fourth);
        model.addAttribute("fifth", fifth);

        return "redirect:statistics";
    }

    @GetMapping("{page}")
    public String router(Model model, @PathVariable String page) {
        model.addAttribute("userType", "admin");
        return String.format("admin/%s", page);
    }

}
