/**
 * 添加品牌
 */
$(function() {
	loadSupCategory();
	$("#sup_cate_id").change(function() {
		var sup_cate_id = $("#sup_cate_id").val();
		loadSubCategory(sup_cate_id);
	});
	$("#upload_imgs").change(function() {
		uploadProductImgs();
	});
	$("#upload_logo_button").click(function() {
		$("#upload_imgs").click();
	});
});
/**
 * 添加商品品牌
 */
function addProductBrand() {
	$("#submit_button").attr("disabled", true);
	clearAlertMsg();
	filterFormInput();
	var sub_cate_id = $("#sub_cate_id").val();
	if ("" == sub_cate_id) {
		showAlertMsg("请选择商品二级分类");
		$("#submit_button").attr("disabled", false);
		return;
	}

	if ("" == $("#brand_name").val()) {
		showAlertMsg("品牌名称不能为空!");
		$("#submit_button").attr("disabled", false);
		return;
	}
	if (0 != Number($("#brand_name")[0].dataset.checkedStatus)) {
		showAlertMsg("品牌名称未通过验证!");
		$("#submit_button").attr("disabled", false);
		return;
	}
	if (window.confirm("确定添加商品品牌吗?")) {
		$("#add_brand_form").submit();
	}
	else
	{
		$("#submit_button").attr("disabled", false);
	}
}
/**
 * 加载商品一级分类
 */
function loadSupCategory() {
	var supCategoryOptions = $("#supCategoryList").clone(false).html();
	$("#sup_cate_id").empty();
	$("#sup_cate_id").append(supCategoryOptions);
}

function loadSubCategory(sup_cate_id) {
	$("#sub_cate_id").empty();
	$("#sub_cate_id").append("<option value=''>--选择商品二级分类--</option>");
	if ("" == sup_cate_id) {
		return;
	}
	$.post(window.contextPath + "/product/load_sub_category", {
		sup_cate_id : sup_cate_id
	}, function(jsondata) {
		if (0 == jsondata.status) {
			$.each(jsondata.data, function() {
				var sub_cate_id = this.sub_cate_id;
				var sub_cate_name = this.sub_cate_name;
				$("#sub_cate_id").append(
						getOptionHTML(sub_cate_id, sub_cate_name));
			});
		}
	}, "json");
}

/**
 * 获取select中option选项HTML内容
 */
function getOptionHTML(value, text) {
	var optionHTML = "<option value='{0}'>{1}</option>";
	optionHTML = optionHTML.replace("{0}", value);
	optionHTML = optionHTML.replace("{1}", text);
	return optionHTML;
}
/**
 * 获取select中被选中option选项HTML内容
 */
function getSelectedOptionHTML(value, text) {
	var optionHTML = "<option value='{0}' selected='selected'>{1}</option>";
	optionHTML = optionHTML.replace("{0}", value);
	optionHTML = optionHTML.replace("{1}", text);
	return optionHTML;
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
							var imgColHTML = "<img src='img_url'  width='60' height='60' alt='加载图片失败'/><button type='button' class='btn btn-link' onclick='deleteLogo()'>删除</button>";
							imgColHTML = imgColHTML.replace("img_url", img_url);
							$("#img_col").html(imgColHTML);
							$("#logo_url").val(img_url);
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
 * 验证品牌名称是否存在
 */
function checkBrandName(brandNameText) {
	clearAlertMsg();
	brandNameText.dataset.checkedStatus = -1; // 设置成未验证状态
	var sub_cate_id = $("#sub_cate_id").val();
	if ("" == sub_cate_id) {
		showAlertMsg("请选择商品二级分类");
		window.setTimeout("clearAlertMsg()", 1000);
		return;
	}
	var brand_name = filterTextVal(brandNameText.value);
	if ("" == brand_name) {
		return;
	}
	$.ajax({
		type : "post",
		url : window.contextPath + "/product/check_brand",
		data : {
			brand_name : brand_name,
			sub_cate_id:sub_cate_id
		},
		dataType : "json",
		success : function(jsonData, textStatus) {
			if (0 == jsonData.status) {
				var count = Number(jsonData.data.count);
				brandNameText.dataset.checkedStatus = count;
				if (1 == count) {
					showAlertMsg("品牌名称已存在");
					window.setTimeout("clearAlertMsg()", 1000);
					brandNameText.value = "";
					return;
				}
				if (0 != count) {
					showAlertMsg("检验品牌名称失败,服务器返回错误状态");
					window.setTimeout("clearAlertMsg()", 1000);
					return;
				}
			} else {
				brandNameText.dataset.checkedStatus = 500;
				showAlertMsg(jsonData.message);
				window.setTimeout("clearAlertMsg()", 1000);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			brandNameText.dataset.checkedStatus = 500;
			showAlertMsg("检验品牌名称失败[" + errorThrown + "]");
			window.setTimeout("clearAlertMsg()", 1000);
		}
	});
}
