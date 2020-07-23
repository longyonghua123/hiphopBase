$(document).ready(function () {
    //检索类型选择
    $(".searchoption li").click(
        function () {
            $(this).siblings("li").children().removeClass("curr");
            $(this).children().addClass("curr");
            $("#hd_searchtype").val($(this).children().html());
        });

        //赏析列表滚动
        $("#s1").xslider({
            unitdisplayed: 4,
            movelength: 1,
            unitlen: 470,
            autoscroll: 3000
        });
});