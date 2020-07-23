/**
 * 
 * 显示或隐藏回复文本域
 */
function replyContext(li) {
	var ul = li.parentNode;
	var lis = ul.getElementsByTagName("li");
	var li_hf = lis[3];
	var dispaly = li_hf.style.display;
	hideAll();
	if ("none" == dispaly || "" == dispaly) {
		li_hf.style.display = "list-item";
	} else {
		li_hf.style.display = "none";
	}
}
/**
 * 隐藏所有回复框
 */
function hideAll() {
	var uls = document.getElementsByTagName("ul");
	var len = uls.length;
	for (var index = 0; index < len; index++) {
		var ul = uls[index];
		var lis = ul.getElementsByTagName("li");
		var li_hf = lis[3];
		li_hf.style.display = "none";
	}
}