function register_validation(form, at) {
	form.validate({
		onfocusin: false,
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		showErrors: function(errorMap, errorList) {
			var VALIDATION_ALERT = 'validation-alert';
			var alert = form.data(VALIDATION_ALERT);
			if (!alert) {
				alert = $('<div></div>').addClass('alert').addClass('alert-danger').attr('role', 'alert').prependTo(at);
				form.data(VALIDATION_ALERT, alert);
			}

			alert.empty().hide();
			if (errorList.length == 0) {
				return;
			}

			var $ul = $('<ul></ul>');
			for (var i = 0; i < errorList.length; i++) {
				// TODO better message
				$ul.append($('<li></li>').text($(errorList[i].element).parent().prev().text() + ":" + errorList[i].message));
			};
			$ul.appendTo(alert);
			alert.show();
			form.trigger('validation-error');
		}
	});
	// $.validator.messages = {
	// 	required: "不能为空.",
	// 	remote: "请修正该字段.",
	// 	email: "请输入正确格式的电子邮件.",
	// 	url: "请输入合法的网址.",
	// 	date: "请输入合法的日期.",
	// 	dateISO: "请输入合法的日期( ISO ).",
	// 	number: "请输入合法的数字.",
	// 	digits: "只能输入整数.",
	// 	creditcard: "请输入合法的信用卡号.",
	// 	equalTo: "请再次输入相同的值.",
	// 	maxlength: $.validator.format("请输入一个 长度最多是 {0} 的字符串."),
	// 	minlength: $.validator.format("请输入一个 长度最少是 {0} 的字符串."),
	// 	rangelength: $.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串."),
	// 	range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值."),
	// 	max: $.validator.format("请输入一个最大为{0} 的值."),
	// 	min: $.validator.format("请输入一个最小为{0} 的值."),

	// 	datecompare: '结束时间必须大于开始时间!'
	// };

	var messages = {
		required: "不能为空.",
		remote: "请修正该字段.",
		email: "请输入正确格式的电子邮件.",
		url: "请输入合法的网址.",
		date: "请输入合法的日期.",
		dateISO: "请输入合法的日期( ISO ).",
		number: "请输入合法的数字.",
		digits: "只能输入整数.",
		creditcard: "请输入合法的信用卡号.",
		equalTo: "请再次输入相同的值.",
		maxlength: $.validator.format("请输入一个 长度最多是 {0} 的字符串."),
		minlength: $.validator.format("请输入一个 长度最少是 {0} 的字符串."),
		rangelength: $.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串."),
		range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值."),
		max: $.validator.format("请输入一个最大为{0} 的值."),
		min: $.validator.format("请输入一个最小为{0} 的值.")
	};
	for ( var name in messages ) {
		$.validator.messages[name] = messages[name];
	}
}