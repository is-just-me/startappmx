package com.jroure.startappmx;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import java.util.Iterator;
import android.net.Uri;
import android.os.Parcelable;

/**
 * This class provides access to vibration on the device.
 */
public class startAppmx extends CordovaPlugin {
	public static final String EXTRA_DECODE_MODE	= "decode_mode";	// (byte)
	public static final String EXTRA_VIDEO_LIST		= "video_list";
	public static final String EXTRA_SUBTITLES		= "subs";
	public static final String EXTRA_SUBTITLES_ENABLE = "subs.enable";
	public static final String EXTRA_TITLE			= "title";
	public static final String EXTRA_POSITION		= "position";
	public static final String EXTRA_RETURN_RESULT	= "return_result";
	public static final String EXTRA_HEADERS		= "headers";
    /**
     * Constructor.
     */
    public startAppmx() { }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True when the action was valid, false otherwise.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            this.start(args, callbackContext);
        }
        if (action.equals("mxplay")) {
            this.mxplay(args, callbackContext);
        }
		else if(action.equals("check")) {
			this.check(args.getString(0), callbackContext);
		} else if(action.equals("startwaze")) {
			this.startwaze(args.getString(0), callbackContext);
		}
		
		return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------
    /**
     * startApp
     */
    public void start(JSONArray args, CallbackContext callback) {
		
		String com_name = null;
		String activity = null;
		Intent LaunchIntent;
		
		try {
			if (args.get(0) instanceof JSONArray) {
				com_name = args.getJSONArray(0).getString(0);
				activity = args.getJSONArray(0).getString(1);                     
			}
			else {
				com_name = args.getString(0);
			}
		
			/**
			 * call activity
			 */
			if(activity != null) {
				if(com_name.equals("action")) {
					// sample: android.intent.action.VIEW
					if(activity.indexOf(".") > 0) {
						LaunchIntent = new Intent(activity);
					}
					else {
						LaunchIntent = new Intent("android.intent.action." + activity);
					}
				}
				else {
					LaunchIntent = new Intent();
					LaunchIntent.setComponent(new ComponentName(com_name, activity));
				}
			}
			else {
				LaunchIntent = this.cordova.getActivity().getPackageManager().getLaunchIntentForPackage(com_name);
			}
			
			/**
			 * put arguments
			 */
			if(args.length() > 1) {
				JSONArray params = args.getJSONArray(1);
				JSONObject key_value;
				String key;
				String value;

				for(int i = 0; i < params.length(); i++) {
					if (params.get(i) instanceof JSONObject) {
						Iterator<String> iter = params.getJSONObject(i).keys();
						
						 while (iter.hasNext()) {
							key = iter.next();
							try {
								value = params.getJSONObject(i).getString(key);
								
								LaunchIntent.putExtra(key, value);
							} catch (JSONException e) {
								callback.error("json params: " + e.toString());
							}
						}
					}
					else {
						LaunchIntent.setData(Uri.parse(params.getString(i)));
					}
				}
			}
			
			this.cordova.getActivity().startActivity(LaunchIntent);
			callback.success();
			
		} catch (JSONException e) {
			callback.error("json: " + e.toString());
		} catch (Exception e) {
			callback.error("intent: " + e.toString());
        }
    }
    public void startwaze(String waze_url, CallbackContext callback) {
    	try
		{
		   String url = waze_url;
		   Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
		   this.cordova.getActivity().startActivity(intent);
		   callback.success();
		}
		catch ( ActivityNotFoundException ex  )
		{
		  Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
		  this.cordova.getActivity().startActivity(intent);
		  callback.error("no esta instalado waze vamos al market");
		}
    }
    public void mxplay(JSONArray args, CallbackContext callback) {
		
		String com_name = null;
		String activity = null;
		Intent LaunchIntent;
		
		try {
			if (args.get(0) instanceof JSONArray) {
				com_name = args.getJSONArray(0).getString(0);
				activity = args.getJSONArray(0).getString(1);                     
			}
			else {
				com_name = args.getString(0);
			}
		
			/**
			 * call activity
			 */
			if(activity != null) {
				if(com_name.equals("action")) {
					// sample: android.intent.action.VIEW
					if(activity.indexOf(".") > 0) {
						LaunchIntent = new Intent(activity);
					}
					else {
						LaunchIntent = new Intent("android.intent.action." + activity);
					}
				}
				else {
					LaunchIntent = new Intent();
					LaunchIntent.setComponent(new ComponentName(com_name, activity));
				}
			}
			else {
				LaunchIntent = this.cordova.getActivity().getPackageManager().getLaunchIntentForPackage(com_name);
			}
			
			/**
			 * put arguments
			 */
			if(args.length() > 1) {
				JSONArray params = args.getJSONArray(1);
				JSONObject key_value;
				String key;
				String value;
				Parcelable sub;
				Parcelable sub_enable;
				String title;

				for(int i = 0; i < params.length(); i++) {
					if (params.get(i) instanceof JSONObject) {
						Iterator<String> iter = params.getJSONObject(i).keys();
						
						 while (iter.hasNext()) {
							key = iter.next();
							try {
								if(key == "sub"){
									LaunchIntent.putExtra(EXTRA_SUBTITLES, new Parcelable[] { Uri.parse(params.getJSONObject(i).getString(key)) });
									LaunchIntent.putExtra(EXTRA_SUBTITLES_ENABLE, new Parcelable[] { Uri.parse(params.getJSONObject(i).getString(key)) });
								}
								if(key == "title"){
									LaunchIntent.putExtra(EXTRA_TITLE, params.getJSONObject(i).getString(key));
								}
							} catch (JSONException e) {
								callback.error("json params: " + e.toString());
							}
						}
					}
					else {
						LaunchIntent.setDataAndType(Uri.parse(params.getString(i)), "application/*");
					}
				}
			}
			
			this.cordova.getActivity().startActivity(LaunchIntent);
			callback.success();
			
		} catch (JSONException e) {
			callback.error("json: " + e.toString());
		} catch (Exception e) {
			callback.error("intent: " + e.toString());
        }
    }
    /**
     * checkApp
     */	 
	public void check(String component, CallbackContext callback) {
		PackageManager pm = this.cordova.getActivity().getApplicationContext().getPackageManager();
		try {
			pm.getPackageInfo(component, PackageManager.GET_ACTIVITIES);
			callback.success();
		} catch (Exception e) {
			callback.error(e.toString());
		}
	}
}
