/**
 * @copyright baocloud
 */
function createXmlHttpRequest() {
	var xmlHttpRequest = false;
	var supportAjax = true;
	if (window.XMLHttpRequest) {
		// IE7+、Opera、Chrome、Firefox、Safari
		xmlHttpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		// IE5、IE6
		var versions = [ "Microsoft.XMLHTTP", "MSXML.XMLHTTP",
				"Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0",
				"Msxml2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP" ];
		var length = versions.length;
		for (var v = 0; v < length; v++) {
			try {
				xmlHttpRequest = new ActiveXObject(versions[v]);
				break;
			} catch (e) {
				continue;
			}
		}
	} else {
		supportAjax = false;
	}
	if (supportAjax) {
		if (!xmlHttpRequest) {
			alert("\u521B\u5EFAXMLHttpRequest\u5BF9\u8C61\u5931\u8D25");
		}
	} else {
		alert("\u5F53\u524D\u6D4F\u89C8\u5668\u4E0D\u652F\u6301\u4F7F\u7528AJAX");
	}
	return xmlHttpRequest;
}
/**
 * 
 * @param url:Ajax访问url
 * @param async:说明请求是否为异步的.true:异步;false:同步
 */
function AjaxUtils(url, async) {
	this.url = url;
	this.async = async;
	this.xmlHttpRequest;
	this.execsuccess;
	this.sentGet = function(execsuccess) {
		this.execsuccess = execsuccess;
		this.xmlHttpRequest = createXmlHttpRequest();
		this.xmlHttpRequest.open("GET", this.url, this.async);
		this.xmlHttpRequest.onreadystatechange = function() {
			if (200 == this.status && 4 == this.readyState) {
				var data = this.responseText;
				eval("execsuccess(" + data + ")");
			}
		}
		this.xmlHttpRequest.send();
	};
	this.sentPost = function(execsuccess, params) {
		this.execsuccess = execsuccess;
		this.xmlHttpRequest = createXmlHttpRequest();
		this.xmlHttpRequest.open("POST", this.url, this.async);
		this.xmlHttpRequest.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		this.xmlHttpRequest.onreadystatechange = function() {
			if (200 == this.status && 4 == this.readyState) {
				var data = this.responseText;
				eval("execsuccess(" + data + ")");
			}
		}
		this.xmlHttpRequest.send(params);
	};
}