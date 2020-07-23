/**
 *加载可以操作的权限菜单
 */
function loadPowerMenu() {
    var tempHTML = $("#rowTemp").clone(true).removeAttr("id").css(
        "display", "block").html();
    $.ajax({
        type: "post",
        url: window.contextPath + "/manager/load_power_menu",
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 != jsonData.status) {
                showAlertMsg("加载权限菜单失败[" + jsonData.message + "]");
                window.setTimeout("clearAlertMsg()", 1000);
                return;
            }
            var powerMenus = jsonData.data;
            $.each(powerMenus, function() {
                var powerMenuRowHTML = tempHTML;
                var menu_name = this.menu_name;
                var sub_menus = this.sub_menus;
                var len = sub_menus.length;
                var power_menu_checkboxs = "";
                for(var index = 0; index < len; index++) {
                    var sub_menu = sub_menus[index];
                    power_menu_checkboxs += getPowerMenuCheckbox(this.main_menu_id,sub_menu.sub_menu_id,sub_menu.menu_name );
                }
                powerMenuRowHTML = powerMenuRowHTML.replace("menu_name", menu_name);
                powerMenuRowHTML = powerMenuRowHTML.replace("power_menu_checkboxs", power_menu_checkboxs);
                $("form:first").append("<div class='form-group'>" + powerMenuRowHTML + "</div>");
            });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            showAlertMsg("加载权限菜单失败[" + errorThrown + "]");
            window.setTimeout("clearAlertMsg()", 1000);
        }
    });
}
$(function() {
    loadPowerMenu();
    loadManagerUsers();
    $("#submit_button").click(function(){
    	setPowerMenu();
    });
});
/**
 *获取权限菜单多选框按钮
 */
function getPowerMenuCheckbox(main_menu_id,sub_menu_id,menu_name) {
    var chechboxHTML = "<label><input type='checkbox' name='menu_ids' value='{main_menu_id}-{sub_menu_id}'/>{menu_name}</label>&nbsp;&nbsp;";
    chechboxHTML = chechboxHTML.replace("{main_menu_id}", main_menu_id);
    chechboxHTML = chechboxHTML.replace("{sub_menu_id}", sub_menu_id);
    chechboxHTML = chechboxHTML.replace("{menu_name}", menu_name);
    return chechboxHTML;
}

/**
 *加载管理员用户
 */
function loadManagerUsers() {
    $.ajax({
        type: "post",
        url: window.contextPath + "/manager/load_manager_user",
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 != jsonData.status) {
                alert("加载管理员用户失败[" + jsonData.message + "]");
                return;
            }
            var managerUsers = jsonData.data;
            var len = managerUsers.length;
            if(len > 0) {
                for(var index = 0; index < len; index++) {
                    var managerUser = managerUsers[index];
                    var name = managerUser.name;
                    var manager_id = managerUser.manager_id;
                    $("#manager_select").append(getOptionHTML(manager_id, name));
                }
            } else {
                $("tbody").append("<tr><td style='text-align:center ;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;word-break:keep-all;' nowrap='nowrap' colspan='5' ><span style='color:#FF0000'>未查询到任何管理员用户数据</span></td></tr>");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("加载管理员用户失败[" + errorThrown + "]");
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

function setPowerMenu()
{
	 $("#submit_button").attr("disabled", true);
	var manager_id=$("#manager_select").val();
	if(""==manager_id)
	{
		 showAlertMsg("请选择管理员!");
         window.setTimeout("clearAlertMsg()", 1000);
         $("#submit_button").attr("disabled", false);
         return;
	}
	var selected=false;
	$(":checkbox[name='menu_ids']").each(function(){
		if($(this).is(":checked"))
		{ 
			selected=true;
			return false;
		}
	});
	if(!selected)
	{
		 showAlertMsg("请选择权限菜单!");
         window.setTimeout("clearAlertMsg()", 1000);
         $("#submit_button").attr("disabled", false);
         return;
	}
	if(window.confirm("确定要设置改用权限菜单吗?"))
	{
	 $("form:first").submit();
	}
	else
	{
		$("#submit_button").attr("disabled", false);
	}
}

function loadPowerMenuId(manager_id)
{ 
	$(":checkbox[name='menu_ids']").prop("checked",false);
	if(""==manager_id)
	{
		return ;
	}
	 $.ajax({
	        type: "post",
	        data:{manager_id:manager_id},
	        url: window.contextPath + "/manager/load_power_menu_id",
	        dataType: "json",
	        success: function(jsonData, textStatus) {
	            if(0 != jsonData.status) {
	                alert("加载管理员权限菜单信息失败[" + jsonData.message + "]");
	                return;
	            }
	            var menuIds = jsonData.data;
	            if(0==menuIds.length)
	            {
	            	 return;
	            }
	            $("#dml_type").val(1);
	            $.each(menuIds,function(){
	            	var menu_id=this;
	            	$(":checkbox[value='"+menu_id+"']").prop("checked",true);
	            });
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert("加载管理员权限菜单信息失败[" + errorThrown + "]");
	        }
	    });
}