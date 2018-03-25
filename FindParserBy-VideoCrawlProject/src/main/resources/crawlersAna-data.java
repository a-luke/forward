defSrcDR1("56","我乐","56.com",()->Analy56VideoPC::new,4).doIt((src)->{
        defSearcherBrowserFKX("56",(kd)->new Search56VideoPC(kd,new Search56VideoOpts()),(b)->{b.setTsleep(2000L).tagPcVideo();});
        defSearcherMobileFKX("56-IOS",(kd)->new Search56tvVideoMobile(kd,MobilePlatform56.IOS),(b)->{b.setBad();});
        defSearcherMobileFKX("56-ANDROID",(kd)->new Search56tvVideoMobile(kd,MobilePlatform56.ANDROID),(b)->{b.setRedundant();}); // FIXME: broken for 56.com urls; case is not preserved in 56 urls
        defAppX("56-ios","56","IOS端$移动端#56视频",(b)->{b.setSearchersV("56").addAliasA("56IOS端","56网IOS端","我乐IOS端","56移动端","56网移动端");}); // same as 56's main results; FIXME: actually contains hot links
//				defAppX("56-android", "56", "安卓端$移动端#56视频", (b)->{b.setPlatStr("ios").setSearchersV("56-ANDROID");}); // can't have two 移动端 anyway
        });
        defSrcD("ku6","酷六","ku6.com;juchang.com").doIt((src)->{
final AnalyzerInfo anaOld=defAnaR1X("ku6",()->AnalyKu6VideoPC::new,null); // no longer usable as of Jan 12 2018
final AnalyzerInfo anaNew=defAnaR1X("ku6-new",()->AnalyKu6VideoPC2::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.contains("/video/detail?"))ctx.addPass(anaNew);
        else ctx.addPass(anaOld);
        },4);
        defSearcherBrowserSKX("ku6",()->SearchKu6VideoPC::new,(b)->{b.setBad();});
        defSearcherBrowserSKX("ku6-new",()->SearchKu6VideoPC2::new,(b)->{b.setForcedKeywordsP(()->SearchKu6VideoPC2.catMap.keySet(),200).tagPcVideo();});
        defSearcherPcSKX("ku6-client",()->Ku6ClientParser::new,(b)->{b.setBad();}); // for ku6's pc client; all the addresses are also accessible from the web site, but only those having a specific form have links in the client.
        defSearcherMobileSKX("ku6-ios",()->SearchKu6VideoMobile::new,(b)->{b.setBad();});
        defAppX("ku6-ios","ku6","移动端#酷6视频",(b)->{b.setSearchersV("ku6-ios").addAliasA("酷6IOS端","酷6移动端");}); // includes search result information for user-friendliness
        });
        defSrcD("letv","乐视","www.letv.com;sports.letv.com;client.letv.com;hz.letv.com;api.letv.com;www.le.com;sports.le.com;www.lesports.com").doIt((src)->{ // excludes cloud.letv.com; reduced limit because vid-letv-mobile:// gives errors
// NOTE: Occasionally there can be very small changes e.g. 灵书妙探第4季16 灵书妙探第四季第16集
// NOTE: The video can be m3u8 at low bitrates!
final AnalyzerInfo anaWeb=defAnaR1X("letv",()->AnalyLetvVideoPC::new,(b)->{b.setCheckTitleChanges(true);});
final AnalyzerInfo ana61=defAnaR1X("letv-61",()->AnaylyV61VideoPC::new,null); // FIXME: this is also used by other sites hosting 61 content; the definition should perhaps be shared.
final AnalyzerInfo anaMobile=defAnaR1X("letv-mobile",()->AnalyLetvVideoMobile::new,(b)->{b.setPlatStr("mobile");});
final AnalyzerInfo anaOtt=defAnaR1X("letv-ott",()->AnalyLetvVideoOTT::new,(b)->{b.setPlatStr("ott");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-letv-ott://"))ctx.addPass(anaOtt);
        // vid-letv stuff must be resolved with the web resolver; they used to be numbered differently, but seem to have converged for id>=20000000
        else if(url.startsWith("vid-letv-mobile://"))ctx.addPass(anaMobile);
        else{
final String host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"61.hz.letv.com"))ctx.addPass(ana61);
        else ctx.addPass(anaWeb);
        }
        },4);
        defSearcherBrowserFKX("letv",(kd)->new SearchLetvVideoPC(kd,new SearchLetvVideoOpts()),(b)->{b.tagPcVideo();}); // NOTE: can cold-link to other sites
        defSearcherPcSKX("letv-client",()->LetvClientParser::new,(b)->{b.setBad();});
        defSearcherMobileSKX("letv-ANDROID",()->SearchLetvVideoMobile::new,(b)->{b.setBad();});
        defSearcherMobileSKX("letv-android2",()->SearchLetvVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defSearcherOttSKX("letv-ott",()->SearchLetvVideoOTT::new,null); // FIXME: can't find the TV box giving its results as of Jun 22 2017; the letv TV does not give the same search results
        defSearcherOttSKX("letv-ott2",()->SearchLetvDianShiVideoOTV::new,(b)->{b.tagOttVideo();}); // NOTE: Chinese searching is supported by the API, so we use Chinese
        defAppX("letv-ios","letv","IOS端#乐视视频",(b)->{b.setSearchersV("letv-android2").addAliasA("乐视IOS端","乐视移动端","乐视视频IOS端","乐视视频移动端");}); // the m.letv.com links to e.g. tudou are not regarded as infringing; the searchers for IOS and Android are currently identical.
        defAppX("letv-android","letv","安卓端#乐视视频",(b)->{b.setSearchersV("letv-android2");});
        defAppX("letv-ott","*letv","OTT",(b)->{b.setSearchersV("letv-ott2").addAliasA("乐视盒子OTT","另个乐视盒子OTT");});
        });
        defSrcD("qiyi","爱奇艺","iqiyi.com;qiyi.com").doIt((src)->{ // high speed can lead to errors; even 4 might be too large...
final AnalyzerInfo anaWeb=defAnaR1X("qiyi",()->AnalyQiyiVideoPC::new,null);
final AnalyzerInfo ana61=defAnaR1X("qiyi-61",()->AnaylyV61VideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final String host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"61.iqiyi.com"))ctx.addPass(ana61);
        else ctx.addPass(anaWeb);
        },4);
        defSearcherBrowserFKX("qiyi",(kd)->new SearchIqiyiVideoPC(kd,new SearchIqiyiVideoOpt()),(b)->{b.tagPcVideo();}); // FIXME: can link to other sites as well
        defSearcherMX("qiyi-ios",(si)->new SearchQiyiIOSVideoInt(si),(b)->{b.tagMobileVideo();});
        defSearcherMX("qiyi-android",(si)->new SearchQiyiAndroidVideoInt(si),(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("qiyi-toutiao",()->SearchIqiyitoutiaoVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("qiyi","#qiyi","网页端",(b)->{b.setAllAccessible();}); // even vid urls have webUrl; FIXME: can link to other sites as well
        defAppX("qiyi-ios","qiyi","IOS端#爱奇艺视频",(b)->{b.setSearchersV("qiyi-ios").addAliasA("爱奇艺移动端","爱奇艺视频移动端","爱奇艺IOS端","爱奇艺视频IOS端");});
        defAppX("qiyi-android","qiyi","安卓端#爱奇艺视频",(b)->{b.setSearchersV("qiyi-android").addAliasA("爱奇艺安卓端","爱奇艺android端");});
        defAppX("qiyi-toutiao","qiyi","安卓端#爱奇艺头条",(b)->{b.setSearchersV("qiyi-toutiao").addAliasA("爱奇艺头条移动端");});
        });
        defSrcD("sohu","搜狐","tv.sohu.com;film.sohu.com;2012.sohu.com;s.sohu.com;v.sohu.com;health.sohu.com;gd.sohu.com;v.blog.sohu.com;v.game.sohu.com;hot.vrs.sohu.com;chinaren.com;focus.cn;go2map.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("sohu",()->AnalySohuVideoPC::new,(b)->{b.setCheckTitleChanges(true);});
@Deprecated final AnalyzerInfo anaMobile=defAnaR1X("sohu-mobile",()->AnalySohuVideoMobile::new,(b)->{b.setPlatStr("ios");});
final AnalyzerInfo anaDirect=defAnaDirectX("sohu-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
final AnalyzerInfo ana17173=defAnaR1X("sohu-17173",()->Analy17173VideoPC::new,null);
        src.setAnaDispatcher((ctx)->{ // too many concurrent requests will lead to 504/502 errors
final String url=ctx.getUrl();
        if(url.startsWith("vid-sohu://"))ctx.addPass(anaWeb); // the mobile resolver has become slow...
        else if(url.startsWith("http://v.game.sohu.com/"))ctx.addPass(ana17173);
        else if(url.startsWith("http://hot.vrs.sohu.com"))ctx.addPass(anaDirect);
        else ctx.addPass(anaWeb);
        },2);
        defSearcherBrowserFKX("sohu",(kd)->new SearchSohuVideoPC(kd,new SearchSohuVideoOpt()),(b)->{b.setTsleep(2000L).tagPcVideo();});
        defSearcherMX("sohu-IOS",(si)->new SearchSohuMobileVideoInt(si,MobilePlatformSohu.IOS),(b)->{b.setRedundant().tagMobileVideo();}); // appears to be the same as Android
        defSearcherMX("sohu-ANDROID",(si)->new SearchSohuMobileVideoInt(si,MobilePlatformSohu.ANDROID),(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("sohu-ios2",()->SearchSoHuVideoIOS::new,(b)->{b.tagMobileVideo();});
        defAppX("sohu-ios","=sohu","IOS端#搜狐视频",(b)->{b.setSearchersV("sohu-ios2").addAliasA("搜狐视频IOS端");}); // we used to use sohu-ANDROID as well, but there is no point giving redundant results
        defAppX("sohu-android","sohu","安卓端#搜狐视频",(b)->{b.setSearchersV("sohu-ANDROID").addAliasA("搜狐视频安卓端");});
        });
        defSrcDR1("youku","优酷","youku.com;youku.net",()->AnalyYoukuVideoPC::new,2).doIt((src)->{ // we get nopagecfg errors or captcha checks if run too fast (captcha checks seems to be based on a timeout though, but maybe we won't need a capture/seid at all if the frequency is sufficiently low); 4 (same for tudou) should be safe if seid is manually set, but even 1 is too high without a valid seid; as of Aug 8 2017, 2 is too much and gives dataerr-6004 even with utid switching; changed back to 2 as of Feb 7 2018 since the per-utid access frequency is now regulated further.
        // soku is for the browser platform
        defSearcherMX("soku-ALL",(si)->new SearchModuleSoku(si,SokuScope.ALL),(b)->{b.tagPcVideo();});
        defSearcherMX("soku-YOUKU",(si)->new SearchModuleSoku(si,SokuScope.YOUKU),(b)->{b.tagPcVideo();});
        defSearcherMX("youku-IOS",(si)->new SearchYoukuMobileVideoInt(si,MobilePlatformYouku.IOS),(b)->{b.setBad();});
        defSearcherMX("youku-ANDROID",(si)->new SearchYoukuMobileVideoInt(si,MobilePlatformYouku.ANDROID),(b)->{b.setBad();});
        defSearcherD("youku-ott"); // FIXME
        defAppX("youku-ios","youku","IOS端",(b)->{b.setNonVidAccessible();});
        defAppX("youku-android","youku","安卓端",(b)->{b.setNonVidAccessible();});
        defAppX("youku-ott","youku","OTT",(b)->{b.setSearchersV("youku-ott").addAliasA("优酷盒子OTT");});
        });
        defSrcD("youkuyun","优酷云","*youkuyun.com").doIt((src)->{
final AnalyzerInfo ana=defAnaR1X("youkuyun",()->AnalyYoukuyunVideoPC::new,(b)->{b.setUnreliable();});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),id=CommonUtil.tryExtractMiddleString(url,"vid-youkuyun://","");
        if(id!=null&&WebAnalyzer.youkuYunIdPat.matcher(id).matches())ctx.addPassL(ana);else ctx.addPassU(ana);
        },1);
        });
        // Search163VideoPC does not work as of Jun 26 2017; the actual website no longer appears to support searching.
        defSrcD("163","网易视频","v.163.com;v.news.163.com;v.2012.163.com;v.sports.163.com;v.auto.163.com;v.ent.163.com;live.163.com").doIt((src)->{ // used to be called 网易
final AnalyzerInfo anaWeb=defAnaR1X("163",()->Analy163VideoPC::new,null);
final AnalyzerInfo anaLive=defAnaR1X("163-live",()->AnalayLive163VideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final String host=CommonUtil.getURLHost(url);
        if(host.equals("live.163.com"))ctx.addPass(anaLive);else ctx.addPass(anaWeb);
        },2);
        defSearcherBrowserSKX("163-live",()->GetLive163VideoPC::new,(b)->{b.setForcedKeywordsP(()->GetLive163VideoPC.catIds.keySet(),200).tagPcVideo();});
        defSearcherBrowserFKX("163-short",(kd)->new Get163ShortVideoPC(),(b)->{b.setForcedKeywordDefaultP(200).tagPcVideo();}); // currently only a single page is available
        });
        defSrcD("sina","新浪","video.sina.com.cn;video.sina.cn;v.sina.cn;video.yayun2010.sina.com.cn;video.2012.sina.com.cn;video.baby.sina.com.cn;auto.sina.com.cn").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("sina",()->AnalySinaVideoPC::new,null);
