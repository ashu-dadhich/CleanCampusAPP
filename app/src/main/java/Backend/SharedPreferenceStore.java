package Backend;

import android.content.Context;
import android.content.SharedPreferences;

/**Class for Getting the Shared Preferences**/
public class SharedPreferenceStore {
    public static SharedPreferences mPrefsStore;
    public static SharedPreferences.Editor mEditor;

    public SharedPreferenceStore() {

    }

    public void getSharedPreferences(Context context) {

        mPrefsStore = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        mEditor = mPrefsStore.edit();
    }

}
