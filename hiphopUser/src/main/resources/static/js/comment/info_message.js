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

function Alerts() {
}
Alerts.success = "alert alert-success";
Alerts.info = "alert alert-info";
Alerts.warning = "alert alert-warning";
Alerts.danger = "alert alert-danger";
/**
 * 提示错误信息
 */
function showAlertMsg(message, alertType) {
	alertType=alertType||Alerts.warning;
	var alertHTML = "<div  class='{alertType}'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>警告！</strong>{message}</div>";
	alertHTML = alertHTML.replace("{alertType}", alertType);
	alertHTML = alertHTML.replace("{message}", message);
	$("#alertMessage").append(alertHTML);
}
