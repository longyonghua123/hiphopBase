/**
 *
 */
function showAddSubMenuDialog() {
    $.ajax({
        type: "post",
        url: window.webContextPath + "/page/menu/add_sub_menu",
        dataType: "html",
        success: function (data, textStatus) {
            $("#subMenuDialog").html(data);
            $("#subMenuDialog").modal();
        },
        error: function () {
        }
    });
}

function showUpdateSubMenuDialog(menuId) {
    $.ajax({
        type: "post",
        data: {
            menu_id: menuId
        },
        url: window.webContextPath + "/page/menu/update_sub_menu",
        dataType: "html",
        success: function (data, textStatus) {
            $("#subMenuDialog").html(data);
            $("#subMenuDialog").modal();
        },
        error: function () {
        }
    });
}

/**
 * 停用子菜单
 */
function disableSubMenu(menuId) {
    var pMenuId = $("#main_menu_select").val();
    window.location.href = window.webContextPath
        + "/menu/set_sub_menu_status?use_status=2&menu_id="
        + menuId + "&rd=" + Math.random() + "&p_menu_id=" + pMenuId;
}

/**
 * 复用子菜单
 */
function enableSubMenu(menuId) {
    var pMenuId = $("#main_menu_select").val();
    window.location.href = window.webContextPath
        + "/menu/set_sub_menu_status?use_status=1&menu_id="
        + menuId + "&rd=" + Math.random() + "&p_menu_id=" + pMenuId;
}

/**
 * 加载主菜单
 */
function loadMainMenu() {
    $.ajax({
        type: "GET",
        url: window.webContextPath + "/menu/load_main_menu",
        dataType: "json",
        success: function (jsonData, textStatus) {
            if (0 != jsonData.status) {
                alert("加载主菜单失败[" + jsonData.message + "]");
                return;
            }
            var mainMenus = jsonData.data;
            if (mainMenus == null || 0 == mainMenus.length) {
                alert("未加载到主菜单信息!");
            }
            $.each(mainMenus, function () {
                var menuId = this.menuId;
                var menuName = this.menuName;
                $("#main_menu_select").append(
                    getOptionHTML(menuId, menuName));
            });
            if ('' != pMenuId) {
                $("#main_menu_select option[value='" + pMenuId + "']").attr("selected", true);
            }

        },
        error: function () {
            alert("加载主菜单失败!");
        }
    });

}

$(function () {
    loadMainMenu();
    $("#main_menu_select").change(function () {
        clearSubMenuRows();
        var pMenuId = $("#main_menu_select").val();
        if ("" != pMenuId) {
            loadSubMenu(pMenuId);
        }
    });
    if ("" != pMenuId) {
        loadSubMenu(pMenuId);
    }
});

/**
 * 加载子菜单
 */
function loadSubMenu(pMenuId) {
    $
        .ajax({
            type: "GET",
            url: window.webContextPath + "/menu/load_sub_menu",
            data: {
                p_menu_id: pMenuId
            },
            dataType: "json",
            success: function (jsonData, textStatus) {
                if (0 != jsonData.status) {
                    alert("加载子菜单失败[" + jsonData.message + "]");
                    return;
                }
                var subMenus = jsonData.data;
                var len = subMenus.length;
                if (len > 0) {
                    var tempHTML = $("#rowTemp").clone(true).removeAttr(
                        "id").css("display", "inline").html();
                    for (var index = 0; index < len; index++) {
                        var rowHTML = tempHTML;
                        var menu = subMenus[index];
                        var menuName = menu.menuName;
                        var menuId = menu.menuId;
                        var menuLogo = menu.menuLogo;
                        var menuUrl = menu.menuUrl;
                        var useStatus = menu.useStatus;
                        rowHTML = rowHTML.replace(/menuId/g,menuId);
                        rowHTML = rowHTML.replace("menuName", menuName);
                        rowHTML = rowHTML.replace("menuUrl", menuUrl);
                        if ("" == menuLogo) {
                            rowHTML = rowHTML.replace("menuLogo", "未设置");
                        } else {
                            rowHTML = rowHTML.replace("menuLogo",
                                getMenuLogoHTML(menuLogo));
                        }
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
                            "<tr><td style='text-align:center ;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;word-break:keep-all;' nowrap='nowrap' colspan='5' ><span style='color:#FF0000'>未查询到任何子菜单数据</span></td></tr>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("加载子菜单失败!");
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

function getMenuLogoHTML(menuLogo) {
    var logoHTML = "<img src='menuLogo'  alt='菜单LOGO' width='30' heigth='30'/>";
    logoHTML = logoHTML.replace("menuLogo", menuLogo);
    return logoHTML;
}

/**
 * 清空子菜单表格行
 */
function clearSubMenuRows() {
    $("tbody>tr:gt(0)").each(function () {
        // 删除行
        $(this).remove();
    });
}