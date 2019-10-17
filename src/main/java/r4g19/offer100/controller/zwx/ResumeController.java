package r4g19.offer100.controller.zwx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.Personal;
import r4g19.offer100.jooq.tables.pojos.Resume;
import r4g19.offer100.service.cjs.UserService;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.zwx.ResumeService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/web/resume")
public class ResumeController extends ControllerBase {
    @Autowired
    ResumeService resumeService;
    @Autowired
    UserService userService;
    @Autowired
    CommonCRUD crud;

    @GetMapping("new")
    @Order(1)
    public String resume(Model model, Authentication authentication) {
        setAttributes(false, true, false, false,
                new Resume(), userService.getPersonal(getUsername(authentication)), model);
        return "/pub/web/resume";
    }

    @PostMapping("new")
    @ResponseBody
    public String resume(Resume resume, Authentication authentication, HttpServletResponse response) {
        resume.setUsername(getUsername(authentication));
        resume = crud.newRecord(resume, getUsername(authentication));
        return showSuccessMsgAndVisit("发布成功，正在跳转...", "/web/resume/" + resume.getId(), response);
    }

    @GetMapping("{id}/{operate}")
    @Order(2)
    public String edit(@PathVariable Long id, Resume newResume, Model model, Authentication authentication, @PathVariable String operate) {
        Resume resume = resumeService.getResume(id);
        if (operate.equalsIgnoreCase("edit") &&
                resumeService.getResume(id).getUsername().equals(getUsername(authentication))) {
            setAttributes(false, false, true, resume.getUsername().equals(getUsername(authentication)), resume, resumeService.getPersonal(resume), model);
            return "/pub/web/resume";
        }
        if (operate.equalsIgnoreCase("save") && resumeService.getResume(id).getUsername().equals(getUsername(authentication))) {
            crud.updateRecord(getUpdatedObject(newResume, resume), getUsername(authentication));
        }
        return "redirect:/web/resume_m";
    }

    @PostMapping("{id}/save")
    @ResponseBody
    @Order(2)
    public String save(@PathVariable Long id, Resume newResume, Authentication authentication, HttpServletResponse response) {
        Resume resume = resumeService.getResume(id);
        if (resumeService.getResume(id).getUsername().equals(getUsername(authentication))) {
            crud.updateRecord(getUpdatedObject(newResume, resume), getUsername(authentication));
        }
        return showSuccessMsgAndVisit("修改成功，正在跳转...", "/web/resume/" + resume.getId(), response);
    }


    @GetMapping("{id}")
    @Order(2)
    public String resume(@PathVariable Long id, Model model, Authentication authentication) {
        Resume resume = resumeService.getResume(id);
        setAttributes(true, false, false,
                resume.getUsername().equals(getUsername(authentication)), resume, resumeService.getPersonal(resume), model);
        return "/pub/web/resume";
    }

    private Model setAttributes(boolean disabled, boolean creating, boolean editing, boolean userEditable, Resume resume, Personal personal, Model model) {
        model.addAttribute("disabled", disabled ? "disabled" : "");
        model.addAttribute("creating", creating);
        model.addAttribute("editing", editing);
        model.addAttribute("userEditable", userEditable);
        model.addAttribute("resume", resume);
        model.addAttribute("personal", personal);
        return model;
    }
}
