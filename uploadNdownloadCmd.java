		/**
		 * 获取签名
		 * @param appid 凭证
		 * @param appsecret 密钥
		 * @return
		 */
		case M2M_CLIENT_V10_UPLOAD_PICTURE_CONFIG:{
			String url = request.getParameter("url");
			String appId = Constants.getString("weixin_appid");
			String secret = Constants.getString("weixin_secret");
			AccessToken token = WeixinUtil.getAccessToken(appId, secret);
			String access_token = token.getToken();
			JsTicket jsTicket = WeixinUtil.getJsTicket(access_token);
			log.info(jsTicket.getTicket());
			String nonceStr = StrUtils.getRandomStr(10);
			long timestamp = System.currentTimeMillis()/1000;
			String[] list = new String[4];
			list[0] = "noncestr="+nonceStr;
			list[1] = "jsapi_ticket="+jsTicket.getTicket();
			list[2] = "timestamp="+timestamp;
			list[3] = "url="+url;
			
			String signs = StrUtils.naturalOrderings(list);
			log.info(signs);
			String signature = SHA1_HexUtil.SHA1(signs);      //签名
			log.info("签名：" + signature);
			
			resMap = PackageMsg.getRightOperCode("OK");
			resMap.put("appId", appId);
			resMap.put("token", access_token);
			resMap.put("timestamp", timestamp);
			resMap.put("nonceStr", nonceStr);
			resMap.put("signature", signature);
		}
			break;	
			
		/**
		 * 获取签名
		 * @param appid 凭证
		 * @param appsecret 密钥
		 * @return
		 */
		case M2M_CLIENT_V10_REAL_NAME_AUTHEN:{
			String frontMediaId = request.getParameter("frontMediaId");
			String backMediaId = request.getParameter("backMediaId");
			String handedMediaId = request.getParameter("handedMediaId");  
			String token = request.getParameter("token");			
					
			//下载图片
			if(WeixinUtil.downloadMedia(token, frontMediaId, Constants.getString("m2mv10_weixin_download_url")) == null){
				log.error("图片下载出错，mediaId：" + frontMediaId);
			}
			if(WeixinUtil.downloadMedia(token, backMediaId, Constants.getString("m2mv10_weixin_download_url")) == null){
				log.error("图片下载出错，mediaId：" + backMediaId);
			}
			if(WeixinUtil.downloadMedia(token, handedMediaId, Constants.getString("m2mv10_weixin_download_url")) == null){
				log.error("图片下载出错，mediaId：" + handedMediaId);
			}
			
			resMap = PackageMsg.getRightOperCode("OK");
		
		}
			break;
		
