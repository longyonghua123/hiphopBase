/**
 * 修改二级分类
 */
$(function() {
	loadSupCategory();
	$("#upload_imgs").change(function() {
		uploadProductImgs();
	});
	$("#upload_logo_button").click(function() {
		$("#upload_imgs").click();
	});
});
/**
 * 添加商品二级分类
 */
function updateSubCategory() {
	$("#submit_button").attr("disabled", true);
	clearAlertMsg();
	filterFormInput();
	var sup_cate_id = $("#sup_cate_id").val();
	if ("" == sup_cate_id) {
		showAlertMsg("请选择商品一级分类");
		$("#submit_button").attr("disabled", false);
		return;
	}
    var  sub_cate_name=$("#sub_cate_name").val();
	if ("" == sub_cate_name) {
		showAlertMsg("商品分类名称不能为空!");
		$("#submit_button").attr("disabled", false);
		return;
	}
	if(sub_cate_name==window.sub_category.sub_cate_name)
    {
		$("#sub_cate_name")[0].dataset.checkedStatus=0;
    }
	if (0 != Number($("#sub_cate_name")[0].dataset.checkedStatus)) {
		showAlertMsg("商品分类名称未通过验证!");
		$("#submit_button").attr("disabled", false);
		return;
	}
	if(!isUpdated())
	{
		showAlertMsg("商品分类未进行任何修改!");
		$("#submit_button").attr("disabled", false);
		return;
	}
	if (window.confirm("确定修改该商品二级分类吗?")) 
	{
		$("#update_sub_category_form").submit();
	}
	else
	{
		$("#submit_button").attr("disabled", false);
	}
}
/**
 * copy一级分类
 */
function loadSupCategory() {
	var supCategoryOptions = $("#supCategoryList").clone(false).html();
	$("#sup_cate_id").append(supCategoryOptions);
}
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
	var contentType = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i;
	var imgType = imgFiles[0].type;
	if (!contentType.test(imgType)) {
		showAlertMsg("文件格式必须为图片");
		return;
	}
	$("#uploadImgForm")
			.ajaxSubmit(
					{
						type : "POST",
						url : window.contextPath + "/common/upload_img",
						dataType : "json",
						success : function(jsonData) {
							if (0 != jsonData.status) {
								// 上传图片异常
								showAlertMsg("上传文件异常[" + jsonData.message + "]");
								return;
							}
							var img_url = jsonData.data.img_urls[0];
							$("#logo_img").attr({
								src : img_url
							});
							var imgHTML = "<img src='img_url'  width='60' height='60' alt='加载图片失败'/><button type='button' class='btn btn-link' onclick='deleteLogo()'>删除</button>";
							imgHTML = imgHTML.replace("img_url", img_url);
							$("#img_col").html(imgHTML);
							$("#sub_cate_logo").val(img_url);
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							showAlertMsg("上传文件异常[" + textStatus + "]");
						}
					});
}

/**
 * 删除logo
 */
function deleteLogo() {
	$("#sub_cate_logo").val("");
	$("#img_col").empty();
}

/**
 * 验证商品二级分类名称是否存在
 */
function checkSubCateName(subCateNameText) {
	clearAlertMsg();
	subCateNameText.dataset.checkedStatus = -1; // 设置成未验证状态
	var sup_cate_id = $("#sup_cate_id").val();
	if ("" == sup_cate_id) {
		showAlertMsg("请选择商品一级分类");
		window.setTimeout("clearAlertMsg()", 1000);
		return;
	}
	var sub_cate_name = filterTextVal(subCateNameText.value);
	if ("" == sub_cate_name) {
		return;
	}
	if(sub_cate_name==window.sub_category.sub_cate_name)
    {
		return;
    }
	$.ajax({
		type : "post",
		url : window.contextPath + "/product/check_sub_category",
		data : {
			sub_cate_name : sub_cate_name,
			sup_cate_id : sup_cate_id
		},
		dataType : "json",
		success : function(jsonData, textStatus) {
			if (0 == jsonData.status) {
				var count = Number(jsonData.data.count);
				subCateNameText.dataset.checkedStatus = count;
				if (1 == count) {
					showAlertMsg("商品分类名称已存在");
					window.setTimeout("clearAlertMsg()", 1000);
					subCateNameText.value = "";
					return;
				}
				if (0 != count) {
					showAlertMsg("检验商品分类名称失败,服务器返回错误状态");
					window.setTimeout("clearAlertMsg()", 1000);
					return;
				}
			} else {
				subCateNameText.dataset.checkedStatus = 500;
				showAlertMsg(jsonData.message);
				window.setTimeout("clearAlertMsg()", 1000);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			subCateNameText.dataset.checkedStatus = 500;
			showAlertMsg("检验商品分类名称失败[" + errorThrown + "]");
			window.setTimeout("clearAlertMsg()", 1000);
		}
	});
}

/**
 * 获取商品二级分类信息
 * 
 * @param sub_cate_id
 */
function getSupCategory(sub_cate_id) {
	$
			.ajax({
				type : "post",
				url : window.contextPath + "/product/get_sub_category",
				data : {
					sub_cate_id : sub_cate_id
				},
				dataType : "json",
				success : function(jsonData, textStatus) {
					if (0 == jsonData.status) {
						var subCateJson = jsonData.data;
						if ($.isEmptyObject(subCateJson)) {
							showAlertMsg("该商品二级分类不存在或已被删除");
							window.setTimeout("clearAlertMsg()", 1000);
							disabledForm();
							return;
						}
						window.sub_category = subCateJson;
						var sub_cate_name = subCateJson.sub_cate_name;
						$("#sub_cate_name").val(sub_cate_name);
						var sub_cate_logo = subCateJson.sub_cate_logo;
						if ("" != sub_cate_logo) {
							var img_url = sub_cate_logo;
							var imgHTML = "<img src='img_url'  width='60' height='60' alt='加载图片失败'/><button type='button' class='btn btn-link' onclick='deleteLogo()'>删除</button>";
							imgHTML = imgHTML.replace("img_url", img_url);
							$("#img_col").html(imgHTML);
							$("#sub_cate_logo").val(img_url);
						}
						var sup_cate_id=subCateJson.sup_cate_id;
						$("#sup_cate_id").find("option[value='"+sup_cate_id+"']").attr({selected:true});
						
					} else {
						disabledForm();
						showAlertMsg("获取商品一级分类失败[" + jsonData.message + "]");
						window.setTimeout("clearAlertMsg()", 1000);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					disabledForm();
					showAlertMsg("获取商品一级分类失败[" + errorThrown + "]");
					window.setTimeout("clearAlertMsg()", 1000);
				}
			});
}
/**
 * 判断是否被修改
 * @returns {Boolean}
 */
function isUpdated() {
    if($("#sub_cate_name").val() != window.sub_category.sub_cate_name) {
        return true;
    }
    if($("#sup_cate_id").val()!=window.sub_category.sup_cate_id) {
        return true;
    }
    
    if($("#sub_cate_logo").val()!=window.sub_category.sub_cate_logo) {
        return true;
    }
    return false;
}