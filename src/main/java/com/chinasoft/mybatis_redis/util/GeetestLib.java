package com.chinasoft.mybatis_redis.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/*
* Java SDK
* */
public class GeetestLib {
    /*设置项目 极验验证中 插件应用属性 及请求过程*/
    protected final String verName = "4.0";
    protected final String sdkLang = "java";

    protected final String apiUrl = "http://api.geetest.com";

    protected final String registerUrl = "/register.php";
    protected final String validateUrl = "/validate.php";

    protected final String json_format = "1";
    /**
     * 闁哄绶氶悰娆愵殽瀹�啰妲堝ù婊冩湰椤愬吋顨ュ畝鍐閻炴稏鍔屽畷鐔煎极閻楀牆绁�chllenge
     */
    public static final String fn_geetest_challenge = "geetest_challenge";

    /**
     * 闁哄绶氶悰娆愵殽瀹�啰妲堝ù婊冩湰椤愬吋顨ュ畝鍐閻炴稏鍔屽畷鐔煎极閻楀牆绁�validate
     */
    public static final String fn_geetest_validate = "geetest_validate";

    /**
     * 闁哄绶氶悰娆愵殽瀹�啰妲堝ù婊冩湰椤愬吋顨ュ畝鍐閻炴稏鍔屽畷鐔煎极閻楀牆绁�seccode
     */
    public static final String fn_geetest_seccode = "geetest_seccode";

    /**
     * 闁稿浚鍓熼幐锟� */
    private String captchaId = "";

    /**
     * 缂佸绶氶幐锟� */
    private String privateKey = "";

    /**
     * 闁哄嫷鍨伴幆浣割嚕閿熻姤鍎欓柡鍌涘濞堟吅ailback
     */
    private boolean newFailback = false;

    /**
     * 閺夆晜鏌ㄥú鏍拷濡ゅ喚鍎婂☉鎿勬嫹	 */
    private String responseStr = "";

    /**
     * 閻犲鍟抽惁顖氼嚕閿熻棄褰犻柨娑樻湰濡叉悂宕ラ敃浣虹炕闁告垿缂氶惃鐔烘嫚閺囩喐锛夐煫鍥锋嫹	 */
    public boolean debugCode = true;

    /**
     * 闁哄绶氶悰娆愵殽瀹�啰妲圓PI闁哄牆绉存慨鐔兼偐閼哥鎷稴ession Key
     */
    public String gtServerStatusSessionKey = "gt_server_status";
    /**
     * 閻㈩垽绠戝顒勫极閻楀牏锟介梺顐ゅТ閸ら亶寮敓锟�*
     * @param captchaId
     * @param privateKey
     */
    public GeetestLib(String captchaId, String privateKey, boolean newFailback) {

        this.captchaId = captchaId;
        this.privateKey = privateKey;
        this.newFailback = newFailback;
    }

    /**
     * 闁兼儳鍢茶ぐ鍥嫉椤掍緡鍋уΔ鐘茬焷閻﹀宕氬┑鍡╂綏闁告牗鐗炵换鎴﹀炊閻愯尙鎽熺紒妤嬬細鐟曪拷	 *
     * @return 闁告帗绻傞～鎰板礌閺嶎偆娉㈤柡瀣舵嫹	 */
    public String getResponseStr() {

        return responseStr;

    }

    public String getVersionInfo() {

        return verName;

    }

