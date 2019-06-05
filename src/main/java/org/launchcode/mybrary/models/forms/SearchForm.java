package org.launchcode.mybrary.models.forms;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchForm {


    @Size(min = 1, max = 100, message = "Title must be between 1-100 characters")
    private String titleTerm;


    @Size(min = 1, max = 100, message = "Author must be between 1-100 characters")
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
