/**
 *加载商品信息
 */
function loadProductInfo(product_id) {
    $.ajax({
        type: "post",
        url: window.contextPath + "/product/info",
        data: {
            product_id: product_id
        },
        dataType: "json",
        success: function(jsonData, textStatus) {
            if(0 == jsonData.status) {
                var productJSON = jsonData.data;
                setProductInfo(productJSON);
            } else {
                alert(jsonData.message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("加载商品信息失败[" + errorThrown + "]");
        }
    });
}
/**
 * 设置商品信息
 */
function setProductInfo(productJSON) {
    if(!("product_id" in productJSON)) {
        alert("该商品不存在");
        return;
    }
    $("#product_name").text(productJSON.product_name);
    $("#brand_name").text(productJSON.brand_name);
    $("#category").text(productJSON.category.sup_cate_name + ">" + productJSON.category.sub_cate_name);
    var plat_price = Number(productJSON.plat_price);
    $("#plat_price").html("&yen" + Math.round(plat_price / 10) / 10 + "元");
    var sale_statustext;
    switch(Number(productJSON.sale_status)) {
        case 1:
            sale_statustext = "正常";
            break;
        case 2:
            sale_statustext = "打折";
            break;
        case 3:
            sale_statustext = "抢购";
            break;
        case 4:
            sale_statustext = "促销";
            break;
        default:
            sale_statustext = "未知";
            break;
    }
    $("#sale_status").html(sale_statustext);
    var action_price = "action_price" in productJSON ? productJSON.action_price : "";
    if("" == action_price) {
        $("#action_price").html("<span style='color:#FF0000'>未设置<span>");
    } else {
        $("#action_price").html("&yen" + Math.round(action_price / 10) / 10 + "元");
    }
    var releaseText;
    switch(Number(productJSON.release_status)) {
        case 0:
            releaseText = "未发布";
            break;
        case 1:
            releaseText = "已发布";
            break;
        case 2:
            releaseText = "已下架";
            break;
        default:
            releaseText = "未知状态";
            break;
    }
    $("#release_statustext").text(releaseText);
    var specs = productJSON.specs;
    var len = specs.length;
    if(0 == len) {
        $("#specs").html("<span style='color:#FF0000'>未设定<span>");
    } else {
        for(var index = 0; index < len; index++) {
            var s = specs[index];
            var specs_name = s.specs_name;
            var specs_values = s.specs_values;
            var ulHTML = "<ul>";
            ulHTML += "<li>" + specs_name + "</li>";
            var count = specs_values.length; //规格属性值总数量
            for(var c = 0; c < count; c++) {
                ulHTML += "<li><span class='label label-info'>" + specs_values[c] + "</span></li>";
            }
            ulHTML += "</ul>";
            $("#specs").append(ulHTML);
            $("#specs ul:last").find("li").last().css({
                float: "none"
            });
        }
    }
    //商品封面图
    var cover_img_url = ("cover_img_url" in productJSON) ? productJSON.cover_img_url : "";
    if("" == cover_img_url) {
        $("#cover_img_url").html("<span style='color:#FF0000'>未设置商品封面图片<span>");
    } else {
        $("#cover_img_url").html(getImgLiHTML(cover_img_url, "点击查看原始图片"));
    }
    //商品小图
    setProductImg(productJSON.min_imgs, "min_imgs", "未设置商品小图", "点击查看原始图片");
    //设置商品大图
    setProductImg(productJSON.max_imgs, "max_imgs", "未设置商品大图", "点击查看原始图片");
    //设置商品标图
    setProductImg(productJSON.norm_imgs, "norm_imgs", "未设置商品标图", "点击查看原始图片");
    //设置商品详情图
    setProductImg(productJSON.desc_imgs, "desc_imgs", "未设置商品详情图", "点击查看原始图片");
}

/**
 * 
 * 获取图片列表LI innerHTML
 * @param imgUrl 图片URL
 * @param imgInfo 图片相关信息
 */
function getImgLiHTML(imgUrl, imgInfo) {
    var liHTML = "<a href='img_url' target='_blank'><img width='60' height='60' src='img_url' alt='未找到图片' title='img_info' lass='img-rounded' /></a>";
    imgUrl=window.imgBasePath +"imgs"+imgUrl;
    liHTML = liHTML.replace(/img_url/g,imgUrl);
    liHTML = liHTML.replace("img_info", imgInfo);
    return liHTML;
}
/**
 * 设置商品图片
 * @param {Object} imgUrls 图片URL
 * @param {Object} container 图片容器即div
 * @param {Object} notSetMsg 未设置图片提示信息
 * @param {Object} imgInfo  图片相关信息
 */
function setProductImg(imgUrls, container, notSetMsg, imgInfo) {
    var len = imgUrls.length;
    if(0 == len) {
        $("#" + container).html("<span style='color:#FF0000'>" + notSetMsg + "<span>");
    } else {
        var ulHTML = "<ul>";
        for(var index = 0; index < len; index++) {
            var imgUrl = imgUrls[index];
            ulHTML += getImgLiHTML(imgUrl, imgInfo);
        }
        ulHTML += "</ul>";
        $("#" + container).html(ulHTML);
    }
}