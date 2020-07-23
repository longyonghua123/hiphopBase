/**
 * 发表评论
 */
function submitComment() {
	var comcon = document.getElementById("comcon").value;
	// 过滤全角半角
	comcon = filterTextareaVal(comcon);
	if ("" == comcon) {
		alert("评论内容不能为空");
		return;
	}
	var words = comcon.length;
	if (words < 5) {
		alert("评论内容不能少于5个字符");
		return;
	}
	if (words > 200) {
		alert("评论内容不能超过200个字符");
		return;
	}
	document.getElementById("comcon").value = comcon;
	document.getElementById("comm_form").submit();
}

/**
 * 添加评论回复
 */
function replyComment(commLi, comId,basePath) {
	repcon = commLi.getElementsByTagName("textarea")[0].value;
	repcon = filterTextareaVal(repcon)
	if ("" == repcon) {
		alert("回复内容不能为空");
		return;
	}
	var words = comcon.length;
	if (words < 5) {
		alert("回复内容不能少于5个字符");
		return;
	}
	if (words > 200) {
		alert("回复内容不能超过200个字符");
		return;
	}
	li = commLi;
	// 调用Ajax请求添加回复记录
	var url = basePath+"comment/add_reply";
	var ajaxUtils = new AjaxUtils(url, true);
	ajaxUtils.sentPost(replySuccess, "comId=" + comId + "&repcon="
			+ repcon); // 发送post请求

}
/**
 * Ajax成功访问后调用 state为服务返回的操作状态
 */
function replySuccess(state) {
	if (1 == state) {
		alert("回复成功");
		var ul = li.parentNode;
		var lis = ul.getElementsByTagName("li");
		var li_hf_list = lis[4];
		var itemHTML = bulidReplyItemHTML("yunku", "../img/user/head/1.jpg",
				repcon);
		var ol = document.createElement("OL");
		ol.innerHTML = itemHTML;
		li_hf_list.insertBefore(ol, li_hf_list.childNodes[0]);
		replyContext(lis[2]);
		li.getElementsByTagName("textarea")[0].value="";
	} else {
		alert("回复失败,状态[" + state + "]");
	}
}
/**
 * 创建回复HTML内容
 * 
 * @param username:用户名
 * @param user_head:用户头像
 * @param mgs:回复内容
 */
function bulidReplyItemHTML(username, user_head, mgs) {
	var itemHTML = "<li style=\"float:left;\">";
	itemHTML += "<img class=\"user_head\" src=\"" + user_head + "\" />";
	itemHTML += "</li>";
	itemHTML += "<li class=\"li_info\">";
	itemHTML += "<span class=\"username\">" + username
			+ "</span>&nbsp;&nbsp;刚刚";
	itemHTML += "<br/>";
	itemHTML += "<span>" + mgs + "</span>"
	itemHTML += "</li>";
	itemHTML += "<li class=\"li_do\">";
	itemHTML += "<input type=\"image\" src=\"../img/zan.png\" />(0)";
	itemHTML += "<input type=\"image\" src=\"../img/zan.png\" />(0)"
	itemHTML += "</li>";
	itemHTML += "<li class=\"li_hr2\"></li>";
	return itemHTML;
}