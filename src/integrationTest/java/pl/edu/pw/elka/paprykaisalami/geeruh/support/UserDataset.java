package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_EMAIL;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_FIRST_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_LOGIN;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_PASSWORD;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_SECOND_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_SURNAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_EMAIL;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_FIRST_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_LOGIN;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_PASSWORD;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_SURNAME;

import org.json.JSONObject;

public class UserDataset {

    public static final JSONObject FIRST_USER = new JSONObject()
            .put("login", FIRST_USER_LOGIN)
            .put("password", FIRST_USER_PASSWORD)
            .put("email", FIRST_USER_EMAIL)
            .put("firstName", FIRST_USER_FIRST_NAME)
            .put("secondName", FIRST_USER_SECOND_NAME)
            .put("surname", FIRST_USER_SURNAME);
    public static final String FIRST_USER_STRING = FIRST_USER.toString();
    public static final String FIRST_USER_LOGIN_STRING = new JSONObject()
            .put("username", FIRST_USER_LOGIN)
            .put("password", FIRST_USER_PASSWORD)
            .toString();

    public static final JSONObject SECOND_USER = new JSONObject()
            .put("login", SECOND_USER_LOGIN)
            .put("password", SECOND_USER_PASSWORD)
            .put("email", SECOND_USER_EMAIL)
            .put("firstName", SECOND_USER_FIRST_NAME)
            .put("surname", SECOND_USER_SURNAME);
    public static final String SECOND_USER_STRING = SECOND_USER.toString();
}
