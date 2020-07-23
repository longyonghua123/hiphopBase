/**
 * @function 替换常用HTML特殊字符 如:&amp;替换成&
 * @param a -
 *            字符串
 */
function replaceSpecialChars(url) {
    url = url.replace(/&amp;/g, '&')
    return url;
}