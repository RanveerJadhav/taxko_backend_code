package com.Tasko.Registration.dto;

import java.util.List;

public class DeleteFilesRequest
{
    private List<Long> fileIds;

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public DeleteFilesRequest(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public DeleteFilesRequest() {
    }

    // Getter and Setter
}
