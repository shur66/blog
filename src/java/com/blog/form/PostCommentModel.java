package com.blog.form;

import com.blog.Util.FormatUtil;
import com.blog.model.Post;
import com.blog.model.PostComment;
import com.blog.model.User;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by ShurA on 14.06.2018.
 * Модель формы поста с комментариями
 */
public class PostCommentModel {
    @Getter @Setter
    Post post;
    @Getter @Setter
    String commentsJson;
    @Setter
    String tagsJson;
    @Getter @Setter
    PostComment postComment;
    @Getter @Setter
    String action;
    @Getter @Setter
    String error;
    @Setter
    String dateFormat;

    public String getDateFormat() {
        if (post!= null && post.getDate() != null){
            return FormatUtil.formatDateTime(post.getDate());
        }else{
            return "";
        }
    }

    /**
     * Запросить Теги в виде json строки
     * @return строка json
     */
    public String getTagsJson() {
        if (post != null && post.getTags()!= null) {
            JSONArray result = new JSONArray();
            for (String tag : post.getTags().split(" ")){
                if (!tag.equals(""))
                    result.add(tag);
            }
            return result.toJSONString();
        }else{
            return null;
        }
    }
}
