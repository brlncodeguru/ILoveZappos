package com.example.lakshminarayanabr.ilovezappos;

import java.util.List;

/**
 * Created by lakshminarayanabr on 2/5/17.
 */

public class ProductData {
    String originalTerm;
    String currentResultCount;
    String totalResultCount;
    String term;
    List<Product> results;
    String statusCode;

    public String getOrginalTerm() {
        return originalTerm;
    }

    public void setOrginalTerm(String orginalTerm) {
        this.originalTerm = orginalTerm;
    }

    public String getCurrentResultCount() {
        return currentResultCount;
    }

    public void setCurrentResultCount(String currentResultCount) {
        this.currentResultCount = currentResultCount;
    }

    public String getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(String totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "orginalTerm='" + originalTerm + '\'' +
                ", currentResultCount='" + currentResultCount + '\'' +
                ", totalResultCount='" + totalResultCount + '\'' +
                ", term='" + term + '\'' +
                ", results=" + results +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
