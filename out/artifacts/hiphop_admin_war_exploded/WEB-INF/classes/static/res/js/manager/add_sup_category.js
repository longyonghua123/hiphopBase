/**
 * 添加商品一级分类
 */
function addSupCategory() {
    $("#submit_button").attr("disabled", true);
    clearAlertMsg();
    filterFormInput();
    if("" == $("#sup_cate_name").val()) {
        showAlertMsg("分类名称不能为空!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(0 != Number($("#sup_cate_name")[0].dataset.checkedStatus)) {
        showAlertMsg("分类名称未通过验证!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(window.confirm("确定添加该商品一级分类吗?")) {
        $("#add_sup_category_form").submit();
    } {
        $("#submit_button").attr("disabled", false);
    }
}
/**
 * 上传商品图片
 */
function uploadProductImgs() {
    clearAlertMsg();
    var imgFiles = $("#upload_imgs")[0].files;
    var length = imgFiles.length;
    if(0 == length) {
        showAlertMsg("未选择任何图片");
        return;
    }
    var contentType = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i;
    var imgType = imgFiles[0].type;
    if(!contentType.test(imgType)) {
        showAlertMsg("文件格式必须为图片");
        return;
    }
    $("#uploadImgForm")
        .ajaxSubmit({
            type: "POST",
            url: window.contextPath + "/common/upload_img",
            dataType: "json",
            success: function(jsonData) {
                if(0 != jsonData.status) {
                    // 上传图片异常
                    showAlertMsg("上传文件异常[" + jsonData.message +
                        "]");
                    return;
                }
                var img_url = jsonData.data.img_urls[0];
                $("#logo_img").attr({
                    src: img_url
                });
                var imgHTML = "<img src='img_url'  width='60' height='60' alt='加载图片失败'/><button type='button' class='btn btn-link' onclick='deleteLogo()'>删除</button>";
                imgHTML = imgHTML.replace("img_url", img_url);
                $("#img_col").html(imgHTML);
                $("#sup_cate_logo").val(img_url);
            },
            error: function(XMLHttpRequest, textStatus,
                errorThrown) {
                showAlertMsg("上传文件异常[" + textStatus + "]");
            }
        });
}
$(function() {
    $("#upload_imgs").change(function() {
        uploadProductImgs();
    });
    $("#upload_logo_button").click(function() {
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
 *删除logo
 */
function deleteLogo() {
    $("#sup_cate_logo").val("");
    $("#img_col").empty();
}

/**
 *验证商品一级分类名称是否存在
 */
function checkSupCateName(supCateNameText) {
	clearAlertMsg();
	supCateNameText.dataset.checkedStatus = -1; //设置成未验证状态
	var sup_cate_name = filterTextVal(supCateNameText.value);
	if ("" == sup_cate_name) {
		return;
	}
	$.ajax({
		type : "post",
		url : window.contextPath + "/product/check_sup_category",
		data : {
			sup_cate_name : sup_cate_name
		},
		dataType : "json",
		success : function(jsonData, textStatus) {
			if (0 == jsonData.status) {
				var count = Number(jsonData.data.count);
				supCateNameText.dataset.checkedStatus = count;
				if (1 == count) {
					showAlertMsg("商品分类名称已存在");
					window.setTimeout("clearAlertMsg()", 1000);
					supCateNameText.value = "";
					return;
				}
				if (0 != count) {
					showAlertMsg("检验商品分类名称失败,服务器返回错误状态!");
					window.setTimeout("clearAlertMsg()", 1000);
					return;
				}
			} else {
				supCateNameText.dataset.checkedStatus = 500;
				showAlertMsg(jsonData.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			supCateNameText.dataset.checkedStatus = 500;
			showAlertMsg("检验商品分类名称失败[" + errorThrown + "]");
			window.setTimeout("clearAlertMsg()", 1000);
		}
	});
}