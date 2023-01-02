package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import org.json.JSONObject;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentAttributeDataset.FIRST_COMMENT_CONTENT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentAttributeDataset.SECOND_COMMENT_CONTENT;

public class CommentDataset {

    public static final JSONObject FIRST_COMMENT = new JSONObject()
            .put("content", FIRST_COMMENT_CONTENT);
    public static final String FIRST_COMMENT_STRING = FIRST_COMMENT.toString();

    public static final JSONObject SECOND_COMMENT = new JSONObject()
            .put("content", SECOND_COMMENT_CONTENT);
    public static final String SECOND_COMMENT_STRING = SECOND_COMMENT.toString();
}
