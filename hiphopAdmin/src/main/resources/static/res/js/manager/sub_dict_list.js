/**
 *
 */
function showAddSubDictDialog() {
    $.ajax({
        type: "post",
        url: window.webContextPath + "/page/dict/add_sub_dict",
        dataType: "html",
        success: function (data, textStatus) {
            $("#subDictDialog").html(data);
            $("#subDictDialog").modal();
        },
        error: function () {
        }
    });
}

function showUpdateSubDictDialog(dictId) {
    $.ajax({
        type: "post",
        data: {
            dict_id: dictId
        },
        url: window.webContextPath + "/page/dict/update_sub_dict",
        dataType: "html",
        success: function (data, textStatus) {
            $("#subDictDialog").html(data);
            $("#subDictDialog").modal();
        },
        error: function () {
        }
    });
}

/**
 * 停用子字典项
 */
function disableSubDict(dictId) {
    var pDictCode = $("#main_dict_select").val();
    window.location.href = window.webContextPath
        + "/dict/set_sub_dict_status?use_status=2&dict_id="
        + dictId + "&rd=" + Math.random() + "&p_dict_code=" + pDictCode;
}

/**
 * 复用子字典项
 */
function enableSubDict(dictId) {
    var pDictCode = $("#main_dict_select").val();
    window.location.href = window.webContextPath
        + "/dict/set_sub_dict_status?use_status=1&dict_id="
        + dictId + "&rd=" + Math.random() + "&p_dict_code=" + pDictCode;
}

/**
 * 加载主字典项
 */
function loadMainDict() {
    $.ajax({
        type: "GET",
        url: window.webContextPath + "/dict/load_main_dict",
        dataType: "json",
        success: function (jsonData, textStatus) {
            if (0 != jsonData.status) {
                alert("加载主字典项失败[" + jsonData.message + "]");
                return;
            }
            var mainDicts = jsonData.data;
            if (mainDicts == null || 0 == mainDicts.length) {
                alert("未加载到主字典项信息!");
            }
            $.each(mainDicts, function () {
                var dictCode = this.dictCode;
                var dictName = this.dictName;
                $("#main_dict_select").append(
                    getOptionHTML(dictCode, dictName));
            });
            if ('' != pDictCode) {
                $("#main_dict_select option[value='" + pDictCode + "']").attr("selected", true);
            }

        },
        error: function () {
            alert("加载主字典项失败!");
        }
    });

}

$(function () {
    loadMainDict();
    $("#main_dict_select").change(function () {
        clearSubDictRows();
        var pDictCode = $("#main_dict_select").val();
        if ("" != pDictCode) {
            loadSubDict(pDictCode);
        }
    });
    if ("" != pDictCode) {
        loadSubDict(pDictCode);
    }
});

/**
 * 加载子字典项
 */
function loadSubDict(pDictCode) {
    $
        .ajax({
            type: "GET",
            url: window.webContextPath + "/dict/load_sub_dict",
            data: {
                p_dict_code: pDictCode
            },
            dataType: "json",
            success: function (jsonData, textStatus) {
                if (0 != jsonData.status) {
                    alert("加载子字典项失败[" + jsonData.message + "]");
                    return;
                }
                var subDicts = jsonData.data;
                var len = subDicts.length;
                if (len > 0) {
                    var tempHTML = $("#rowTemp").clone(true).removeAttr(
                        "id").css("display", "inline").html();
                    for (var index = 0; index < len; index++) {
                        var rowHTML = tempHTML;
                        var dict = subDicts[index];
                        var dictName = dict.dictName;
                        var dictId = dict.dictId;
                        var dictCode = dict.dictCode;
                        var useStatus = dict.useStatus;
                        rowHTML = rowHTML.replace(/dictId/g, dictId);
                        rowHTML = rowHTML.replace("dictName", dictName);
                        rowHTML = rowHTML.replace("dictCode", dictCode);
                        if (1 == useStatus) {
                            rowHTML = rowHTML.replace("useStatus", "使用中");
                        } else if (2 == useStatus) {
                            rowHTML = rowHTML.replace("useStatus", "已停用");
                        } else {
                            rowHTML = rowHTML.replace("useStatus", "未知状态");
                        }
                        $("tbody").append("<tr>" + rowHTML + "</tr>");
                        if (1 == useStatus) {
                            $("tbody tr:last").find("button:last").attr(
                                "disabled", true);
                        } else if (2 == useStatus) {
                            $("tbody tr:last").find("button:eq(1)").attr(
                                "disabled", true);
                        }

                    }

                } else {
                    $("tbody")
                        .append(
                            "<tr><td style='text-align:center ;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;word-break:keep-all;' nowrap='nowrap' colspan='4' ><span style='color:#FF0000'>未查询到任何子字典项数据</span></td></tr>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("加载子字典项失败!");
            }
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

function getDictLogoHTML(dictLogo) {
    var logoHTML = "<img src='dictLogo'  alt='字典项LOGO' width='30' heigth='30'/>";
    logoHTML = logoHTML.replace("dictLogo", dictLogo);
    return logoHTML;
}

/**
 * 清空子字典项表格行
 */
function clearSubDictRows() {
    $("tbody>tr:gt(0)").each(function () {
        // 删除行
        $(this).remove();
    });
}