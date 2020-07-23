$(function(){
	$("#supCategoryList").change(function() {
		loadSubCategory();
	});	
	$("#subCategoryList").change(function() {
		loadBrand();
	});
	loadProductBaseInfo(window.product_id);
	loadSpecs();
});

/**
 * 加载商品信息
 */
function loadProductBaseInfo(product_id) {
	$.ajax({
		type : "post",
		url : window.contextPath + "/product/base_info",
		data : {
			product_id : product_id
		},
		dataType : "json",
		success : function(jsonData, textStatus) {
			if (0 == jsonData.status) {
				var productJSON = jsonData.data;
				if ($.isEmptyObject(productJSON)) {
					alert("该商品不存在!");
					return;
				}
				$("#product_name").val(productJSON.product_name);
				$("#product_area").val(productJSON.product_area);
				$("#plat_price").val(productJSON.plat_price);
				var sale_status=Number(productJSON.sale_status);
				$(":radio[name='sale_status'][value='"+sale_status+"']").attr({checked:true});
				$(":radio[name='sale_status']:checked").click();
				if (2 == sale_status || 3 == sale_status || 4 == sale_status) {
					$("#action_price").val(productJSON.action_price);
				}
				$("#keyword").val(productJSON.keyword);
				$("#info_desc").val(productJSON.info_desc);
				loadSupCategory(productJSON.sup_categories);
				$("#supCategoryList").find("option[value='" + productJSON.sup_cate_id + "']").attr({
					selected : true
				});
				initSubCategory(productJSON.sub_categories);
				$("#subCategoryList").find("option[value='" + productJSON.sub_cate_id + "']").attr({
					selected : true
				});
				initBrand(productJSON.brands);
				$("#brandList").find("option[value='" + productJSON.brand_name + "']").attr({
					selected : true
				});
				initProductSpecs(productJSON.specs,productJSON.product_specs);
			} else {
				alert(jsonData.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载商品信息失败[" + errorThrown + "]");
		}
	});
}

/**
 * 加载商品一级分类
 */
function loadSupCategory(sup_categories) {
	$("#suCategoryList").empty();
	$("#supCategoryList").append("<option value=''>--选择商品一级分类--</option>");
	$.each(sup_categories,
			function() {
				var sup_cate_id = this.sup_cate_id;
				var sup_cate_name = this.sup_cate_name;
				$("#supCategoryList").append(
						getOptionHTML(sup_cate_id, sup_cate_name));
			});
}
/**
 * 加载商品二级分类
 */
function loadSubCategory() {
	var sup_cate_id = $("#supCategoryList").val();
	$("#subCategoryList").empty();
	$("#subCategoryList").append("<option value=''>--选择商品二级分类--</option>");
	$("#brandList").empty();
	$("#brandList").append("<option value=''>--选择商品品牌--</option>");
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
				$("#subCategoryList").append(getOptionHTML(sub_cate_id, sub_cate_name));
			});
		}
	}, "json");
}

/**
 * 初始化商品所属二级分类同类别所有分类数据
 */
function initSubCategory(sub_categories)
{
$("#subCategoryList").empty();
$("#subCategoryList").append("<option value=''>--选择商品二级分类--</option>");
$.each(sub_categories, function() {
	var sub_cate_id = this.sub_cate_id;
	var sub_cate_name = this.sub_cate_name;
	$("#subCategoryList").append(getOptionHTML(sub_cate_id, sub_cate_name));
});
}
/**
 * 加载商品品牌
 */
function loadBrand() {
	var sub_cate_id = $("#subCategoryList").val();
	$("#brandList").empty();
	$("#brandList").append("<option value=''>--选择商品品牌--</option>");
	if ("" == sub_cate_id) {
		return;
	}
	$.post(window.contextPath + "/product/load_brand", {
		sub_cate_id : sub_cate_id
	}, function(jsondata) {
		if (0 == jsondata.status) {
			$.each(jsondata.data, function() {
				var brand_name = this.brand_name;
				$("#brandList").append(getOptionHTML(brand_name, brand_name));
			});
		}
	}, "json");
}

/**
 *初始化商品类型所有品牌数据
 */
