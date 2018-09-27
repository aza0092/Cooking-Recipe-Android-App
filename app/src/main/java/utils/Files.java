package utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

public class Files {
    /**
     * @param contentURI of the file
     * @return the path of this file in disk.
     */
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        return RealPathUtil.getRealPathFromURI_API19(context, contentURI);
    }
}
