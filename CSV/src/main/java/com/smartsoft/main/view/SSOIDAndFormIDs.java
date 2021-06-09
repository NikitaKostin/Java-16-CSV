package com.smartsoft.main.view;

import java.util.ArrayList;

public class SSOIDAndFormIDs {

    private String ssoId;
    private ArrayList<String> formIds;

    public SSOIDAndFormIDs(String ssoId, ArrayList<String> formIds) {
        this.ssoId = ssoId;
        this.formIds = formIds;
    }

    public String formIdsToString() {
        var sb = new StringBuilder();
        formIds.forEach(id -> {
            sb.append(id);
            sb.append("; ");
        });
        return sb.toString();
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public ArrayList<String> getFormIds() {
        return formIds;
    }

    public void setFormIds(ArrayList<String> formIds) {
        this.formIds = formIds;
    }
}
