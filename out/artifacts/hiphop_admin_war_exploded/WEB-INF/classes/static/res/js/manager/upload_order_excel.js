/**
 * 上传订单数据
 */
function uploadOrderExcel() {
	clearAlertMsg();
	var excelFile = $("#upload_order_excel")[0].files;
	var length = excelFile.length;
	if (0 == length) {
		showAlertMsg("未选择电子表格文件!");
		return;
	}
	var fileName = excelFile[0].name;
	if (!(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
		showAlertMsg("文件格式必须为Excel表格");
		window.setTimeout("clearAlertMsg()", 1000);
		return;
	}
	var excelNameFormat = /^\d{12}.(xls|xlsx)$/;
	if (!excelNameFormat.test(fileName.toLowerCase())) {
		showAlertMsg("订单Excel文件名称格式错误,必须由12个数字组成");
		window.setTimeout("clearAlertMsg()", 1000);
		return;
	}

	$('#uploadModal').modal({
		backdrop : false
	});
	$("#upload_order_excel_form").ajaxSubmit({
		type : "POST",
		url : window.contextPath + "/order/upload_order_excel",
		dataType : "json",
		success : function(jsonData) {
			$('#uploadModal').modal('hide');
			var status = jsonData.status;
			if (1 == status) {
				showAlertMsg(jsonData.message);
			} else if (0 == status) {
				var data = jsonData.data;
				switch (data) {
				case 1:
					showAlertMsg("上传成功,并成功导入数据.3秒后跳转订单查询页面",Alerts.success);
					window.setTimeout("queryOrder()",3000);
					break;
				case 0:
					showAlertMsg("上传成功,表格无数据");
					break;
				default:
					showAlertMsg("服务器返回未知状态");
				}

			} else if (500 == status) {
				showAlertMsg("服务器运行异常");
			} else {
				showAlertMsg("服务器返回未知状态");
			}
			window.setTimeout("clearAlertMsg()",3000);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('#uploadModal').modal('hide');
			showAlertMsg("上传文件异常[" + textStatus + "]");
			window.setTimeout("clearAlertMsg()", 3000);
		}
	});
}
/**
 * 注册事件
 */
$(function() {
	$("#upload_order_excel").change(function() {
		uploadOrderExcel();
	});
	// 上传小图按钮
	$("#upload_order_button").click(function() {
		$("#upload_order_excel").click();
	});
});
/**
 * 查询订单
 */
function    queryOrder()
{
	window.location.href=window.contextPath +"/order/query_offine_order";
}