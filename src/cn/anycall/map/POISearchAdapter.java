package cn.anycall.map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.baidu.navisdk.util.common.CoordinateTransformUtil;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import com.htx.conn.Const;
import com.htx.pub.LoadingDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class POISearchAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<POIResultBean> searchList;

	private String slat="0";
	private String slng="0";
	private String addr="";
	private String city="0";
	private String loa;
	private BMapApiDemoApp app;
	MKSearch mMKSearch;

	private HashMap<String, Bitmap> bitmap_Map = new HashMap<String, Bitmap>();

	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	
	public POISearchAdapter(Context c, ArrayList<POIResultBean> _searchList,
			String slat, String slng, String addr, String query, BMapApiDemoApp app,
			String city, String loa) {
		context = c;
		searchList = _searchList;
		this.slat = slat;
		this.slng = slng;  
		this.addr = addr;
		this.city = city;
		this.loa = loa;
		this.app = app;
		// 定位初始化
		mLocClient = new LocationClient(context);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setPriority(LocationClientOption.GpsFirst);
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		app = (BMapApiDemoApp) ((Activity) context).getApplication();
		app.mLocClient = mLocClient;
		mMKSearch = new MKSearch();
		mMKSearch.init(app.mBMapMan, new MKSearchListener() {

			public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			}

			public void onGetAddrResult(MKAddrInfo result, int iError) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult result,
					int iError) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult result,
					int iError) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult result,
					int iError) {
			}

			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			}

			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			}

			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			}

			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
			}
		});
	}

	public void putBitmap(String id, Bitmap bitmap) {
		bitmap_Map.put(id, bitmap);
	}

	public int getCount() {
		int size = 0;
		if (searchList != null) {
			size = searchList.size();
		}
		return size;
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}
  
	public View getView(final int position, View convertView, ViewGroup parent) {

		// inflater对象可以把xml转换为view
		LayoutInflater inflater = LayoutInflater.from(context);
		View template = inflater.inflate(R.layout.poisearch_view, null);
		if (searchList != null) {
			TextView poiName = (TextView) template.findViewById(R.id.poiname);
			TextView poiTag = (TextView) template.findViewById(R.id.poitag);
			TextView poiPrice = (TextView) template.findViewById(R.id.poiprice);
			TextView poiDistance = (TextView) template
					.findViewById(R.id.poidistance);
			RatingBar poiRating = (RatingBar) template
					.findViewById(R.id.poirating);
			LinearLayout bg = (LinearLayout) template.findViewById(R.id.bg);
			Button bupoiMap = (Button) template.findViewById(R.id.bupoimap);
			Button poiMap = (Button) template.findViewById(R.id.poimap);
			Button busmap = (Button) template.findViewById(R.id.busmap);
			Button poiPhonenum = (Button) template.findViewById(R.id.context);
			Button infomap = (Button) template.findViewById(R.id.infomap);

			String string = "";
			switch (position) {
			case 0:
				string = "A";
				break;
			case 1:
				string = "B";
				break;
			case 2:
				string = "C";
				break;
			case 3:
				string = "D";
				break;
			case 4:
				string = "E";
				break;
			case 5:
				string = "F";
				break;
			case 6:
				string = "G";
				break;
			case 7:
				string = "H";
				break;
			case 8:
				string = "I";
				break;
			case 9:
				string = "J";
				break;
			case 10:
				string = "K";
				break;
			case 11:
				string = "L";
				break;
			case 12:
				string = "M";
				break;
			case 13:
				string = "N";
				break;
			case 14:
				string = "O";
				break;
			case 15:
				string = "P";
				break;
			case 16:
				string = "Q";
				break;
			case 17:
				string = "R";
				break;
			case 18:
				string = "S";
				break;
			case 19:
				string = "T";
				break;
			case 20:
				string = "U";
				break;
			case 21:
				string = "V";
				break;
			case 22:
				string = "W";
				break;
			case 23:
				string = "X";
				break;
			case 24:
				string = "Y";
				break;
			case 25:
				string = "Z";
				break;
			default:
				break;
			}
			String type = searchList.get(position).getDetail_info().getType();
			String price = searchList.get(position).getDetail_info().getPrice();
			if (type.equals("0")) {
				if (price.contains("直返")) {
					bg.setBackgroundResource(R.drawable.daww);
					poiName.setText(string + ". "
							+ searchList.get(position).getName() + "(合约商家)");
					poiName.setTextColor(0xFF6ab527);
					poiPrice.setText(searchList.get(position).getDetail_info()
							.getPrice());
				} else {
					// poiPrice.setText("人均:￥"
					// + searchList.get(position).getDetail_info()
					// .getPrice());
					poiName.setText(string + ". "
							+ searchList.get(position).getName());
				}
			} else {
				poiName.setText(string + ". "
						+ searchList.get(position).getName());
			}
			poiDistance.setText(" "
					+ searchList.get(position).getDetail_info().getDistance()
					+ "米");

			if (searchList.get(position).getAddress() != null
					&& searchList.get(position).getAddress() != "") {
				poiTag.setText(searchList.get(position).getAddress());
			}
			if (searchList.get(position).getDetail_info().getOverall_rating() != null
					&& searchList.get(position).getDetail_info()
							.getOverall_rating() != "") {
				if (Float.parseFloat(searchList.get(position).getDetail_info()
						.getOverall_rating()) > 1) {
					poiRating.setRating(Float
							.parseFloat(searchList.get(position)
									.getDetail_info().getOverall_rating()));
				} else {
					poiRating.setRating(Float.parseFloat("3.0"));
				}
			} else {
				poiRating.setRating(Float.parseFloat("3.0"));
			}
			if (position <= 25 || position >= 0) {
				bupoiMap.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						
//						Bundle bundle = new Bundle();
						Intent it = new Intent(context,WalkingRouteDemoActivity.class);
//						it.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
//						bundle.putString("type", "1");
						it.putExtra("index", position + 1 + "");
						it.putExtra("lng", searchList.get(position).getLocation().getLng()+"");
						it.putExtra("lat", searchList.get(position)
								.getLocation().getLat()+"");
						it.putExtra("destination", searchList.get(position)
								.getName());
//						it.putExtras(bundle);
						if (context.getPackageManager().resolveActivity(it, 0) ==null ) {
							Intent intent2 = new Intent(context,DownloadActivity.class);
							context.startActivity(intent2);
					
						}
						else {
							context.startActivity(it);
						}
					}
				});
			}

			if (position <= 25 || position >= 0) {
				busmap.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
//						Bundle bundle = new Bundle();
						Intent intent = new Intent(context,BusPlaneSelectionActivity.class);
//						intent.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
//						bundle.putString("type", "2");
						intent.putExtra("lng", searchList.get(position).getLocation().getLng()+"");
						intent.putExtra("lat", searchList.get(position)
								.getLocation().getLat()+"");
						intent.putExtra("destination", searchList.get(position)
								.getName());
						intent.putExtra("city", city);
						intent.putExtra("loa", loa);
//						intent.putExtras(bundle);
						if (context.getPackageManager().resolveActivity(intent, 0) ==null ) {
							Intent intent2 = new Intent(context,DownloadActivity.class);

								context.startActivity(intent2);
						}
						else {
							context.startActivity(intent);
						}
					}
				});
			}

			if (position <= 25 || position >= 0) {
				poiMap.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					
//						Bundle bundle  =new Bundle();
//						Intent intent = new Intent();
//						intent.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
//						bundle.putString("type", "3");
//						bundle.putString("lng", searchList.get(position).getLocation().getLng()+"");
//						bundle.putString("lat", searchList.get(position).getLocation().getLat()+"");
//						bundle.putString("loc_lng", locData.longitude+"");
//						bundle.putString("loc_lat", locData.latitude+"");
//						bundle.putString("destination", searchList.get(position).getAddress());
//						intent.putExtras(bundle);
						
//						if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//							File[] files = 
//						}
//						
//						if (context.getPackageManager().resolveActivity(intent, 0) ==null ) {
//							Intent intent2 = new Intent(context,DownloadActivity.class);
//								context.startActivity(intent2);
//						}
//						else {
//							context.startActivity(intent);
//						}
						
						
						com.baidu.nplatform.comapi.basestruct.GeoPoint geoPoint = CoordinateTransformUtil.transferBD09ToGCJ02(searchList.get(position).getLocation().getLng(),searchList.get(position).getLocation().getLat());
						com.baidu.nplatform.comapi.basestruct.GeoPoint geoPoint2 = CoordinateTransformUtil.transferBD09ToGCJ02(locData.longitude,locData.latitude);
						double sslat = (double)(geoPoint2.getLatitudeE6())/100000;
						double sslon = (double)(geoPoint2.getLongitudeE6())/100000;

						double eelat = (double)(geoPoint.getLatitudeE6())/100000;
						double eelon = (double)(geoPoint.getLongitudeE6())/100000;
						
						BaiduNaviManager.getInstance().launchNavigator((Activity)context, 
								sslat, sslon,"", 
								eelat, eelon,searchList.get(position).getAddress(),
								NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, 		 //算路方式
								true, 									   		 //真实导航
								BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
								new OnStartNavigationListener() {				 //跳转监听
									
									
									public void onJumpToNavigator(Bundle configParams) {
										Intent intent = new Intent(context, BNavigatorActivity.class);
										intent.putExtras(configParams);
										context.startActivity(intent);
									}
									
									public void onJumpToDownloader() {
									}
								});
						
					}
				});
			}

			if (searchList.get(position).getTelephone() != null
					&& searchList.get(position).getTelephone() != "" && !searchList.get(position).getTelephone().equals("")) {
				if (position <= 9 || position >= 0) {
					poiPhonenum.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							Uri uri = Uri.parse("tel:"
									+ searchList.get(position).getTelephone());
							Intent intent = new Intent(Intent.ACTION_DIAL, uri);
							context.startActivity(intent);
						}
					});
				}

			} else {
				poiPhonenum.setVisibility(View.INVISIBLE);
			}

			if (position <= 25 || position >= 0) {

				final String murl = searchList.get(position).getDetail_info()
						.getDetail_url();

				if (murl.equals("0")) {
					infomap.setVisibility(View.GONE);
				}

				infomap.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (searchList.get(position).getUid().length() > 6) {
							if (searchList.get(position).getDetail_info()
									.getDetail_url() != null) {
								Intent intent = new Intent();
								intent.putExtra("uid", searchList.get(position)
										.getUid());
								intent.putExtra("index", position + 1 + "");
								intent.putExtra(
										"lat",
										Double.toString(searchList.get(position)
												.getLocation().getLat()));
								intent.putExtra(
										"lng",
										Double.toString(searchList.get(position)
												.getLocation().getLng()));
								intent.putExtra("destination", searchList.get(position)
										.getName());
								intent.putExtra("city", city);
								intent.putExtra("loa", loa);
								intent.putExtra("loc_lat", locData.latitude);
								intent.putExtra("loc_lng", locData.longitude);
								intent.setClass(context, InfoViews.class);
								context.startActivity(intent);
							}
						} else {
							LoadingDialog dialog = new LoadingDialog(context);
							dialog.show();

							Intent intent = new Intent(context,
									Info_heyue_Views.class);
							intent.putExtra("StoreId", searchList.get(position)
									.getUid());
							intent.putExtra("title", searchList.get(position)
									.getName());
							intent.putExtra("address", searchList.get(position)
									.getAddress());
							intent.putExtra("brief", searchList.get(position)
									.getDetail_info().getTag());
							intent.putExtra("isFocus", "0");
							intent.putExtra("logo", searchList.get(position)
									.getDetail_info().getImage_num());
							
							intent.putExtra("index", position + 1 + "");
							intent.putExtra(
									"lat",
									Double.toString(searchList.get(position)
											.getLocation().getLat()));
							intent.putExtra(
									"lng",
									Double.toString(searchList.get(position)
											.getLocation().getLng()));
							intent.putExtra("destination", searchList.get(position)
									.getDetail_info().getTag());
							intent.putExtra("city", city);
							intent.putExtra("loa", loa);
							intent.putExtra("loc_lat", locData.latitude);
							intent.putExtra("loc_lng", locData.longitude);
							intent.putExtra("phone", searchList.get(position).getTelephone());
							intent.putExtra("price",searchList.get(position).getDetail_info().getPrice());
							intent.putExtra("Discount", searchList.get(position).getDetail_info().getDiscount_num());

							context.startActivity(intent);

							dialog.dismiss();
						}
					}
				});
			}
		}

		return template;
	}



	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			slat = String.valueOf(location.getLatitude());
			slng = String.valueOf(location.getLongitude());
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}


}