package utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

public class ActivityTransition {
    /**
     * @param context        the source activity
     * @param targetActivity the activity to be transitioned to
     * @param sharedView     between the two activities
     * @param transitionName name of the shared element transition
     */
    public static void startActivityWithSharedElement(Context context,
                                                      Class<?> targetActivity,
                                                      View sharedView,
                                                      String transitionName) {
        Intent intent = new Intent(context, targetActivity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            context.startActivity(intent);
        else {
            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) context, sharedView, transitionName);
            context.startActivity(intent, transitionActivityOptions.toBundle());
        }
    }

    public static void startActivityWithSharedElement(Context context,
                                                      Intent intent,
                                                      View sharedView,
                                                      String transitionName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            context.startActivity(intent);
        else {
            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) context, sharedView, transitionName);
            context.startActivity(intent, transitionActivityOptions.toBundle());
        }
    }

    public static void startActivityForResultWithSharedElement(Context context,
                                                               Intent intent,
                                                               View sharedView,
                                                               String transitionName,
                                                               int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else {
            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) context, sharedView, transitionName);
            ((Activity) context).startActivityForResult(
                    intent, requestCode, transitionActivityOptions.toBundle());
        }
    }

    public static void startActivityForResultWithMultipleSharedElements(
            Context context, Intent intent, int requestCode, Pair<View, String>... sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else {
            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedElements);
            ((Activity) context).startActivityForResult(
                    intent, requestCode, transitionActivityOptions.toBundle());
        }
    }

    public static void startActivityWithMultipleSharedElements(
            Context context, Intent intent, Pair<View, String>... sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            context.startActivity(intent);
        else {
            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedElements);
            context.startActivity(intent, transitionActivityOptions.toBundle());
        }
    }
}
