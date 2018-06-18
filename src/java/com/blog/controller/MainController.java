package com.blog.controller;

import com.blog.Util.FormatUtil;
import com.blog.Util.PostAction;
import com.blog.form.PostCommentModel;
import com.blog.form.PostModel;
import com.blog.form.UserInfoModel;
import com.blog.model.Post;
import com.blog.form.SignModel;
import com.blog.model.PostComment;
import com.blog.model.User;
import com.blog.service.AuthService;
import com.blog.service.PostService;
import com.blog.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by ShurA on 09.06.2018.
 * Основной контроллер проекта
 */
@Controller
@RequestMapping("/")
@SessionAttributes({"authUser"})
public class MainController {

    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    /**
     * Основной путь
     * @return редирект на welcome
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "redirect:welcome";
    }

    /**
     * Страница приветствия
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    /**
     * Регистрация пользователя
     */
    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public ModelAndView sing() {
        ModelAndView mv = new ModelAndView();
        SignModel signModel = new SignModel();
        mv.addObject("signModel", signModel);
        mv.setViewName("sign");
        return mv;
    }
    /**
     * Информация пользователя
     *
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam("user_id") Integer user_id,
                                 @RequestParam("goback") String goBack
    ) {
        ModelAndView mv = new ModelAndView();
        UserInfoModel userInfoModel = new UserInfoModel();
        User user = userService.findUserById(user_id);
        userInfoModel.setUser(user);
        userInfoModel.setGoBack(goBack);
        mv.addObject("userInfoModel", userInfoModel);
        mv.setViewName("userinfo");
        return mv;
    }
    /**
     * Регистрация пользователя
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ModelAndView sing(@ModelAttribute("signModel") SignModel signModel
    ) {
        ModelAndView mv = new ModelAndView();
        try {
            userService.createUser(signModel.getUser());
            mv.addObject("signModel", signModel);
            mv.setViewName("redirect:login");
        } catch (Exception ex) {
            signModel.setError(ex.getMessage());
            signModel.getUser().setPassword("");
            signModel.setPasswordConfirm("");
            mv.addObject("signModel", signModel);
            mv.setViewName("sign");
        }
        return mv;
    }

    /**
     * Главная форма
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(ModelMap model, HttpSession session) {
        User authUser = (User) session.getAttribute("authUser");
        model.addAttribute("posts", getPostListJson(authUser).toJSONString());
        return "main";
    }

    /**
     * Запросить список постов
     * @param authUser текущий активный пользователь
     * @return JSON массив постов
     */
    private JSONArray getPostListJson(User authUser){
        JSONArray postArray = new JSONArray();
        for (Post post : postService.getPostList()) {
            JSONObject postJson = new JSONObject();
            postJson.put("id", post.getId());
            postJson.put("caption", post.getCaption());
            postJson.put("body", post.getBody());
            postJson.put("tags", post.getTags());
            postJson.put("date", FormatUtil.formatDateTime(post.getDate()));
            postJson.put("user_name", post.getUser().getName());
            postJson.put("user_id", post.getUser().getId());
            postJson.put("owner", post.getUser().getId() == authUser.getId());

            postArray.add(postJson);
        }
        return postArray;
    }

    /**
     * Форма ввода авторизация пользователя
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String formLogin(ModelMap model) {
        return "login";
    }

    /**
     * Выход пользователя
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String formLogout(ModelMap model, HttpSession session) {
        session.removeAttribute("authUser");
        return "login";
    }

    /**
     * Форма входа в форму поста
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ModelAndView post(ModelMap model,
                             @RequestParam(value = "action") String action,
                             @RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mv = new ModelAndView();
        PostModel postModel = new PostModel();
        postModel.setAction(action);
        mv.setViewName("post");
        if (action.equalsIgnoreCase(PostAction.create.name())) {
            postModel.setPost(new Post());
            postModel.setAction(action);
        }
        if (action.equalsIgnoreCase(PostAction.update.name())) {
            Post post = postService.getPostById(id);
            postModel.setPost(post);
        }
        mv.addObject("postModel", postModel);
        return mv;
    }

    /**
     * Форма поста c комментариями
     */
    @RequestMapping(value = "/postcomment", method = RequestMethod.GET)
    public ModelAndView postComment(HttpSession session,
                                    @RequestParam(value = "id") Integer id) {
        ModelAndView mv = new ModelAndView("postcomment");
        PostCommentModel model = new PostCommentModel();
        try {
            model = initPostCommentModel(id, session);
        }catch (Exception ex){
            model.setError(ex.getMessage());
        }
        mv.addObject("postCommentModel", model);
        return mv;
    }

