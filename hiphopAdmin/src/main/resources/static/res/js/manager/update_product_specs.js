/**
 * 修改商品规格
 */
function updateProductSpecs() {
    $("#submit_button").attr("disabled", true);
    clearAlertMsg();
    filterFormInput();
    var spec_name = $("#spec_name").val();
    if("" == spec_name) {
        showAlertMsg("规格名称不能为空!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(spec_name == window.spec.spec_name) {
        //规格名称未进行修改,无需验证
        $("#spec_name")[0].dataset.checkedStatus = 0;
    }
    if(0 != Number($("#spec_name")[0].dataset.checkedStatus)) {
        showAlertMsg("规格名称未通过验证!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    var success = true; //规格属性验证是否成功
    $("input[name='spec_value']:enabled").each(function() {
        if($(this).val() == "") {
            showAlertMsg("规格属性不能为空!");
            $(this).focus();
            success = false;
            return false;
        }
    });
    if(!success) {
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(!isUpdated()) {
        showAlertMsg("未对商品规格进行任何修改!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(window.confirm("确定修改该商品规格吗?")) {
        $("#update_specs_form").submit();
    } {
        $("#submit_button").attr("disabled", false);
    }
}
/**
 *添加规格属性输入项
 */
function addSpecsInput() {
    var form_group = $("#form-group-temp").clone();
    $(form_group).find("input[name='spec_value']").attr("disabled", false);
    $(form_group).removeAttr("id");
    $(form_group).css({
        display: "block"
    });
    form_group.appendTo("#update_specs_form");
}
/**
 *删除规格属性输入项
 */
function deleteSpecsInput(button) {
    $(button).parent().parent().parent().remove();
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
/**
 *验证规格名称是否已经存在
 */
function checkSpecName(specNameText) {
    specNameText.dataset.checkedStatus = -1; //设置成未验证状态
    var spec_name = filterTextVal(specNameText.value);
    if("" == spec_name) {
        return;
    }
    //属性名称等于原来的,无需验证
    if(spec_name == window.spec.spec_name) {
        return;
    }
    $.ajax({
        type: "post",
        url: window.contextPath + "/product/check_spec",
        data: {
            spec_name: spec_name
        },
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 == jsonData.status) {
                var count = Number(jsonData.data.count);
                specNameText.dataset.checkedStatus = count;
                if(1 == count) {
                    showAlertMsg("商品规格名称已存在");
                    window.setTimeout("clearAlertMsg()", 1000);
                    specNameText.value = "";
                    return;
                }
                if(0 != count) {
                    showAlertMsg("检验商品规格名称失败,服务器返回错误状态!");
                    window.setTimeout("clearAlertMsg()", 1000);
                    return;
                }
            } else {
                specNameText.dataset.checkedStatus = 500;
                showAlertMsg(jsonData.message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            specNameText.dataset.checkedStatus = 500;
            showAlertMsg("检验商品规格名称失败[" + errorThrown + "]");
            window.setTimeout("clearAlertMsg()", 1000);
        }
    });
}
/**
 *检查规属性是否重复
 */
function checkRepeatSpecValue(specValueText) {
    var specValue = filterTextVal($(specValueText).val());
    if("" == specValueText.value) {
        return;
    }
    $(specValueText).val(specValue);
    $("input[name='spec_value']:enabled").each(function() {
        if(this != specValueText && specValue == $(this).val()) {
            alert("规格属性不能重复!");
            $(specValueText).val("");
            return false;
        }
    });
}
/**
 * 加载商品规格数据
 * @param {Object} spec_id
 */
function getSpecInfo(spec_id) {
    $.ajax({
        type: "post",
        url: window.contextPath + "/product/get_specs",
        data: {
            spec_id: spec_id
        },
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 == jsonData.status) {
                var specJson = jsonData.data;
                if($.isEmptyObject(specJson)) {
                    showAlertMsg("该商品规格不存在或已被删除");
                    window.setTimeout("clearAlertMsg()", 1000);
                    disabledForm();
                    return;
                }
                window.spec = specJson;
                $("#spec_name").val(specJson.spec_name);
                var spec_values = specJson.spec_values;
                //设置第一个规格属性
                $("input[name='spec_value']:enabled").val(spec_values[0]);
                var len = spec_values.length;
                for(var index = 1; index < len; index++) {
                    var form_group = $("#form-group-temp").clone();
                    $(form_group).find("input[name='spec_value']").attr(
                        "disabled", false);
                    $(form_group).removeAttr("id");
                    $(form_group).css({
                        display: "block"
                    });
                    var spec_value = spec_values[index];
                    $(form_group).find("input[name='spec_value']").val(
                        spec_value);
                    form_group.appendTo("#update_specs_form");
                }

            } else {
                disabledForm();
                showAlertMsg(jsonData.message);
                window.setTimeout("clearAlertMsg()", 1000);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            disabledForm();
            showAlertMsg("获取商品规格信息失败[" + errorThrown + "]");
            window.setTimeout("clearAlertMsg()", 1000);
        }
    });
}
/**
 * 检查规格数据是否被修改
 * true:被修改
 * false:未被修改
 */
function isUpdated() {
    if($("#spec_name").val() != window.spec.spec_name) {
        return true;
    }
    if($("input[name='spec_value']:enabled").size() != window.spec.spec_values.length) {
        return true;
    }
    var updated = false;
    $("input[name='spec_value']:enabled").each(function() {
        var spec_value = $(this).val();
        if(-1 == $.inArray(spec_value, window.spec.spec_values)) {
            updated = true;
            return false;
        }
    });
    return updated;
}