function initBrand(brands)
{
	$("#brandList").empty();
	$.each(brands, function() {
		var brand_name = this.brand_name;
		$("#brandList").append(getOptionHTML(brand_name, brand_name));
	});
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
 * 改变商品活动类型
 * 
 * @param sale_statusaRadio
 */
function changeSaleStatus(sale_statusaRadio) {
	var sale_status = Number(sale_statusaRadio.value);
	if (1 == sale_status) {
		$("#action_price").attr({
			disabled : true
		});
		$("#action_price").parent().next().find("span").css({
			color : "rgb(238,238,238)"
		});
	} else if (2 == sale_status || 3 == sale_status || 4 == sale_status) {
		$("#action_price").attr({
			disabled : false
		});
		$("#action_price").parent().next().find("span").css({
			color : "rgb(255,0,0)"
		});

	} else {
		alert("未知的销售活动类型");
	}
}
/**
 * 
 * @param specs 商品规格数据
 * @param product_specs 已选商品规格
 */
function initProductSpecs(specs,product_specs)
{ 
	if($.isEmptyObject(product_specs))
	{
		return;
	}	
	var length=specs.length;
	for(var index=0;index<length;index++)
	{
		var spec=specs[index];
		var specs_name=spec.specs_name;
		var specs_values=spec.specs_values;
		var optgroupHTML = "<optgroup label='" + specs_name + "'>";
		var count = specs_values.length;
		for (var c = 0; c < count; c++) {
			var specs_value = specs_values[c];
			if(isSelectedSpec(specs_name, specs_value, product_specs))
			{
				optgroupHTML += getSelectedOptionHTML(specs_name + "-"+ specs_value, specs_value);
			}else{
				optgroupHTML += getOptionHTML(specs_name + "-"+ specs_value, specs_value);
			}
	    }
		optgroupHTML += "</optgroup>";
		$("#attr_json").append(optgroupHTML); 
	}
	
}
/**
 * 判断商品规格值是否选中
 * @param specs_name 规格名称
 * @param specs_value 规格值
 * @param product_specs  已选商品规格
 */
function isSelectedSpec(specs_name,specs_value,product_specs)
{
	var isSelected=false;
	var length=product_specs.length;
	for(var index=0;index<length;index++)
	{
		var spec=product_specs[index];
		if(specs_name!=spec.specs_name)
		{
			continue;
		}
		if(-1!=$.inArray(specs_value,spec.specs_values))
		{
			isSelected=true;
			break;
		}
	}
	return isSelected;
}

/**
 * 加载商品规格
 */
function loadSpecs() {
	$.post(window.contextPath + "/product/load_specs", function(jsondata) {
		if (0 == jsondata.status) {
			window.specs_array = jsondata.data;
			$.each(jsondata.data, function() {
				var spec_name = this.spec_name;
				var optionHTML = "<option value='" + spec_name + "'>"
						+ spec_name + "</option>";
				$("#specs_list").append(optionHTML);
			});
		}
	}, "json");
}

/****
 * 选择规格
 * 
 */
function selectSpecs(spec_name) {
	if ("" == spec_name) {
		return;
	}
	var isExist = $("#attr_json").find("optgroup[label='" + spec_name + "']")
			.size() > 0 ? true : false;
	if (!isExist) {
		var specs_array = window.specs_array;
		var len = specs_array.length;
		var spec_value_array = false;
		for (var index = 0; index < len; index++) {
			var specs = specs_array[index];
			if (spec_name == specs.spec_name) {
				spec_value_array = specs.spec_value;
				break;
			}
		}
		if (spec_value_array) {
			var optgroupHTML = "<optgroup label='" + spec_name + "'>";
			len = spec_value_array.length;
			for (var index = 0; index < len; index++) {
				var spec_value = spec_value_array[index];
				optgroupHTML += "<option value='" + spec_name + "-"
						+ spec_value + "'>" + spec_value + "</option>";
			}
			optgroupHTML += "</optgroup>";
			$("#attr_json").append(optgroupHTML);
		}
	}
}

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

function setDisabled(disabled) {
	$("#submit_button").attr({
		disabled : disabled
	});
}

/**
 * 修改商品
 */
function updateProduct() {
	setDisabled(true);
	filterFormInput();
	clearAlertMsg();
	var product_name = $("#product_name").val();
	if ("" == product_name) {
		showAlertMsg("请输入商品名称");
		setDisabled(false);
		return;
	}
	var product_area = $("#product_area").val();
	if ("" == product_area) {
		showAlertMsg("请输入商品生产地");
		setDisabled(false);
		return;
	}
	var sup_cate_id = $("#supCategoryList").val();
	if ("" == sup_cate_id) {
		showAlertMsg("请选择商品一级分类");
		setDisabled(false);
		return;
	}
	var sub_cate_id = $("#subCategoryList").val();
	if ("" == sub_cate_id) {
		showAlertMsg("请选择商品二级分类");
		setDisabled(false);
		return;
	}
	var brand_name = $("#brandList").val();
	if ("" == brand_name) {
		showAlertMsg("请选择商品品牌");
		setDisabled(false);
		return;
	}
	var plat_price = $("#plat_price").val();
	if ("" == plat_price) {
		showAlertMsg("请输入商品价格");
		setDisabled(false);
		return;
	}
	var priceReg = /^[1-9]\d*$/;// 价格正则表达式,大于0正整数
	if (!priceReg.test(plat_price)) {
		showAlertMsg("商品价格必须为大于0的正整数");
		setDisabled(false);
		return;
	}
	var sale_status = Number($(":radio[name='sale_status']:checked").val());
	if (2 == sale_status || 3 == sale_status || 4 == sale_status) {
		// 检查活动价格
		var action_price = $("#action_price").val();
		if ("" == action_price) {
			showAlertMsg("请输入商品活动价格");
			setDisabled(false);
			return;
		}
		if (!priceReg.test(action_price)) {
			showAlertMsg("商品活动价格必须为大于0的正整数");
			setDisabled(false);
			return;
		}

	}
	// 检查商品属性
	var unselected = false;
	$("#attr_json").find("optgroup").each(function() {
		var size = $(this).find("option:selected").size();
		if (0 == size) {
			var spec_name = $(this).attr("label");
			showAlertMsg("未选择'{0}'规格".replace("{0}", spec_name));
			unselected = true;
			return false;
		}
	});
	if (unselected) {
		setDisabled(false);
		return;
	}
	// 商品标签或关键字
	var keyword = $("#keyword").val();
	if ("" == keyword) {
		showAlertMsg("请输入商品关键字或标签");
		setDisabled(false);
		return;
	}
	// 商品详细信息
	var info_desc = $("#info_desc").val();
	if ("" == info_desc) {
		showAlertMsg("请输入商品详细信息");
		setDisabled(false);
		return;
	}
	if (window.confirm("你确定现在要修改该商品吗?请检查完所有信息后再确定操作!")) {
		$("#update_product_form").submit();
	}else
	{
		setDisabled(false);
	}
}