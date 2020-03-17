package com.parkingmanagement.controller.system;

import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.Captcha;
import com.parkingmanagement.utils.GifCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Random;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 获取验证码（Gif版本）
     * @param response
     */
    @RequestMapping(value="getGifCode",method = RequestMethod.GET)
    public void getGifCode(HttpServletResponse response, HttpServletRequest request){
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            HttpSession session = request.getSession(true);

            Captcha captcha = new GifCaptcha(146,33,4);
            /* 输出 */
            captcha.out(response.getOutputStream());
            String vcodeText = captcha.text().toLowerCase();
            //存入Session
            session.setAttribute("_code",vcodeText);
//            logger.info("保存vcode:"+vcodeText);

            System.out.println("------"+session.getAttribute("_code"));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取验证码异常："+e.getMessage());
        }
    }

    @RequestMapping("/userLogin")
    public String userLogin(HttpServletRequest request,User user,String vcode){
        HttpSession session = request.getSession();
//        //转化成小写字母
//        vcode = vcode.toLowerCase();
//        String v = (String)session.getAttribute("_code");//还可以读取一次后把验证码清空，这样每次登录都必须获取验证码;
////        logger.info("获取保存vcode:"+v);
////        logger.info("验证vcode:"+vcode);
//        if(!vcode.equals(v)){
////            logger.info("对用户[" + user.getUsername() + "]验证码不通过");
//            request.setAttribute("message", "验证码不正确");
//            return "/base/login";//返回登录页面
//        }

//        logger.info("进行账号"+username+",密码验证"+password+".....");
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            String  loginUsername=(String ) subject.getPrincipal();
            session.setAttribute("user", loginUsername);
            session.setAttribute("clickId","home");
            User loginUser = userService.getUserByUsername(loginUsername);
            for (Role role : loginUser.getRoles()) {
                if ("admin".equals(role.getRoleCode())){
                    return "redirect:/admin/home";
                }
            }

            return "/user/home";
        }catch(UnknownAccountException uae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            request.setAttribute("message", "未知账户");
            return "/base/login";//返回登录页面
        }catch(IncorrectCredentialsException ice){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            request.setAttribute("message", "密码不正确");
            return "/base/login";//返回登录页面
        }catch(LockedAccountException lae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            request.setAttribute("message", "账户已锁定");
            return "/base/login";//返回登录页面
        }catch(ExcessiveAttemptsException eae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            request.setAttribute("message", "用户名或密码错误次数过多");
            return "/base/login";//返回登录页面
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            request.setAttribute("message", "用户名或密码不正确");
            return "/base/login";//返回登录页面
        }

    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.removeAttribute("user");
        return "/base/login";
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
