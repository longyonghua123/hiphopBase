/**
 * 上传商品图片
 */
function uploadProductImgs() {
	clearAlertMsg();
	var imgFiles = $("#upload_imgs")[0].files;
	var length = imgFiles.length;
	if (0 == length) {
		showAlertMsg("未选择任何图片");
		return;
	}
	var img_type = Number($("#img_type").val());
	if(54!=img_type)
	{
		var restCount = getRestUploadCount();
		if (length > restCount) {
			showAlertMsg("该类型商品图片剩余可上传图片数量最大为:" + restCount);
			return;
		}
	}
	var contentType = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i;
	for (var index = 0; index < length; index++) {
		var imgType = imgFiles[index].type;
		if (!contentType.test(imgType)) {
			showAlertMsg("文件格式必须为图片");
			return;
		}
	}
	$('#uploadModal').modal({
		backdrop : false
	});
	$("#uploadImgForm").ajaxSubmit({
		type : "POST",
		url : window.contextPath + "/common/upload_img",
		dataType : "json",
		success : function(json) {
			$('#uploadModal').modal('hide');
			$("#upload_imgs").val("");
			if (0 != json.status) {
				// 上传图片异常
				showAlertMsg(json.message);
			} else {
				setImgUL(json.data.img_urls);
				var ul = $("#" + getImgName());
				resetLiMovePower(ul);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('#uploadModal').modal('hide');
			$("#upload_imgs").val("");
			showAlertMsg("上传文件异常[" + textStatus + "]");
		}
	});
}
/**
 * 注册事件
 */
$(function() {
	$("#upload_imgs").change(function() {
		uploadProductImgs();
	});
	// 上传小图按钮
	$("#upload_min_imgs").click(function() {
		$("#upload_imgs").attr("multiple", true);
		$("#img_type").val(51);
		$("#upload_imgs").click();
	});
	// 上传大图按钮
	$("#upload_max_imgs").click(function() {
		$("#upload_imgs").attr("multiple", true);
		$("#img_type").val(52);
		$("#upload_imgs").click();
	});
	// 上传标图按钮
	$("#upload_norm_imgs").click(function() {
		$("#upload_imgs").attr("multiple", "multiple");
		$("#img_type").val(53);
		$("#upload_imgs").click();
	});
	// 上传商品详情图按钮
	$("#upload_desc_imgs").click(function() {
		$("#upload_imgs").attr("multiple", true);
		$("#img_type").val(54);
		$("#upload_imgs").click();
	});
	// 上传商品封面图按钮
	$("#upload_cover_imgs").click(function() {
		$("#upload_imgs").attr("multiple", false);
		$("#img_type").val(55);
		$("#upload_imgs").click();
	});
});
/**
 * 提示错误信息
 */
function showAlertMsg(message) {
	var alertHTML = "<div  class='alert alert-warning'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>警告！</strong>{message}</div>";
	alertHTML = alertHTML.replace("{message}", message);
	$("#alertMessage").append(alertHTML);
}
/**
 * 清除提示错误信息
 */
function clearAlertMsg() {
	$("#alertMessage").empty();
}

/**
 * 添加图片列表项
 * 
 * @param ul
 * @param img_name
 * @param img_url
 */
function appendLI(ul, img_name, img_url) {
	var liHTML = "<li class='list-group-item'><input type='hidden' name='img_name'  value='img_url'/><img src='img_url' alt='商品图' width='60' height='60'  /><button type='button' class='btn btn-link' onclick='moveUp(this)'>上移</button><button type='button' class='btn btn-link' onclick='moveDown(this)'>下移</button><button type='button' class='btn btn-link' onclick='deleteImgLi(this)'>删除</button><button type='button' class='btn btn-link' onclick='showOriginalImg(this)' data-toggle='modal' data-target='#imgModal'>查看原图</button></li>";
	liHTML = liHTML.replace("img_url",  img_url);
	liHTML = liHTML.replace("img_url",  imgBasePath+"imgs"+img_url);
	liHTML = liHTML.replace("img_name", img_name);
	ul.append(liHTML);
}
/**
 * 重新设置列表项上移或下移操作权限
 * 
 * @param ul
 */
function resetLiMovePower(ul) {
	var size = ul.find("li").size();
	ul.find("li").each(function(i) {
		// 第一行不能上移
		if (0 == i) {
			$(this).find("button").first().attr({
				disabled : true
			});
		} else {
			$(this).find("button").first().attr({
				disabled : false
			});
		}
		// 最后不能下移
		if (size - 1 == i) {
			$(this).find("button:eq(1)").attr({
				disabled : true
			});
		} else {
			$(this).find("button:eq(1)").attr({
				disabled : false
			});
		}

	});
}
/**
 * 向上移动 button
 */
function moveUp(button) {
	var currentLI = $(button).parent();
	$(currentLI).prev().insertAfter(currentLI);
	resetLiMovePower($("#" + getImgName()));
}

/**
 * 向下移动 button
 */
function moveDown(button) {
	var currentLI = $(button).parent()[0];
	$(currentLI).insertAfter($(currentLI).next());
	resetLiMovePower($("#" + getImgName()));
}
/**
 * 设置图片列表
 */
function setImgUL(img_urls) {
	var img_name = getImgName();
	var ul = $("#" + img_name);
	var len = img_urls.length;
	for (var index = 0; index < len; index++) {
		var img_url = img_urls[index];
		appendLI(ul, img_name, img_url);
	}
}

/**
 * 设置图片列表
 */
function initImgUL(img_urls, img_name) {
	var ul = $("#" + img_name);
	var len = img_urls.length;
	for (var index = 0; index < len; index++) {
		var img_url = img_urls[index];
		appendLI(ul, img_name, img_url);
	}
}
/**
 * 获取图片名称
 */
function getImgName() {
	var img_type = $("#img_type").val();
	var img_name = "";
	switch (Number(img_type)) {
	case 51:
		img_name = "min_imgs";
		break;
	case 52:
		img_name = "max_imgs";
		break;
	case 53:
		img_name = "norm_imgs";
		break;
	case 54:
		img_name = "desc_imgs";
		break;
	case 55:
		img_name = "cover_img_url";
		break;
	default:
		img_name = "";
		break;
	}
	return img_name;
}
/**
 * 查看原图
 */
function showOriginalImg(button) {
	var img_url = $(button).parent().find("img").attr("src");
	$("#original_img").attr({
		src : img_url
	});
}
/**
 * 获取当前剩余可上传图片最大数量
 */
function getRestUploadCount() {
	var img_name = getImgName();
	var img_type = Number($("#img_type").val());
	var max_count = 55 == img_type ? 1 : 5;
	return max_count - $("#" + img_name).find("li").size();
}
/**
 * 设置商品图片
 */
function setProductImgs() {
	clearAlertMsg();
	var min_count = $("#min_imgs li").size();
	if (0 == min_count) {
		showAlertMsg("未上传商品小图");
		return;
	}
	var norm_count = $("#norm_imgs li").size();
	if (0 == norm_count) {
		showAlertMsg("未上传商品标准图片");
		return;
	}
	var desc_count = $("#desc_imgs li").size();
	if (0 == desc_count) {
		showAlertMsg("未上传商品详细信息图片");
		return;
	}
	var cover_count = $("#cover_img_url li").size();
	if (0 == cover_count) {
		showAlertMsg("未上传商品封面图片");
		return;
	}
	// 检查各类商品图片是否匹配
	if (min_count != norm_count) {
		showAlertMsg("商品小图与标图数量不匹配");
		return;
	}
	var max_count = $("#max_imgs li").size();
	if (0 != max_count && max_count != min_count) {
		showAlertMsg("商品大图与小图数量不匹配");
		return;
	}
	// 提交表单
	$("#set_product_imgs_button").attr({
		disabled : true
	});
	$("#set_product_imgs_form").submit();
}
/**
 * 刪除图片列表
 * 
 * @param button
 */
function deleteImgLi(button) {
	$(button).parent().remove();
}
/**
 * 加载商品图片
 * 
 * @param product_id
 */
function loadProductImgs(product_id) {
	$.ajax({
		type : "post",
		url : window.contextPath + "/product/img_info",
		data : {
			product_id : product_id
		},
		dataType : "json",
		success : function(jsonData, textStatus) {
			if (0 != jsonData.status) {
				alert(jsonData.message);
				return;
			}
			var productJSON = jsonData.data;
			if (0 == Number(productJSON.rows)) {
				alert("该商品不存在!");
				return;
			}
			if (0 == Number(productJSON.set_status)) {
				alert("该商品未设置图片!");
				$("#set_status").val(0);
				return;
			}
			// 初始化商品图片
			initImgUL(productJSON.min_imgs, "min_imgs");
			initImgUL(productJSON.max_imgs, "max_imgs");
			initImgUL(productJSON.norm_imgs, "norm_imgs");
			initImgUL(productJSON.desc_imgs, "desc_imgs");
			initImgUL(productJSON.cover_img_url, "cover_img_url");
			resetLiMovePower($("#min_imgs"));
			resetLiMovePower($("#max_imgs"));
			resetLiMovePower($("#norm_imgs"));
			resetLiMovePower($("#desc_imgs"));
			resetLiMovePower($("#cover_img_url"));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载商品图片失败[" + errorThrown + "]");
		}
	});

}