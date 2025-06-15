package com.logicerror.e_learning.services.video.models;

public record RangeRequest(long start, long end, long contentLength) {
    public boolean isValid() {
        return start < contentLength && end < contentLength && start <= end;
    }

    public long getLength() {
        return end - start + 1;
    }

    public String getContentRangeHeader(long totalLength) {
        return "bytes " + start + "-" + end + "/" + totalLength;
    }

}
