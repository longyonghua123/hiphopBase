/**
 * 重新设置上移或下移操作权限
 * 
 * @param ul
 */
function resetMovePower() {	
  $("tbody tr:last button:last").attr("disabled",true);
  $("tbody tr:first button:first").attr("disabled",true);
  $("tbody tr:last").prev().find("button:last").attr("disabled",false);
  $("tbody tr:first").next().find("button:first").attr("disabled",false);
}
/**
 * 向上移动 button
 */
function moveUp(button) {
    var currentTR = $(button).parent().parent();
    $(currentTR).prev().insertAfter(currentTR);
    resetMovePower();
    resetMenuIndex();
}

/**
 * 向下移动 button
 */
function moveDown(button) {
    var currentTR = $(button).parent().parent()[0];
    $(currentTR).insertAfter($(currentTR).next());
    resetMovePower();
    resetMenuIndex();
}
/**
 * 重新设置菜单索引
 */
function resetMenuIndex()
{
	$("tbody").find("input[name='menu_indexes']").each(function(i){
		$(this).val(i+1);
	});
}

/**
 *加载主菜单
 */
function loadMainMenu() {
    $.ajax({
        type: "post",
        url: window.contextPath + "/manager/load_main_menu",
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 != jsonData.status) {
                alert("加载主菜单失败[" + jsonData.message + "]");
                return;
            }
            var mainMenus = jsonData.data;
            var len = mainMenus.length;
            if(0 == len) {
                alert("未加载到主菜单信息!");
            }
            $.each(mainMenus, function() {
                var main_menu_id = this.main_menu_id;
                var menu_name = this.menu_name;
                var menu_index = this.menu_index;
                var menu_logo = this.menu_logo;
                var menu_url = this.menu_url;
                var status = this.status;
                var trHTML = "<tr>";
                trHTML += getTableCellHTML(getCurrentMenuIndex(menu_index,main_menu_id), false,0);
                trHTML += getTableCellHTML("<input type='text' value='" + menu_index + "' disabled='disabled' class='menu_index_text'/>", false,0);
                trHTML += getTableCellHTML(menu_name, true,1);
                trHTML += getTableCellHTML(menu_url, true,1);
                trHTML += getTableCellHTML(getLOGOImg(menu_logo), false,0);
                trHTML += getTableCellHTML(getStatusText(status), true,0);
                trHTML += getTableCellHTML(getActionButton(), false,0);
                trHTML + "</tr>";
                $("tbody").append(trHTML);
            });
            resetMovePower();
        },
        error: function() {
            alert("加载主菜单失败!");
        }
    });
}
function getCurrentMenuIndex(menu_index,main_menu_id)
{
 var  tdHTML="<input type='text' name='menu_indexes' value='#{menu_index}#' readonly='readonly' class='menu_index_text'/><input type='hidden' name='main_menu_ids' value='#{main_menu_id}#'/>";
 tdHTML=tdHTML.replace("#{menu_index}#", menu_index);
 tdHTML=tdHTML.replace("#{main_menu_id}#", main_menu_id);
 return tdHTML;
}
/**
 * 获取"上移"或"下移"按钮HTML元素内容
 * @returns {String}
 */
function getActionButton(){
	return "<button type='button' class='btn btn-link' onclick='moveUp(this)'>上移</button><button type='button' class='btn btn-link' onclick='moveDown(this)'>下移</button>";
}
/**
 * 获取菜单LOGO图像
 * @param menu_logo
 * @returns {String}
 */
function getLOGOImg(menu_logo) {
    if("" != menu_logo) {
        return "<img src='" + menu_logo + "' width='30' height='30' alt='图片加载失败'/>";
    } else {
        return "<img  width='60' height='30' alt='未设置' title='未设置'/>";
    }
}
/**
 * 获取菜单状态文本
 * @param status
 * @returns {String}
 */
function getStatusText(status) {
    switch(status) {
        case 1:
            return "使用中";
        case 0:
            return "已停用";
        default:
            return "未知状态";
    }
}
/**
 * 获取表格单元格HTML内容
 * @param html
 * @param isText 是否为文本
 * @param align  对其方式 1左对齐;0居中;2右对齐
 * @returns
 */
function getTableCellHTML(html, isText,align) {
    var tdHTML = "<td class='record_td' nowrap='nowrap'  style='text-align:#{align}#'>html</td>";
    switch (align) {
	case 1:
		tdHTML=tdHTML.replace("#{align}#", "left");
		break;
	case 0:
		tdHTML=tdHTML.replace("#{align}#", "center");
		break;
	case 2:
		tdHTML=tdHTML.replace("#{align}#", "right");
		break;
	default:
		break;
	}
    if(isText) {
        return tdHTML.replace(/html/g, "<span>" + html + "</span>");
    } else {
        return tdHTML.replace(/html/g, html);
    }
}
$(function() {
    loadMainMenu();
});

/**
 * 修改主菜单信息
 */
function setMainMenuIndex() {
    $("#submit_button").attr("disabled", true);
    var isUpdated = false;
    $("tbody tr").each(function(i) {
        var currentIndex = i + 1; //当前索引序号
        var oldIndex = $(this).find(":text:eq(1)").val(); //原来索引序号
        if(currentIndex != oldIndex) {
            isUpdated = true;
            return false;
        }
    });
    if(!isUpdated) {
        alert("主菜单索引未修改!");
        $("#submit_button").attr("disabled", false);
        return;
    }
    if(window.confirm("确定修改主菜单信息吗?")) {
        $("form:first").submit();
    } else {
        $("#submit_button").attr("disabled", false);
    }
}