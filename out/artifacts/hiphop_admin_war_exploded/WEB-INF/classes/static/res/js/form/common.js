/**
 * 通用js
 */
function getOffset(e) {
    var target = e.target;
    if (target.offsetLeft == undefined) {
        target = target.parentNode;
    }
    var pageCoord = getPageCoord(target);
    var eventCoord = {
        x: window.pageXOffset + e.clientX,
        y: window.pageYOffset + e.clientY
    };
    var offset = {
        offsetX: eventCoord.x - pageCoord.x,
        offsetY: eventCoord.y - pageCoord.y
    };
    return offset;
}

function getPageCoord(element) {
    var coord = {
        x: 0,
        y: 0
    };
    while (element) {
        coord.x += element.offsetLeft;
        coord.y += element.offsetTop;
        element = element.offsetParent;
    }
    return coord;
}

function getOffsetandXY(evt) {
    var evt = evt ? evt : (window.event ? window.event : null);
    var offsetXY = {
        X: evt.x || evt.clientX,
        Y: evt.y || evt.clientY,
        offsetX: evt.offsetX || getOffset(evt).offsetX,
        offsetY: evt.offsetY || getOffset(evt).offsetY
    };
    return offsetXY;
}

function filterTextareaVal(value) {
    value = value.replace(/\s*/g, "");
    value = value.replace(/[\u3000]/g, "");
    if ("" == value) {
        return "";
    }
    value = CtoH(value);
    value = value.replace(/\r\n/g, "");
    value = value.replace(/&nbsp;/g, "");
    return value;
}

function filterTextVal(value) {
    value = value.replace(/\s*/g, "");
    value = value.replace(/[\u3000]/g, "");
    if ("" == value) {
        return "";
    }
    value = CtoH(value);
    value = value.replace(/&nbsp;/g, "");
    return value;
}

function CtoH(str) {
    var length = str.length;
    var result = "";
    for (var i = 0; i < length; i++) {
        if (str.charCodeAt(i) == 12288) {
            result += String.fromCharCode(str.charCodeAt(i) - 12256);
            continue;
        }
        if (str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375) {
            result += String.fromCharCode(str.charCodeAt(i) - 65248);
        } else {
            result += String.fromCharCode(str.charCodeAt(i));
        }
    }
    return result;
}

Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    var week = {
        "0": "\u65e5",
        "1": "\u4e00",
        "2": "\u4e8c",
        "3": "\u4e09",
        "4": "\u56db",
        "5": "\u4e94",
        "6": "\u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt
            .replace(
                RegExp.$1,
                ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
                    : "\u5468")
                    : "")
                + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

/**
 * 过滤form表单input输入项和文本域textarea中的空格、全角字符
 */
function filterFormInput() {
    if (0 == $("form").size()) {
        return;
    }
    $("form:first").find("input").each(function () {
        var type = $(this).attr("type");
        var required = true; // 是否需要过滤
        switch (type) {
            case "button":
            case "reset":
            case "submit":
            case "hidden":
            case "checkbox":
            case "radio":
            case "color":
            case "number":
            case "range":
            case "image":
            case "file":
            case "date":
            case "datetime":
            case "datetime-local":
            case "month":
            case "week":
            case "time":
                required = false;
                break;
            default:
                break;
        }
        if (required) {
            var val = $(this).val();
            if ("" != val) {
                $(this).val(filterTextVal(val));
            }
        }
    });
    $("form:first").find("textarea").each(function () {
        var val = $(this).val();
        if ("" != val) {
            $(this).val(filterTextareaVal(val));
        }
    });
}

/**
 * 禁用form所有元素
 */
function disabledForm() {
    $("form input").attr({disabled: true});
    $("form select").attr({disabled: true});
    $("form textarea").attr({disabled: true});
    $("form button").attr({disabled: true});
}

/**
 * 验证密码
 * 密码必须是8-16位数字、大、小写字母、特殊符号中的三种及三种以上的组合！
 * @param password
 * @returns {boolean}
 */
function isValidPassword(password) {
    var num = 0;
    var rule1 = /\d+/;
    var rule2 = /[a-z]+/;
    var rule3 = /[A-Z]+/;
    var rule4 = /[~!@#\$%^&*\{\};,.\?\/'"]/;
    var rule5 = /^.{8,16}$/;
    var flag1 = rule1.test(password);
    var flag2 = rule2.test(password);
    var flag3 = rule3.test(password);
    var flag4 = rule4.test(password);
    var flag5 = rule5.test(password);
    if (flag1) {
        num = num + 1;
    }
    if (flag2) {
        num = num + 1;
    }
    if (flag3) {
        num = num + 1;
    }
    if (flag4) {
        num = num + 1;
    }
    if (!(num > 2 && flag5)) {
        return false;
    }
    return true;
}