final AnalyzerInfo anaMobile=defAnaR1X("sina-mobile",()->AnalySinaVideoMobile::new,(b)->{b.setPlatStr("android");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        ctx.addPass(anaWeb);if(url.startsWith("vid-sina://"))ctx.addPass(anaMobile);
        },1);
        defSearcherMX("sina",(si)->new SearchSinaVideoInt(si),(b)->{b.tagPcVideo();});
        defSearcherBrowserSKX("sina-client",()->SinaClientParser::new,(b)->{b.setTsleep(5000L).setBad();});
        defSearcherMobileSKX("sina-ios",()->SearchSinaVideoMobile::new,(b)->{b.setTsleep(5000L).tagMobileVideo();});
        defAppX("sina-ios","sina","IOS端$移动端#新浪视频",(b)->{b.setSearchersV("sina-ios");});
        });
        defSrcD("qq","腾讯","v.qq.com;film.qq.com;sports.qq.com").doIt((src)->{
        // "/cover/" urls from qq can easily change...
        src.setSingleAna(defAnaR1X("qq",()->AnalyQQVideoPC::new,(b)->{b.setCheckTitleChanges(true);}),4); // too many concurrent requests may lead to 400 errors, but even the default (8) isn't immediately problematic
        defSearcherBrowserFKX("qq",(kd)->new SearchQQVideo(kd,new SearchQQVideoOpts()),(b)->{b.tagPcVideo();});
        defSearcherPcSKX("qq-pc",()->SearchQQVideoPC::new,(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("qq-mobile",()->SearchQqVideoVideoMobile::new,(b)->{b.tagMobileVideo();});
        defAppX("qq-pc","qq","PC端#腾讯视频",(b)->{b.setSearchersV("qq-pc");});
        defAppX("qq-mobile","qq","IOS端#腾讯视频",(b)->{b.setSearchersV("qq-mobile").addAliasA("腾讯移动端","腾讯IOS端","腾讯视频移动端","腾讯视频IOS端");});
        defAppX("qq-android","qq","安卓端#腾讯视频",(b)->{b.setSearchersV("qq-mobile").addAliasA("腾讯安卓","腾讯视频安卓端").setRedundant();});
        });
        defSrcDC("tudou","土豆","tudou.com",()->VideoProcessorTudou::new,2).doIt((src)->{ // should be 2; too many concurrent requests will lead to nosegs/noiid/500
        defSearcherMX("soku-TUDOU",(si)->new SearchModuleSoku(si,SokuScope.TUDOU),(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("tudou-ios",()->SearchTudouVideoMobile::new,(b)->{b.setBad();}); // FIXME: no longer works as of Jun 22 2017; using soku-TUDOU data, which is mostly similar
        defSearcherD("tudou-ott");
        defAppX("tudou-ios","tudou","IOS端",(b)->{b.setSearchersV("soku-TUDOU");});
        defAppX("tudou-android","tudou","安卓端",(b)->{b.setSearchersV("soku-TUDOU");}); // no separate android searcher?
        defAppX("tudou-ott","tudou","TV端",(b)->{b.setSearchersV("tudou-ott").addAliasA("土豆TV端","土豆XLTV端","土豆OTT","土豆XLOTT");}); // actually -tv
        });
        defSrcD("xunlei","迅雷","video.xunlei.com;yule.xunlei.com;yinyue.xunlei.com").doIt((src)->{
final AnalyzerInfo anaSoku=defAnaDirectX("xunlei-ml-soku",DirectVideoUrlMode.NEED_DLPARAM,null);
final AnalyzerInfo anaShort=defAnaR1X("xunlei-ml-short",()->AnalyXunleiShortVideoAndroid::new,(b)->{b.setPlatStr("android");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-xunleiml-soku://"))ctx.addPass(anaSoku);
        else if(url.startsWith("vid-xunleiml-short://"))ctx.addPass(anaShort);
        },2);
        defSearcherMX("xunlei-ml-mobile",(si)->new SearchXunleiMLMobileVideoInt(si),(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("xunlei-ml-short",()->SearchXunleiVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("xunlei-ml-mobile","*xunlei","安卓端#迅雷",(b)->{b.setSearchersV("xunlei-ml-mobile");}); // 迅雷 is different from 响巢看看
        defAppX("xunlei-ml-short-android","xunlei","安卓端#迅雷短视频",(b)->{b.addUrlFilter((entCtx)->entCtx.entUrl.startsWith("vid-xunleiml-short://")).setAllAccessible().setSearchersV("xunlei-ml-short");});
        });
        defSrcD("kankan","响巢看看","kankan.com;kankan.xunlei.com").doIt((src)->{ // kuai.xunlei.com is separate
final AnalyzerInfo anaWeb=defAnaCX("xunlei",()->VideoProcessorXunleiWeb::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-xunlei://")){} //new VideoProcessorXunleiMobile();
        else ctx.addPass(anaWeb); // including vid-xunlei-gcid:// urls (which don't need download addresses)
        },-1);
        defSearcherBrowserFKX("xunlei",(kd)->new SearchkankanVideoPC(kd,new SearchKanVideoOpt()),(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("xunlei-ios",()->SearchXunleikankanVideoMobile::new,(b)->{b.tagMobileVideo();}); // Actually android
        defSearcherMobileSKX("xunlei-ios2",()->SearchXunleiKKVideoIOS::new,(b)->{b.tagMobileVideo();}); // Now this is really for IOS
        defAppX("xunlei-android","kankan","安卓端#看看视频",(b)->{b.setSearchersV("xunlei-ios").addAliasA("迅雷看看安卓端","响巢看看安卓端");});
        defAppX("xunlei-ios","kankan","IOS端#看看视频",(b)->{b.setSearchersV("xunlei-ios2").addAliasA("迅雷看看IOS端","响巢看看IOS端");});
        });
        defSrcDR1("pps","PPS","pps.tv;ppstream.com",()->AnalyPpsVideoPC::new,2).doIt((src)->{ // -1 gives many erroneous xdel's (but so is 2...)
final Predicate<RawVideoMatchCtx> vidQiyiFilter=(entCtx)->entCtx.entUrl.startsWith("vid-qiyi://"); // SearchPpsVideoMobile always gives such urls
        defSearcherMobileFKX("pps-ios",(kd)->new SearchPpsVideoMobile(kd,MobilePlatformPPS.IOS),(b)->{b.tagMobileVideo();});
        defSearcherMobileFKX("pps-android",(kd)->new SearchPpsVideoMobile(kd,MobilePlatformPPS.ANDROID),(b)->{b.tagMobileVideo();});
        defAppX("pps-ios","=pps","IOS端#爱奇艺PPS影音",(b)->{b.addUrlFilter(vidQiyiFilter).setSearchersV("pps-ios").addAliasA("爱奇艺PPSIOS端","PPS移动端","爱奇艺pps移动端");});
        defAppX("pps-android","=pps","安卓端#爱奇艺PPS影音",(b)->{b.addUrlFilter(vidQiyiFilter).setSearchersV("pps-android").addAliasA("爱奇艺pps安卓端","爱奇艺ppsandroid端","爱奇艺PPSandroid端");});
        });
        defSrcD("pptv","PP视频","pptv.com;pplive.com;pp.tv").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("pptv",()->AnalyPptvVideoPC::new,(b)->{b.setCheckTitleChanges(true);});
@Deprecated final AnalyzerInfo anaMobile=defAnaR1X("mobile",()->AnalyPptvVideoMobile::new,(b)->{b.setPlatStr("ios");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        ctx.addPass(anaWeb); // vid's are supported by both web and mobile resolvers; we should normally use the web resolver as it is better maintained.
//					if (url.startsWith("vid-pptv://")) ctx.addPass(anaMobile);
        },-1);
        defSearcherBrowserSKX("pptv",()->SearchPptvVideoPC::new,(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("pptv-ios",()->SearchPptvVideoMobile::new,(b)->{b.tagMobileVideo();}); // actually automatic android results
        defSearcherD("pptv-android"); // FIXME: manual results only for now
        defAppX("pptv-ios","pptv","IOS端",(b)->{b.setSearchersV("pptv-ios","pptv-android").addAliasA("PP视频IOS端","PP视频移动端","PPTV移动端","PPTVIOS端","PPTV IOS端","PPTV聚力移动端","PPTV聚力IOS端","PP视频移动端");});
        defAppX("pptv-android","pptv","安卓端",(b)->{b.setSearchersV("pptv-ios","pptv-android").addAliasA("PP视频安卓端","PPTV安卓端","PPTV聚力安卓端","pptv安卓端");});
        });
        defSrcD("funshion","风行","funshion.com;fun.tv").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("funshion",()->AnalyFunshionVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-funshion://")){}else ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserSKX("funshion",()->SearchFuntvVideoPC::new,(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("funshion-mobile",()->SearchFunshionVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("funshion-xiankan-mobile",()->SearchXiankanShipingVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("funshion-mobile","funshion","IOS端$移动端#风行电影",(b)->{b.setSearchersV("funshion-mobile").addAliasA("风行网IOS端","风行IOS端","风行网移动端","风行移动端");});
        defAppX("funshion-xiankan-mobile","funshion","安卓端#闲看视频",(b)->{b.setSearchersV("funshion-xiankan-mobile").addAliasA("闲看视频移动端");});
        });
        defSrcDR1("kankanews","看看新闻网","kankanews.com;kksmg.com",()->AnalyKankanewsVideoPC::new,-1);
        defSrcDR1("people","人民网","people.com.cn;people.cn",()->AnalyPeopleVideoPC::new,-1);
        defSrcDR1("6cn","六间房","6.cn;6rooms.com",()->Analy6cnPC::new,-1).doIt((src)->{
        defSearcherBrowserFKX("6cn-video",(kd)->new Search6CN(Search6CNType.VIDEO,kd),(b)->{b.tagPcVideo();});
        defSearcherBrowserFKX("6cn-music",(kd)->new Search6CN(Search6CNType.MUSIC,kd),(b)->{b.tagMusic();});
        defAppX("6cn-mobile","6cn","移动端",(b)->{b.setSearchersV("6cn-video","6cn-music");});
        });
        defSrcDC("baomihua","爆米花","baomihua.com;p.pomoho.com",()->VideoProcessorBaomihua::new,1).doIt((src)->{
        defSearcherBrowserFKX("baomihua",(kd)->new SearchBaomihuaVideoPC(kd,BaomihuaDurationMode.ALL),(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("baomihua-ios",()->SearchBaomihuaVideoMobile::new,(b)->{b.tagMobileVideo();});
        defAppX("baomihua-ios","baomihua","移动端",(b)->{b.setSearchersV("baomihua-ios").addAliasA("爆米花IOS端","爆米花网IOS端");});
        });
        defSrcDR1("cntv","央视网","cntv.cn;cctv.com",()->AnalyCntvVideoPC::new,2).doIt((src)->{ // high rate of errors if requested too frequently (Oct 24 2015)
        // We have been blocked by cntv at multiple IP addresses... as of Oct 26 2015, 252/253 have been 403'd, 1/250 hangs (incompatible DNS?), 254 and 251 still work.
        // 251 will be used by the browser/pc crawlers as well as 10.0.0.5, while 254 will be used by the mobile crawler.
        defSearcherBrowserFKX("cntv",(kd)->new SearchCntvVideoPC(kd,CntvDurationMode.ALL),(b)->{b.tagPcVideo();}); // access frequency controlled by CntvSession
        defSearcherBrowserFKX("cntv-pc",(kd)->new SearchCboxVideoPC(kd,CboxDurationMode.ALL),(b)->{b.setTsleep(30000L).tagPcVideo();});
        defSearcherMobileSKX("cntv-mobile",()->SearchCntvVideoMobile::new,(b)->{b.setTsleep(30000L).tagMobileVideo();});
        defAppX("cntv-pc","cntv","PC端#CNTV",(b)->{b.setSearchersV("cntv-pc");});
        defAppX("cntv-mobile","cntv","安卓端$移动端#CNTV",(b)->{b.setSearchersV("cntv-mobile").addAliasA("CNTV移动端","央视移动端","央视影音移动端");});
        });
        defSrcDR1("cctv4g","CCTV手机电视","cctv4g.com",()->AnalyCCTVshoujiVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("cctv4g-android",()->SearchCCTVshoujiVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("cctv4g-android","cctv4g","安卓端",(b)->{b.setAllAccessible().setSearchersV("cctv4g-android");}); // has html5 webUrl
        });
        defSrcD("yunshijie","云视界","bj.chinamobile.com");
        defSrcD("wo","沃视频","wo.cn;17wo.cn");
        defSrcDR1("3gtv","3gtv","3gtv.net",()->AnalyYueTVVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("yuetv-android",()->SearchYueTVVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("yuetv-android","3gtv","安卓端#悦TV",(b)->{b.setSearchersV("yuetv-android");});
        });
        defSrcDR1("ifeng","凤凰网","v.ifeng.com;phtv.ifeng.com",()->AnalyIfengVideoPC::new,1).doIt((src)->{ // will timeout if accessed too fast
        defSearcherBrowserFKX("ifeng",(kd)->new SearchIfengVideoPC(kd,IfengDurationMode.ALL),(b)->{b.tagPcVideo();});
        defAppX("ifeng-mobile","ifeng","IOS端$移动端#凤凰视频",(b)->{b.setSearchersV("ifeng").addAliasA("凤凰视频移动端","凤凰视频IOS端");});
        });
        defSrcDC("joy","激动网","joy.cn",()->VideoProcessorJoy::new,-1);
        defSrcDC("tangdou","糖豆网","tangdou.com",()->VideoProcessorTangdou::new,-1).doIt((src)->{
        defSearcherBrowserSKX("tangdou",()->SearchTangdouVideoPC::new,(b)->{b.tagPcVideo();});
        defAppX("tangdou-mobile","tangdou","IOS端$移动端#糖豆",(b)->{b.setSearchersV("tangdou").addAliasA("糖豆网移动端","糖豆网IOS端","糖豆移动端","糖豆IOS端");});
        });
        defSrcDC("bokecc","糖豆网(bokecc)","v.bokecc.com",()->VideoProcessorBokecc::new,-1); // appears to share the same web interface with tangdou according to VideoParserAPI...
        //		defSrcDC("iqilu", "iqilu.com", AnalyzerIqilu::new, -1); // now supported by AnalyzerShanDongTV
        defSrcD("wasu","华数","wasu.cn;wasu.com.cn;hzcnc.com;wasu.tv").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("wasu",()->AnalyWasuVideoPC::new,null);
final AnalyzerInfo anaOtt=defAnaR1X("wasu-ott",()->AnalyGiecVideoOTT::new,(b)->{b.setPlatStr("ott");});
final AnalyzerInfo anaMobile=defAnaR1X("wasu-mobile",()->AnalyWasuVideoAndroid::new,(b)->{b.setPlatStr("mobile");});
final AnalyzerInfo anaDirect=defAnaDirectX("wasu-direct",DirectVideoUrlMode.NEED_DLPARAM,null);
final AnalyzerInfo anaIosDirect=defAnaDirectX("wasu-ios-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://html5-epg.wasu.tv"))ctx.addPass(anaOtt); // can have an explicit :80 port number
        else if(url.startsWith("vid-wasu-mobile://"))ctx.addPass(anaMobile); // url has been prenormalized, so we can check just this case
        else if(url.startsWith("vid-wasu://"))ctx.addPass(anaDirect);
        else if(url.startsWith("http://apkvod-cnc.wasu.cn/"))ctx.addPass(anaIosDirect);
        else ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserFKX("wasu",(kd)->new SearchWasuVideoPC(kd,new SearchWasuVideoOpt()),(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("wasu-tv-mobile",()->SearchWasutvVideoMobile::new,(b)->{b.tagMobileVideo();});
        defAppX("wasu-mobile","wasu","安卓端$移动端#华数TV",(b)->{b.setSearchersV("wasu-tv-mobile").addAliasA("华数移动端","华数TV移动端","华数TVIOS端");});
        });
        defSrcD("douban","豆瓣","douban.com;douban.fm;doubanio.com").doIt((src)->{
final AnalyzerInfo anaMovie=defAnaR1X("douban-movie",()->AnalyDoubanmovieVideoPC::new,null);
final AnalyzerInfo anaMusic=defAnaR1X("douban-music",()->AnalyDoubanMusicPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-douban://fm/")){} // the old douban fm site no longer works
        else if(url.startsWith("vid-douban://music/"))ctx.addPass(anaMusic);
        else ctx.addPass(anaMovie); // this is for the website...
        },1); // easily blocked; movie.douban.com; mr*.douban.com (for direct music links)
        defSearcherBrowserSKX("douban-music",()->SearchDoubanMusicPC::new,(b)->{b.setTsleep(5000L).tagMusic().tagAudiobook();});
        defAppX("douban-music-pc","#douban","网页端",(b)->{b.setAllAccessible().setSearchersV("douban-music");}); // NOTE: also includes non-music douban stuff!
        defAppX("douban-music-mobile","douban","移动端",(b)->{b.setAllAccessible().setSearchersV("douban-music");});
        });
        defSrcDR1("kanjian","看见音乐","kanjian.com",()->AnalyKanjianMusicAndroid::new,-1).doIt((src)->{
        defSearcherBrowserSKX("kanjian",()->SearchKanJianMusicAndroid::new,(b)->{b.tagMusic();}); // actually the browser version
        });
        defSrcDC("m1905","M1905","m1905.com;1905.com",()->VideoProcessorM1905::new,-1);
        defSrcDX("tv189","天翼视讯","tv189.com;tv189.cn;netitv.com;vnet.cn;189.cn",(b)->{b.tagTelecom();}).doIt((src)->{
final AnalyzerInfo anaOld=defAnaCX("tv189-old",()->VideoProcessorTv189Old::new,null);
final AnalyzerInfo anaNew=defAnaR1X("tv189-new",()->AnalyTv189VideoPC2::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-tv189://"))ctx.addPass(anaNew);
        else ctx.addPass(anaOld); // this is for the website...
        },2);
        defSearcherBrowserSKX("tv189",()->SearchTV189VideoPC::new,(b)->{b.tagPcVideo();});
        defAppX("tv189","#tv189","网页端",(b)->{b.setAllAccessible().setSearchersV("tv189");}); // has webUrl
        defAppX("tv189-mobile","tv189","移动端",(b)->{b.setSearchersV("tv189");});
        });
        defSrcDX("ttcatv","天途云","ttcatv.tv",(b)->{b.tagTelecom();}).doIt((src)->{
        src.setSingleAna(defAnaR1X("ttcatv",()->AnalyTiantuyunVideoIpad::new,null),2);
        defSearcherMobileSKX("ttcatv",()->SearchTiantuyunVideoIpad::new,(b)->{b.tagMobileVideo();});
        defAppX("ttcatv-mobile","#ttcatv","移动端",(b)->{b.setAllAccessible().setSearchersV("ttcatv");}); // has webUrl
        });
        defSrcDX("wojiashipin","沃家视频","newlxtv.jstv.com",(b)->{b.tagTelecom();}).doIt((src)->{
        src.setSingleAna(defAnaDirectX("wojiashipin",DirectVideoUrlMode.NEED_DLPARAM,null),2);
        defSearcherMobileSKX("wojiashipin",()->SearchWojiashipingVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("wojiashipin-android","wojiashipin","安卓端",(b)->{b.setSearchersV("wojiashipin");});
        });
        defSrcDR1("daxiangv","橡视频","daxiangv.com",()->AnalyXiangshipingVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("daxiangv-android",()->SearchXiangshipingVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("daxiangv-android","daxiangv","安卓端",(b)->{b.setSearchersV("daxiangv-android");});
        });

        defSrcD("imgo","芒果网","mgtv.com;imgo.tv;hunantv.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("imgo",()->AnalyHunantvVideoPC::new,null); // this supercedes AnalyImgoVideo; also usable on mobile
final AnalyzerInfo anaOtt=defAnaR1X("imgo-ott",()->AnalyImgoVideoOTT::new,(b)->{b.setPlatStr("ott");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-imgo-ott://"))ctx.addPass(anaOtt);
        else ctx.addPass(anaWeb); // this is for the website...
        },-1);
        defSearcherBrowserSKX("imgo",()->SearchImgoVideoPC::new,(b)->{b.setBad();});
        defSearcherBrowserFKX("imgo2",(kd)->new SearchImgoVideoPC2(kd,new SearchImgoVideoOpt()),(b)->{b.tagPcVideo();}); // new interface
        defSearcherMobileSKX("imgo-mobile",()->SearchMangguotvVideoMobile::new,(b)->{b.tagMobileVideo();});
        defSearcherOttSKX("imgo-ott",()->SearchImgoVideoOTT::new,(b)->{b.setRedundant();}); // not exactly redundant... the interface is just no longer used
        defSearcherOttSKX("imgo-ott2",()->SearchImgoVideoOTT2::new,(b)->{b.tagOttVideo();});
        defAppX("imgo-mobile","imgo","移动端#芒果TV",(b)->{b.setSearchersV("imgo-mobile").addAliasA("芒果TV移动端","芒果TVIOS端");});
        defAppX("imgo-ott","imgo","OTT",(b)->{b.setSearchersV("imgo-ott2").addAliasA("imgo-芒果OTT","芒果OTT","芒果盒子OTT");});
        });
        defSrcD("hifuntv","Hifuntv","hifuntv.com").doIt((src)->{
        defSearcherD("qingmang-ott");
        defAppX("qingmang-ott","hifuntv","OTT",(b)->{b.setSearchersV("qingmang-ott").addAliasA("青芒OTT","青芒盒子OTT");});
        });
        defSrcDR1("jingan","上海静安","jingan.gov.cn",()->AnalyJinganVideoPC::new,-1);
        // We have not yet analyzed the browser versions of these applications (if they even exist), so we set just defaultDomain (so that the site can be recognized by the output code) but not domains to be safe.
        defSrcD("qvod","快播","*qvod.com;kuaibo.com").doIt((src)->{
        src.setSingleAna(defAnaCX("qvod",()->VideoProcessorKuaiBoMobile::new,(b)->{b.setPlatStr("mobile");}),-1);
        defSearcherBrowserSKX("yunfan",()->SearchYunfanVideoPC::new,(b)->{b.tagPcVideo();});
        defSearcherHotSKX("kuaibo-mobile",()->SearchKuaiboVideoMobile::new,null);
        defSearcherHotSKX("shipintoutiao-android",()->SearchShipingtoutiaoVideoAndroid::new,(b)->{b.tagMobileVideo();});
        // For kuaibo, actually most of the urls are still vid-kuaibo://
        defAppX("kuaibo-mobile","*qvod","安卓端$移动端#快播",(b)->{b.setSearchersV("kuaibo-mobile").setOffline();});
        defAppX("shipintoutiao-android","*qvod","安卓端$移动端#视频头条",(b)->{b.setSearchersV("shipintoutiao-android");});
        });
        defSrcDDirect("wandoujia","豌豆荚","*wandoujia.com",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherHotSKX("wandoujia-mobile",()->SearchWandoujiaVideoMobile::new,(b)->{b.tagMobileVideo();});
        defAppX("wandoujia-mobile","*wandoujia","安卓端#豌豆荚",(b)->{b.setSearchersV("wandoujia-mobile");});
        });
        defSrcDDirect("vgo","Vgo视频","*vgo.21cn.com",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherMobileSKX("vgo-mobile",()->SearchVgoVideoMobile::new,(b)->{b.tagMobileVideo();});
        defAppX("vgo-mobile","vgo","安卓端$移动端#Vgo视频",(b)->{b.setSearchersV("vgo-mobile");});
        });
        defSrcD("mygica","美如画","ttzx.tv").doIt((src)->{
        src.setSingleAna(defAnaR1X("mygica",()->AnalyMygicaVideoOTT::new,(b)->{b.setPlatStr("ott");}),-1);
        defSearcherOttSKX("mygica-ott",()->SearchMygicaVideoOTT::new,(b)->{b.tagOttVideo();});
        defAppX("mygica-ott","mygica","OTT",(b)->{b.setPlatStr("ott").setSearchersV("mygica-ott").addAliasA("美如画盒子OTT");});
        });
        defSrcD("tvfan","电视粉","tvfan.cn").doIt((src)->{
        defSearcherHotSKX("tvfan-android",()->SearchDianshifenVideoMobile::new,(b)->{b.setRedundant();}); // now using tvfan-android2 instead (Jan 11 2017)
        defSearcherHotSKX("tvfan-android2",()->SearchDianshifenVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defSearcherHotSKX("tvfan-tv",()->SearchDianshifenVideoOTV::new,(b)->{b.setBad();}); // FIXME: far too slow (Jan 12 2017)
        defSearcherHotSKX("baishitv-ios",()->SearchBaiShiTVVideoIOS::new,null);
        defAppX("tvfan-android","*tvfan","安卓端#电视粉",(b)->{b.setSearchersV("tvfan-android2").addAliasA("电视粉移动端").setNoTakedownMails();}); // as of Aug 24 2017, many of the links (e.g. for youku's 三生三世十里桃花) are no longer embedded
        defAppX("tvfan-tv","*tvfan","TV端#电视粉",(b)->{b.setSearchersV("tvfan-tv").addAliasA("电视粉OTV").setNoTakedownMails();}); // ditto
        defAppX("baishitv-ios","*tvfan","IOS端#百视TV",(b)->{b.setSearchersV("baishitv-ios").addAliasA("百视TV移动端").setOffline();});
        });
        defSrcD("doukan","都看TV","doukantv.com").doIt((src)->{
        defSearcherHotSKX("chaogaoqing-ios",()->SearchChaogaoqingVideoIOS::new,null);
        defAppX("chaogaoqing-ios","*doukan","IOS端#超高清影视",(b)->{b.setSearchersV("chaogaoqing-ios").addAliasA("超高清影视移动端").setOffline();});
        });
        defSrcD("m1905cn","叮叮影视","m1905.cn").doIt((src)->{ // m1905cn appears to be different from m1905
        defSearcherHotSKX("dingdingyingshi-ios",()->SearchDingdingyingshiVideoIOS::new,(b)->{b.setBad();});
        defAppX("dingdingyingshi-ios","*m1905cn","IOS端#叮叮影视",(b)->{b.setSearchersV("dingdingyingshi-ios").addAliasA("叮叮影视移动端");});
        });
        defSrcD("youyisijie","YouYiSiJie","youyisijie.com").doIt((src)->{
        defSearcherHotSKX("huanggua-ios",()->SearchHuangGuaBoFangQiIOS::new,(b)->{b.setForcedKeywordsP(()->SearchHuangGuaBoFangQiIOS.possibleTypes,200);});
        defAppX("huanggua-ios","*youyisijie","IOS端#黄瓜播放器",(b)->{b.setSearchersV("huanggua-ios").addAliasA("黄瓜播放器移动端").setOffline();});
        });
        defSrcD("dazhuangzhuang","DaZhuangZhuang","dazhuangzhuang.com").doIt((src)->{
        defSearcherHotSKX("jipinyingshi-ios",()->SearchJipinyingshiVideoIOS::new,(b)->{b.tagMobileVideo();});
        defSearcherHotSKX("lierenyingshi-ios",()->SearchLierenyingshiVideoIOS::new,(b)->{b.setRedundant();}); // redundant
        defAppX("jipinyingshi-ios","*dazhuangzhuang","IOS端#极品影视",(b)->{b.setSearchersV("jipinyingshi-ios").addAliasA("极品影视移动端");});
        defAppX("lierenyingshi-ios","*dazhuangzhuang","IOS端#猎人影视",(b)->{b.setSearchersV("jipinyingshi-ios").addAliasA("猎人影视移动端");});
        });
        defSrcD("mimito","Mimito","mimito.com").doIt((src)->{
        defSearcherHotSKX("dydsjzyys-ios",()->SerachDianyingdshjzyVideoIOS::new,(b)->{b.setForcedKeywordsP(()->SerachDianyingdshjzyVideoIOS.possibleTypes,200);});
        defAppX("dydsjzyys-ios","*mimito","IOS端#电影电视剧综艺影视",(b)->{b.setSearchersV("dydsjzyys-ios").addAliasA("电影电视剧综艺影视移动端");});
        });
        defSrcD("whaley","微鲸","whaley.cn").doIt((src)->{
        defSearcherHotSKX("whaley-tv",()->SearchWeiJingTV::new,(b)->{b.tagOttVideo();}); // the new interface no longer accepts pinyin
        defAppX("whaley-mobile","*whaley","移动端",(b)->{b.setSearchersV("whaley-tv").setOffline();}); // does not actually exist
        defAppX("whaley-tv","*whaley","TV端#微鲸电视",(b)->{b.setSearchersV("whaley-tv").addAliasA("微鲸TV端");});
        });
        defSrcDR1("17173","17173视频","v.17173.com;17173v.com",()->Analy17173VideoPC::new,-1);
        defSrcD("2345","看点啥影视","2345.com;km.com").doIt((src)->{
final AnalyzerInfo anaKandian=defAnaR1X("2345-kandian",()->AnalyKandianshaVideoAndroid::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.contains("//ys.km.com/kandian/"))ctx.addPass(anaKandian);
        },2);
        defSearcherBrowserSKX("2345",()->Search2345VideoPC::new,(b)->{b.setBad();}); // no longer working; use 2345x instead
        defSearcherMobileSKX("2345x",()->Search2345YingshidaquanVideoAndroid::new,(b)->{b.tagMobileVideo();}); // not sure about link hotness; assumed to be cold links
        defSearcherMobileSKX("kandiansha-android",()->SearchKandianshaVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("2345","#=2345","网页端#看点啥影视",(b)->{b.setSearchersV("2345x").setNonVidAccessible().addAliasA("2345网页端","2345影视大全网页端","看点啥网页端");});
        defAppX("2345-android","=2345","安卓端#2345影视大全",(b)->{b.setSearchersV("2345x").addAliasA("2345安卓端");});
        defAppX("kandiansha-android","2345","安卓端#看点啥",(b)->{b.setSearchersV("kandiansha-android");}); // should also be shown in the browser 2345 results
        });
        defSrcDR1("360","360影视","v.360.cn;v.360kan.com;www.so.com;www.haosou.com",()->Analy360kanVideoPC::new,-1).doIt((src)->{
        defSearcherDX("360-web",(b)->{b.setFmt(SearchResultFormat.WEB_NOPOS);});
        defSearcherD("360Video-web");
        defSearcherD("360-web-mobile");
        defSearcherHotSKX("360-a",()->Search360kanatimeVideoPC::new,(b)->{b.tagPcVideo();});
        defSearcherHotSKX("360-mobile",()->Search360VideoVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defSearcherHotSKX("360-ios",()->Search360VideoVideoIOS::new,(b)->{b.tagMobileVideo();});
        defAppX("360-web","=360","网页端#360搜索",(b)->{b.setSearchersV("360-web").setCatSearch();}); // should be called 360-browser...
        defAppX("360Video-web","=360","网页端#360视频",(b)->{b.setSearchersV("360Video-web").setCatSearch();}); // should be called 360Video-browser...
        defAppX("360-web-mobile","=360","安卓端#360搜索",(b)->{b.setSearchersV("360-web-mobile");});
        defAppX("360-mobile","*360","安卓端#360影视大全",(b)->{b.setSearchersV("360-mobile").addAliasA("360移动端","360影视移动端","360影视大全移动端","360影视大全安卓端","360影视大全android");}); // youku want us to treat 360-mobile as cold-linking as of Oct 19 2017, but changed mind according to lizongren (probably wrong though) for embedded youku content as of Oct 24 2017
        defAppX("360-ios","*360","IOS端#360影视大全",(b)->{b.setSearchersV("360-ios").addAliasA("360影视大全IOS端","360影视大全ios端","360影视IOS端");});
        });
        // NOTE: Having too many different apps under the same source (e.g. 360) would mean more work separating them when defining the apps.
        defSrcDR1("360Kuai","快视频","k.360kan.com",()->AnalyKuaiVideoMobile::new,2).doIt((src)->{
        defSearcherPcSKX("360Kuai",()->SerachKuaiVideoMobile::new,(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("jinrishijie-android",()->SearchJinrishijieVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("360Kuai-mobile","360Kuai","安卓端$移动端",(b)->{b.setNonVidAccessible().setSearchersV("360Kuai").addAliasA("快视频安卓端","快视频IOS端","快视频移动端");});
        defAppX("jinrishijie-mobile","360Kuai","安卓端#今日视界",(b)->{b.setSearchersV("jinrishijie-android");});
        });
        defSrcD("zhuia","追啊","zhuiaa.com").doIt((src)->{
        defSearcherHotSKX("zhuia-ios",()->SearchZhuiaVideoIOS::new,(b)->{b.tagMobileVideo();});
        defAppX("zhuia-ios","*zhuia","IOS端$移动端",(b)->{b.setSearchersV("zhuia-ios").addAliasA("追啊移动端");});
        });
        defSrcD("shenma","神马搜索","sm.cn;tv.uc.cn").doIt((src)->{ // 神马视频 at tv.uc.cn appears to be different
        defSearcherHotFKX("shenma-video-movie",(kd)->new SearchShenmaVideoPC(kd,CategoryOfShenMaShiPin.movie),null);
        defSearcherHotFKX("shenma-video-short",(kd)->new SearchShenmaVideoPC(kd,CategoryOfShenMaShiPin.shortVideo),null);
        defSearcherD("shenma-web");
        defSearcherD("shenma-web-mobile");
        defAppX("shenma-video","*shenma","网页端#神马视频",(b)->{b.setSearchersV("shenma-video-movie","shenma-video-short");});
        defAppX("shenma-web","=shenma","网页端",(b)->{b.setSearchersV("shenma-web").setCatSearch();});
        defAppX("shenma-web-mobile","=shenma","安卓端",(b)->{b.setSearchersV("shenma-web-mobile");});
        });
        defSrcD("le123","影视大全","le123.com").doIt((src)->{
        defSearcherHotSKX("le123",()->SearchYingshiDQVideoMobile::new,(b)->{b.tagMobileVideo();});
        defSearcherHotSKX("kuaikan-mobile",()->SearchKuaikanyingshi::new,null);
        defAppX("le123-mobile","*le123","安卓端#影视大全",(b)->{b.setSearchersV("le123").addAliasA("影视大全移动端");});
        defAppX("kuaikan-mobile","*le123","安卓端#快看影视",(b)->{b.setSearchersV("kuaikan-mobile").addAliasA("快看影视移动端");});
        });
        defSrcD("baiduVideo","百度视频","video.baidu.com;v.baidu.com;baishi.baidu.com").doIt((src)->{
final AnalyzerInfo anaBaishi=defAnaR1X("baiduVideo-baishi",()->AnalyBaiduVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(AnalyBaiduVideoPC.innerUrlPat.matcher(url).find())ctx.addPass(anaBaishi);
        },2);
        defSearcherMX("baiduVideo",(si)->new SearchModuleBaidu(si),(b)->{b.setFmt(SearchResultFormat.VIDEO_BAIDU).tagPcVideo();});
        defSearcherMobileSKX("baidu-mobile",()->SearchBaiduVideoMobile::new,(b)->{b.tagMobileVideo();});
        defSearcherMobileSKX("baidu-eurocup-mobile",()->SearchBaiduzhuantiVideoAndroid::new,(b)->{b.setForcedKeywordsP(()->Arrays.asList("欧洲杯"),50);});
        defAppX("baiduVideo-web","#=baiduVideo","网页端#百度视频",(b)->{b.setSearchersV("baiduVideo").setCatSearch();});
        defAppX("baiduVideo-mobile","*baiduVideo","安卓端$移动端#百度视频",(b)->{b.setSearchersV("baidu-mobile","baidu-eurocup-mobile").addAliasA("百度移动端","百度视频移动端","百度视频IOS端");}); // same as 小度头条
        });
        defSrcD("mtime","时光网","mtime.com");
        defSrcDX("baiduPan","百度网盘","pan.baidu.com;yun.baidu.com",(b)->{b.tagWangpan();}); // AnalyBaidupanVideoPC is disabled for now
        defSrcD("baiduZhidao","百度知道","zhidao.baidu.com").doIt((src)->{
        defSearcherD("baiduZhidao");
        });
        defSrcD("baiduBaobao","百度宝宝知道","baobao.baidu.com");
        defSrcD("baiduTieba","百度贴吧","tieba.baidu.com");
        defSrcDR1("baiduBaijia","百度百家","baijia.baidu.com;baijiahao.baidu.com",()->AnalyBaiduBaijiaVideoPC::new,2);
        defSrcD("tianya","天涯论坛","tianya.cn");
        defSrcD("qzone","QQ空间","qzone.com;qzone.qq.com");
        defSrcD("wukong","悟空问答","wukong.com");
        defSrcD("sina-kandian","新浪看点","kandian.com;k.sina.cn;k.sina.com.cn");
        defSrcDR1("ttkb","天天快报","kb.qq.com;kuaibao.qq.com",()->AnalyTtkbVideoPC::new,2);
//			defSrcD("dayu", "大鱼", "dayu.com"); // 大鱼 is mostly uctoutiao, and it works with youku/tudou as well
        defSrcDX("letvCloud","乐视云盘","cloud.letv.com",(b)->{b.tagWangpan();}).doIt((src)->{
        src.setSingleAna(defAnaR1X("letvCloud",()->AnalyLetvCloud::new,(b)->{b.setUnreliable();}),2);
        });
        defSrcDX("xunleiKuai","迅雷快传","kuai.xunlei.com",(b)->{b.tagWangpan();});
        defSrcDX("360Pan","360云盘","yunpan.cn;yunpan.360.cn",(b)->{b.tagWangpan();});
        defSrcDDirect("qianxun","千寻影视","1kxun.com;1kxun.mobi",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{ // direct is for vid-qianxun:// urls
        defSearcherMX("qianxun-mobile",(si)->new SearchQianXunMobileVideoInt(si),(b)->{b.tagMobileVideo();}); // seems to be the same as 千寻Station OTV
        defSearcherMX("qianxun-mobile-android",(si)->new SearchQianXunAndroidVideoInt(si),(b)->{b.setRedundant();});
        defAppX("qianxun-mobile","*qianxun","IOS端$移动端#千寻影视",(b)->{b.setSearchersV("qianxun-mobile");});
        defAppX("qianxun-tv","*qianxun","TV端#千寻Station",(b)->{b.setSearchersV("qianxun-mobile");});
        });
        defSrcD("kanketv","看客影视","kanketv.com").doIt((src)->{
        defSearcherHotSKX("kanketv",()->SearchKanketvVideoMobile::new,null);
        defAppX("kanketv-mobile","*kanketv","安卓端$移动端#看客影视",(b)->{b.setSearchersV("kanketv").setOffline();});
        defAppX("kanketv-ott","*kanketv","TV端",(b)->{b.setSearchersV("kanketv").addAliasA("看客影视TV端","看客影视HDTV端","看客影视OTT","看客影视HDOTT","看客OTT").setOffline();});
        });
        defSrcD("51tv","快手看片","51tv.com;ikuaishou.com").doIt((src)->{
        defSearcherHotSKX("kuaishou-mobile",()->SearchKuaishouKPVideoMobile::new,(b)->{b.tagMobileVideo();});
        defSearcherD("kuaishou-tv");
        defAppX("kuaishou-mobile","*51tv","安卓端#快手看片",(b)->{b.setSearchersV("kuaishou-mobile");});
        defAppX("kuaishou-tv","*51tv","TV端",(b)->{b.setSearchersV("kuaishou-tv").addAliasA("快手看片OTT","快手看片TV端");});
        });
        defSrcD("giec","杰科","giec.cn").doIt((src)->{
        defSearcherOttSKX("giec-ott",()->SearchGiecVideoOTT::new,(b)->{b.tagOttVideo();}); // the keyword will automatically be converted to pinyin
        defAppX("giec-ott","*giec","OTT",(b)->{b.setPlatStr("ott").setSearchersV("giec-ott").addAliasA("giec-杰科OTT","杰科OTT","杰科盒子OTT");}); // NOTE: used to be wasu
        });
        defSrcD("egreat","亿格瑞","*ikanhd.net").doIt((src)->{
        defSearcherHotSKX("egreat-ott",()->SearchEgreatVideoOTT::new,(b)->{b.setBad().tagOttVideo();}); // FIXME: need to support its strange urls
        defAppX("egreat-ott","*egreat","OTT",(b)->{b.setSearchersV("egreat-ott");});
        });
        defSrcD("kaiboer","开博尔","*veryhd.net").doIt((src)->{
        defSearcherHotSKX("kaiboer-ott",()->SearchKaiboerVideoOTT::new,(b)->{b.setPinyin().tagOttVideo();});
        defAppX("kaiboer-ott","*kaiboer","OTT",(b)->{b.setSearchersV("kaiboer-ott");});
        });
        defSrcD("mele","迈乐","mele.cn").doIt((src)->{
        defSearcherHotSKX("mele-ott",()->SearchMeleVideoOTT::new,null); // obsolete
        defSearcherD("mele-ott2");
        defAppX("mele-ott","*mele","OTT",(b)->{b.setSearchersV("mele-ott2").addAliasA("迈乐OTT","迈乐盒子","迈乐盒子OTT").setOffline();});
        defAppX("mele-tv","*mele","TV端",(b)->{b.setSearchersV("mele-ott2").setOffline();}); // FIXME: does this really exist?  obsolete
        });
        defSrcD("inphic","英菲克","inphic.cn").doIt((src)->{
        defSearcherD("inphic-ott");
        defAppX("inphic-ott","*inphic","OTT",(b)->{b.setSearchersV("inphic-ott").addAliasA("英菲克盒子","英菲克盒子OTT");});
        });
        defSrcD("moretv","电视猫","moretv.com.cn;tvmore.com.cn").doIt((src)->{
        defSearcherHotSKX("moretv-mobile",()->SearchDianshimaoVideoAndroid::new,null); // FIXME: way too slow
        defSearcherHotSKX("maoxian-mobile",()->SearchMaoxianVideoAndroid::new,(b)->{b.setBad();}); // FIXME: still slow; no longer available as of Oct 15 2017
        defSearcherHotSKX("moretv-tv",()->SearchDianshimaotvVideoOTT::new,(b)->{b.setPinyin().tagOttVideo();}); // FIXME: way too slow
        defSearcherHotSKX("moretv-tv2",()->SearchMoretvOTV::new,(b)->{b.setPinyin().tagOttVideo();}); // optimized
        defSearcherD("moretv-ott");
        defAppX("moretv-mobile","*moretv","安卓端",(b)->{b.setSearchersV("moretv-mobile").addAliasA("电视猫移动端");});
        defAppX("maoxian-mobile","*moretv","安卓端#猫闲",(b)->{b.setSearchersV("maoxian-mobile").addAliasA("猫闲移动端").setBad();});
        // FIXME: what's the difference between the two?
        defAppX("moretv-tv","*moretv","TV端",(b)->{b.setSearchersV("moretv-tv").addAliasA("电视猫TV端","电视猫视频TV端");});
        defAppX("moretv-tv2","*moretv","TV端#云视听MoreTV",(b)->{b.setSearchersV("moretv-tv2").addAliasA("云视听MoreTV");});
        defAppX("moretv-ott","*moretv","OTT",(b)->{b.setSearchersV("moretv-ott").addAliasA("电视猫OTT","电视猫盒子OTT");});
        });
        defSrcD("msports","体育疯","msports.cn");
        defSrcD("moli","魔力视频","moliv.cn").doIt((src)->{
        defSearcherHotFKX("moli-mobile",(kd)->new SearchMoliVideoOTT(MoliKeywordMode.CHINESE,kd),(b)->{b.tagMobileVideo();});
        defSearcherHotFKX("moli-ott",(kd)->new SearchMoliVideoOTT(MoliKeywordMode.PINYIN,kd),(b)->{b.setPinyinL().tagOttVideo();});
        defAppX("moli-mobile","*moli","移动端",(b)->{b.setSearchersV("moli-mobile").addAliasA("魔力视频HD移动端","魔力视频IOS端","魔力视频安卓端","魔力视频HDIOS端","魔力视频HD安卓端");});
        defAppX("moli-ott","*moli","TV端",(b)->{b.setSearchersV("moli-ott").addAliasA("魔力视频OTT","魔力视频TV端","魔力视频TV版","魔力视频TV版TV端","魔力TV端");}); // actually -tv
        });
        defSrcD("verycd","VeryCD","verycd.com").doIt((src)->{ // used to be called emule
        defSearcherD("emule-tv");
        defAppX("emule-tv","*verycd","TV端",(b)->{b.setSearchersV("emule-tv").addAliasA("电驴TV版","电驴TV版TV端","电驴TV版视频","电驴TV版视频TV端","电驴TV版视频OTT");});
        });
        defSrcD("baidutv","百度电视云","tv.baidu.com").doIt((src)->{
        defSearcherD("baidutv-tv");
        defSearcherD("baidutv-ott");
        defAppX("baidutv-tv","*baidutv","TV端",(b)->{b.setSearchersV("baidutv-tv").addAliasA("百度TV端","百度云TV端","百度电视云TV端");});
        defAppX("baidutv-ott","*baidutv","OTT",(b)->{b.setSearchersV("baidutv-ott").addAliasA("百度电视云盒子OTT","百度电视云盒子视频OTT","百度盒子OTT","百度电视云OTT","百度云OTT");});
        });
        defSrcD("buding","布丁视频","*buding.example.com").doIt((src)->{ // FIXME: don't know the actual domain...
        defSearcherD("buding-tv");
        defAppX("buding-tv","*buding","TV端",(b)->{b.setSearchersV("buding-tv").addAliasA("布丁视频TV端","布丁TV端");});
        });
        defSrcD("dianshijia","TVAPK","tvapk.net").doIt((src)->{
        defSearcherHotSKX("dianshijia-mobile",()->SearchDianshijiabrowserVideoAndroid::new,(b)->{b.setBad();});
        defSearcherHotSKX("dianshijia-tv",()->SearchDianshiJiaVideoOTT::new,(b)->{b.tagOttVideo();});
        defAppX("dianshijia-mobile","*dianshijia","安卓端#电视家浏览器",(b)->{b.setSearchersV("dianshijia-tv").addAliasA("电视家浏览器移动端").setNoTakedownMails();}); // still gives non-hot links (Dec 11 2017)
        defAppX("dianshijia-tv","*dianshijia","TV端#电视家浏览器",(b)->{b.setSearchersV("dianshijia-tv").addAliasA("电视家浏览器TV端","电视家浏览器视频TV端","电视家浏览器OTT","电视家浏览器视频OTT").setNoTakedownMails();});
        });
        defSrcD("dianshijia2","电视家2.0","dianshijia.cn"); // Related to dianshijia; see http://down.hdpfans.com/news/deepin/81.htm for details
        defSrcD("hdplive","HDP直播","hdplive.net");
        defSrcD("skyworth","创维","skyworth.com").doIt((src)->{
        defSearcherD("skyworth-ott");
        defAppX("skyworth-ott","*skyworth","OTT",(b)->{b.setSearchersV("skyworth-ott").addAliasA("创维盒子OTT");});
        });
        defSrcD("tmall-tv","天猫魔盒","tshop.tmall.com").doIt((src)->{
        defSearcherD("tmall-ott");
        defAppX("tmall-ott","tmall-tv","OTT",(b)->{b.setSearchersV("tmall-ott").addAliasA("天猫OTT");});
        });
        defSrcD("xiaomi","小米","mi.com;xiaomi.com").doIt((src)->{
        defSearcherHotSKX("miui-music",()->SearchMIUIMusicAndroid::new,(b)->{b.tagMusic();}); // FIXME: no corresponding app
        defSearcherD("xiaomi-ott");
        defAppX("xiaomi-ott","xiaomi","OTT",(b)->{b.setSearchersV("xiaomi-ott").addAliasA("小米OTT");});
        });
        defSrcDX("kuaitou","快投视频","qcast.cn",(b)->{b.setOffline();}).doIt((src)->{
        defSearcherHotSKX("kuaitou-ott",()->SearchKuaitouVideoOTT::new,(b)->{b.tagOttVideo();});
        defAppX("kuaitou-ott","*kuaitou","TV端",(b)->{b.setSearchersV("kuaitou-ott").addAliasA("快投视频TV端","快投TV端","快投视频OTT");}); // actually -tv
        });
        defSrcD("chaojishipin","超级视频","chaojishipin.com").doIt((src)->{
        defSearcherHotSKX("chaojishipin-android",()->SearchChaojishipinVideoAndroid::new,(b)->{b.setTsleep(1000L).tagMobileVideo();});
        defAppX("chaojishipin-android","*chaojishipin","安卓端",(b)->{b.setSearchersV("chaojishipin-android").addAliasA("超级视频移动端");});
        });
        defSrcD("tiantiankan123","天天看高清影视","tiantiankan123.com"); // does not appear to be the same as 天天看电影 (http://www.tiantiankan.org/)
        defSrcD("yuhe","羽禾","myott.sinaapp.com").doIt((src)->{
        defSearcherD("yuhe-tv");
        defAppX("yuhe-tv","*yuhe","TV端",(b)->{b.setSearchersV("yuhe-tv");});
        });
        defSrcD("himedia","海美迪","himedia.com").doIt((src)->{
        defSearcherD("himedia-ott"); // FIXME: now actually the same as the new imgo-ott
        defAppX("himedia-ott","*himedia","OTT",(b)->{b.setSearchersV("himedia-ott").addAliasA("海美迪OTT","海美迪盒子OTT");});
        });
        defSrcD("lingyun","灵云","*lingyun.example.com").doIt((src)->{
        defSearcherD("lingyun-ott");
        defAppX("lingyun-ott","*lingyun","OTT",(b)->{b.setSearchersV("lingyun-ott").addAliasA("灵云盒子OTT");});
        });
        defSrcD("szider","亿典","szider.com").doIt((src)->{
        defSearcherD("szider-ott");
        defAppX("szider-ott","*szider","OTT",(b)->{b.setSearchersV("szider-ott").addAliasA("亿典盒子","亿典盒子OTT");});
        });
        defSrcD("ruibo","瑞珀","realplay.com").doIt((src)->{
        defSearcherD("ruibo-ott");
        defAppX("ruibo-ott","*ruibo","OTT",(b)->{b.setSearchersV("ruibo-ott").addAliasA("瑞珀盒子","瑞珀盒子OTT");});
        });
        defSrcD("beevideo","蜜蜂视频","beevideo.tv").doIt((src)->{
        defSearcherD("beevideo");
        defAppX("beevideo-android","*beevideo","安卓端",(b)->{b.setSearchersV("beevideo").addAliasA("蜜蜂安卓端");});
        defAppX("beevideo-ott","*beevideo","TV端",(b)->{b.setSearchersV("beevideo").addAliasA("蜜蜂视频OTT","蜜蜂视频TV端");}); // actually TV
        });
        defSrcD("tdan","推单视频","tdan.tv").doIt((src)->{
        defSearcherHotSKX("tdan",()->SearchTuidanVideoOTV::new,null); // offline as of Oct 22 2017
        defSearcherHotSKX("kongquetuiping",()->SearchKongQueTuipingVideoOTV::new,null);
        defAppX("tdan-android","*tdan","安卓端",(b)->{b.setSearchersV("tdan").addAliasA("推单安卓端").setOffline();});
        defAppX("tdan-ott","*tdan","TV端",(b)->{b.setSearchersV("tdan").addAliasA("推单视频OTT","推单视频TV端","推单TV端").setOffline();}); // actually TV?
        defAppX("kongquetuiping-tv","*tdan","TV端#孔雀推屏",(b)->{b.setSearchersV("kongquetuiping").setOffline();});
        });
        defSrcD("tuzi","兔子视频","16tree.com;aituzi.com").doIt((src)->{ // offline as of Dec 15 2017
        defSearcherHotSKX("tuzi-tv",()->SearchTuziVideoOTV::new,null);
        defAppX("tuzi-tv","*tuzi","TV端",(b)->{b.setSearchersV("tuzi-tv").setOffline();});
        });
        defSrcD("91vst","VST","91vst.com").doIt((src)->{
        defSearcherHotSKX("91vst",()->SearchVSTquanjuheVideoOTV::new,(b)->{b.setPinyin();});
        defAppX("91vst-ott","*91vst","TV端",(b)->{b.setSearchersV("91vst").addAliasA("VST全聚合TV端","VST全聚合OTT");}); // actually -tv
        });
        defSrcD("cibn","CIBN高清影视","cibntv.net").doIt((src)->{
final AnalyzerInfo anaDirect=defAnaDirectX("cibn-eps-direct",DirectVideoUrlMode.NEED_DLPARAM,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-cibn-eps://"))ctx.addPass(anaDirect);
        },2);
        defSearcherMobileSKX("cibn-sjds-android",()->SearchCIBNshoujidsVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defSearcherD("cibn-tv");
        defSearcherHotSKX("cibn-vst-tv",()->SearchCibnVstVideoOTV::new,(b)->{b.tagOttVideo();});
        defAppX("cibn-sjds-android","cibn","安卓端#CIBN手机电视",(b)->{b.setSearchersV("cibn-sjds-android");}); // can't do status checking; must rely on searching
        defAppX("cibn-tv","*cibn","TV端",(b)->{b.setSearchersV("cibn-tv").addAliasA("CIBN高清影视TV端");});
        defAppX("cibn-vst-tv","*cibn","TV端#CIBN微视听",(b)->{b.setSearchersV("cibn-vst-tv");});
        });
        defSrcDR1("chaojikanyy","超级看影院","ysmer.net",()->AnalyChaojikanyyVideoAndroid::new,-1).doIt((src)->{ // download speed of a single thread is low
        defSearcherHotSKX("chaojikanyy-android",()->SearchChaojikanyyVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("chaojikanyy-android","*chaojikanyy","安卓端",(b)->{b.setSearchersV("chaojikanyy-android");});
        });
        defSrcD("szfirstlive","szfirstlive","v.szfirstlive.com").doIt((src)->{
        defSearcherHotSKX("xiukong-tv",()->SearchXiukongVideoOTV::new,null);
        defAppX("xiukong-tv","*szfirstlive","TV端#秀控视频",(b)->{b.setSearchersV("xiukong-tv").addAliasA("秀控视频TV端","秀控TV端");}); // very slow...
        });
        defSrcDR1("yidongbaimohe","移动百魔和","ysten.com",()->AnalyYidongbaimoheVideoAndroid::new,-1).doIt((src)->{
        defSearcherMobileSKX("yidongbaimohe-android",()->SearchYidongbaimoheVideoAndroid::new,null);
        defAppX("yidongbaimohe-android","yidongbaimohe","安卓端",(b)->{b.setAllAccessible().setSearchersV("yidongbaimohe-android");}); // actually still requires searchability, but we don't bother...
        });
        defSrcD("kaixun","开迅视频","video.kascend.com");
        defSrcD("togic","泰捷视频","togic.com").doIt((src)->{
        defSearcherOttSKX("togic-tv",()->SearchTaijieVideoOTV::new,(b)->{b.setPinyin().tagOttVideo();});
        defAppX("togic-mobile","*togic","安卓端",(b)->{b.setSearchersV("togic-tv");});
        defAppX("togic-tv","*togic","TV端",(b)->{b.setSearchersV("togic-tv");});
        });
        defSrcDR1("haotushipin","好兔视频","v.yilan.tv;howto.yilan.tv",()->AnalyHaotushipingVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("haotushipin-android",()->SearchHaotushipingVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("haotushipin","#haotushipin","网页端",(b)->{b.setSearchersV("haotushipin-android").setAllAccessible();});
        defAppX("haotushipin-android","haotushipin","安卓端",(b)->{b.setSearchersV("haotushipin-android");});
        });
        defSrcD("yuntutv","云图TV","yuntutv.net");
        defSrcD("yingshikuaisou","影视快搜","*yingshikuaisou.example.com");
        defSrcD("qianlong","千龙网","qianlong.com");
        defSrcD("brtn","北京网络广播电视台","video.brtn.cn");
        defSrcDR1("cuctv","视友网","cuctv.com",()->AnalyCuctvVideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("cuctv",()->SearchCuctvVideoPC::new,(b)->{b.tagPcVideo();});
        });
        defSrcD("71","宣讲家网","71.cn");
        defSrcD("autohome","汽车之家","autohome.com.cn");
        defSrcD("rbc","菠萝网","v.rbc.cn");
        defSrcD("cnr","央广网","cnr.cn;cri.cn");
        defSrcD("cetv","中国教育网络电视台","cetv.cn");
        defSrcD("tianshan","天山网","ts.cn");
        defSrcD("waqu","蛙趣","waqu.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("waqu",()->AnalyWaquVideoPC::new,null);
final AnalyzerInfo anaAndroid=defAnaR1X("waqu-android",()->AnalyWaquVideoAndroid::new,(b)->{b.setPlatStr("mobile");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-waqu://"))ctx.addPass(anaAndroid);else ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserSKX("waqu",()->SearchWaquVideoPC::new,(b)->{b.setBad();}); // www.waqu.com is no longer available
        defSearcherDX("waqu-mobile",(b)->{b.setBad();}); // the original hot searcher of waqu; no longer available
        defSearcherMobileSKX("waqu-mobile2",()->SearchWaquVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("waqu-mobile","*waqu","IOS端$移动端",(b)->{b.setOffline().setSearchersV("waqu-mobile").addAliasA("蛙趣视频IOS端","蛙趣视频移动端");}); // the original waqu-mobile searcher is a hot searcher, and it uses waqu.com servers, which are no longer available
        defAppX("waqu-mobile2","waqu","安卓端",(b)->{b.setAllAccessible().setSearchersV("waqu-mobile2").addAliasA("蛙趣视频安卓端");});
        });
        defSrcDR1("bjnews","新京报","bjnews.com.cn",()->AnalyBjnewsVideoPC::new,1); // can 403
        defSrcDR1("yidianzixun","一点资讯","yidianzixun.com;go2yd.com",()->AnalyYidianzixunVideoIOS::new,-1).doIt((src)->{
        defSearcherBrowserSKX("yidianzixun",()->SearchYidianzixunVideoIOS::new,(b)->{b.setTsleep(5000L).tagMobileVideo();});
        defAppX("yidianzixun-mobile","yidianzixun","IOS端$移动端",(b)->{b.setSearchersV("yidianzixun");}); // also supported by the PC browser
        });

        defSrcDX("400gb","城通网盘","400gb.com;ctfile.com",(b)->{b.tagWangpan();});
        defSrcDX("kanbox","酷盘","kanbox.com",(b)->{b.tagWangpan();});
        defSrcDR1("dongqiudi","懂球帝","dongqiudi.com",()->AnalayDongqiudiVideoPC::new,2).doIt((src)->{
        defSearcherBrowserSKX("dongqiudi",()->GetDongqiudiVideoPC::new,(b)->{b.setForcedKeywordsP(()->GetDongqiudiVideoPC.kwCatMap.keySet(),1000).tagPcVideo();});
        });
        // music sites
        defSrcD("1ting","一听音乐","1ting.com;1ting.com.cn").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("1ting",()->Analy1tingMusicPC::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("1ting-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://p.1ting.com"))ctx.addPass(anaDirect);else ctx.addPass(anaWeb);
        },1); // don't know; often unable to download
        defSearcherBrowserSKX("1ting",()->Search1tingMusicPC::new,(b)->{b.setTsleep(5000L).tagMusic();}); // easily 403's...
        defAppX("1ting-mobile","1ting","移动端",(b)->{b.setSearchersV("1ting");});
        });
        defSrcDR1("9ku","九酷音乐","9ku.com",()->Analy9kuMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("9ku",()->Search9kuMusicPC::new,(b)->{b.tagMusic();});
        defAppX("9ku-mobile","9ku","移动端",(b)->{b.setSearchersV("9ku");});
        });
        defSrcD("9sky","九天音乐","9sky.com").doIt((src)->{
final AnalyzerInfo anaWebOld=defAnaR1X("9sky",()->Analy9skyMusicPC::new,null);
final AnalyzerInfo anaWebNew=defAnaR1X("9sky2",()->Analy9SkyMusicAndroid::new,null); // for the new site; usable in the browser as well
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-9sky2://"))ctx.addPass(anaWebNew);else ctx.addPass(anaWebOld);
        },1); // would give dlslow if accessed too frequently...
        defSearcherBrowserSKX("9sky",()->Search9skyMusicPC::new,(b)->{b.setBad();});
        defSearcherBrowserSKX("9sky-mv",()->Search9skymvMusicPC::new,(b)->{b.setBad();});
        defSearcherMobileSKX("9sky-mobile",()->Search9skyMusicMobile::new,(b)->{b.setBad();});
        defSearcherMobileSKX("9sky2",()->Search9SkyMusicAndroid::new,(b)->{b.tagMusic();});
        defAppX("9sky","#9sky","网页端",(b)->{b.setAllAccessible().setSearchersV("9sky2");});
        defAppX("9sky-mobile","9sky","移动端",(b)->{b.setSearchersV("9sky2");});
        });
        // play.baidu.com urls should be normalized first
        defSrcD("baiduMusic","百度音乐","music.baidu.com;y.baidu.com").doIt((src)->{ // can limit; but 1 is currently much too slow...
final AnalyzerInfo anaBrowser=defAnaR1X("baiduMusic",()->AnalyBaiduMusicPC::new,(b)->{b.setPlatStr("browser");});
final AnalyzerInfo anaAndroid=defAnaR1X("baiduMusic-android",()->AnalyBaiduMusicPad::new,(b)->{b.setPlatStr("android");});
final AnalyzerInfo anaMV=defAnaR1X("baiduMusic-MV",()->AnalyBaiduMVMusicPC::new,null);
final AnalyzerInfo anaY=defAnaR1X("baiduYMusic",()->AnalyYbaiduMusicPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final String host=CommonUtil.getURLHost(url);
        if(host.equals("y.baidu.com"))ctx.addPass(anaY);
        else if(url.startsWith("http://music.baidu.com/song/")){ctx.addPass(anaBrowser);ctx.addPass(anaAndroid);}
        else if(url.startsWith("http://music.baidu.com/mv/"))ctx.addPass(anaMV);
        },4);
        defSearcherBrowserFKX("baiduMusic-SONG",(kd)->new SearchBaidumusicMusicPC(SearchBaiduMusicMode.SONG,kd),(b)->{b.setTsleep(7000L).tagMusic();});
        defSearcherBrowserFKX("baiduMusic-MV",(kd)->new SearchBaidumusicMusicPC(SearchBaiduMusicMode.MV,kd),(b)->{b.setTsleep(7000L).tagMusic();});
        defSearcherBrowserSKX("baiduYMusic",()->SearchYbaiduMusicPC::new,(b)->{b.tagMusic();});
        defSearcherMobileSKX("baiduMusic-android",()->SearchBaiDuMusicPad::new,(b)->{b.setTsleep(2000L).tagMusic();}); // SearchBaiDuIpad appears to be similar
        // NOTE: many deleted songs at baiduMusic can still be downloaded via the Android interface; actually they still appear in search results (and is accessible in Android) if the search keyword include some other metadata information in addition to the song title.
        // NOTE: seems to be no longer searchable as of Nov 21 2016; e.g. http://music.baidu.com/song/23389781
        defAppX("baiduMusic-android","baiduMusic","移动端",(b)->{b/*.setPlatStr("android")*/.setSearchersV("baiduMusic-android");});
        });
        defSrcDR1("baiduLebo","百度乐播","leboapi.baidu.com",()->AnalyBaiduleboMusicMobile::new,4).doIt((src)->{
        defSearcherMobileSKX("baiduLebo-mobile",()->SearchBaiduleboMusicMobile::new,(b)->{b.tagMusic().tagAudiobook();});
        defAppX("baiduLebo","#baiduLebo","网页端",(b)->{b.setSearchersV("baiduLebo-mobile").setAllAccessible();});
        defAppX("baiduLebo-mobile","baiduLebo","移动端",(b)->{b.setSearchersV("baiduLebo-mobile");});
        });
        defSrcDR1("qqMusic","腾讯音乐","y.qq.com;music.qq.com;qqmusic.qq.com",()->AnalyQqmusicMusicPC::new,-1).doIt((src)->{ // don't know if there is any limit, but since we are using cookies, just be careful; disabled the limit on May 21 2015
        defSearcherHotSKX("qq-music",()->SearchQqmusicMusicPCPC::new,(b)->{b.tagMusic();});
        defSearcherBrowserSKX("qq-music-spec",()->SearchQqmusicsingerMusicPC::new,(b)->{b.setKwMode(AnimeKeywordMode.GROUP).tagMusic();});
        defSearcherBrowserSKX("qq-music-multi",()->SearchQqmusicmultipleMusicPC::new,(b)->{b.setKwMode(AnimeKeywordMode.GROUP).setBad().tagMusic();}); // not working as of Nov 24 2016
        // qqMusic links to MVs on v.qq.com, thus the *
        defAppX("qqMusic-mobile","*qqMusic","移动端",(b)->{b.setSearchersV("qq-music");}); // the PC searcher suffices
        });
        defSrcDR1("qiefm","企鹅FM","fm.qq.com;fm.qzone.qq.com",()->AnalyQieFMMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserFKX("qiefm-music",(kd)->new SearchQiefmMusicPC(QieFM.MUSIC),(b)->{b.setForcedKeywordDefaultP(200);});
        defSearcherBrowserFKX("qiefm-novel",(kd)->new SearchQiefmMusicPC(QieFM.NOVEL),(b)->{b.setForcedKeywordDefaultP(200);});
        });
        defSrcD("qingtingfm","蜻蜓.fm","qingtingfm.com;qingting.fm").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("qingtingfm",()->AnalyQingtingFMMusicPC::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("qingtingfm-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final String host=CommonUtil.getURLHost(url);
        if(host.equals("od.qingting.fm"))ctx.addPass(anaDirect);
        else ctx.addPass(anaWeb);
        },1); // would gives noplayobj if accessed too quickly
        // SearchQingtingMusicPC is a bit old, and the resulting links might not be compatible...
        defSearcherBrowserSKX("qingtingfm-music-pc",()->SearchQingtingMusicPC::new,(b)->{b.setBad();}); // not currently used
        defSearcherBrowserSKX("qingtingfm-music-mobile",()->SearchQingtingFMMusicPC::new,(b)->{b.tagMusic().tagAudiobook();}); // actually running on the browser
        defAppX("qingtingfm-music-pc","#qingtingfm","网页端",(b)->{b.setSearchersV("qingtingfm-music-mobile").setAllAccessible();}); // NOTE: currently all the urls that can possibly pass status checking have corresponding webUrl's
        defAppX("qingtingfm-music-mobile","qingtingfm","移动端",(b)->{b.setSearchersV("qingtingfm-music-mobile");});
        });
        defSrcDC("ik123","深港DJ俱乐部","ik123.com",()->MusicProcessorik123::new,-1);
        defSrcD("kugou","酷狗","web.kugou.com;www.kugou.com").doIt((src)->{
        src.setSingleAna(defAnaR1X("kugou-pad",()->AnalyKugouMusicPad::new,(b)->{b.setPlatStr("mobile");}),-1);
        defSearcherBrowserSKX("kugou",()->SearchKugouMusicPC::new,(b)->{b.tagMusic();});
        defSearcherPcSKX("kugou-mac",()->SearchKugoumacMusicPC::new,null); // NOTE: albumId appears to be unavailable
        defSearcherBrowserSKX("kugou-album",()->SearchKugoualbumMusicPC::new,(b)->{b.setKwMode(AnimeKeywordMode.ALBUM);});
        defSearcherMobileSKX("kugou-ios",()->SearchKuGouMusicPad::new,(b)->{b.tagMusic();}); // NOTE: As of Nov 18 2017, the results here appears incomplete; e.g. "One more chance" yields nothing, even though other common keywords do yield something.
        // NOTE: kugou-pc receives special treatment; we specify UrlAccessMode.ALL here and filter out urls that are actually inaccessible
        // NOTE: some search results from searchers other than kugou-ios might have been disabled, so we don't use them in report generation
        defAppX("kugou-pc","#kugou","PC端",(b)->{b.setAllAccessible().setSearchersV("kugou-ios");}); // kugou no longer has a browser version as of Jun 8 2015 but resumed on Jun 18 2015; the PC version supports MVs just like the iOS version
        // NOTE: kugou-mac is not currently affected by kugouWebAccessible
        defAppX("kugou-mac","kugou","MAC端",(b)->{b.setSearchersV("kugou-mac");});
        defAppX("kugou-ios","kugou","移动端",(b)->{b.setPlatStr("mobile").setSearchersV("kugou-ios","kugou-album");}); // the MV searcher has been combined
        });
        defSrcD("5sing","5sing","5sing.kugou.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("5sing",()->Analy5SingMusicPC::new,null);
final AnalyzerInfo anaMobile=defAnaR1X("5sing-mobile",()->Analy5SingVideoMobile::new,(b)->{b.setPlatStr("mobile");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://5sing.kugou.com"))ctx.addPass(anaWeb);
        else if(url.startsWith("vid-5sing://"))ctx.addPass(anaMobile);
        },-1);
        });
        defSrcDR1("kuwo","酷我","kuwo.cn",()->AnalyKuwoMusicPC::new,4).doIt((src)->{ // includes bd.kuwo.cn; a high access frequency would cause some requests to return no-response
        defSearcherBrowserFKX("kuwo",(kd)->new SearchkuwoMusic(SearchkuwoMode.SONG,kd),(b)->{b.tagMusic();});
        defSearcherPcSKX("kuwoPc",()->SearchKuwoMusicPC::new,(b)->{b.tagMusic();});
        defSearcherBrowserFKX("kuwo-mv",(kd)->new SearchkuwoMusic(SearchkuwoMode.MV,kd),(b)->{b.tagMusic();});
        defSearcherMobileSKX("kuwo-ios",()->SearchKuwoMusicPad::new,(b)->{b.tagMusic();});
        defAppX("kuwo-ios","kuwo","移动端",(b)->{b.setSearchersV("kuwo-ios");});
        });
        defSrcDX("migu","咪咕音乐","migu.cn",(b)->{b.tagTelecom();}).doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("migu",()->AnalymiguMusicPC::new,null);
