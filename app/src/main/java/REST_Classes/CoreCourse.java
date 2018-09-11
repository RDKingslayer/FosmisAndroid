package REST_Classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoreCourse {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("regStat")
    @Expose
    private String regStat;
    @SerializedName("degreeStat")
    @Expose
    private String degreeStat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegStat() {
        return regStat;
    }

    public void setRegStat(String regStat) {
        this.regStat = regStat;
    }

    public String getDegreeStat() {
        return degreeStat;
    }

    public void setDegreeStat(String degreeStat) {
        this.degreeStat = degreeStat;
    }

}

class CoreCourses {

    @SerializedName("coreCourses")
    @Expose
    private List<CoreCourse> coreCourses = null;

    public List<CoreCourse> getCoreCourses() {
        return coreCourses;
    }

    public void setCoreCourses(List<CoreCourse> coreCourses) {
        this.coreCourses = coreCourses;
    }

}