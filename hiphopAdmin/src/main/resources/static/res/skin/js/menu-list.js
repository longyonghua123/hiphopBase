/**
 * 菜单主界面
 */
function getMenuHTML(menuJson,basePath)
{
    var htmlTemp='<dd><div class="title"><span><img src="images/leftico03.png"/></span>#{main_menu_name}</div> <ul class="menuson">#{sub_menu_lis}</ul></dd>';
    var main_menu_name=menuJson.menu_name;
    htmlTemp=htmlTemp.replace("#{main_menu_name}",main_menu_name);
    var sub_menus=menuJson.sub_menus;
    var sub_menu_lis="";
    var len=sub_menus.length;
    for(var index=0;index<len;index++)
    {
        var sub_menu_li='<li><cite></cite><a href="#{menu_url}">#{sub_menu_name}</a><i></i></li>';
        sub_menu=sub_menus[index];
        var menu_url=sub_menu.menu_url;
        var sub_menu_name=sub_menu.menu_name;
        sub_menu_li=sub_menu_li.replace("#{menu_url}",basePath+menu_url);
        sub_menu_li=sub_menu_li.replace("#{sub_menu_name}",sub_menu_name);
        sub_menu_lis+=sub_menu_li;
    }
     htmlTemp=htmlTemp.replace("#{sub_menu_lis}",sub_menu_lis);
     return htmlTemp;
}
