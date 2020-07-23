/**
 * @param frameId:上传框架ID
 * @param basePath:服务器基础路径
 * @param maxSize:文件最大上限(单位M)
 * @param fileType:文件类型(1:图片;2:视频)
 * @param success:上传成功后执行的操作
 * @copyright baocloud
 */
function UploadFile(frameId, basePath, maxSize, fileType, success) {
	this.frameId = "#" + frameId; // jQuery id选择器加上#号
	this.basePath = basePath;
	this.maxSize = maxSize;
	this.fileType = fileType;
	this.success = success;
	var finished = false;//文件上传是否完成,上传成功或失败后设置为true
	/**
	 * 获取文件大小
	 */
	this.getFileSize = function(file) {
		var filesize = -1;
		try {
			filesize = file.size;
		} catch (e) {
			alert(e);
		}
		if (-1 == filesize) {
			try {
				var fileobject = new ActiveXObject("Scripting.FileSystemObject");
				file = fileobject.GetFile(filePath);
				var filesize = file.Size;
			} catch (e) {
				alert(e);
			}
		}
		return filesize;
	};
	/**
	 * 获取上传进度条
	 */
	this.getProgressBar = function() {
		var uploadFile = this;// 保存文件上传对象
		var timestamp = new Date().getTime();
		try {
			$
					.getJSON(
							basePath + "upload/get_progress",
							{
								"t" : timestamp
							},
							function(json) {
								if(uploadFile.finished)
								{
									//window.clearTimeout(interval);
									return;
								}
								if (0 != json.state) {
									alert("\u4E0A\u4F20\u6587\u4EF6\u51FA\u73B0\u5F02\u5E38,\u8BF7\u91CD\u8BD5\!");
									window.clearTimeout(interval);
									window.setTimeout(function() {
										uploadFile.afterUpload();
									}, 1000);
									uploadFile.finished = true;
									return;
								}
								var bytesRead = json.pBytesRead;
								var contentLength = json.pContentLength;
								if (bytesRead == contentLength
										&& "" != json.path) {
									uploadFile.finished = true;
									$(uploadFile.frameId + " .pro_bar").css(
											"width", 300);
									$(uploadFile.frameId + " .pro_info").text(
											"100%");
									var filePath = json.path;
									eval("success('" + filePath + "')");
									window.clearTimeout(interval);
									window.setTimeout(function() {
										uploadFile.afterUpload();
									}, 1000);
									return;
								} else {
									var percent = Math
											.floor((bytesRead / contentLength) * 100)
											+ "%";
									var w = Math
											.floor((bytesRead / contentLength) * 300);
									$(uploadFile.frameId + " .pro_bar").css(
											"width", w);
									$(uploadFile.frameId + " .pro_info").text(
											percent);
								}
							});
		} catch (e) {
			alert(e);
		}
		if (!uploadFile.finished) {
			interval = window.setTimeout(function() {
				uploadFile.getProgressBar();
			}, 500);
		}
	};
	/**
	 * 上传文件成功或失败后调用
	 */
	this.afterUpload = function() {
		$(this.frameId + " .progress").hide();
		$(this.frameId + " .pro_bar").css("width", 0);
		$(this.frameId + " .pro_info").text("");
	};
	/**
	 * 文件上传
	 */
	this.fileUpload = function() {
		if (this.maxSize <= 0) {
			alert("\u6587\u4EF6\u5927\u5C0F\u6700\u5927\u4E0A\u9650\u5FC5\u987B\u5927\u4E8E0");
			return;
		}
		var filePath = $(this.frameId + " :file").val();
		if ("" == filePath) {
			alert("\u8BF7\u9009\u62E9\u4E0A\u4F20\u6587\u4EF6");
			return;
		}
		if (1 == this.fileType) {
			if (!(/^.*?\.(gif|png|jpg|JPG|JPEG|jpeg|bmp)$/.test(filePath))) {
				alert("\u53ea\u80fd\u4e0a\u4f20gif, png, jpg, bmp\u683c\u5f0f\u7684\u56fe\u7247");
				return;
			}
		} else if (2 == this.fileType) {
			if (!(/^.*?\.(mp4|ogg|m4v|3gpp|mpeg|mov|mkv)$/.test(filePath))) {
				alert("\u53ea\u80fd\u4e0a\u4f20mp4,ogg,m4v,3gpp,mpeg,mov,mkv\u683c\u5f0f\u7684\u56fe\u7247");
				return;
			}
		} else {
			alert("\u6682\u65F6\u4EC5\u63D0\u4F9B\u56FE\u7247\u548C\u89C6\u9891\u6587\u4EF6\u4E0A\u4F20");
			return;
		}
		var filesize = this.getFileSize($(this.frameId + " :file")[0].files[0]);
		if (-1 == filesize) {
			alert("\u65E0\u6CD5\u83B7\u53D6\u4E0A\u4F20\u56FE\u7247\u5927\u5C0F,\u4E0A\u4F20\u56FE\u7247\u5931\u8D25\!");
			return;
		}
		var maxLength = parseInt(this.maxSize * 1024 * 1024);
		if (filesize > maxLength) {
			var errorMsg = "\u4E0A\u4F20\u56FE\u7247\u6700\u5927\u4E0D\u5F97\u8D85\u8FC7";
			errorMsg += String(maxSize) + "M";
			errorMsg += ",\u5B9E\u9645\u5927\u5C0F\u4E3A";
			errorMsg += String(Math.floor(filesize / 1024 / 1024 * 100) / 100)
					+ "M";
			alert(errorMsg);
			return;
		}
		$(this.frameId + ">form").submit();
		$(this.frameId + " .progress").show();
		var that = this;
		window.setTimeout(function() {
			that.getProgressBar();
		}, 1000);
	};
}