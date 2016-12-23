# android-navi-quick-start
综合使用搜索和导航实现导航到周边餐馆功能示例
## 前述 ##
- [高德官网申请Key](http://lbs.amap.com/dev/#/).
- 阅读[地图参考手册](http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html).
- 阅读[导航参考手册](http://a.amap.com/lbs/static/unzip/Android_Navi_Doc/index.html).
- [科大讯飞开放平台申请Appid](http://www.xfyun.cn/services/online_tts).
- 工程基于Android 导航 SDK、科大讯飞在线语音合成SDK实现


## 扫一扫安装##
![Screenshot]( https://github.com/amap-demo/android-navi-quick-start/blob/master/resource/download.png?raw=true )

## 使用方法##
###1:配置搭建AndroidSDK工程###
- [Android Studio工程搭建方法](http://lbs.amap.com/api/android-sdk/guide/creat-project/android-studio-creat-project/#add-jars).
- [通过maven库引入SDK方法](http://lbsbbs.amap.com/forum.php?mod=viewthread&tid=18786).

## 结果展示##

- 搜索周边餐饮服务

 ![Screenshot](https://github.com/amap-demo/android-navi-quick-start/blob/master/resource/screenshot_search.png?raw=true)

- 导航到选定目的地

 ![Screenshot](https://github.com/amap-demo/android-navi-quick-start/blob/master/resource/screenshot_navi.png?raw=true)
 
## 核心类/接口 ##
| 类    | 接口  | 说明   | 版本  |
| -----|:-----:|:-----:|:-----:|
|PoiSearch.Query|Query(String query,String ctgr) |Query构造函数 |V2.1.0|
| PoiSearch.SearchBound|SearchBound(LatLonPoint center,int radiusInMeters)|根据给定的参数来构造PoiSearch.SearchBound 新对象|V2.1.0|
|PoiSearch|searchPOIAsyn()|查询POI异步接口|V2.1.0|
|AMapNavi|startNavi(int naviType)|开始导航。实时导航GPS未开启时，会自动打开GPS定位功能。模拟导航则不需要使用定位功能||

## 核心难点 ##
```java
    /**
     * 自定义marker点击弹窗内容
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        int index = mPoiOverlay.getPoiIndex(marker);
        float distance = mPoiOverlay.getDistance(index);
        String showDistance = Utils.getFriendlyDistance((int) distance);
        snippet.setText("距当前位置" + showDistance);
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        // 调起导航
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAMapNavi(marker);
            }
        });
        return view;
    }
    
     /**
     * 驾车路径规划计算,计算单条路径
     */
    private void calculateDriveRoute(NaviLatLng start, NaviLatLng end) {
        int strategyFlag = 0;
        List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
        /**
         * 途径点坐标集合
         */
        List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
        /**
         * 终点坐标集合［建议就一个终点］
         */
        List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
        try {
            strategyFlag = mAMapNavi.strategyConvert(true, false, false, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startList.add(start);
        endList.add(end);
        mAMapNavi.calculateDriveRoute(startList, endList, wayList, strategyFlag);
    }
```