final AnalyzerInfo anaLive=defAnaR1X("migu-live",()->AnalyMiguLive::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("migu-ios-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://tyst.migu.cn"))ctx.addPass(anaDirect);
        else if(url.startsWith("vid-migu-live://"))ctx.addPass(anaLive);
        else ctx.addPass(anaWeb);
        },2); // easily returns 403/404 when downloading
        defSearcherBrowserSKX("migu",()->SearchMiguMusicPC::new,(b)->{b.setTsleep(2000L).tagMusic();});
        defAppX("migu-mobile","migu","移动端",(b)->{b.setSearchersV("migu");});
        });
        defSrcD("songtaste","SongTaste","songtaste.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaRX("songtaste",()->AnalySongtasteMusicPC::new,null);
final AnalyzerInfo anaMobile=defAnaDirectX("songtaste-mobile",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://www.songtaste.com")||url.contains("/song/"))ctx.addPass(anaWeb);
        else ctx.addPass(anaMobile);
        },-1);
        });
        defSrcDC("top100","Top100","top100.cn",()->MusicProcessorTop100::new,-1);
        defSrcDC("wo99","Wo99","wo99.com",()->MusicProcessorWo99::new,-1);
        defSrcD("xiami","虾米","xiami.com").doIt((src)->{
final AnalyzerInfo anaWebSong=defAnaR1X("xiami-song",()->AnalyXiamiMusicPC::new,null);
final AnalyzerInfo anaWebMv=defAnaR1X("xiami-mv",()->AnalyXiamimvVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-xiamiM://")){} // the old mobile interface no longer works
        else if(url.contains("/mv/"))ctx.addPass(anaWebMv);
        else ctx.addPass(anaWebSong);
        },1); // has been IP-blocked before (e..g on Sep 4 2016); set higher temporarily on Sep 9 2014
        defSearcherBrowserFKX("xiami-album",(kd)->new SearchXiamiMusicPC(kd,PlatformXiaMi.album),(b)->{b.setRedundant().tagMusic();}); // NOTE: deprecated because it sends too many requests
        defSearcherBrowserFKX("xiami",(kd)->new SearchXiamiMusicPC(kd,PlatformXiaMi.song),(b)->{b.tagMusic();});
        defSearcherMobileSKX("xiami-ios",()->SearchXiaMiMusicPad::new,(b)->{b.setBad().tagMusic();}); // obsolete; now using the web searcher instead
        defAppX("xiami-mobile","xiami","移动端",(b)->{b.setSearchersV("xiami");}); // As of Nov 4 2015, xiami uses the PC searcher
        });
        defSrcD("yinyuetai","音悦台","yinyuetai.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("yinyuetai",()->AnalyYinyuetaiMusicPC::new,null);
final AnalyzerInfo anaAndroid=defAnaDirectX("yinyuetai-mobile",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://hc.yinyuetai.com"))ctx.addPass(anaAndroid);else ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserSKX("yinyuetai",()->SearchYinyuetaiMusicPC::new,(b)->{b.tagMusic();});
        defSearcherMobileSKX("yinyuetai-ios",()->SearchYinYueTaiMusicPad::new,(b)->{b.tagMusic();});
        defAppX("yinyuetai-ios","yinyuetai","移动端",(b)->{b.setSearchersV("yinyuetai-ios");});
        });
        defSrcDR1("yue365","365音乐网","yue365.com",()->AnalyYue365MusicPC::new,2).doIt((src)->{ // don't access bd.kuwo.cn too frequently
        defSearcherBrowserSKX("365music",()->Search365YinyueMusicPC::new,(b)->{b.tagMusic();});
        });
        defSrcDX("ttpodMusic","天天动听","ttpod.com;dongting.com",(b)->{b.setOffline();}).doIt((src)->{
final AnalyzerInfo anaXingqiu=defAnaR1X("alixingqiu",()->AnalyAlixingqiuMusicIOS::new,(b)->{b.setPlatStr("ios");});
final AnalyzerInfo anaDongting=defAnaR1X("ttpodMusic",()->AnalyDongTingMusicViaSearch::new,null); // currently supports browser only (mobile platforms are assumed to have the same results)
final AnalyzerInfo anaDirect=defAnaDirectX("ttpodMusic-direct",DirectVideoUrlMode.NEED_DLPARAM,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final VideoTaskDesc anaTask=ctx.getTaskDesc();
        ctx.addPass(anaXingqiu);
        // Below are deprecated
        if(!url.startsWith("vid-ttpodmv://")&&anaTask.getRawDlParam()==null){
        // ttpodmv urls are not currently supported by MusicProcessorDongTing, and when rawDlParam is available (mobile results found by the searcher rather than from status checking), they are always used.
        ctx.addPass(anaDongting);
        }else ctx.addPass(anaDirect);
        },-1);
        defSearcherBrowserSKX("ttpodMusic",()->SearchDongtingMusicPC::new,(b)->{b.setBad();}); // www.dongting.com now redirects to www.alibabaplanet.com, which appears to be an SNS site without much music searching functionality...
        defSearcherBrowserSKX("ttpodMusicSpec",()->SearchDongtingspecialMusicPC::new,(b)->{b.setKwMode(AnimeKeywordMode.GROUP).setBad();}); // ditto
        defSearcherMobileSKX("alixingqiu",()->SearchAlixingqiuMusicIOS::new,(b)->{b.setBad();});
        // ttpodMusicSpec does not appear to be available, at least in the browser version
        defAppX("ttpodMusic-pc","#ttpodMusic","网页端",(b)->{b.setSearchersV("ttpodMusic" /*, "ttpodMusicSpec"*/).setPlatStr("browser");}); // NOTE: if we can't find a working webUrl, it should not be "+"
        defAppX("ttpodMusic-mobile","ttpodMusic","移动端",(b)->{b.setSearchersV("ttpodMusic" /*, "ttpodMusicSpec"*/).setPlatStr("browser");});
        defAppX("alixingqiu-mobile","ttpodMusic","IOS端#阿里星球",(b)->{b.setSearchersV("alixingqiu").setPlatStr("ios").setAllAccessible();}); // has webUrl, but it is mostly meant for mobile browsers, so report in mobile app only
        });
        defSrcDDirect("ttpodDirect","天天动听CDN","*direct.ttpod.com",DirectVideoUrlMode.NEED_DLPARAM,-1); // direct http links to the audio files, including keys; NOTE: this is not currently moved to ttpodMusic because of existing entries
        defSrcDR1("163Music","网易云音乐","music.163.com",()->AnalyWangyiMusicPC::new,-1).doIt((src)->{ // gives 503 if access frequency is too high, even 2; now regulated within WangyiMusicSession
        defSearcherBrowserSKX("163Music",()->SearchWangyiMusicPC::new,(b)->{b.setTsleep(1000L).tagMusic();}); // the search interface gives 503 if access frequency is too high; now regulated within WangyiMusicSession
        defSearcherBrowserSKX("163Music-video",()->SearchWangyiMusicVideoPC::new,(b)->{b.setTsleep(2000L).tagPcVideo().tagMusic();});
        defSearcherBrowserSKX("163Music-radio",()->SearchWangyiDiantaiMusicPC::new,(b)->{b.setKwMode(AnimeKeywordMode.RADIO).setTsleep(2000L).tagMusic().tagAudiobook();});
        defSearcherMobileSKX("163Music-ios",()->SearchWangyiMusicPad::new,(b)->{b.setRedundant();});
        defAppX("163Music-ios","163Music","移动端",(b)->{b.setSearchersV("163Music","163Music-radio");});
        });
        defSrcDC("omusicMusic","OMusic","omusic.cc",()->MusicProcessorOMusic::new,1).doIt((src)->{ // homepage has been "403 Forbidden"'d on 10.0.0.19 on Jul 12 2013.  Using 10.0.0.20, which is also 403'd on Jul 19 2013.
        defSearcherBrowserSKX("omusicMusic",()->SearchOmusicMusicPC::new,(b)->{b.setTsleep(2000L).tagMusic();});
        defSearcherMobileSKX("omusicMusic-ios",()->SearchOmusicMusicPad::new,(b)->{b.setTsleep(2000L).tagMusic();}); // many songs are not licensed and thus unplayable
        defAppX("omusicMusic-ios","omusicMusic","移动端",(b)->{b.setSearchersV("omusicMusic-ios");});
        });
        defSrcD("sinaMusic","新浪乐库","music.sina.com.cn").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaRX("sinaMusic",()->AnalySinamusicMusicPC::new,null);
