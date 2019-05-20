package org.launchcode.mybrary.models.forms;


public class SearchForm {

    private String titleTerm;

    private String authorTerm;


    public String getTitleTerm() { return titleTerm; }

    public void setTitleTerm(String titleTerm) { this.titleTerm = titleTerm; }

    public String getAuthorTerm() {
        return authorTerm;
    }

    public void setAuthorTerm(String authorTerm) {
        this.authorTerm = authorTerm;
    }


}
