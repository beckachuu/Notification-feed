package com.beckachu.androidfeed.data.local;

import android.content.Context;
import android.net.Uri;

import java.util.List;

public class ExternalStorageManager {
    private Context context;

    public ExternalStorageManager(Context context) {
        this.context = context;
    }

    public Uri saveAttachment(Uri uri) {
        // Save the attachment to external storage
        return null;
    }

    public List<Uri> getAttachments() {
        // Get all attachments from external storage
        return null;
    }

    public void deleteAttachment(int id) {
        // Delete the attachment from external storage
    }
}