final AnalyzerInfo anaMobile=defAnaDirectX("sinaMusic-mobile",DirectVideoUrlMode.NEED_DLPARAM,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-sinaMusic://"))ctx.addPass(anaMobile);else ctx.addPass(anaWeb);
        },1);
        defSearcherBrowserSKX("sinaMusic",()->SearchSinamusicMusicPC::new,(b)->{b.setTsleep(2000L).tagMusic();});
        defSearcherMobileSKX("sinaMusic-mobile",()->SearchSinaMusicMobile::new,(b)->{b.tagMusic();});
        defAppX("sinaMusic-mobile","sinaMusic","移动端",(b)->{b.setSearchersV("sinaMusic-mobile");});
        });
        defSrcD("118100","爱音乐","118100.cn").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("118100",()->Analy118100MusicPC::new,null);
final AnalyzerInfo anaMobile=defAnaRX("118100-mobile",()->Analy118100aMusicPad::new,(b)->{b.setPlatStr("android");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-118100apad://"))ctx.addPass(anaMobile);
        else if(url.startsWith("vid-118100://"))ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserSKX("118100",()->Search118100MusicPC::new,(b)->{b.setBad().tagMusic();}); // replaced by 360.so.com; not working as of Nov 24 2016
        defSearcherMobileSKX("118100-android",()->Search118100MusicPad::new,(b)->{b.tagMusic();});
        });
        defSrcDDirect("renrenFm","人人电台","renren.fm",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherMX("renrenFm",(si)->new SearchModuleRenrenFm(si),(b)->{b.setKwMode(AnimeKeywordMode.GROUP).tagMusic();});
        });
        defSrcD("herook","K歌达人","herook.kuro.cn").doIt((src)->{
        src.setSingleAna(defAnaRX("herook-ios",()->AnalyOkeheroMusicPad::new,(b)->{b.setPlatStr("ios");}),-1);
        defSearcherMobileSKX("herook-ios",()->SearchOkeheroMusicPad::new,(b)->{b.tagMusic();});
//				defAppX("herook-ios", "herook", "移动端", "herook-ios"); // currently no results
        });
        defSrcDX("nokia","NOKIA音乐","music.ovi.com",(b)->{b.setOffline();}).doIt((src)->{
        src.setSingleAna(defAnaDirectX("nokia-direct",DirectVideoUrlMode.NEED_DLPARAM,null),-1);
        defSearcherMobileSKX("nokia",()->SearchNokiaMusicPad::new,(b)->{b.tagMusic();});
        defAppX("nokia-mobile","nokia","移动端",(b)->{b.setSearchersV("nokia");});
        });
        defSrcDDirect("kxt","开心听","kxting.cn",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherMobileSKX("kxt",()->SearchKxtMusicPC::new,(b)->{b.tagMusic();});
        });
        defSrcDDirect("aishang-music","爱尚音乐","yymommy.com",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherMobileSKX("aishang-music",()->SearchAishangMusicPC::new,(b)->{b.setBad().tagMusic();});
        defAppX("aishang-music-mobile","aishang-music","移动端",(b)->{b.setSearchersV("aishang-music");});
        });
        defSrcD("ximalaya","喜马拉雅","ximalaya.com;xmcdn.com").doIt((src)->{ // fdfs.xmcdn.com, audio.xmcdn.com
final AnalyzerInfo anaWeb=defAnaR1X("ximalaya",()->AnalyXimalayaMusicPC::new,(b)->{b.setPlatStr("browser");});
final AnalyzerInfo anaMobile=defAnaR1X("ximalaya-mobile",()->AnalyXimaLayaMusicMobile::new,(b)->{b.setPlatStr("mobile");});
final AnalyzerInfo anaDirect=defAnaDirectX("ximalaya-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
final String host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"xmcdn.com"))ctx.addPass(anaDirect);
        else if(url.startsWith("vid-ximalaya://"))ctx.addPass(anaMobile);
        else ctx.addPass(anaWeb);
        },-1);
        defSearcherBrowserSKX("ximalaya",()->SearchXimalayaMusicPC::new,(b)->{b.tagMusic().tagAudiobook();});
        defSearcherMobileSKX("ximalaya-mobile",()->SearchXimaLayaMusicMobile::new,(b)->{b.tagMusic().tagAudiobook();});
        defAppX("ximalaya","#ximalaya","网页端",(b)->{b.setAllAccessible();}); // has webUrl
        defAppX("ximalaya-mobile","ximalaya","移动端",(b)->{b.setPlatStr("mobile").setSearchersV("ximalaya-mobile");});
        });
        defSrcD("echo","ECHO","echosystem.kibey.com").doIt((src)->{
        // When echo-mobile fails to work, NEED_DLPARAM can be used temporarily
        src.setSingleAna(defAnaR1X("echo-mobile",()->AnalyEchoVideoMobile::new,(b)->{b.setPlatStr("mobile");}),2); // we used to get blocked; crawl slowly
        defSearcherBrowserSKX("echo-ios",()->SearchEchoVideoMobile::new,(b)->{b.setTsleep(5000L).tagMusic().tagAudiobook();}); // we actually crawl the browser version
        defAppX("echo-pc","#echo","网页端",(b)->{b.setAllAccessible().setSearchersV("echo-ios");});
        defAppX("echo-ios","echo","移动端",(b)->{b.setAllAccessible().setSearchersV("echo-ios");}); // searcher unstable
        });
        defSrcD("kaola","考拉FM","kaolafm.com;tingban.cn").doIt((src)->{
// We also have a PC resolver, but do they give different results?  Seems so for vid-kaola://1000000001548
final AnalyzerInfo anaWeb=defAnaR1X("kaola",()->AnalyKaoLaFMMusicPC::new,(b)->{b.setPlatStr("browser").setCheckTitleChanges(true);});
final AnalyzerInfo anaMobile=defAnaR1X("kaola-mobile",()->AnalyKaoLaMusicMobile::new,(b)->{b.setPlatStr("mobile").setCheckTitleChanges(true);});
        src.setAnaDispatcher((ctx)->{
        ctx.addPass(anaWeb);ctx.addPass(anaMobile);
        },-1);
        defSearcherBrowserSKX("kaola",()->SearchKaoLaFMMusicPC::new,(b)->{b.tagMusic().tagAudiobook();});
        defSearcherMobileSKX("kaola-mobile",()->SearchFmKaolaMusicMobile::new,(b)->{b.tagMusic().tagAudiobook();});
        defAppX("kaola-pc","#kaola","网页端",(b)->{b.setPlatStr("browser").setSearchersV("kaola");});
        defAppX("kaola-mobile","kaola","移动端",(b)->{b.setPlatStr("mobile").setSearchersV("kaola-mobile");});
        });
        defSrcDR1("kbox","KBOX","ktv.show.sina.com.cn",()->AnalyKboxMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("kbox",()->SearchKboxMusicPC::new,(b)->{b.tagMusic();});
        defAppX("kbox-pc","#kbox","网页端",(b)->{b.setSearchersV("kbox");});
        });
        defSrcDR1("lizhifm","荔枝FM","lizhi.fm",()->AnalyLizhiFMVideoPC::new,1).doIt((src)->{ // would quickly 403 when accessed with multiple threads
        defSearcherBrowserFKX("lizhifm-audio",(kd)->new SearchLizhiFMMusicPC(kd,PlatformLiZhi.audio),(b)->{b.tagMusic().tagAudiobook();});
        defAppX("lizhifm-mobile","lizhifm","移动端",(b)->{b.setSearchersV("lizhifm-audio").setAllAccessible();}); // same as the browser results; links are http
        });
        defSrcD("chrrs","樱桃音乐","chrrs.com").doIt((src)->{
        src.setSingleAna(defAnaR1X("chrrs-android",()->AnalyChrrsMusicAndroid::new,(b)->{b.setPlatStr("mobile");}),-1);
        defSearcherMobileSKX("chrrs-android",()->SearchChrrsVideoAndroid::new,(b)->{b.setBad().tagMusic();}); // the app no longer works as of Jan 3 2017
        defAppX("chrrs-android","chrrs","移动端",(b)->{b.setSearchersV("chrrs-android");});
        });
        defSrcD("ifengfm","凤凰FM","diantai.ifeng.com").doIt((src)->{
        src.setSingleAna(defAnaR1X("ifengfm-mobile",()->AnalyIfengFMMusicMobile::new,(b)->{b.setPlatStr("mobile");}),-1);
        defSearcherBrowserFKX("ifengfm-song",(kd)->new SearchIfengFMMusicPC(kd,SearchIfengMode.SONG),(b)->{b.tagMusic().tagAudiobook();});
        defAppX("ifengfm-pc","#ifengfm","网页端",(b)->{b.setSearchersV("ifengfm-song").setAllAccessible();}); // we have webUrl now
        defAppX("ifengfm-mobile","ifengfm","移动端",(b)->{b.setSearchersV("ifengfm-song");}); // searching by song yields identical results
        });
        defSrcDDirect("duole","多乐FM","duole.com;duole.fm",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1).doIt((src)->{
        defSearcherBrowserSKX("duole-android",()->SearchFmDuoleMusicPC::new,(b)->{b.tagMusic().tagAudiobook();}); // called duole-android rather than duole for compatibility
        defAppX("duole-android","duole","移动端",(b)->{b.setSearchersV("duole-android");});
        });
        defSrcDC("qianqian","千千静听","qianqian.baidu.com;qianqian.com",()->VideoProcessorQianQian::new,1).doIt((src)->{ // with baidu
        defSearcherMobileSKX("qianqian-bd-mobile",()->SearchQQJTMusicPad::new,(b)->{b.tagMusic();}); // the version owned by baidu
        defAppX("qianqian-pc","#qianqian","网页端",(b)->{b.setAllAccessible();});
        defAppX("qianqian-mobile","*qianqian","移动端",(b)->{b.setSearchersV("qianqian-bd-mobile");});
        });
        defSrcDC("aiting","爱听","aiting.com",()->VideoProcessorAiTing::new,-1).doIt((src)->{
        defSearcherBrowserSKX("aiting",()->SearchAitingMusicPC::new,(b)->{b.tagMusic();});
        });
        defSrcDC("duomi","多米","duomi.com",()->VideoProcessorDuoMi::new,-1).doIt((src)->{
        defSearcherBrowserSKX("duomi",()->SearchDuomiMusicPC::new,(b)->{b.tagMusic();});
        // duomi's vids have corresponding webUrls, so searchability is not essential...
        defAppX("duomi-pc","#duomi","PC端",(b)->{b.setAllAccessible().setSearchersV("duomi");});
        defAppX("duomi-mobile","duomi","移动端",(b)->{b.setSearchersV("duomi");});
        });
        defSrcDC("360music","360音乐","music.haosou.com;music.so.com",()->VideoProcessor360Music::new,-1).doIt((src)->{
        defSearcherBrowserSKX("360music",()->Search360musicMusicPC::new,(b)->{b.setBad();}); // Search360MusicPC does work; it now returns external results
        });
        defSrcDC("sogoumusic","搜狗音乐","music.sogou.com",()->VideoProcessorSogou::new,1).doIt((src)->{ // limited access?
        defSearcherBrowserSKX("sogou-music",()->SearchSogouMusicPC::new,(b)->{b.tagMusic();});
        defAppX("sogoumusic-pc","#sogoumusic","网页端",(b)->{b.setAllAccessible();});
        });
        defSrcD("luoo","落网","luoo.net").doIt((src)->{
        src.setSingleAna(defAnaCX("luoo",()->MusicProcessorLuoo::new,(b)->{b.setPlatStr("direct");}),-1);
        defSearcherBrowserSKX("luoo",()->SearchLuooMusicPC::new,(b)->{b.tagMusic();});
        defAppX("luoo-pc","#luoo","网页端",(b)->{b.setNonVidAccessible().setSearchersV("luoo");}); // the urls are http and are directly accessible in the browser, thus the "+"
        defAppX("luoo-mobile","luoo","移动端",(b)->{b.setSearchersV("luoo");});
        });
        defSrcD("papa","音乐圈","papa.me;ppsrc.com").doIt((src)->{
        src.setSingleAna(defAnaCX("papa-direct",()->MusicProcessorPapa::new,(b)->{b.setPlatStr("direct");}),-1);
        defSearcherBrowserFKX("papa",(kd)->new SearchPapaMusicPC(SearchPapaMusicMode.ME),(b)->{b.setForcedKeywordDefaultP(50).tagMusic();}); // no keyword
        defSearcherBrowserFKX("papa-mobile-chan",(kd)->new SearchPapaMusicVideoAndroid(kd,SearchPapaMobileMusicMode.CHANNEL),(b)->{b.tagMusic();});
        defSearcherBrowserFKX("papa-mobile-tag",(kd)->new SearchPapaMusicVideoAndroid(kd,SearchPapaMobileMusicMode.TAG),(b)->{b.tagMusic();});
        defAppX("papa-pc","#papa","网页端",(b)->{b.setNonVidAccessible().setSearchersV("papa");}); // ditto; the mobile searchers are unavailable at the browser side, so search keywords from there won't be shown, but the links are still available.
        defAppX("papa-mobile","papa","移动端",(b)->{b.setSearchersV("papa-mobile-chan","papa-mobile-tag");});
        });
        defSrcDDirect("qiniu","七牛CDN","qiniucdn.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1); // used by lrts
        // FIXME: should include the entire 520tingshu.com site, but currently we want to leave the main site to the small-site resolver, so main site urls must be marked unresolveable here
        defSrcDDirect("520tingshu","520听书网","tingmp3.520tingshu.com;kiohhb.520tingshu.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,2);
        defSrcDDirect("kuwots","kuwots","ting.kuwots.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,2);
        defSrcD("lrts","懒人听书","lrts.me;mting.info;miloli.info;wting.info;kting.info;ating.info").doIt((src)->{
final AnalyzerInfo anaPlaylist=defAnaR1X("lrts-playlist",()->AnalyLanRenTSMusicPC::new,null);
final AnalyzerInfo anaAlbum=defAnaR1X("lrts-album",()->AnalyLanRenTingShuMusicPC::new,null);
final AnalyzerInfo anaDirectVid=defAnaDirectX("lrts-direct-vid",DirectVideoUrlMode.NEED_DLPARAM,null);
final AnalyzerInfo anaDirect=defAnaDirectX("lrts-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://www.lrts.me/sound/"))ctx.addPass(anaAlbum);
        else if(url.startsWith("vid-lrts://album/"))throw new RuntimeException("lrts album urls should have been normalized into web urls");
        else if(url.startsWith("http://www.lrts.me/ajax/playlist"))ctx.addPass(anaPlaylist);
        else if(url.startsWith("vid-lrts://book/"))ctx.addPass(anaDirectVid); // can't check status though
        else ctx.addPass(anaDirect); // for old results that uses the download url directly; the token quickly expires though.
        },-1);
        defSearcherBrowserFKX("lanrentingshu",(kd)->new SearchLanRentingshu(kd,SearchLanRenTingShuMode.ALBUM),(b)->{b.tagMusic();});
        defSearcherBrowserFKX("lanrentingshu-book",(kd)->new SearchLanRentingshu(kd,SearchLanRenTingShuMode.BOOK),(b)->{b.tagAudiobook();});
        defAppX("lrts-pc","#lrts","网页端",(b)->{b.setSearchersV("lanrentingshu","lanrentingshu-book");}); // NOTE: All album links should have been normalized to e.g. http://www.lrts.me/sound/740715
        defAppX("lrts-mobile","lrts","移动端",(b)->{b.setSearchersV("lanrentingshu","lanrentingshu-book");});
        });
        defSrcDR1("kwting","酷我听书","kwting.com",()->AnalyKwtingMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserFKX("kwting",(kd)->new SearchKuwotingshuMusicPC(SearchKuwotingshuMode.TITLE,kd),(b)->{b.setTkeywordSleep(8000L).tagMusic().tagAudiobook();}); // NOTE: the site explicitly says at most one search per 6 seconds; page flipping speed does not appear to be limited
        });
        defSrcDDirect("kting","酷听","audio.kting.cn",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1).doIt((src)->{
        defSearcherBrowserSKX("kting",()->SearchKtingMusicPC::new,(b)->{b.tagMusic().tagAudiobook();}); // as of Jan 17 2018, even the actual app seems to have stopped working...
        });
        defSrcDDirect("duotin","多听","duotin.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1).doIt((src)->{
        defSearcherBrowserSKX("duotin",()->SearchFmDuotinMusicPC::new,(b)->{b.tagMusic().tagAudiobook();});
        defSearcherMobileSKX("duotin-mobile",()->SearchDuotingfmMusicAndroid::new,(b)->{b.tagAudiobook();}); // not quite the same as duotin
        defAppX("duotin-pc","duotin","网页端",(b)->{b.setNonVidAccessible().setSearchersV("duotin").addAliasA("多听FM","多听FM网页端");}); // webUrl is not usable as of Jun 5 2017, but the direct dlUrl still works...
        defAppX("duotin-mobile","#duotin","移动端",(b)->{b.setSearchersV("duotin-mobile").addAliasA("多听FM移动端");});
        });
        defSrcDC("666ccc","今生缘音乐网","666ccc.com",()->MusicProcessor666ccc::new,-1).doIt((src)->{
        defSearcherBrowserFKX("jinshengyuan",(kd)->new SearchJinshengyuanMusicPC(kd,Platform666ccc.song),(b)->{b.setTsleep(5000L).tagMusic();}); // can block access
        });
        defSrcDR1("fm101","动感101泡菜电台","fm101.cn",()->AnalyFm101MusicPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("fm101",()->GetFm101MusicPC::new,(b)->{b.setForcedKeywordDefaultP(100);});
        });
        defSrcDR1("kufm","酷FM","kufm.cn",()->AnalyKuFMAndroid::new,1).doIt((src)->{ // times out or returns 502 if accessed too frequently
        defSearcherMobileSKX("kufm-mobile",()->SearchKufmMusicAndroid::new,(b)->{b.tagMusic();}); // also supports direct downloading, but now AnalyKuFMAndroid works anyway
        defAppX("kufm","kufm","网页端",(b)->{b.setSearchersV("kufm-mobile").setAllAccessible();});
        defAppX("kufm-mobile","kufm","安卓端",(b)->{b.setSearchersV("kufm-mobile").setAllAccessible();});
        });
        defSrcDR1("anyradio","优听","anyradio.cn",()->AnalyYoutingRadioMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserFKX("anyradio-chapter",(kd)->new SearchYoutingRadioMusicPC(YoutingSearchMode.CHAPTER,kd),(b)->{b.tagMusic().tagAudiobook();});
        defAppX("anyradio","#anyradio","网页端",(b)->{b.setSearchersV("anyradio-chapter").setAllAccessible().setOffline();});
        defAppX("anyradio-mobile","anyradio","移动端",(b)->{b.setSearchersV("anyradio-chapter").setAllAccessible();}); // webUrl usable on mobile browsers
        });
        defSrcDR1("o2ting","氧气听书","o2ting.com",()->AnalyYangqitingshuMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("o2ting",()->SearchYangqitingshuMusicPC::new,(b)->{b.tagMusic();});
        defAppX("o2ting","#o2ting","网页端",(b)->{b.setSearchersV("o2ting").setAllAccessible();}); // has webUrl
        defAppX("o2ting-mobile","o2ting","移动端",(b)->{b.setSearchersV("o2ting").setAllAccessible();});
        });
        defSrcDR1("tingbook","天方听书网","tingbook.com",()->AnalyTingBook::new,2).doIt((src)->{
        defSearcherBrowserSKX("tingbook",()->SearchTingbookMusicPc::new,(b)->{b.tagAudiobook();});
        defAppX("tingbook","#tingbook","网页端",(b)->{b.setSearchersV("tingbook").setAllAccessible();}); // book has webUrl
        });
        defSrcDR1("tingchina","听中国","tingchina.com",()->AnalyTingchinaMusicAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("tingchina-android",()->SearchTingchinaMusicAndroid::new,(b)->{b.setKwMode(AnimeKeywordMode.ALBUM).tagMusic().tagAudiobook();});
        defAppX("tingchina","#tingchina","网页端",(b)->{b.setSearchersV("tingchina-android").setAllAccessible();}); // has webUrl
        defAppX("tingchina-android","tingchina","安卓端",(b)->{b.setSearchersV("tingchina-android");});
        });
        defSrcDR1("yuanmusic","元音乐","yuanmusic.com",()->AnalyYuanyinyueMusicAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("yuanmusic-android",()->SearchYuanyinyueMusicAndroid::new,(b)->{b.tagMusic();});
        defAppX("yuanmusic","#yuanmusic","网页端",(b)->{b.setSearchersV("yuanmusic-android").setAllAccessible();}); // has webUrl
        defAppX("yuanmusic-android","yuanmusic","安卓端",(b)->{b.setSearchersV("yuanmusic-android");});
        });
        defSrcDR1("aiyinsitanfm","爱因斯坦FM","aiyinsitanfm.com",()->AnalyAiyinsitanfmMusicAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("aiyinsitanfm-android",()->SearchAiyinsitanfmMusicAndroid::new,(b)->{b.tagMusic().tagAudiobook();});
        defAppX("aiyinsitanfm","#aiyinsitanfm","网页端",(b)->{b.setSearchersV("aiyinsitanfm-android").setAllAccessible();}); // has webUrl
        defAppX("aiyinsitanfm-android","aiyinsitanfm","安卓端",(b)->{b.setSearchersV("aiyinsitanfm-android");});
        });
        defSrcDR1("jitingfm","即听FM","fm.hebrbtv.com",()->AnalyJitingfmMusicAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("jitingfm-android",()->SearchJitingfmMusicAndroid::new,(b)->{b.setKwMode(AnimeKeywordMode.ALBUM).tagMusic().tagAudiobook();});
        defAppX("jitingfm","#jitingfm","网页端",(b)->{b.setSearchersV("jitingfm-android").setAllAccessible();}); // has webUrl
        defAppX("jitingfm-android","jitingfm","安卓端",(b)->{b.setSearchersV("jitingfm-android");});
        });
        defSrcDR1("ivoix","广雅听书","ivoix.cn",()->AnalyGuangyatingshuMusicAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("guangyatingshu-android",()->SearchGuangyatingshuMusicAndroid::new,(b)->{b.setKwMode(AnimeKeywordMode.ALBUM).tagAudiobook();});
        defAppX("guangyatingshu-android","ivoix","安卓端",(b)->{b.setSearchersV("guangyatingshu-android").setAllAccessible();}); // no webUrl, but hopefully the status checking mechanism is sufficient...
        });

        // local government sites
        defSrcDR1("ahtv","安徽网络电视台","ahtv.cn;cnahtv.cn",()->AnalyAhtvVideoPC::new,-1);
        defSrcDR1("hntv","大象网","hntv.tv",()->AnalyHntvVideoPC::new,-1); // not sure about blocking, but the site is quite slow; perhaps we need a high number of threads to achieve reasonable throughput.
        defSrcD("bestv","百视通","bestv.com.cn").doIt((src)->{
final AnalyzerInfo anaMobile=defAnaCX("bestv-mobile",()->VideoProcessorBestvMobile::new,(b)->{b.setPlatStr("mobile");});
final AnalyzerInfo anaOtt=defAnaR1X("bestv-ott",()->AnalyBesttvVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-bestv://"))ctx.addPass(anaMobile);
        else ctx.addPass(anaOtt);
        },-1);
        defSearcherD("bestv-mobile"); // used to be SearchBaishitongVideoMobile::new; now no longer exists
        defSearcherD("bestv-ott");
        defAppX("bestv-mobile","bestv","安卓端$移动端#百视通影视",(b)->{b.setSearchersV("bestv-mobile");});
        defAppX("bestv-ott","bestv","OTT",(b)->{b.setSearchersV("bestv-ott").addAliasA("百视通OTT","百事通OTT","百视通盒子OTT","百事通盒子OTT");});
        });
        defSrcDR1("cditv","橙网在线","cditv.cn",()->AnalyChengwangtvVideoPC::new,-1);
        defSrcDR1("dragontv","东方卫视","dragontv.cn",()->AnalyDongfangkptvVideoPC::new,-1);
        defSrcDR1("echannel","EChannel","echannel.com.cn",()->AnalyEchannelVideoPC::new,-1);
        defSrcDR1("gdtv","广东电视台","gdtv.cn",()->AnalyGuangdongtvVideoPC::new,-1);
        defSrcDR1("gxtv","广西电视网","gxtv.cn",()->AnalyGuangxitvVideoPC::new,-1);
        defSrcDR1("gztv","广视网","gztv.com",()->AnalyGuangzhoutvVideoPC::new,-1);
        defSrcDR1("gzstv","贵视网","gzstv.com",()->AnalyGuizhoutvVideoPC::new,-1);
        defSrcD("hebtv","河北网络电视台","hebtv.com;nongmintv.com").doIt((src)->{
final AnalyzerInfo anaHebtv=defAnaR1X("hebtv",()->AnalyHebtvVideoPC::new,null);
final AnalyzerInfo anaNongmintv=defAnaR1X("nongmintv",()->AnalyNongmintvVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"hebtv.com"))ctx.addPass(anaHebtv);
        else if(CommonUtil.hostInDomain(host,"nongmintv.com"))ctx.addPass(anaNongmintv);
        },-1);
        });
        defSrcDR1("hljtv","黑龙江网络广播电视台","hljtv.com",()->AnalyHljtvLivePC::new,2);
        defSrcDR1("sctv","四川网络广播电视台","sctv.cn",()->AnalySctvLivePC::new,2);
        defSrcDR1("dltv","大连广播电视台","dltv.cn",()->AnalyDltvLivePC::new,2);
        defSrcDR1("hsw","华商网","hsw.cn",()->AnalyHuashangtvVideoPC::new,-1);
        defSrcD("jstv","江苏网络电视台","jstv.com").doIt((src)->{
final AnalyzerInfo anaV=defAnaR1X("jstv-v",()->AnalyJstvVideoPC::new,null);
final AnalyzerInfo anaWeb=defAnaR1X("jstv",()->AnalyJiangshutvVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{ctx.addPass(anaV);},-1);
        defSearcherBrowserSKX("jstv",()->SearchJstvVideoPC::new,null);
        defSearcherD("lizhi-tv");
        defAppX("lizhi-tv","*jstv","TV端",(b)->{b.setSearchersV("lizhi-tv").addAliasA("荔枝视频TV端");});
        });
        defSrcDR1("jxgdw","今视网","jxgdw.com",()->AnalyJiangxitvVideoPC::new,-1);
        defSrcDR1("veve","南方TV","veve.tv;utvgo.com",()->AnalyNanfangtvVideoPC::new,-1);
        defSrcDR1("iqilu","齐鲁网","iqilu.com",()->AnalyShandongtvVideoPC::new,-1);
        defSrcDR1("topway","天威","topway.cn",()->AnalyTopwaytvVideoPC::new,-1);
        defSrcDR1("xiancity","西安城市","xiancity.cn",()->AnalyXiantvVideoPC::new,-1);
        defSrcDR1("boosj","播视网","boosj.com",()->AnalyBoosjVideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("boosj",()->SearchBoosjVideoPC::new,(b)->{b.tagPcVideo();});
        });
        defSrcD("cnlive","视讯中国","cnlive.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("cnlive",()->AnalyCnliveVideoPC::new,null);
final AnalyzerInfo anaMobile=defAnaR1X("cnlive-android",()->AnalyMobileVideoAndroid::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("cnlive-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://vod.cnlive.com:8080/video_files")||url.startsWith("http://cmcc.ips.cnlive.com/content/movie"))ctx.addPass(anaDirect);
        else if(url.startsWith("http://data.cnlive.com"))ctx.addPass(anaMobile);
        else ctx.addPass(anaWeb);
        },1);
        });
        defSrcDR1("cqnews","华龙网","cqnews.net",()->AnalyCqnewsVideoPC::new,-1);
        defSrcDR1("cutv","城视网","cutv.com",()->AnalyCutvVideoPC::new,-1);
        defSrcDR1("feixiangtv","飞翔TV","feixiangtv.com",()->AnalyFeiXiangtvVideoPC::new,-1);
        defSrcDR1("hexun","和讯网","hexun.com",()->AnalyHeXunVideoPC::new,-1);
        defSrcDR1("hinews","南海网","hinews.cn",()->AnalyHinewsVideoPC::new,-1);
        defSrcDR1("jxyinyue","江西音乐","jxyinyue.com",()->AnalyJxyinyueVideoPC::new,-1);
        defSrcDR1("kids21","中国未成年人网","kids21.cn",()->AnalyKids21VideoPC::new,-1);
        defSrcDR1("moxtv","魔视网","moxtv.cn",()->AnalyMoxtvVideoPC::new,-1);
        defSrcDR1("nbasina","新浪NBA","roll.news.sina.com.cn",()->AnalyNbaVideoPC::new,-1);
        defSrcD("nba","NBA","nba.com");
        defSrcDC("ououv","OUOUV","ououv.com",()->VideoProcessorOUOUV::new,-1);
        defSrcDC("p5w","全景网","p5w.net",()->VideoProcessorP5wTV::new,-1);
        defSrcDC("sznews","深圳新闻网","sznews.com",()->VideoProcessorSZNews::new,-1);
        defSrcD("mp4ba","mp4吧","*mp4ba.example.com"); // the mp4ba in qq's license list; appears to have been taken down, not the mp4ba.com etc. sites that we are familiar with

        defSrcD("aipai","爱拍","aipai.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("aipai",()->AnalyAipaiVideoPC::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("aipai-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://hc"))ctx.addPass(anaDirect);else ctx.addPass(anaWeb);
        },1);
        defSearcherBrowserSKX("aipai",()->SearchAipaiVideoPC::new,(b)->{b.tagPcVideo();});
        defAppX("aipai-mobile","aipai","移动端",(b)->{b.setSearchersV("aipai");});
        });
        defSrcDC("liaku","LiaKu","liaku.com",()->VideoProcessorLiaKu::new,-1);
        defSrcDR1("pinshan","品善网","pinshan.com",()->AnalyPinshanVideoPC::new,-1);
        defSrcDC("uusee","悠视","uusee.com;henkuai.com",()->VideoProcessorUusee::new,-1).doIt((src)->{
        defSearcherBrowserSKX("uusee",()->SearchUuseeVideoPC::new,(b)->{b.tagPcVideo();}); // is it downloading videos correctly?
        });
        defSrcDR1("v1","第一视频","v1.cn",()->AnalyV1VideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("v1",()->SearchV1VideoPC::new,(b)->{b.tagPcVideo();}); // FIXME: even the actual search page seems to be unusable as of Nov 29 2017
        });
        defSrcDC("yicai","一财网","yicai.com",()->VideoProcessorYiCai::new,-1);
        defSrcD("jlntv","吉林电视台","v.jlntv.cn");

        defSrcD("bilibili","Bilibili","bilibili.com;bilibili.tv").doIt((src)->{
        src.setSingleAna(defAnaR1X("bilibili",()->AnalybilibiliVideoPC::new,(b)->{b.setCheckDurationChanges(true);}),2); // there used to be some mix-ups regarding avId and cid, so it is necessary to check the duration.
        defSearcherBrowserFKX("bilibili",(kd)->new SearchbilibiliVideoPC(kd,new SearchBilibiliVideoOpt()),(b)->{b.tagPcVideo();});
        // FIXME: vid-bilibili stuff must be searchable, even though av stuff does not need to be
        // NOTE: As of Mar 21 2016, the old bilibili mobile searcher no longer works, and we use browser-side results instead; NonVidAccessible is set because av numbers are accepted by the app
        defSearcherMobileSKX("bilibili-mobile",()->SearchBilibiliVideoMobile::new,(b)->{b.setBad();});
        defSearcherMobileFKX("bilibili-mobile2",(kd)->new SearchBilibiliVideoAndroid(BilibiliSearchMode.ALL,kd),(b)->{b.tagMobileVideo();});
        defAppX("bilibili-mobile","bilibili","安卓端$移动端#Bilibili",(b)->{b.setNonVidAccessible().setSearchersV("bilibili-mobile2").addAliasA("Bilibili移动端","bilibili移动端","哔哩哔哩移动端","哔哩哔哩动画移动端","哔哩哔哩动画安卓端","哔哩哔哩动画android端","BilibiliIOS端","bilibiliIOS端","哔哩哔哩弹IOS端");});
        });
        defSrcDR1("acfun","AcFun","acfun.cn;acfun.tv;acfun.com",()->AnalyAcfunVideoPC::new,1).doIt((src)->{ // -1 causes blocking (Jun 9 2015); 2 also causes blocking (Jun 23 2015); actually even 1 causes significant slowdowns (Apr 3 2017).
        defSearcherBrowserSKX("acfun",()->SearchAcfunVideoPC::new,(b)->{b.setTsleep(2000L).tagPcVideo();}); // searching blocked (even when using the actual site) at 10.0.0.25 as of Feb 28 2018; now uses proxies
        defSearcherMobileSKX("acfun-mobile",()->SearchAcfunVideoMobile::new,(b)->{b.setTsleep(4000L).tagMobileVideo();}); // SearchAixifanMobile has been partly merged there
        defAppX("acfun-mobile","acfun","安卓端$移动端#AcFun",(b)->{b.setSearchersV("acfun-mobile").addAliasA("AcFun移动端","AcFun安卓端","AcFunIOS端","Acfun移动端","AcfunIOS端","ACFUN移动端","acfun移动端","acfun安卓端","acfunIOS端");});
        });
        defSrcDC("sochi","索契冬奥会应用","sochi.example.com",()->VideoProcessorSochi::new,-1).doIt((src)->{ // mobile only
        defSearcherMobileFKX("sochi-mobile",(kd)->new SearchSochiDAHVideoMoblie(),(b)->{b.setForcedKeywordDefaultP(50);});
        defAppX("sochi-mobile","sochi","安卓端#索契冬奥会应用",(b)->{b.setSearchersV("sochi-mobile");});
        });
        defSrcDC("kumi","酷米","kumi.cn",()->VideoProcessorKumi::new,-1).doIt((src)->{
        defSearcherBrowserSKX("kumi",()->SearchKumiMusicPC::new,(b)->{b.tagPcVideo();});
        });
        defSrcDC("61","淘米","v.61.com",()->VideoProcessor61::new,-1).doIt((src)->{
        defSearcherBrowserSKX("61",()->SearchV61VideoPC::new,(b)->{b.tagPcVideo();});
        });
        defSrcD("meipai","美拍","meipai.com;meitudata.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("meipai",()->AnalyMeipaiVideoPC::new,null);
final AnalyzerInfo anaDirect=defAnaDirectX("meipai-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"meipai.com"))ctx.addPass(anaWeb);
        else ctx.addPass(anaDirect);
        },4); // at -1 it sometimes returns bad encUrl's
        defSearcherBrowserSKX("meipai",()->SearchMeipaiVideoPC::new,(b)->{b.tagPcVideo();});
        defAppX("meipai-pc","#meipai","网页端",(b)->{b.setSearchersV("meipai").setAllAccessible();}); // anaWeb urls are directly accessible
        defAppX("meipai-mobile","meipai","移动端",(b)->{b.setSearchersV("meipai");}); // FIXME: should actually assume all urls of the right form to be accessible, but ignore the urls that can never be generated by the searcher.
        });
        defSrcDR1("miaopai","秒拍","miaopai.com;yixia.com",()->AnalyMiaopaiVideoPC::new,2).doIt((src)->{ // somewhat blocked on Mar 6 2017
        defSearcherMobileSKX("miaopai-mobile",()->SearchMiaopaiVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("miaopai","#miaopai","网页端",(b)->{b.setSearchersV("miaopai-mobile").setAllAccessible();}); // has webUrl
        defAppX("miaopai-mobile","miaopai","移动端",(b)->{b.setSearchersV("miaopai-mobile").addAliasA("秒拍移动端","秒拍IOS端");});
        });
        defSrcD("toutiao","今日头条","toutiao.com;m.zjurl.cn;365yg.com;ixigua.com").doIt((src)->{
final AnalyzerInfo anaOld=defAnaR1X("toutiao-old",()->AnalyJinritoutiaoVideoMobile::new,null);
final AnalyzerInfo anaWeb=defAnaR1X("toutiao-web",()->AnalyJinRiTouTiaoVideo::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("vid-toutiao://"))ctx.addPass(anaOld); // old API
        else ctx.addPass(anaWeb); // new API for toutiao-news; probably works better than VideoProcessorToutiaoNews
        },1);
        defSearcherMobileSKX("toutiao-mobile",()->SearchJinritoutiaoVideoAndroid::new,(b)->{b.setTsleep(8000L).setRedundant();});
        defSearcherMobileSKX("toutiaosp-mobile",()->SearchToutiaoshipingVideoAndroid::new,(b)->{b.setTsleep(8000L).setRedundant();});
        defSearcherBrowserSKX("365yg",()->Search365ygVideoPC::new,(b)->{b.setTsleep(8000L).tagPcVideo();}); // easily gets blocked
        defAppX("toutiao","#toutiao","网页端",(b)->{b.setNonVidAccessible();});
        defAppX("toutiao-mobile","toutiao","移动端",(b)->{b.setSearchersV("365yg").addAliasA("今日头条移动端","今日头条IOS端");});
        defAppX("toutiaosp-mobile","toutiao","安卓端#西瓜视频",(b)->{b.setNonVidAccessible().setSearchersV("365yg").addAliasA("西瓜视频移动端","头条视频安卓端","头条视频移动端");});
        });
        defSrcDR1("pearvideo","梨视频","pearvideo.com",()->AnalyPearvideoVideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("pearvideo",()->SearchPearvideoVideoPC::new,(b)->{b.tagPcVideo();});
        defAppX("pearvideo-mobile","pearvideo","移动端",(b)->{b.setSearchersV("pearvideo");});
        });
        defSrcDR1("uctoutiao","UC头条","news.uc.cn;v.mp.uc.cn;uczzd.cn",()->AnalyUCtoutiaoVideoAndroid::new,2).doIt((src)->{
        defSearcherBrowserSKX("uctoutiao",()->SearchUCtoutiaoVideoAndroid::new,(b)->{b.setTsleep(3000L).tagPcVideo();}); // unsure about access limits...
        defAppX("uctoutiao","#*uctoutiao","网页端",(b)->{b.setAllAccessible();}); // has webUrl
        });
        // FIXME: dftoutiao should have been called eastday; it is too much trouble to rename now.
        defSrcD("dftoutiao","东方网","eastday.com;dftoutiao.com").doIt((src)->{ // also called 头条视频 (different from toutiao's version); used to be called 东方头条 even though the contents are not necessarily from dftoutiao.
final AnalyzerInfo anaMini=defAnaR1X("dftoutiao-mini",()->AnalyMaopuVideoAndroid::new,null);
final AnalyzerInfo anaVideo=defAnaR1X("dftoutiao-video",()->AnalyEastdayVideoPC::new,null);
final AnalyzerInfo anaSports=defAnaR1X("dftoutiao-sports",()->AnalyEastdaySportsVideoPC::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),host=CommonUtil.getURLHost(url);
        if(CommonUtil.hostInDomain(host,"mini.eastday.com"))ctx.addPass(anaMini);
        else if(CommonUtil.hostInDomain(host,"video.eastday.com"))ctx.addPass(anaVideo);
        else if(CommonUtil.hostInDomain(host,"sports.eastday.com"))ctx.addPass(anaSports);
        },-1);
        defSearcherBrowserFKX("dftoutiao-video",(kd)->new SearchEastdayVideoPC(EastdaySearchMode.VIDEO,kd),(b)->{b.tagPcVideo();});
        defSearcherBrowserFKX("dftoutiao-sports",(kd)->new SearchEastdayVideoPC(EastdaySearchMode.SPORTS,kd),(b)->{b.tagPcVideo();});
        defAppX("dftoutiao-mobile","dftoutiao","安卓端$移动端#东方头条",(b)->{b.setSearchersV("dftoutiao-video");});
        });
        defSrcD("mop","猫扑视频","mop.com").doIt((src)->{
        // NOTE: SearchMaopuVideoPC is no longer usable as of Jan 28 2018
        defSearcherMobileSKX("mop-android",()->SearchMaopuVideoPC::new,(b)->{b.tagMobileVideo();}); // mtv.mop.com => mini.eastday.com; actually still identical to dftoutiao-video once the mini.eastday.com urls are converted to video.eastday.com urls, but mini.eastday.com gives userNickname information that is not available from video.eastday.com.
        // NOTE: tv.mop.com gives the same results as dftoutiao-video; http://video.eastday.com/a/171210234133852188152.html is equivalent to http://tv.mop.com/video/171210234133852188152.html
        defAppX("mop-android","*mop","安卓端",(b)->{b.setSearchersV("mop-android");}); // same as 东方头条安卓端
        });
        defSrcDR1("osportsmedia","东方体育","osportsmedia.com",()->AnalyDongfangtiyuVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("osportsmedia-android",()->SearchDongfangtiyuVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("osportsmedia-android","#osportsmedia","安卓端#东方体育app",(b)->{b.setNonVidAccessible().setSearchersV("osportsmedia-android");}); // they are shtml files...
        });
        defSrcDR1("klm123","看了吗","klm123.com",()->AnalayKlm12VideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("klm123",()->GetKlm123VideoPC::new,(b)->{b.setForcedKeywordsP(()->GetKlm123VideoPC.categories.keySet(),200).tagPcVideo();});
        defSearcherMobileSKX("klm123-android",()->SearchKlm123VideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("klm123-android","klm123","安卓端",(b)->{b.setNonVidAccessible().setSearchersV("klm123-android").addAliasA("看了么安卓端","看了吗移动端","看了么移动端");});
        });
        defSrcDR1("baiduHaokan","好看视频","sv.baidu.com;haokan.baidu.com",()->AnalyHaokanshipingVideoAndroid::new,2).doIt((src)->{
        defSearcherBrowserSKX("baiduHaokan",()->GetHaokanShipingVideoAndroid::new,(b)->{b.setForcedKeywordsP(()->GetHaokanShipingVideoAndroid.catIds.keySet(),200).tagPcVideo();});
        defSearcherMobileSKX("baiduHaokan2",()->SearchHaokanshipingVideoIOS::new,(b)->{b.tagMobileVideo();});
        defAppX("baiduHaokan","#baiduHaokan","网页端",(b)->{b.setAllAccessible();}); // both baiduHaokan and baiduHaokan2 have webUrl
        defAppX("baiduHaokan-ios","baiduHaokan","IOS端",(b)->{b.setSearchersV("baiduHaokan2");});
        });
        defSrcDR1("1sapp","趣头条","1sapp.com;qktoutiao.com",()->Analy1sappVideo::new,2).doIt((src)->{
        defSearcherMobileSKX("1sapp",()->SearchQutoutiaoVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("1sapp-mobile","1sapp","安卓端",(b)->{b.setNonVidAccessible().setSearchersV("1sapp");}); // the same http urls will also be given in browser results
        });
        defSrcDR1("meiyou","美柚","meiyou.com;xmmeiyou.com;seeyouyima.com",()->AnalyMeiyouVideoIOS::new,2).doIt((src)->{
        defSearcherMobileSKX("meiyou-ios",()->SerachMeiyouVideoIOS::new,(b)->{b.tagMobileVideo();});
        defAppX("meiyou-ios","#meiyou","IOS端$移动端",(b)->{b.setAllAccessible().setSearchersV("meiyou-ios");}); // has webUrl
        });
        defSrcD("xiaokaxiu","小咖秀","xiaokaxiu.com");
        defSrcD("yizhibo2","一直播","yizhibo.com");
        defSrcD("neihanshequ","内涵社区","neihanshequ.com"); // 内涵段子app
        defSrcDR1("douyin","抖音","douyin.com",()->AnalyDouyinVideoAndroid::new,2).doIt((src)->{
        defSearcherMobileSKX("douyin-android",()->SearchDouyinVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("douyin","douyin","网页端",(b)->{b.setAllAccessible().setSearchersV("douyin-android");}); // has webUrl
        defAppX("douyin-android","douyin","安卓端",(b)->{b.setSearchersV("douyin-android");});
        });
        defSrcD("kuaishou2","快手","kuaishou.com"); // different from 快手看片 (ikuaishou.com)
        defSrcD("xiaoying","小影","xiaoying.tv");
        defSrcD("huoshanzhibo","火山小视频","huoshanzhibo.com");

        // foreign sites
        defSrcDR1("youtube","Youtube","youtube.com",()->AnalyYoutube2VideoPC::new,2).doIt((src)->{
        defSearcherBrowserFKX("youtube",(kd)->new SearchYoutubeVideoPC(YoutubeSearchMode.KEYWORD,kd),(b)->{b.setTsleep(10000L);}); // 5000L lead to CAPTCHA's
        });
        defSrcDR1("dailymotion","DailyMotion","dailymotion.com",()->AnalyDailymotionVideoPC::new,2).doIt((src)->{
        defSearcherBrowserSKX("dailymotion",()->SearchDailymotionVideoPC::new,(b)->{b.setTsleep(5000L);});
        });
        defSrcD("dramafever","Dramafever","dramafever.com");
        defSrcD("amazon","Amazon","amazon.com");
        defSrcD("facetook","Facebook","facebook.com");
        defSrcD("twitter","Twitter","twitter.com");
        defSrcD("instagram","Instagram","instagram.com");

        defSrcD("100tv","100tv","100tv.com");
        defSrcD("365pub","英雄宽频","365pub.com");
        defSrcD("baidu","百度","www.baidu.com").doIt((src)->{ // FIXME: baidu.com -- we don't want to include bdhd stuff for qiyi...
        defSearcherMX("baidu-web",(si)->new SearchBaiduWebInt(si),(b)->{b.setFmt(SearchResultFormat.WEB_NORMAL).setTsleep(7000L);});
        defSearcherD("baidu-web-mobile");
        defAppX("baidu-web","=baidu","网页端#百度搜索",(b)->{b.setSearchersV("baidu-web").setCatSearch();});
        defAppX("baidu-web-mobile","=baidu","安卓端#百度搜索",(b)->{b.setSearchersV("baidu-web-mobile");}); // FIXME: do not setCatSearch() for now; leave the results in the -Mobile file
        });
        defSrcD("skycn","天空下载","skycn.com");
        defSrcD("hao123","hao123","hao123.com");
        defSrcD("smg","SMG","smgbb.cn");
        defSrcD("baofeng","暴风","baofeng.com;baofeng.net;baofengcloud.com").doIt((src)->{
final AnalyzerInfo anaWeb=defAnaR1X("baofeng",()->AnalyBaofengVideoPC2::new,null);
final AnalyzerInfo anaCloud=defAnaR1X("baofeng-cloud",()->AnalyBaofengtvVideoAndroid::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith(AnalyBaofengtvVideoAndroid.baofengCloudUrlPrefix))ctx.addPass(anaCloud);
        else ctx.addPass(anaWeb); // including vid-baofeng:// and vid-storm:// urls
        },-1);
        defSearcherBrowserSKX("baofeng",()->SearchBaofengVideoPC::new,(b)->{b.tagPcVideo();});
        // As of Dec 25 2013, baofeng no longer seems to be hot-linking videos elsewhere
        // As of Jun 26 2014, only hot-linked results are reported
        defSearcherHotSKX("baofeng-mobile",()->SearchBaofengVideoIOS::new,(b)->{b.tagMobileVideo();});
        defSearcherHotSKX("baofeng-sports",()->GetBaofengtvVideoAndroid::new,(b)->{b.setForcedKeywordsP(()->GetBaofengtvVideoAndroid.categories.keySet(),200);}); // FIXME: access speed limited
        defAppX("baofeng-mobile","*baofeng","安卓端$移动端#暴风影音",(b)->{b.setSearchersV("baofeng-mobile","baofeng-sports");});
        });
        defSrcD("cnmobile","中国移动","cnmobile.example.com"); // mobile only (see the youku SBS license list)
        defSrcD("google","Google","google.com;google.com.hk;google.cn;google.co.uk;google.jp").doIt((src)->{
        defSearcherMX("google-web",(si)->new SearchGoogleWebInt(si),(b)->{b.setFmt(SearchResultFormat.WEB_NORMAL).setTsleep(8000L);});
        defSearcherBrowserSKX("google-video",()->SearchGooglevideoVideoPC::new,(b)->{b.setTsleep(3000L);});
        defAppX("google-web","=google","网页端#Google搜索",(b)->{b.setSearchersV("google-web").setCatSearch();});
        defAppX("googleVideo-web","=google","网页端#Google视频",(b)->{b.setSearchersV("google-video").setCatSearch();});
        });
        defSrcD("bing","Bing","bing.com").doIt((src)->{
        defSearcherD("bing-web");
        defAppX("bing-web","=bing","网页端#必应搜索",(b)->{b.setSearchersV("bing-web").setCatSearch();});
        });
        defSrcD("youdao","有道","www.youdao.com").doIt((src)->{
        defSearcherD("youdao-web");
        defAppX("youdao-web","=youdao","网页端#有道搜索",(b)->{b.setSearchersV("youdao-web").setCatSearch();});
        });
        defSrcD("yahoo","雅虎","www.yahoo.com;search.yahoo.com").doIt((src)->{
        defSearcherD("yahoo-web");
        defAppX("yahoo-web","=yahoo","网页端#雅虎搜索",(b)->{b.setSearchersV("yahoo-web").setCatSearch();});
        });
        defSrcD("chinaso","中国搜索","chinaso.com").doIt((src)->{
        defSearcherD("chinaso-web");
        defAppX("chinaso-web","=chinaso","网页端#中国搜索",(b)->{b.setSearchersV("chinaso-web").setCatSearch();});
        });
        defSrcD("hualu","华录网","hualu5.com;hualuwood.com;hualuwu.com");
        defSrcD("pipi","皮皮","pipi.cn;ppmovie.com;pipi.com");
        defSrcD("soso","搜搜","soso.com").doIt((src)->{
        defSearcherMX("soso-web",(si)->new SearchSosoWebInt(si),(b)->{b.setFmt(SearchResultFormat.WEB_NORMAL).setTsleep(4000L);});
        defSearcherMX("sosoVideo",(si)->new SearchModuleSoso(si),(b)->{b.tagPcVideo();});
        defAppX("soso-web","=soso","网页端#搜搜搜索",(b)->{b.setSearchersV("soso-web").setCatSearch();});
        defAppX("sosoVideo-web","=soso","网页端#搜搜视频",(b)->{b.setSearchersV("sosoVideo").setCatSearch();});
        });
        defSrcD("sogouVideo","搜狗视频","v.sogou.com").doIt((src)->{
        defSearcherMX("sogouVideo",(si)->new SearchModuleSogou(si),(b)->{b.setBad().tagPcVideo();});
        defAppX("sogouVideo-web","=sogouVideo","网页端#搜狗视频",(b)->{b.setSearchersV("sogouVideo").setCatSearch();});
        });
        defSrcD("sogou","搜狗搜索","www.sogou.com").doIt((src)->{
        defSearcherDX("sogou-web",(b)->{b.setFmt(SearchResultFormat.WEB_NORMAL);});
        defAppX("sogou-web","=sogou","网页端#搜狗搜索",(b)->{b.setSearchersV("sogou-web").setCatSearch();});
        });
        defSrcD("taohua","淘花","hua.taobao.com;video.taobao.com");
        defSrcD("vv8","VV8影视网","vv8.com;netmovie.com;netfilm.cn;165tv.com;163tv.com");
        defSrcD("weixin","微信","weixin.qq.com").doIt((src)->{
        // There is also a browser-side app
        defAppX("weixin-mobile","weixin","移动端",(b)->{b.setNonVidAccessible().addAliasA("微信移动端","微信移动客户端","微信移动客服端","微信移动端","微信文章");}); // doesn't require searchability
        });
        defSrcD("yod","优点","yod.com");
        defSrcD("zlvod","中录宽频","zlvod.com");
        defSrcD("hupu","虎扑体育","v.opahnet.com").doIt((src)->{
        defSearcherHotSKX("hupu",()->SearchHuputvVideoPC::new,null);
        defAppX("hupu-pc","#*hupu","网页端#虎扑体育",(b)->{b.setSearchersV("hupu");});
        });
        defSrcD("qq-weiyun","微云","weiyun.com");
        defSrcD("guangxian","光线传媒","xiankan.com");
        defSrcD("douyu","斗鱼TV","douyu.tv");
        defSrcD("bianfeng","边锋","bianfeng.com"); // actually own zhanqi?  kept separate for now (Nov 16 2016)
        defSrcD("huya","虎牙","huya.com");
        defSrcD("zhangyu","章鱼TV","zhangyu.tv");
        defSrcD("pandatv","熊猫TV","panda.tv");
        defSrcD("qmtv","QMTV","qmtv.com");
        defSrcD("plu","PLU游戏视频","plu.cn");
        defSrcD("shafa","沙发管家","shafa.com");
        defSrcD("baiduMarket","百度安卓市场","shouji.baidu.com");
        defSrcD("anzhi","安智市场","anzhi.com");
        defSrcD("yy","YY直播","www.yy.com");

        defSrcD("sina-blog","新浪博客","blog.sina.com.cn");
        defSrcDR1("sina-weibo","新浪微博","weibo.com",()->AnalySinaWeiboVideo::new,1); // We can only assume that, like video.sina.com.cn, access is limited quite severely at the weibo sites.
        defSrcDC("sohu-weibo","搜狐微博","t.sohu.com",()->WeiboAnalyzerSohu::new,-1);
        defSrcDC("163-weibo","网易微博","t.163.com",()->WeiboAnalyzer163::new,1);
        defSrcDC("qq-weibo","腾讯微博","t.qq.com",()->WeiboAnalyzerQQ::new,-1);

        defSrcDC("baodian","爆点TV","bao.tv",()->VideoProcessorBaoDianTV::new,-1);
        defSrcD("cztv","新蓝网","cztv.com").doIt((src)->{
final AnalyzerInfo anaTv=defAnaR1X("cztv-tv",()->AnalyCztvtvVideoPC::new,null);
final AnalyzerInfo anaMe=defAnaR1X("cztv-me",()->AnalyZhejiangtvVideoPC::new,null);
final AnalyzerInfo anaMobile=defAnaR1X("cztv-mobile",()->AnalyCztvVideoMobile::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://tv.cztv.com"))ctx.addPass(anaTv);
        else if(url.startsWith("http://me.cztv.com"))ctx.addPass(anaMe);
        else if(url.startsWith("http://m.tv.cztv.com"))ctx.addPass(anaMobile);
        },-1);
        });
        defSrcDR1("dilidili","嘀哩嘀哩","dilidili.com;dilidili.wang",()->AnalyDilidiliVideoPC::new,1).doIt((src)->{
        defSearcherBrowserSKX("dilidili",()->SearchDilidiliVideoPC::new,(b)->{b.setTsleep(5000L);});
        });
        defSrcDR1("tucao","吐槽网","tucao.tv",()->AnalytucaoVideoPC::new,1).doIt((src)->{
        defSearcherBrowserSKX("tucao",()->SearchtucaoVideoPC::new,(b)->{b.setTsleep(5000L);});
        });
        // For now, migu is the music site while cmvideo is for video
        // FIXME: in youku's licensing information, 和视界 is considered similar to migu
        defSrcDX("cmvideo","咪咕视频","miguvideo.com;lovev.com;cmvideo.cn",(b)->{b.tagTelecom();}).doIt((src)->{
final AnalyzerInfo anaHsj=defAnaR1X("cmvideo-heshijie",()->AnalyHeShiJieVideoPC::new,null);
final AnalyzerInfo anaMiguIos=defAnaR1X("cmvideo-migu-ios",()->AnalyMiguVideoIOS::new,null); // FIXME: all urls return novu as of Nov 25 2017; they are also unplayable in the actual browser
final AnalyzerInfo anaMiguTv=defAnaR1X("cmvideo-migutv",()->AnalyMiguTv::new,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),host=CommonUtil.getURLHost(url);
        if(url.startsWith("vid-cmvideo://video/"))ctx.addPass(anaMiguIos);
        else if(host.equals("tv.miguvideo.com"))ctx.addPass(anaMiguTv);
        else ctx.addPass(anaHsj);
        },1); // downloading too fast will lead to 503 errors during downloads.
        defSearcherBrowserSKX("miguVideo-ios",()->SearchMiguVideoIOS::new,(b)->{b.tagPcVideo();});
        defSearcherMobileSKX("miguZhibo-android",()->SearchMiguzhiboVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("miguVideo","#cmvideo","网页端",(b)->{b.setSearchersV("miguVideo-ios").setAllAccessible();});
        defAppX("miguVideo-ios","cmvideo","IOS端$移动端#咪咕视频",(b)->{b.setSearchersV("miguVideo-ios");});
        defAppX("miguZhibo-android","cmvideo","安卓端#咪咕直播",(b)->{b.setSearchersV("miguZhibo-android");});
        });
        defSrcDX("cmread","咪咕阅读","cmread.com",(b)->{b.tagTelecom();}).doIt((src)->{
        src.setSingleAna(defAnaR1X("cmread",()->AnalyMiguSimpler::new,null),-1);
        });
        defSrcD("itunes","iTunes","itunes.apple.com");
        defSrcD("icntv","未来电视","chinaott.net");

        defSrcDC("vlook","微录客","vlook.cn",()->VideoProcessorVlook::new,-1);
        defSrcDC("yakuhd","雅酷高清","yakuhd.com",()->VideoProcessorYaKu::new,-1);
        defSrcDC("nxtv","宁夏TV","nxtv.cn",()->VideoProcessorNingXia::new,-1);
        defSrcDC("cdstm","中国数字科技馆","cdstm.cn",()->VideoProcessorCdstm::new,-1);
        defSrcDR1("maxtv","迈播","maxtv.cn",()->AnalyMaxtvVideoPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("maxtv",()->SearchMaxtvVideoPC::new,(b)->{b.tagPcVideo();});
        });
        //defSrcDC("80ys.cn", "迈播", "80ys.cn", () -> VideoProcessor80YS::new, 1); //TODO 这个最后的链接是西瓜播放器 暂时无法下载
        //defSrcDC("cbg", "视界网", "cbg.cn", () -> VideoProcessorCBG::new, 1);  //TODO 协议是rtsp 无法下载
        defSrcDC("fs024","辽东网","fs024.com",()->VideoProcessorFs024::new,-1);
        defSrcDC("hcbtv","河池视频网","hcbtv.com",()->VideoProcessorHCBTV::new,-1);
        defSrcDC("pcauto","太平洋汽车","pcauto.com.cn",()->VideoProcessorPCAuto::new,-1);
        defSrcDC("tvquan","勤能影视","tvquan.cn",()->VideoProcessorQinNeng::new,-1);
        defSrcDC("scnj","甜橙网","scnj.tv",()->VideoProcessorTianCheng::new,-1);
        defSrcDC("uitv","联合网视","uitv.com;uitv.com.cn",()->VideoProcessorUitv::new,-1);
        defSrcDC("jeboo","捷报网","jeboo.com",()->VideoProcessorJeboo::new,-1);
        defSrcD("vmovier","V电影","vmovier.com").doIt((src)->{
final AnalyzerInfo anaPcDirect=defAnaDirectX("vmovier-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
final AnalyzerInfo anaPcVid=defAnaR1X("vmovier-pc",()->AnalyVmovierVideoPC::new,(b)->{b.setPlatStr("browser");});
@Deprecated final AnalyzerInfo anaMobile=defAnaR1X("vmovier-mobile",()->AnalyVmovierVideoMobile::new,(b)->{b.setPlatStr("mobile");});
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl(),host=CommonUtil.getURLHost(url);
        if(url.startsWith("vid-vmovier://"))ctx.addPass(anaPcVid);
        else if(CommonUtil.hostInDomain(host,"www.vmovier.com"))ctx.addPass(anaPcDirect);
        },-1);
        });
        defSrcDC("woyo","我友网","woyo.com",()->VideoProcessorWoyo::new,-1);
        defSrcDC("xwei","小微视频","xwei.tv",()->VideoProcessorXWei::new,-1);
        defSrcDC("weishi","微视","weishi.com",()->VideoProcessorWeiShi::new,-1);
        defSrcDDirect("ttmv","TT直播","ttmv.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1);
        defSrcDDirect("ikan","爱看动漫乐园","ikan.cn",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1);
        defSrcDC("yzntv","名城扬州","yzntv.com",()->VideoProcessorYzntv::new,-1);
        defSrcDDirect("colorv","彩视","colorv.cn",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1);
        defSrcDDirect("voole","优朋普乐","voole.com;5a5e.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1).doIt((src)->{
        defSearcherD("voole-ott");
        defAppX("voole-ott","voole","OTT",(b)->{b.setSearchersV("voole-ott").addAliasA("优朋普乐OTT","优朋普乐盒子");});
        });
        defSrcDR1("svipmovie","手机电影","svipmovie.com",()->AnalyShoujimovieAndroid::new,-1).doIt((src)->{
        defSearcherMobileSKX("svipmovie-android",()->SearchShoujimovieVideoAndroid::new,(b)->{b.tagMobileVideo();});
        // This is an HTML5 site, so it is also usable from the browser
        defAppX("svipmovie-android","svipmovie","安卓端",(b)->{b.setSearchersV("svipmovie-android").addAliasA("手机电影移动端");});
        });
        defSrcDDirect("huanliu","欢流","huanliu.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1);
        defSrcDDirect("ibofm","爱播FM","ibo.fm",DirectVideoUrlMode.NEED_DLPARAM,-1).doIt((src)->{
        defSearcherHotSKX("ibofm-ios",()->SearchAibofmMusicIOS::new,(b)->{b.tagAudiobook();});
        defAppX("ibofm-ios","*ibofm","IOS端",(b)->{b.setSearchersV("ibofm-ios").addAliasA("爱播FM移动端");});
        });
        defSrcD("tingshubao","听书宝","app.9nali.com").doIt((src)->{
        defSearcherHotSKX("tingshubao-ios",()->SearchTingshubaoMusicIOS::new,(b)->{b.tagAudiobook();});
        defAppX("tingshubao-ios","*tingshubao","IOS端",(b)->{b.setSearchersV("tingshubao-ios").addAliasA("听书宝移动端");});
        });
        defSrcDDirect("wisetv","万视达","8686c.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1);
        defSrcDDirect("yizhibo","易直播","yizhibo.tv",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1); // NOTE: different from yizhibo.com!
        defSrcDR1("zhibotv","直播TV","zhibo.tv",()->AnalyZhiboTvVideoPC::new,1); // we'd frequently get connection resets during downloading otherwise
        defSrcD("ntjoy","江海明珠网","ntjoy.com").doIt((src)->{
final AnalyzerInfo anaDirect=defAnaDirectX("ntjoy-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,null);
        src.setAnaDispatcher((ctx)->{
final String url=ctx.getUrl();
        if(url.startsWith("http://media.ntjoy.com"))ctx.addPass(anaDirect);
        },-1);
        });
        defSrcDR1("ajimide","阿基米德FM","ajmide.com;ajimide.com",()->AnalyAjimidefmMusicAndroid::new,-1).doIt((src)->{
        defSearcherMobileSKX("ajimide-android",()->SearchAjimidefmMusicAndroid::new,(b)->{b.tagMusic().tagAudiobook();});
        defAppX("ajimide-android","ajimide","安卓端",(b)->{b.setSearchersV("ajimide-android").setAllAccessible().addAliasA("阿基米德FM移动端","阿基米德安卓端","阿基米德移动端");}); // has web-accessible url (though not really playable)
        });
        defSrcD("shoujiduoduo","铃声多多","shoujiduoduo.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("shoujiduoduo-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("aliyuncs","阿里云","aliyuncs.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("aliyuncs-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("djyule-mobile","DJ娱乐网","jyw8.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("djyule-mobile-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcDR1("kuke","库客","kuke.com",()->AnalyKukeMusicAndroid::new,-1).doIt((src)->{
        defSearcherMobileSKX("kuke-android",()->SearchKukeMusicAndroid::new,(b)->{b.tagMusic();});
        defAppX("kuke-android","kuke","安卓端",(b)->{b.setSearchersV("kuke-android").setAllAccessible();});
        });
        defSrcD("lsdq-mobile","铃声大全","diyring.cc;kuyinyun.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("lsdq-mobile-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("mchang","麦唱","mchang.cn").doIt((src)->{
        src.setSingleAna(defAnaDirectX("mchang-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("lexiangdong","乐享动","ifitmix.com;igeekery.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("lexiangdong-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("moefm","萌否电台","moe.fm;90g.org").doIt((src)->{
        src.setSingleAna(defAnaDirectX("moefm-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("niting","你听","8zhuayule.com").doIt((src)->{
        src.setSingleAna(defAnaR1X("niting",()->AnalyNitingMusicAndroid::new,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("shuijingdj","水晶DJ","oscaches.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("shuijingdj-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("tianlaizhiyin","天籟之音","tienal.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("tianlaizhiyin-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("tuneshell","TuneShell","*tuneshell.apple.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("tuneshell-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcDC("5nd","5ND音乐","5nd.com",()->MusicProcessor5NDMusic::new,-1).doIt((src)->{
        defSearcherBrowserFKX("5nd-music-daqu",(kd)->new Search5ndMusicPC(kd,Search5ndMusicPC.PlatformFiveND.danqu),(b)->{b.tagMusic();});
        defSearcherBrowserFKX("5nd-music-zhuanji",(kd)->new Search5ndMusicPC(kd,Search5ndMusicPC.PlatformFiveND.zhuanji),(b)->{b.setRedundant().tagMusic();});
        });
        defSrcDDirect("changba","唱吧","changba.com",DirectVideoUrlMode.DIRECT_DOWNLOAD,-1).doIt((src)->{
        defSearcherBrowserFKX("changba",(kd)->new SearchChangbaMusicPC(),(b)->{b.setForcedKeywordDefaultP(50).tagMusic();});
        });
        defSrcDC("23356","56流行音乐","23356.com",()->MusicProcessor56Music::new,-1);
        defSrcD("y2002","Y2002","y2002.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("y2002-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("ktvdaren","移动练歌房","ktvdaren.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("ktvdaren-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcDC("imusic","爱音乐","imusic.com",()->MusicProcessorIMusic::new,-1);
        defSrcDC("2651","天地人音乐网","2651.com",()->MusicProcessorTiandiren::new,-1);
        defSrcDC("ting30","听三零音乐网","ting30.com",()->MusicProcessorTing30::new,-1);
        defSrcDC("tingall","诠音网","tingall.com",()->MusicProcessorTingAll::new,-1);
        defSrcDC("yymp3","音乐MP3","yymp3.com",()->MusicProcessorYymp3::new,1).doIt((src)->{
        defSearcherBrowserFKX("yymp3",(kd)->new SearchYymp3MusicPC(kd,PlatformYymp3.song),(b)->{b.tagMusic();});
        });
        defSrcDR1("51vv","VV音乐","51vv.com",()->Analy51vvMusicPC::new,-1).doIt((src)->{
        defSearcherBrowserSKX("51vv",()->Search51vvMusicPC::new,(b)->{b.tagMusic();});
        defAppX("51vv-pc","#51vv","网页端",(b)->{b.setNonVidAccessible().setSearchersV("51vv");});
        });
        defSrcDC("contentchina","喜福网","contentchina.com",()->VideoProcessorXifu::new,-1);
        defSrcDC("csztv","苏州台","csztv.com",()->VideoProcessorCsztv::new,-1);
        defSrcDC("hnntv","海南网络广播电台","hnntv.cn",()->VideoProcessorHainantv::new,-1);

        defSrcD("yinyuepai","音乐派","yyp.yy.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("yinyuepai-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("xueyuzangzu","雪域藏族音乐网","snowmusic.com.cn").doIt((src)->{
        src.setSingleAna(defAnaDirectX("xueyuzangzu-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("zan","赞","zanmeishi.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("zan-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("baidu-shiting","百度视听","zhangmenshiting.baidu.com;yinyueshiting.baidu.com").doIt((src)->{
        src.setSingleAna(defAnaDirectX("baidu-shiting-direct",DirectVideoUrlMode.DIRECT_DOWNLOAD,(b)->{b.setPlatStr("mobile");}),-1);
        });
        defSrcD("hanjutv","韩剧TV","hanju.koudaibaobao.com").doIt((src)->{
        defSearcherHotSKX("hanjutv-android",()->SearchHanJuTVVideoAndroid::new,(b)->{b.tagMobileVideo();});
        defAppX("hanjutv-android","*hanjutv","安卓端",(b)->{b.setSearchersV("hanjutv-android","baidu-mobile","bilibili-mobile2");});
        });
        defSrcDR1("idol001","爱豆","idol001.com",()->AnalyAidouappVideoAndroid::new,2).doIt((src)->{
        defSearcherHotFKX("idol001-android",(kd)->new SearchAidouappVideoAndroid(kd,AidouappSearchMode.VIDEO),(b)->{b.tagMobileVideo();});
        // FIXME: idol001-android's MOVIE mode does not work yet due to timeouts
        defAppX("idol001-android","*idol001","安卓端",(b)->{b.setSearchersV("idol001-android");});
        });
        defSrcD("nanguadianying","南瓜电影","*nanguadianying.example.com");
        defSrcD("haier","海尔","haier.com;xshuai.com");
        defSrcD("wekids","万童互动","wekids.com");
        defSrcD("crunchyroll","crunchyroll","crunchyroll.com");
        defSrcD("xyylnet","星游娱乐","xyylnet.com");
        defSrcD("ctsports","楚天运动频道","ctsports.com.cn");
		