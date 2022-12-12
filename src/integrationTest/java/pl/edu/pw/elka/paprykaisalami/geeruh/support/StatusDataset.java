package pl.edu.pw.elka.paprykaisalami.geeruh.support;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_ORDER_NUMBER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_ORDER_NUMBER;

import org.json.JSONObject;

public class StatusDataset {

    public static final JSONObject FIRST_STATUS = new JSONObject()
            .put("name", FIRST_STATUS_NAME)
            .put("orderNumber", FIRST_STATUS_ORDER_NUMBER);
    public static final String FIRST_STATUS_STRING = FIRST_STATUS.toString();

    public static final JSONObject SECOND_STATUS = new JSONObject()
            .put("name", SECOND_STATUS_NAME)
            .put("orderNumber", SECOND_STATUS_ORDER_NUMBER);
    public static final String SECOND_STATUS_STRING = SECOND_STATUS.toString();
}