    /**
     * 濡澘瀚ˇ鈺呮偠閸℃ぜ浜奸悹鎰╁劚閹鎯冮崟顔剧闁搞儳鍋為悧绋款嚕韫囧氦顩�	 *
     * @return
     */
    private String getFailPreProcessRes() {

        Long rnd1 = Math.round(Math.random() * 100);
        Long rnd2 = Math.round(Math.random() * 100);
        String md5Str1 = md5Encode(rnd1 + "");
        String md5Str2 = md5Encode(rnd2 + "");
        String challenge = md5Str1 + md5Str2.substring(0, 2);

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("success", 0);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);
            jsonObject.put("new_captcha", this.newFailback);

        } catch (JSONException e) {

            gtlog("json dumps error");

        }

        return jsonObject.toString();

    }

    /**
     * 濡澘瀚ˇ鈺呮偠閸℃ê鐏囬柛鏃傚枎閹鎯冮崟顒傚灱闁告垵妫旂憰锟� *
     */
    private String getSuccessPreProcessRes(String challenge) {

        gtlog("challenge:" + challenge);

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("success", 1);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);

        } catch (JSONException e) {

            gtlog("json dumps error");

        }

        return jsonObject.toString();

    }

    /**
     * 濡ょ姴鐭侀惁澶愬礆濠靛棭娼楅柛鏍ㄧ墵椤ｂ晜寰勯崟顓熷�
     *
     * @return 1閻炴稏鍔庨妵姘跺礆濠靛棭娼楅柛鏍ㄧ墬閸ㄦ岸宕濋悤鍌滅0閻炴稏鍔庨妵姘跺礆濠靛棭娼楅柛鏍ㄧ墪閵囨垹鎷归敓锟�*/
    public int preProcess(HashMap<String, String> data) {

        if (registerChallenge(data) != 1) {

            this.responseStr = this.getFailPreProcessRes();
            return 0;

        }

        return 1;

    }

    /**
     * 闁烩懇鏆塧ptchaID閺夆晜绋栭、鎴濃枖閵娿儱鏂�柨娑樻湰濞插潡寮惂鐪宎llenge
     *
     * @return 1閻炴稏鍔庨妵姘枖閵娿儱鏂�柟瀛樺姇婵盯鏁嶉敓鐣屾偘閵娧佷粵婵炲鍔岄崬鑺ュ緞鏉堫偉袝
     */
    private int registerChallenge(HashMap<String, String>data) {

        try {
            String userId = data.get("user_id");
            String clientType = data.get("client_type");
            String ipAddress = data.get("ip_address");

            String getUrl = apiUrl + registerUrl + "?";
            String param = "gt=" + this.captchaId + "&json_format=" + this.json_format;

            if (userId != null){
                param = param + "&user_id=" + userId;
            }
            if (clientType != null){
                param = param + "&client_type=" + clientType;
            }
            if (ipAddress != null){
                param = param + "&ip_address=" + ipAddress;
            }


            gtlog("GET_URL:" + getUrl + param);
            String result_str = readContentFromGet(getUrl + param);
            if (result_str == "fail"){

                gtlog("gtServer register challenge failed");
                return 0;

            }

            gtlog("result:" + result_str);
            JSONObject jsonObject = new JSONObject(result_str);
            String return_challenge = jsonObject.getString("challenge");

            gtlog("return_challenge:" + return_challenge);

            if (return_challenge.length() == 32) {

                this.responseStr = this.getSuccessPreProcessRes(this.md5Encode(return_challenge + this.privateKey));

                return 1;

            }
            else {

                gtlog("gtServer register challenge error");

                return 0;

            }
        } catch (Exception e) {

            gtlog(e.toString());
            gtlog("exception:register api");

        }
        return 0;
    }

    /**
     * 闁告帇鍊栭弻鍥ㄧ▔閿熶粙鍤嬮悶娑栧妼瀹曠喓锟界涵鍛版澖闁稿﹤鍚嬪Σ鎼佸触閿旇儻绀嬬紒宀嬫嫹	 *
     * @param gtObj
     * @return
     */
    protected boolean objIsEmpty(Object gtObj) {

        if (gtObj == null) {

            return true;

        }

        if (gtObj.toString().trim().length() == 0) {

            return true;

        }

        return false;
    }

    /**
     * 婵☆偓鎷烽悡锛勶拷閵忊�鐓曠紒鏃戝灣濞堟垹鎷犻柨瀣勾闁哄嫷鍨伴幆渚�触閸喓銆�濞戞挸顦柌婊堝矗椤忓浂娲ｉ柡鍫濐槷缁斿瓨绋夐鍐缂佸瞼灏ㄧ槐婵嬪礆濞嗗繐鐏查柡鍌ゅ幒缁楀宕ラ崼鐔恒�
     *
     * @param
     * @return
     */
    private boolean resquestIsLegal(String challenge, String validate, String seccode) {

        if (objIsEmpty(challenge)) {

            return false;

        }

        if (objIsEmpty(validate)) {

            return false;

        }

        if (objIsEmpty(seccode)) {

            return false;

        }

        return true;
    }


    /**
     * 闁哄牆绉存慨鐔奉潰閿濆懐鍩楅柣銊ュ閸庡繘宕橀崗鍝ョ憮濞达綀娉曢弫銈夋儍閸曨垳宕ｉ悹鍥︾劍閺岀喎顕ｉ敓浠嬪触閹辩焙-server閺夆晜绋栭、鎴炵鐏炵虎鍋уΔ鐘茬焷閻︼拷闁兼儳鍢茶ぐ鍥殽瀹�啰妲堢紓浣规尰閻忥拷	 *
     * @param challenge
     * @param validate
     * @param seccode
     * @return 濡ょ姴鐭侀惁澶岀磼閹惧浜�1閻炴稏鍔庨妵姘殽瀹�啰妲堥柟瀛樺姇婵拷閻炴稏鍔庨妵姘殽瀹�啰妲堝鎯扮簿鐟欙拷	 */
    public int enhencedValidateRequest(String challenge, String validate, String seccode, HashMap<String, String> data) {

        if (!resquestIsLegal(challenge, validate, seccode)) {

            return 0;

        }

        gtlog("request legitimate");

        String userId = data.get("user_id");
        String clientType = data.get("client_type");
        String ipAddress = data.get("ip_address");

        String postUrl = this.apiUrl + this.validateUrl;
        String param = String.format("challenge=%s&validate=%s&seccode=%s&json_format=%s",
                challenge, validate, seccode, this.json_format);

        if (userId != null){
            param = param + "&user_id=" + userId;
        }
        if (clientType != null){
            param = param + "&client_type=" + clientType;
        }
        if (ipAddress != null){
            param = param + "&ip_address=" + ipAddress;
        }

        gtlog("param:" + param);

        String response = "";
        try {

            if (validate.length() <= 0) {

                return 0;

            }

            if (!checkResultByPrivate(challenge, validate)) {

                return 0;

            }

            gtlog("checkResultByPrivate");

            response = readContentFromPost(postUrl, param);

            gtlog("response: " + response);

        } catch (Exception e) {

            e.printStackTrace();

        }

        String return_seccode = "";

        try {

            JSONObject return_map = new JSONObject(response);
            return_seccode = return_map.getString("seccode");
            gtlog("md5: " + md5Encode(return_seccode));

            if (return_seccode.equals(md5Encode(seccode))) {

                return 1;

            } else {

                return 0;

            }

        } catch (JSONException e) {


            gtlog("json load error");
            return 0;

        }

    }

    /**
     * failback濞达綀娉曢弫銈夋儍閸曨垳宕ｉ悹鍥︾劍閺岀喎顕ｉ敓锟�*
     * @param challenge
     * @param validate
     * @param seccode
     * @return 濡ょ姴鐭侀惁澶岀磼閹惧浜�1閻炴稏鍔庨妵姘殽瀹�啰妲堥柟瀛樺姇婵拷閻炴稏鍔庨妵姘殽瀹�啰妲堝鎯扮簿鐟欙拷	 */
    public int failbackValidateRequest(String challenge, String validate, String seccode) {

        gtlog("in failback validate");

        if (!resquestIsLegal(challenge, validate, seccode)) {
            return 0;
        }
        gtlog("request legitimate");

        return 1;
    }

    /**
     * 閺夊牊鎸搁崵鐠琫bug濞ｅ洠鍓濇导鍛存晬瀹�嫭浠橀悷鏇氱缁辨垿宕ラ惀妾坆ugCode
     *
     * @param message
     */
    public void gtlog(String message) {
        if (debugCode) {
            System.out.println("gtlog: " + message);
        }
    }

    protected boolean checkResultByPrivate(String challenge, String validate) {
        String encodeStr = md5Encode(privateKey + "geetest" + challenge);
        return validate.equals(encodeStr);
    }

    /**
     * 闁告瑦鍨块敓绱綞T閻犲洭鏀遍惇浼存晬瀹�啫绠柛娆愮墬濠�洭宕濋垾铏彜閺夆晜鏌ㄥú鏍磼閹惧浜�	 *
     * @param
     * @return 闁哄牆绉存慨鐔煎闯閵娿劎绠查柛銉у仧缁劑寮搁敓锟�* @throws IOException
     */
    public String readContentFromGet(String URL) throws IOException {

          URL getUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();

        connection.setConnectTimeout(2000);// 閻犱礁澧介悿鍡樻交閻愭潙澶嶅☉鎾剁帛濠э拷鎼鹃崨顔筋槯闁挎稑鐗嗗畷鐔告媴瀹ュ繒绐楁慨锝庡亞椤鏁嶉敓锟�connection.setReadTimeout(2000);// 閻犱礁澧介悿鍡樼鎼存繂鐦滈柡鍫ョ細椤曚即宕ｉ弽銊︽闁硅鍠涚粔鎾籍鐠佸湱绀勯柛妤佹磻缂嶅懘鏁嶅顓у殤缂佸甯槐锟�		// 鐎点倛娅ｉ悵娑欑▔鎼淬垺绠涢柛鏂猴拷濞呮帡鎯冮崟顔剧闁规亽鍎荤槐婵嬬嵁閼稿灚寮撻柛娆愬灴閿熶粙寮悧鍫濈ウ
        connection.connect();

        if (connection.getResponseCode() == 200) {
            // 闁告瑦鍨块敓浠嬪极閻楀牆绁﹂柛鎺斿濠�洭宕濋垾铏彜妤犵偞婀规繛鍥偨閳湯ader閻犲洩顕цぐ鍥ㄦ交閺傛寧绀�柣銊ュ閺嗙喖骞戦敓锟�
            StringBuffer sBuffer = new StringBuffer();

            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1;) {
                sBuffer.append(new String(buf, 0, n, "UTF-8"));
            }
            inStream.close();
            connection.disconnect();// 闁哄偆鍘肩槐鎴炴交閻愭潙澶�
            return sBuffer.toString();
        }
        else {

            return "fail";
        }
    }

    /**
     * 闁告瑦鍨块敓绲嘜ST閻犲洭鏀遍惇浼存晬瀹�啫绠柛娆愮墬濠�洭宕濋垾铏彜閺夆晜鏌ㄥú鏍磼閹惧浜�	 *
     * @param
     * @return 闁哄牆绉存慨鐔煎闯閵娿劎绠查柛銉у仧缁劑寮搁敓锟�* @throws IOException
     */
    private String readContentFromPost(String URL, String data) throws IOException {

        gtlog(data);
        URL postUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();

        connection.setConnectTimeout(2000);// 閻犱礁澧介悿鍡樻交閻愭潙澶嶅☉鎾剁帛濠э拷鎼鹃崨顔筋槯闁挎稑鐗嗗畷鐔告媴瀹ュ繒绐楁慨锝庡亞椤鏁嶉敓锟�connection.setReadTimeout(2000);// 閻犱礁澧介悿鍡樼鎼存繂鐦滈柡鍫ョ細椤曚即宕ｉ弽銊︽闁硅鍠涚粔鎾籍鐠佸湱绀勯柛妤佹磻缂嶅懘鏁嶅顓у殤缂佸甯槐锟�	connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // 鐎点倛娅ｉ悵娑欑▔鎼淬垺绠涢柛鏂猴拷濞呮帡鎯冮崟顔剧闁规亽鍎荤槐婵嬬嵁閼稿灚寮撻柛娆愬灴閿熶粙寮悧鍫濈ウ
        connection.connect();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        if (connection.getResponseCode() == 200) {
            // 闁告瑦鍨块敓浠嬪极閻楀牆绁﹂柛鎺斿濠�洭宕濋垾铏彜妤犵偞婀规繛鍥偨閳湯ader閻犲洩顕цぐ鍥ㄦ交閺傛寧绀�柣銊ュ閺嗙喖骞戦敓锟�
            StringBuffer sBuffer = new StringBuffer();

            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1;) {
                sBuffer.append(new String(buf, 0, n, "UTF-8"));
            }
            inStream.close();
            connection.disconnect();// 闁哄偆鍘肩槐鎴炴交閻愭潙澶�
            return sBuffer.toString();
        }
        else {

            return "fail";
        }
    }

    /**
     * md5 闁告梻濮撮惁锟� *
     * @time 2014妤犵儑鎷烽柡鍫嫹0闁哄喛鎷峰☉鎾愁儏瀹曪拷:30:01
     * @param plainText
     * @return
     */
    private String md5Encode(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }


}
