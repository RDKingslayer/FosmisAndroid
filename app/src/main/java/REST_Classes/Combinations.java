
package REST_Classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Combinations {

    @SerializedName("combinations")
    @Expose
    private List<String> combinations = null;
    @SerializedName("combination_ids")
    @Expose
    private List<String> combinationIds = null;

    public List<String> getCombinations() {
        return combinations;
    }

    public void setCombinations(List<String> combinations) {
        this.combinations = combinations;
    }

    public List<String> getCombinationIds() {
        return combinationIds;
    }

    public void setCombinationIds(List<String> combinationIds) {
        this.combinationIds = combinationIds;
    }

}
