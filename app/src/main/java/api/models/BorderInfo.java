package api.models;

public class BorderInfo {
    private String keyWord;
    private String responseRo;
    private String responseEng;
    private String responseRu;

    public String getKeyWord() {
        return keyWord;
    }
    public String getResponseRo() {
        return responseRo;
    }
    public String getResponseEng() {
        return responseEng;
    }
    public String getResponseRu() {
        return responseRu;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public void setResponseRo(String responseRo) {
        this.responseRo = responseRo;
    }
    public void setResponseEng(String responseEng) {
        this.responseEng = responseEng;
    }
    public void setResponseRu(String responseRu) {
        this.responseRu = responseRu;
    }
}