    /**
     * Удаление поста
     */
    @RequestMapping(value = "/post", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Object postDelete(HttpSession session,
                                           @RequestParam("id") Integer id) {
        JSONObject json = new JSONObject();
        try {
            postService.deletePostById(id);
            User authUser = (User) session.getAttribute("authUser");
            json.put("posts", getPostListJson(authUser));
            json.put("result", "1");
        }catch (Exception ex){
            json.put("result", "0");
            json.put("error", ex.getMessage());
        }
        return json;
    }

    /**
     * Действия над комментарием постов
     */
    @RequestMapping(value = "/postcomment", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Object postCommentAction(HttpSession session,
                                        @ModelAttribute("postCommentModel") PostCommentModel model) {
        ModelAndView mv = new ModelAndView();
        User user = (User)session.getAttribute("authUser");
        JSONObject json = new JSONObject();
        try {
            if (model.getAction().equalsIgnoreCase(PostAction.delete.name())) {
                postService.delete(model.getPostComment());
            } else
            if (model.getAction().equalsIgnoreCase(PostAction.create.name())) {
                PostComment postComment = model.getPostComment();
                postComment.setPost(model.getPost());
                postComment.setUser(user);
                postComment.setDate(new Date());
                postService.create(postComment);
            }
            json.put("result", "1");
            Post post = postService.getPostById(model.getPost().getId());
            json.put("comments", postService.getCommentsJson(post, user));
        }catch (Exception ex){
            json.put("result", "0");
            json.put("error", ex.getMessage());
        }
        return json;
    }

    /**
     * Инициализация для формы модели комментриев поста
     * @param postId Идентификатор поста
     * @param session http сессия
     * @return модель комментриев поста
     */
    private PostCommentModel initPostCommentModel(Integer postId, HttpSession session){
        PostCommentModel model = new PostCommentModel();
        User user = (User) session.getAttribute("authUser");
        Post post = postService.getPostById(postId);
        model.setPost(post);
        model.setCommentsJson(postService.getCommentsJson(post, user).toJSONString());
        return model;
    }


    /**
     * Действия над постом
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ModelAndView postAction(HttpSession session,
                                   @ModelAttribute("postModel") PostModel postModel) {
        ModelAndView mv = new ModelAndView();
        User user = (User)session.getAttribute("authUser");
        try {
            if (postModel.getAction().equalsIgnoreCase(PostAction.create.name())) {
                Post post = postModel.getPost();
                post.setUser(user);
                post.setDate(new Date());
                postService.create(post);
            }else
            if (postModel.getAction().equalsIgnoreCase(PostAction.update.name())) {

                Post post = postService.getPostById(postModel.getPost().getId());
                post.setCaption(postModel.getPost().getCaption());
                post.setBody(postModel.getPost().getBody());
                post.setTags(postModel.getPost().getTags());
                postService.update(post);
            }
            mv.addObject("postModel", postModel);
            mv.setViewName("redirect:main");
        }catch (Exception ex){
            postModel.setError(ex.getMessage());
            mv.addObject("postModel", postModel);
            mv.setViewName("post");
        }
        return mv;
    }

    /**
     * Авторизация пользователя
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap model,
                        HttpSession session,
                        @RequestParam(value = "login") String login,
                        @RequestParam(value = "password")String password) {

        try {
            authService.authUser(session, login, password);
            return "redirect:main";
        }catch(Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }

    /**
     * Нет доступа
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }

}
