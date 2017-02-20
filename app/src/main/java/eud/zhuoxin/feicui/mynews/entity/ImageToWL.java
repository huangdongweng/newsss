package eud.zhuoxin.feicui.mynews.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ImageToWL {
    private boolean error;
    private List<ImageInfo> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ImageInfo> getResults() {
        return results;
    }

    public void setResults(List<ImageInfo> results) {
        this.results = results;
    }
}
